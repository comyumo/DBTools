package com.yumo.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.yumo.Column;
import com.yumo.DBMark;
import com.yumo.Table;
import com.yumo.TableData;

public class SQLCreater {

	private static DBMark dbMark = null;

	public static List<String> createDataSql(List<TableData> tableDatas) {
		List<String> sqls = null;
		dbMark = getDBMark();

		switch (CommonUtil.getDBType()) {
		case PropertiesConstants.DATABASE_TYPE_MYSQL:
			sqls = createDataSqlMysql(tableDatas);
			break;

		default:
			sqls = createDataSqlMysql(tableDatas);
			break;
		}

		return sqls;
	}

	public static List<String> createDDLSql(List<Table> tables) {
		List<String> sqls = null;
		dbMark = getDBMark();
		switch (CommonUtil.getDBType()) {
		case PropertiesConstants.DATABASE_TYPE_MYSQL:
			sqls = createDDLMysql(tables);
			break;

		default:
			sqls = createDDLMysql(tables);
			break;
		}

		return sqls;
	}

	private static List<String> createDataSqlMysql(List<TableData> tableDatas) {
		List<String> sqls = new ArrayList<String>();
		if (CollectionUtils.isEmpty(tableDatas)) {
			return sqls;
		}
		StringBuilder dataSql = new StringBuilder();
		for (TableData tbData : tableDatas) {
			sqls.add(StringUtils.EMPTY);
			sqls.add(createDataColSql(tbData, dataSql));
			sqls.add(StringUtils.EMPTY);
		}

		return sqls;
	}

	private static String createDataColSql(TableData tbData, StringBuilder sb) {
		sb.setLength(0);
		sb.append("-- ").append(tbData.getTableName()).append(CommonConstants.NEWLINE);
		sb.append("TRUNCATE TABLE ").append(dbMark.getMarkSt()).append(tbData.getTableID()).append(dbMark.getMarkEnd())
				.append(";").append(CommonConstants.NEWLINE);
		sb.append("INSERT INTO ").append(dbMark.getMarkSt()).append(tbData.getTableID()).append(dbMark.getMarkEnd())
				.append(" ( ");
		int validCelIndex = 0;
		for (Column col : tbData.getCols()) {
			if (StringUtils.isBlank(col.getColName())) {
				continue;
			}
			if (validCelIndex > 0) {
				sb.append(CommonConstants.COMMA);
			}
			sb.append(StringUtils.SPACE).append(dbMark.getMarkSt()).append(col.getColName())
					.append(dbMark.getMarkEnd());
			validCelIndex++;
		}
		sb.append(")").append(CommonConstants.NEWLINE);
		int validRowIndex = 0;
		for (List<Column> data : tbData.getDatas()) {
			if (isEmptyRow(data)) {
				continue;
			}
			if (validRowIndex > 0) {
				sb.append("UNION ALL SELECT ");
			} else {
				sb.append("SELECT ");
			}
			validCelIndex = 0;
			for (Column col : data) {
				if (StringUtils.isBlank(col.getColName())) {
					continue;
				}
				if (validCelIndex > 0) {
					sb.append(CommonConstants.COMMA);
				}
				String val = col.getVal();
				if (StringUtils.equalsIgnoreCase(CommonConstants.NULL, val)) {
					sb.append(StringUtils.SPACE).append(CommonConstants.NULL);
				} else if (StringUtils.equalsIgnoreCase(CommonConstants.STR_TRUE, val) || StringUtils.equalsIgnoreCase(CommonConstants.STR_FALSE, val)) {
						sb.append(StringUtils.SPACE).append(val.toUpperCase());
				} else {
					sb.append(StringUtils.SPACE).append(CommonConstants.SING_QUOT).append(val)
							.append(CommonConstants.SING_QUOT);
				}
				// sb.append(StringUtils.SPACE).append("AS");
				// sb.append(StringUtils.SPACE).append(dbMark.getMarkSt()).append(col.getColName())
				// .append(dbMark.getMarkEnd());
				validCelIndex++;
			}
			sb.append(CommonConstants.NEWLINE);
			validRowIndex++;
		}
		sb.append(";");
		return sb.toString();
	}

	private static List<String> createDDLMysql(List<Table> tables) {

		List<String> sqls = new ArrayList<String>();
		if (CollectionUtils.isEmpty(tables)) {
			return sqls;
		}

		StringBuilder ddlSql = new StringBuilder();
		for (Table tb : tables) {
			sqls.add(StringUtils.EMPTY);
			sqls.add(createDDLColSql(tb, ddlSql));
			sqls.add(StringUtils.EMPTY);
		}

		return sqls;
	}

	private static String createDDLColSql(Table tb, StringBuilder sb) {
		sb.setLength(0);
		sb.append("-- ").append(tb.getTableName()).append(CommonConstants.NEWLINE);
		sb.append("DROP TABLE IF EXISTS ").append(dbMark.getMarkSt()).append(tb.getTableID())
				.append(dbMark.getMarkEnd()).append(";").append(CommonConstants.NEWLINE);
		sb.append("CREATE TABLE ").append(dbMark.getMarkSt()).append(tb.getTableID()).append(dbMark.getMarkEnd())
				.append(" ( ").append(CommonConstants.NEWLINE);

		for (Column col : tb.getColumns()) {
			sb.append(CommonConstants.TAB);
			sb.append(dbMark.getMarkSt()).append(col.getColName()).append(dbMark.getMarkEnd())
					.append(StringUtils.SPACE);
			sb.append(col.getDataType()).append(StringUtils.SPACE);
			if (StringUtils.isNotBlank(col.getDataLen())) {
				sb.append("(").append(col.getDataLen()).append(")").append(StringUtils.SPACE);
			}
			if (!col.isNull()) {
				sb.append("NOT NULL").append(StringUtils.SPACE);
			}
			if (StringUtils.isNotBlank(col.getDefVal())) {
				sb.append("DEFAULT").append(StringUtils.SPACE);
				if (StringUtils.equalsIgnoreCase(CommonConstants.STR_TRUE, col.getDefVal())
						|| StringUtils.equalsIgnoreCase(CommonConstants.STR_FALSE, col.getDefVal())) {
					sb.append(col.getDefVal()).append(StringUtils.SPACE);
				} else {
					sb.append(CommonConstants.SING_QUOT).append(col.getDefVal()).append(CommonConstants.SING_QUOT)
							.append(StringUtils.SPACE);
				}
			}
			if (col.isAuto()) {
				sb.append("AUTO_INCREMENT").append(StringUtils.SPACE);
			}
			if (StringUtils.isNotBlank(col.getRemark())) {
				sb.append("COMMENT").append(StringUtils.SPACE);
				sb.append(CommonConstants.SING_QUOT).append(col.getRemark()).append(CommonConstants.SING_QUOT)
						.append(StringUtils.SPACE);
			}
			sb.append(CommonConstants.COMMA).append(CommonConstants.NEWLINE);
		}
		sb = createPK(tb.getColumns(), sb).append(CommonConstants.NEWLINE);
		sb.append(");");
		return sb.toString();
	}

	private static StringBuilder createPK(List<Column> columns, StringBuilder sb) {
		sb.append(CommonConstants.TAB).append("PRIMARY KEY (");
		for (Column col : columns) {
			if (col.isPK()) {
				sb.append(dbMark.getMarkSt()).append(col.getColName()).append(dbMark.getMarkEnd())
						.append(CommonConstants.COMMA);
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb;
	}

	private static DBMark getDBMark() {

		DBMark dbMark = new DBMark();
		switch (CommonUtil.getDBType()) {
		case PropertiesConstants.DATABASE_TYPE_MYSQL:
			dbMark.setMarkSt(CommonConstants.MYSQL_MARK_ST);
			dbMark.setMarkEnd(CommonConstants.MYSQL_MARK_END);
			break;

		case PropertiesConstants.DATABASE_TYPE_MSSQL:
			dbMark.setMarkSt(CommonConstants.MSSQL_MARK_ST);
			dbMark.setMarkEnd(CommonConstants.MSSQL_MARK_END);
			break;

		default:
			dbMark.setMarkSt(StringUtils.EMPTY);
			dbMark.setMarkEnd(StringUtils.EMPTY);
			break;
		}
		return dbMark;
	}

	private static boolean isEmptyRow(List<Column> row) {
		String unionStr = StringUtils.EMPTY;
		for (Column col : row) {
			unionStr += StringUtils.defaultIfEmpty(col.getVal(), StringUtils.EMPTY);
		}

		return StringUtils.isEmpty(unionStr);
	}

}
