package com.yumo.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

public class CommonUtil {

	public static Properties pro = System.getProperties();
	public static Properties dbToolsPro = new Properties();

	public static String getDDLFilePath() throws FileNotFoundException, IOException {
		return getDBToolsPro().getProperty(PropertiesConstants.TOOLS_PRO_DDL_DEFINE_FILE_PATH);
	}

	public static File getDDLSQLFile() throws FileNotFoundException, IOException {
		String proPath = pro.getProperty(PropertiesConstants.SYSTEM_PRO_KEY_USER_DIR);
		File file = new File(proPath, CommonConstants.SQL_FILE_NAME_DDL);
		return file;
	}

	public static String getDBType() {
		return dbToolsPro.getProperty(PropertiesConstants.TOOLS_PRO_DATABASE_TYPE);
	}

	public static Properties getDBToolsPro() throws FileNotFoundException, IOException {
		String proPath = pro.getProperty(PropertiesConstants.SYSTEM_PRO_KEY_USER_DIR);
		if (StringUtils.isNotBlank(proPath)) {
			dbToolsPro.load(new InputStreamReader(new FileInputStream(new File(proPath, PropertiesConstants.TOOLS_PRO_NAME)) , CommonConstants.CHARACTER_ENCODING_UTF8));
//			dbToolsPro.load(new FileInputStream(new File(proPath, PropertiesConstants.TOOLS_PRO_NAME)));
		}
		return dbToolsPro;
	}
}
