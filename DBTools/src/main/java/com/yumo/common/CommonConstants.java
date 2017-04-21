package com.yumo.common;

public interface CommonConstants {

	public final static String SQL_FILE_NAME_DDL = "DDL.sql";

	public final static String DDL_DEFINE_SHEET_NAME_START = "T-";
	
	public final static int DDL_ROW_NUM_TABLE_ID = 2;
	public final static int DDL_CELL_NUM_TABLE_ID = 7;

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
	
	public final static String YES = "YES";
	public final static String CHARACTER_ENCODING_UTF8 = "UTF-8";
	@SuppressWarnings("restriction")
	public final static String NEWLINE = java.security.AccessController.doPrivileged(
            new sun.security.action.GetPropertyAction("line.separator"));
	public final static String TAB = "    ";
	public final static String COMMA = ",";
	public final static String MYSQL_SQ = "`";
}
