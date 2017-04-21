package com.yumo;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.yumo.common.CommonConstants;
import com.yumo.common.CommonUtil;
import com.yumo.common.CommonXSSF;
import com.yumo.common.SQLCreater;

public class DDLCreater {

	public static void main(String[] args) {
		try {

			String ddlFilePath = CommonUtil.getDDLFilePath();
			File ddlSqlFile = CommonUtil.getDDLSQLFile();
			List<String> sqls = SQLCreater.createDDL(CommonXSSF.readFileToObject(ddlFilePath));
			FileUtils.writeLines(ddlSqlFile, CommonConstants.CHARACTER_ENCODING_UTF8, sqls);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
