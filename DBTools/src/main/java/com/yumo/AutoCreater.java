package com.yumo;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.yumo.common.CommonConstants;
import com.yumo.common.CommonUtil;
import com.yumo.common.CommonXSSF;
import com.yumo.common.SQLCreater;

public class AutoCreater {

	public static void main(String[] args) {
		List<String> sqls;
		File inFile;
		File outSqlFile;
		try {
			inFile = CommonUtil.getDDLFile();
			if (inFile != null) {
				outSqlFile = CommonUtil.getDDLSQLFile();
				sqls = SQLCreater.createDDLSql(CommonXSSF.readDDLFileToObject(inFile));
				FileUtils.writeLines(outSqlFile, CommonConstants.CHARACTER_ENCODING_UTF8, sqls);
				System.out.println("a DDL file is created! path:" + outSqlFile.getPath());
			}

			inFile = CommonUtil.getDataFile();
			if (inFile != null) {
				outSqlFile = CommonUtil.getDataSQLFile();
				sqls = SQLCreater.createDataSql(CommonXSSF.readDataFileToObject(inFile));
				FileUtils.writeLines(outSqlFile, CommonConstants.CHARACTER_ENCODING_UTF8, sqls);
				System.out.println("a Data file is created! path:" + outSqlFile.getPath());
			}
		} catch (Exception e) {
			System.out.println("file create is failed!");
			e.printStackTrace();
		}

	}

}
