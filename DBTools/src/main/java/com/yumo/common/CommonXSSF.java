package com.yumo.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yumo.Column;
import com.yumo.Table;
import com.yumo.TableData;
import com.yumo.exception.ToolException;

public class CommonXSSF {

	private static XSSFWorkbook wb;

	public static List<TableData> readDataFileToObject(File dataFile) throws ToolException {

		if (!FileUtil.isFile(dataFile)) {
			throw new ToolException("data file is not exists!");
		}
		List<TableData> tbDatas = new ArrayList<TableData>();
		try {
			wb = new XSSFWorkbook(dataFile);
			for (Sheet sheet : wb) {
				String sheetNm = sheet.getSheetName();
				if (StringUtils.startsWith(sheetNm, CommonConstants.DATA_DEFINE_SHEET_NAME_START)) {
					// 字段
					List<Column> cols = new ArrayList<Column>();
					Row colRow = sheet.getRow(CommonConstants.DATA_ROW_NUM_COL);
					for (Cell cell : colRow) {
						if (cell.getColumnIndex() < CommonConstants.DATA_CELL_NUM_START) {
							continue;
						}
						Column col = new Column();
						col.setColName(getCellValue(cell));
						cols.add(col);
					}

					// 数据
					List<List<Column>> datas = new ArrayList<List<Column>>();
					for (Row row : sheet) {
						List<Column> data = new ArrayList<Column>();
						if (row.getRowNum() < CommonConstants.DATA_ROW_NUM_DATA_START) {
							continue;
						}
						for (Cell cell : row) {
							if (cell.getColumnIndex() < CommonConstants.DATA_CELL_NUM_START
									|| (cell.getColumnIndex() - CommonConstants.DATA_CELL_NUM_START) >= cols.size()) {
								continue;
							}
							Column col = (Column) BeanUtils
									.cloneBean(cols.get(cell.getColumnIndex() - CommonConstants.DATA_CELL_NUM_START));
							col.setVal(getCellValue(cell));
							data.add(col);
						}
						datas.add(data);
					}

					TableData tbData = new TableData();
					tbData.setDatas(datas);
					tbData.setCols(cols);
					tbData.setTableID(getCellValue(sheet, CommonConstants.DATA_ROW_NUM_TABLE_ID,
							CommonConstants.DATA_CELL_NUM_TABLE_ID));
					tbData.setTableName(getCellValue(sheet, CommonConstants.DATA_ROW_NUM_TABLE_NAME,
							CommonConstants.DATA_CELL_NUM_TABLE_NAME));
					tbDatas.add(tbData);
				}
			}
		} catch (Exception e) {
			throw new ToolException(e);
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}
			} catch (Exception e) {
				throw new ToolException(e);
			}
		}
		return tbDatas;
	}

	public static List<Table> readDDLFileToObject(File ddlFile) throws ToolException {

		if (!FileUtil.isFile(ddlFile)) {
			throw new ToolException("ddl file is not exists!");
		}

		List<Table> tabls = new ArrayList<Table>();
		try {
			wb = new XSSFWorkbook(ddlFile);
			for (Sheet sheet : wb) {
				String sheetNm = sheet.getSheetName();
				if (StringUtils.startsWith(sheetNm, CommonConstants.DDL_DEFINE_SHEET_NAME_START)) {
					List<Column> cols = new ArrayList<Column>();
					int rowMaxNum = sheet.getLastRowNum();
					for (int j = CommonConstants.DDL_ROW_NUM_COL_START; j < rowMaxNum; j++) {
						String id = getCellValue(sheet, j, CommonConstants.DDL_CELL_NUM_COL_ID);
						if (StringUtils.isEmpty(id)) {
							break;
						}
						Column col = new Column();

						// 字段名
						String colName = getCellValue(sheet, j, CommonConstants.DDL_CELL_NUM_COL_NAME);
						col.setColName(colName);

						// 主键
						String pk = getCellValue(sheet, j, CommonConstants.DDL_CELL_NUM_COL_PK);
						col.setPK(StringUtils.equals(CommonConstants.YES, pk));

						// 类型
						String dataType = getCellValue(sheet, j, CommonConstants.DDL_CELL_NUM_COL_TYPE);
						col.setDataType(dataType);

						// 长度
						String dataLen = getCellValue(sheet, j, CommonConstants.DDL_CELL_NUM_COL_LEN);
						col.setDataLen(dataLen);

						// NOT NULL
						String notNull = getCellValue(sheet, j, CommonConstants.DDL_CELL_NUM_COL_NOTNULL);
						col.setNull(!StringUtils.equals(CommonConstants.YES, notNull));

						// 默认值
						String defVal = getCellValue(sheet, j, CommonConstants.DDL_CELL_NUM_COL_DEFAULT);
						col.setDefVal(defVal);

						// 自增
						String auto = getCellValue(sheet, j, CommonConstants.DDL_CELL_NUM_COL_ISAUTO);
						col.setAuto(StringUtils.equals(CommonConstants.YES, auto));

						// 说明
						String remark = getCellValue(sheet, j, CommonConstants.DDL_CELL_NUM_COL_REMARK);
						col.setRemark(remark);

						cols.add(col);
					}
					if (CollectionUtils.isNotEmpty((cols))) {
						Table table = new Table();
						// Table ID
						String tableID = getCellValue(sheet, CommonConstants.DDL_ROW_NUM_TABLE_ID,
								CommonConstants.DDL_CELL_NUM_TABLE_ID);
						table.setTableID(tableID);
						// Table Name
						String tableName = getCellValue(sheet, CommonConstants.DDL_ROW_NUM_TABLE_NAME,
								CommonConstants.DDL_CELL_NUM_TABLE_NAME);
						table.setTableName(tableName);
						table.setColumns(cols);
						tabls.add(table);
					}
				}
			}
		} catch (Exception e) {
			throw new ToolException(e);
		} finally {
			try {
				if (wb != null) {
					wb.close();
				}
			} catch (Exception e) {
				throw new ToolException(e);
			}
		}
		return tabls;
	}

	private static String getCellValue(Sheet sheet, int rownum, int cellnum) {
		return getCellValue(sheet.getRow(rownum).getCell(cellnum));
	}

	@SuppressWarnings("deprecation")
	private static String getCellValue(Cell cell) {
		if (cell.getCellTypeEnum() == CellType.NUMERIC) {
			return StringUtils.removeEnd(cell.toString(), ".0");
		} else {
			return cell.toString();
		}
	}
}
