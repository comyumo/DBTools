package com.yumo.common;

import java.io.File;

import org.apache.commons.io.FileUtils;

public class FileUtil extends FileUtils {

	public static boolean isFile(File file) {
		return file == null ? false : file.isFile();
	}
}
