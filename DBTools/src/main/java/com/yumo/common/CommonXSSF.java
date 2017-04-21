package com.yumo.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.yumo.Column;
import com.yumo.Table;

public class CommonXSSF {

	private static XSSFWorkbook wb;

	public static List<Table> readFileToObject(String ddlFilePath) throws InvalidFormatException, IOException {
		List<Table> tabls = new ArrayList<Table>();
		wb = new XSSFWorkbook(new File(ddlFilePath));
		int sheetCount = wb.getNumberOfSheets();
		for (int i = 0; i < sheetCount; i++) {
			XSSFSheet sheet = wb.getSheetAt(i);
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
					String tableNm = getCellValue(sheet, CommonConstants.DDL_ROW_NUM_TABLE_ID,
							CommonConstants.DDL_CELL_NUM_TABLE_ID);
					table.setName(tableNm);
					table.setColumns(cols);
					tabls.add(table);
				}
			}
		}
		return tabls;
	}

	@SuppressWarnings("deprecation")
	public static String getCellValue(Sheet sheet, int rownum, int cellnum) {
		Cell cell = sheet.getRow(rownum).getCell(cellnum);
		if (cell.getCellTypeEnum() == CellType.NUMERIC) {
			return StringUtils.removeEnd(cell.toString(), ".0");
		} else {
			return cell.toString();
		}
	}
}
