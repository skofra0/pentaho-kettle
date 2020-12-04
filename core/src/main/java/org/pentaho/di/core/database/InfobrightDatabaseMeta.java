/*
 * ! ******************************************************************************
 *
 * Pentaho Data Integration
 *
 * Copyright (C) 2002-2017 by Hitachi Vantara : http://www.pentaho.com
 *
 *******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************/

package org.pentaho.di.core.database;

import org.pentaho.di.core.Const;
import org.pentaho.di.core.row.ValueMetaInterface;

public class InfobrightDatabaseMeta extends MySQLDatabaseMeta implements DatabaseInterface {

    public static final String COMMENT_LOOKUP = " COMMENT \"LOOKUP\"";

    @Override
    public int getDefaultDatabasePort() {
        if (getAccessType() == DatabaseMeta.TYPE_ACCESS_NATIVE) {
            return 5029;
        }
        return -1;
    }

    @Override
    public void addDefaultOptions() {
        super.addDefaultOptions(); // SKOFRA
        addExtraOption(getPluginId(), "characterEncoding", "UTF-8");
    }

    public String getFieldDefinition(ValueMetaInterface v, String tk, String pk, boolean use_autoinc, boolean add_fieldname, boolean add_cr) {
        String retval = "";

        String fieldname = v.getName();
        int length = v.getLength();
        int precision = v.getPrecision();

        if (precision < 0) {
            precision = 0;
        }

        if (add_fieldname)
            retval += fieldname + " ";

        int type = v.getType();
        switch (type) {
            case ValueMetaInterface.TYPE_DATE:
                retval += "DATETIME";
                break;
            case ValueMetaInterface.TYPE_BOOLEAN:
                if (supportsBooleanDataType()) {
                    retval += "BOOLEAN" + COMMENT_LOOKUP;
                } else {
                    retval += "CHAR(1)" + COMMENT_LOOKUP;
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
                            if (length < 16) {
                                // can hold signed values between
                                // -9223372036854775808 and 9223372036854775807
                                // 18 significant digits
                                retval += "BIGINT(" + length + ")" + COMMENT_LOOKUP;
                            } else if (length < 19) {
                                // can hold signed values between
                                // -9223372036854775808 and 9223372036854775807
                                // 18 significant digits
                                retval += "BIGINT(" + length + ")";
                            } else {
                                // retval += "DECIMAL(" + length + ")"; SKOFRA
                                // INFOBRIGHT FIX
                                retval += "BIGINT(18) /* MAX LENGTH=18 */";
                            }
                        } else {
                            if (length > 0) {
                                retval += "INT(" + length + ")" + COMMENT_LOOKUP;
                            } else {
                                retval += "INT" + COMMENT_LOOKUP;
                            }
                        }
                    }
                    // Floating point values...
                    else {
                        // Infobright fix (do not support DECIMAL>18)
                        if (length < 19) {
                            if (length < 0) {
                                retval += "DOUBLE";
                            } else {
                                retval += "DOUBLE(" + length;
                                if (precision > 0) {
                                    retval += ", " + precision;
                                } else {
                                    retval += ", 0";
                                }
                                retval += ")";
                            }
                        } else {
                            // A double-precision floating-point number is accurate
                            // to approximately 15 decimal places.
                            // http://mysql.mirrors-r-us.net/doc/refman/5.1/en/numeric-type-overview.html
                            if (precision > 0) {
                                retval += "DOUBLE(" + length + ", " + precision + ")";
                            } else {
                                retval += "DOUBLE";
                            }
                        }
                    }
                }
                break;
            case ValueMetaInterface.TYPE_STRING:
                if (length > 0) {
                    if (length == 1)
                        retval += "CHAR(1)" + COMMENT_LOOKUP;
                    else if (length < 101)
                        retval += "VARCHAR(" + length + ")" + COMMENT_LOOKUP;
                    else if (length < 256)
                        retval += "VARCHAR(" + length + ")";
                    else if (length < 65536)
                        retval += "TEXT";
                    else if (length < 16777215)
                        retval += "MEDIUMTEXT";
                    else
                        retval += "LONGTEXT";
                } else {
                    if (length > 2000) {
                        retval += "TINYTEXT";
                    } else if (length > 255) {
                        retval += "VARCHAR(" + length + ")";
                    } else {
                        retval += "VARCHAR(255)";
                    }
                }
                break;
            case ValueMetaInterface.TYPE_BINARY:
                retval += "LONGBLOB";
                break;
            default:
                retval += " UNKNOWN";
                break;
        }

        if (add_cr)
            retval += Const.CR;

        return retval;
    }
}
