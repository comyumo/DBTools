package com.yumo.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.yumo.exception.ToolException;

public class CommonUtil {

	public static Properties pro = System.getProperties();
	public static Properties dbToolsPro;

	public static File getDataFile() throws ToolException {
		File dataFile = null;
		try {
			String dataFilePath = getDBToolsPro().getProperty(PropertiesConstants.TOOLS_PRO_DATA_DEFINE_FILE_PATH);
			if (StringUtils.isNotBlank(dataFilePath)) {
				dataFile = new File(dataFilePath);
			}
		} catch (Exception e) {
			throw new ToolException(e);
		}
		return dataFile;
	}

	public static File getDataSQLFile() {
		String proPath = pro.getProperty(PropertiesConstants.SYSTEM_PRO_KEY_USER_DIR);
		File file = new File(proPath, CommonConstants.SQL_FILE_NAME_DATA);
		return file;
	}

	public static File getDDLFile() throws ToolException {
		File ddlFile = null;
		try {
			String filePath = getDBToolsPro().getProperty(PropertiesConstants.TOOLS_PRO_DDL_DEFINE_FILE_PATH);
			if (StringUtils.isNotBlank(filePath)) {
				ddlFile = new File(filePath);
			}
		} catch (Exception e) {
			throw new ToolException(e);
		}
		return ddlFile;
	}

	public static File getDDLSQLFile() {
		String proPath = pro.getProperty(PropertiesConstants.SYSTEM_PRO_KEY_USER_DIR);
		File file = new File(proPath, CommonConstants.SQL_FILE_NAME_DDL);
		return file;
	}

	public static String getDBType() {
		return dbToolsPro.getProperty(PropertiesConstants.TOOLS_PRO_DATABASE_TYPE);
	}

	public static Properties getDBToolsPro() throws ToolException {
		try {
			if (dbToolsPro == null) {
				dbToolsPro = new Properties();
				String proPath = pro.getProperty(PropertiesConstants.SYSTEM_PRO_KEY_USER_DIR);
				dbToolsPro.load(new InputStreamReader(
						new FileInputStream(new File(proPath, PropertiesConstants.TOOLS_PRO_NAME)),
						CommonConstants.CHARACTER_ENCODING_UTF8));
			}
		} catch (Exception e) {
			throw new ToolException(e);
		}

		return dbToolsPro;
	}
}
