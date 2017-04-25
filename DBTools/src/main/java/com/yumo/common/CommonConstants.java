package com.yumo.common;

public interface CommonConstants {


	public final static String YES = "YES";
	public final static String STR_TRUE = "TRUE";
	public final static String STR_FALSE = "FALSE";
	public final static String CHARACTER_ENCODING_UTF8 = "UTF-8";
	@SuppressWarnings("restriction")
	public final static String NEWLINE = java.security.AccessController.doPrivileged(
            new sun.security.action.GetPropertyAction("line.separator"));
	public final static String TAB = "    ";
	public final static String COMMA = ",";
	public final static String NULL = "NULL";
	public final static String SING_QUOT = "'";
	public final static String MYSQL_MARK_ST = "`";
	public final static String MYSQL_MARK_END = "`";
	public final static String MSSQL_MARK_ST = "[";
	public final static String MSSQL_MARK_END = "]";
	public final static String ORACLE_MARK_ST = "";
	public final static String ORACLE_MARK_END = "";

	/** DDL static define */
	public final static String SQL_FILE_NAME_DATA = "DATAS.sql";
	public final static String DATA_DEFINE_SHEET_NAME_START = "T-";
	public final static int DATA_CELL_NUM_START = 2;
	public final static int DATA_ROW_NUM_TABLE_ID = 2;
	public final static int DATA_CELL_NUM_TABLE_ID = 3;
	public final static int DATA_ROW_NUM_TABLE_NAME = 3;
	public final static int DATA_CELL_NUM_TABLE_NAME = 3;
	public final static int DATA_ROW_NUM_COL = 6;
	public final static int DATA_ROW_NUM_DATA_START = 8;
	/** DDL static define */

	/** DDL static define */
	public final static String SQL_FILE_NAME_DDL = "DDL.sql";
	public final static String DDL_DEFINE_SHEET_NAME_START = "T-";
	public final static int DDL_ROW_NUM_TABLE_ID = 2;
	public final static int DDL_CELL_NUM_TABLE_ID = 7;
	public final static int DDL_ROW_NUM_TABLE_NAME = 3;
	public final static int DDL_CELL_NUM_TABLE_NAME = 7;
	public final static int DDL_ROW_NUM_COL_START = 6;
	public final static int DDL_CELL_NUM_COL_ID = 2;
	public final static int DDL_CELL_NUM_COL_NAME = 4;
	public final static int DDL_CELL_NUM_COL_PK = 14;
	public final static int DDL_CELL_NUM_COL_TYPE = 16;
	public final static int DDL_CELL_NUM_COL_LEN = 19;
	public final static int DDL_CELL_NUM_COL_NOTNULL = 22;
	public final static int DDL_CELL_NUM_COL_DEFAULT = 25;
	public final static int DDL_CELL_NUM_COL_ISAUTO = 28;
//	public final static int DDL_CELL_NUM_COL_RANGE = 31;
	public final static int DDL_CELL_NUM_COL_REMARK = 35;
	/** DDL static define */


}
