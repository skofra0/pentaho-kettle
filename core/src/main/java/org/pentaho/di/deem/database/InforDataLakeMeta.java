package org.pentaho.di.deem.database;

/*
 * /*
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
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **************************************************************************
 */

import org.pentaho.di.core.Const;
import org.pentaho.di.core.database.BaseDatabaseMeta;
import org.pentaho.di.core.database.DatabaseInterface;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.plugins.DatabaseMetaPlugin;
import org.pentaho.di.core.row.ValueMetaInterface;

/**
 * Contains DataLake specific information through static final members
 * 
 */

@DatabaseMetaPlugin(type = "INFOR_DATALAKE", typeDescription = "Infor DataLake (Beta, download drivers)")
public class InforDataLakeMeta extends BaseDatabaseMeta implements DatabaseInterface {

    public static String DRIVER_CLASS = "com.infor.idl.jdbc.Driver";

    private static String driverClass = DRIVER_CLASS;

    @Override
    public int[] getAccessTypeList() {
        return new int[] {DatabaseMeta.TYPE_ACCESS_NATIVE};
    }

    @Override
    public int getDefaultDatabasePort() {
        return -1;
    }

    @Override
    public String getLimitClause(int nrRows) {
        return " LIMIT " + nrRows;
    }

    @Override
    public boolean supportsPreparedStatementMetadataRetrieval() {
        return false;
    }

    /**
     * Returns the minimal SQL to launch in order to determine the layout of the resultset for a given database table
     * 
     * @param tableName The name of the table to determine the layout for
     * @return The SQL to launch.
     */
    @Override
    public String getSQLQueryFields(String tableName) {
        return "SELECT * FROM " + tableName + " LIMIT 0";
    }

    @Override
    public String getSQLTableExists(String tablename) {
        return getSQLQueryFields(tablename);
    }

    @Override
    public String getSQLColumnExists(String columnname, String tablename) {
        return getSQLQueryColumnFields(columnname, tablename);
    }

    public String getSQLQueryColumnFields(String columnname, String tableName) {
        return "SELECT " + columnname + " FROM " + tableName + " LIMIT 0";
    }

    /**
     * @see org.pentaho.di.core.database.DatabaseInterface#getNotFoundTK(boolean)
     */
    @Override
    public int getNotFoundTK(boolean use_autoinc) {
        if (supportsAutoInc() && use_autoinc) {
            return 1;
        }
        return super.getNotFoundTK(use_autoinc);
    }

    @Override
    public String getDriverClass() {
        return driverClass;
    }

    @Override
    public String getURL(String hostname, String port, String databaseName) {
        if (getAccessType() == DatabaseMeta.TYPE_ACCESS_ODBC) {
            return "jdbc:odbc:" + databaseName;
        } else {
            return "jdbc:infordatalake://" + hostname;
        }
    }

    /**
     * @return The extra option separator in database URL for this platform (usually this is semicolon ; )
     */
    @Override
    public String getExtraOptionSeparator() {
        return "&";
    }

    /**
     * @return This indicator separates the normal URL from the options
     */
    @Override
    public String getExtraOptionIndicator() {
        return "?";
    }

    /**
     * @return true if the database supports transactions.
     */
    @Override
    public boolean supportsTransactions() {
        return false;
    }

    /**
     * @return true if the database supports bitmap indexes
     */
    @Override
    public boolean supportsBitmapIndex() {
        return false;
    }

    /**
     * @return true if the database supports views
     */
    @Override
    public boolean supportsViews() {
        return false;
    }

    /**
     * @return true if the database supports synonyms
     */
    @Override
    public boolean supportsSynonyms() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pentaho.di.core.database.DatabaseInterface#getReservedWords()
     */
    @Override
    public String[] getReservedWords() {
        return new String[] {"ADD", "ALL", "ALTER", "ANALYZE", "AND", "AS", "ASC", "ASENSITIVE", "BEFORE", "BETWEEN", "BIGINT", "BINARY", "BLOB", "BOTH", "BY", "CALL", "CASCADE", "CASE", "CHANGE", "CHAR", "CHARACTER", "CHECK", "COLLATE", "COLUMN", "CONDITION", "CONNECTION", "CONSTRAINT", "CONTINUE", "CONVERT", "CREATE", "CROSS", "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "CURSOR", "DATABASE", "DATABASES", "DAY_HOUR", "DAY_MICROSECOND", "DAY_MINUTE", "DAY_SECOND", "DEC",
                "DECIMAL", "DECLARE", "DEFAULT", "DELAYED", "DELETE", "DESC", "DESCRIBE", "DETERMINISTIC", "DISTINCT", "DISTINCTROW", "DIV", "DOUBLE", "DROP", "DUAL", "EACH", "ELSE", "ELSEIF", "ENCLOSED", "ESCAPED", "EXISTS", "EXIT", "EXPLAIN", "FALSE", "FETCH", "FLOAT", "FOR", "FORCE", "FOREIGN", "FROM", "FULLTEXT", "GOTO", "GRANT", "GROUP", "HAVING", "HIGH_PRIORITY", "HOUR_MICROSECOND", "HOUR_MINUTE", "HOUR_SECOND", "IF", "IGNORE", "IN", "INDEX", "INFILE", "INNER", "INOUT", "INSENSITIVE",
                "INSERT", "INT", "INTEGER", "INTERVAL", "INTO", "IS", "ITERATE", "JOIN", "KEY", "KEYS", "KILL", "LEADING", "LEAVE", "LEFT", "LIKE", "LIMIT", "LINES", "LOAD", "LOCALTIME", "LOCALTIMESTAMP", "LOCATE", "LOCK", "LONG", "LONGBLOB", "LONGTEXT", "LOOP", "LOW_PRIORITY", "MATCH", "MEDIUMBLOB", "MEDIUMINT", "MEDIUMTEXT", "MIDDLEINT", "MINUTE_MICROSECOND", "MINUTE_SECOND", "MOD", "MODIFIES", "NATURAL", "NOT", "NO_WRITE_TO_BINLOG", "NULL", "NUMERIC", "ON", "OPTIMIZE", "OPTION", "OPTIONALLY",
                "OR", "ORDER", "OUT", "OUTER", "OUTFILE", "POSITION", "PRECISION", "PRIMARY", "PROCEDURE", "PURGE", "READ", "READS", "REAL", "REFERENCES", "REGEXP", "RENAME", "REPEAT", "REPLACE", "REQUIRE", "RESTRICT", "RETURN", "REVOKE", "RIGHT", "RLIKE", "SCHEMA", "SCHEMAS", "SECOND_MICROSECOND", "SELECT", "SENSITIVE", "SEPARATOR", "SET", "SHOW", "SMALLINT", "SONAME", "SPATIAL", "SPECIFIC", "SQL", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "SQL_BIG_RESULT", "SQL_CALC_FOUND_ROWS",
                "SQL_SMALL_RESULT", "SSL", "STARTING", "STRAIGHT_JOIN", "TABLE", "TERMINATED", "THEN", "TINYBLOB", "TINYINT", "TINYTEXT", "TO", "TRAILING", "TRIGGER", "TRUE", "UNDO", "UNION", "UNIQUE", "UNLOCK", "UNSIGNED", "UPDATE", "USAGE", "USE", "USING", "UTC_DATE", "UTC_TIME", "UTC_TIMESTAMP", "VALUES", "VARBINARY", "VARCHAR", "VARCHARACTER", "VARYING", "WHEN", "WHERE", "WHILE", "WITH", "WRITE", "XOR", "YEAR_MONTH", "ZEROFILL"};
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.pentaho.di.core.database.DatabaseInterface#getStartQuote()
     */
    @Override
    public String getStartQuote() {
        return "\"";
    }

    /**
     * Simply add an underscore in the case of MySQL!
     * 
     * @see org.pentaho.di.core.database.DatabaseInterface#getEndQuote()
     */
    @Override
    public String getEndQuote() {
        return "\"";
    }

    /**
     * @param tableNames The names of the tables to lock
     * @return The SQL command to lock database tables for write purposes.
     */
    @Override
    public String getSQLLockTables(String[] tableNames) {
        String sql = "LOCK TABLES ";
        for (int i = 0; i < tableNames.length; i++) {
            if (i > 0) {
                sql += ", ";
            }
            sql += tableNames[i] + " WRITE";
        }
        sql += ";" + Const.CR;

        return sql;
    }

    /**
     * @param tableName The name of the table to unlock
     * @return The SQL command to unlock a database table.
     */
    @Override
    public String getSQLUnlockTables(String[] tableName) {
        return "UNLOCK TABLES"; // This unlocks all tables
    }

    @Override
    public boolean needsToLockAllTables() {
        return true;
    }

    /**
     * @return extra help text on the supported options on the selected database platform.
     */
    @Override
    public String getExtraOptionsHelpText() {
        return "http://infor.com";
    }

    @Override
    public String[] getUsedLibraries() {
        return new String[] {"idl-api-jdbc-2.0.26-RELEASE.jar"};
    }

    /**
     * @param tableName
     * @return true if the specified table is a system table
     */
    @Override
    public boolean isSystemTable(String tableName) {
        if (tableName.startsWith("sys")) {
            return true;
        }
        if (tableName.equals("dtproperties")) {
            return true;
        }
        return false;
    }

    /**
     * Get the SQL to insert a new empty unknown record in a dimension.
     * 
     * @param schemaTable the schema-table name to insert into
     * @param keyField The key field
     * @param versionField the version field
     * @return the SQL to insert the unknown record into the SCD.
     */
    @Override
    public String getSQLInsertAutoIncUnknownDimensionRow(String schemaTable, String keyField, String versionField) {
        return "insert into " + schemaTable + "(" + keyField + ", " + versionField + ") values (1, 1)";
    }

    /**
     * @param string
     * @return A string that is properly quoted for use in a SQL statement (insert, update, delete, etc)
     */
    @Override
    public String quoteSQLString(String string) {
        string = string.replaceAll("'", "\\\\'");
        string = string.replaceAll("\\n", "\\\\n");
        string = string.replaceAll("\\r", "\\\\r");
        return "'" + string + "'";
    }

    /**
     * Returns a false as Oracle does not allow for the releasing of savepoints.
     */
    @Override
    public boolean releaseSavepoint() {
        return false;
    }

    @Override
    public boolean supportsErrorHandlingOnBatchUpdates() {
        return true;
    }

    @Override
    public boolean isRequiringTransactionsOnQueries() {
        return false;
    }

    /**
     * @return true if Kettle can create a repository on this type of database.
     */
    @Override
    public boolean supportsRepository() {
        return false;
    }

    @Override
    public boolean supportsBatchUpdates() {
        return false;
    }

    @Override
    public String getModifyColumnStatement(String tablename, ValueMetaInterface v, String tk, boolean use_autoinc, String pk, boolean semicolon) {
        return null;
    }

    @Override
    public String getAddColumnStatement(String tablename, ValueMetaInterface v, String tk, boolean use_autoinc, String pk, boolean semicolon) {
        return null;
    }

    @Override
    public String getFieldDefinition(ValueMetaInterface v, String tk, String pk, boolean use_autoinc, boolean add_fieldname, boolean add_cr) {
        return null;
    }
}
