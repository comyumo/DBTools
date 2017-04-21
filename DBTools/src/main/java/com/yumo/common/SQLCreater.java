package com.yumo.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.yumo.Column;
import com.yumo.Table;

public class SQLCreater {

	public static List<String> createDDL(List<Table> tables) {
		List<String> sqls = null;
		;

		switch (CommonUtil.getDBType()) {
		case PropertiesConstants.DATABASE_TYPE_MYSQL:
			sqls = createMysql(tables);
			break;

		default:
			sqls = createMysql(tables);
			break;
		}

		return sqls;
	}

	private static List<String> createMysql(List<Table> tables) {

		List<String> sqls = new ArrayList<String>();
		if (CollectionUtils.isEmpty(tables)) {
			return sqls;
		}

		StringBuilder ddlSql = new StringBuilder();
		for (Table tb : tables) {
			sqls.add(StringUtils.EMPTY);
			sqls.add(createColSql(tb, ddlSql));
			sqls.add(StringUtils.EMPTY);
		}

		return sqls;
	}

	private static String createColSql(Table tb, StringBuilder sb) {

		sb.setLength(0);

		sb.append(" drop table if exists ").append(CommonConstants.MYSQL_SQ).append(tb.getName()).append(CommonConstants.MYSQL_SQ).append(";").append(CommonConstants.NEWLINE);
		sb.append(" CREATE TABLE ").append(CommonConstants.MYSQL_SQ).append(tb.getName()).append(CommonConstants.MYSQL_SQ).append(" ( ").append(CommonConstants.NEWLINE);

		for (Column col : tb.getColumns()) {
			sb.append(CommonConstants.TAB);
			sb.append(CommonConstants.MYSQL_SQ).append(col.getColName()).append(CommonConstants.MYSQL_SQ).append(StringUtils.SPACE);
			sb.append(col.getDataType()).append(StringUtils.SPACE);
			if (StringUtils.isNotBlank(col.getDataLen())) {
				sb.append("(").append(col.getDataLen()).append(")").append(StringUtils.SPACE);
			}
			if (!col.isNull()) {
				sb.append("NOT NULL").append(StringUtils.SPACE);
			}
			if (StringUtils.isNotBlank(col.getDefVal())) {
				sb.append("DEFAULT").append(StringUtils.SPACE);
				if (isNumber(col.getDataType())) {
					sb.append(col.getDefVal()).append(StringUtils.SPACE);
				} else {
					sb.append("'").append(col.getDefVal()).append("'").append(StringUtils.SPACE);
				}
			}
			if (col.isAuto()) {
				sb.append("AUTO_INCREMENT").append(StringUtils.SPACE);
			}
			if (StringUtils.isNotBlank(col.getRemark())) {
				sb.append("COMMENT").append(StringUtils.SPACE);
				sb.append("'").append(col.getRemark()).append("'").append(StringUtils.SPACE);
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
				sb.append(CommonConstants.MYSQL_SQ).append(col.getColName()).append(CommonConstants.MYSQL_SQ).append(CommonConstants.COMMA);
			}
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb;
	}
	
	private static boolean isNumber(String dataType) {
		return ArrayUtils.contains(numberType, dataType.toLowerCase());
	}

	private static String[] numberType = { "int", "bigint", "double", "flort", "decimal", "bit" };
}
