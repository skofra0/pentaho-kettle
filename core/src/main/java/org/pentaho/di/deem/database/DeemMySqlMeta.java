/*
 **************************************************************************
 *
 * Copyright 2020 - Deem
 *
 * Based upon code from Pentaho Data Integration
 *
 **************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **************************************************************************
 */

package org.pentaho.di.deem.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.database.DatabaseInterface;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.database.MySQLDatabaseMeta;
import org.pentaho.di.core.exception.KettleDatabaseException;
import org.pentaho.di.core.plugins.DatabaseMetaPlugin;
import org.pentaho.di.core.row.ValueMetaInterface;

@DatabaseMetaPlugin(type = "DEEM_MYSQL", typeDescription = "Deem MySQL")
public class DeemMySqlMeta extends MySQLDatabaseMeta implements DatabaseInterface {

    public static String DRIVER_CLASS_MARIADB = "org.mariadb.jdbc.Driver";
    public static String DRIVER_CLASS_MYSQL = "org.gjt.mm.mysql.Driver";

    private static String driverClass = "";
    
    @Override
    public String[] getUsedLibraries() {
        return new String[] {"mariadb-java-client-2.6.0.jar"};
    }

    @Override
    public int[] getAccessTypeList() {
        return new int[] {DatabaseMeta.TYPE_ACCESS_NATIVE};
    }

    @Override
    public String getDriverClass() {
        if (getAccessType() == DatabaseMeta.TYPE_ACCESS_ODBC) {
            return "sun.jdbc.odbc.JdbcOdbcDriver";
        } else {
            if (driverClass.isEmpty()) {
                try {
                    driverClass = DRIVER_CLASS_MARIADB;
                    Class.forName(driverClass);
                } catch (Exception e) {
                    driverClass = DRIVER_CLASS_MYSQL;
                }
            }
            return driverClass;
        }
    }

    @Override
    public String getFieldDefinition(ValueMetaInterface v, String tk, String pk, boolean use_autoinc, boolean add_fieldname, boolean add_cr) {
        String retval = "";

        String fieldname = v.getName();
        int length = v.getLength();
        int precision = v.getPrecision();

        if (add_fieldname)
            retval += fieldname + " ";

        int type = v.getType();
        switch (type) {
            case ValueMetaInterface.TYPE_TIMESTAMP:
            case ValueMetaInterface.TYPE_DATE:
                retval += "DATETIME";
                break;
            case ValueMetaInterface.TYPE_BOOLEAN:
                if (supportsBooleanDataType()) {
                    retval += "BOOLEAN";
                } else {
                    retval += "CHAR(1)";
                }
                break;

            case ValueMetaInterface.TYPE_NUMBER:
            case ValueMetaInterface.TYPE_INTEGER:
            case ValueMetaInterface.TYPE_BIGNUMBER:
                if (fieldname.equalsIgnoreCase(tk) || // Technical key
                        fieldname.equalsIgnoreCase(pk) // Primary key
                ) {
                    if (use_autoinc) {
                        retval += "BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY";
                    } else {
                        retval += "BIGINT NOT NULL PRIMARY KEY";
                    }
                } else {
                    // Integer values...
                    if (precision == 0) {
                        if (length > 9) {
                            if (length < 19) {
                                // can hold signed values between -9223372036854775808 and 9223372036854775807
                                // 18 significant digits
                                retval += "BIGINT(" + length + ")";
                            } else {
                                retval += "DECIMAL(" + length + ")";
                            }
                        } else {
                            if (length > 0) {
                                retval += "INT(" + length + ")";
                            } else {
                                retval += "INT";
                            }
                        }
                    }
                    // Floating point values...
                    else {
                        if (length > 15) {
                            retval += "DECIMAL(" + length;
                            if (precision > 0)
                                retval += ", " + precision;
                            retval += ")";
                        } else {
                            // A double-precision floating-point number is accurate to approximately 15 decimal places.
                            // http://mysql.mirrors-r-us.net/doc/refman/5.1/en/numeric-type-overview.html
                            retval += "DOUBLE";
                        }
                    }
                }
                break;
            case ValueMetaInterface.TYPE_STRING:
                if (length > 0) {
                    if (length == 1)
                        retval += "CHAR(1)";
                    else if (length < 256)
                        retval += "VARCHAR(" + length + ")";
                    else if (length < 65536)
                        retval += "TEXT";
                    else if (length < 16777216)
                        retval += "MEDIUMTEXT";
                    else
                        retval += "LONGTEXT";
                } else {
                    retval += "TINYTEXT";
                }
                break;
            case ValueMetaInterface.TYPE_BINARY:
                retval += "LONGBLOB";
                break;
            default:
                retval += " UNKNOWN";
                break;
        }

        if (add_cr) {
            retval += Const.CR;
        }
        return retval;
    }

    public int getDriverMajorVersion() {
        return 4;
    }

    public int getDriverMinorVersion() {
        return 1;
    }

    /**
     * @return true if the database is streaming results (normally this is an option just for MySQL).
     */
    @Override
    public boolean isStreamingResults() {
        return true;
    }
    
    /**
     * This method allows a database dialect to convert database specific data types to Kettle data types.
     *
     * @param resultSet
     *        The result set to use
     * @param valueMeta
     *        The description of the value to retrieve
     * @param index
     *        the index on which we need to retrieve the value, 0-based.
     * @return The correctly converted Kettle data type corresponding to the valueMeta description.
     * @throws KettleDatabaseException
     */
    // SKOFRA (Problem with newer MariaDB jdbc drivers Date/Timestamp)
    @Override
    public Object getValueFromResultSet(ResultSet rs, ValueMetaInterface val, int i) throws KettleDatabaseException {
        Object data = null;
        try {
            switch (val.getType()) {
                case ValueMetaInterface.TYPE_DATE:
                case ValueMetaInterface.TYPE_TIMESTAMP:
                    if (val.getOriginalColumnType() == java.sql.Types.DATE) {
                        return rs.getDate(i + 1);
                    } else if (val.getOriginalColumnType() == java.sql.Types.TIME) {
                        return rs.getTime(i + 1);
                    } else if (val.getPrecision() != 1 && supportsTimeStampToDateConversion()) {
                        data = rs.getTimestamp(i + 1);
                        break; // Timestamp extends java.util.Date
                    } else {
                        data = rs.getDate(i + 1);
                        break;
                    }
                default:
                    return val.getValueFromResultSet(this, rs, i);
            }
            if (rs.wasNull()) {
                data = null;
            }
        } catch (SQLException e) {
            throw new KettleDatabaseException("Unable to get value '" + val.toStringMeta() + "' from database resultset, index " + i, e);
        }

        return data;
    }


}
