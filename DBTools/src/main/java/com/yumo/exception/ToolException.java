package com.yumo.exception;

@SuppressWarnings("serial")
public class ToolException extends Exception {

	public ToolException(Exception e) {
		super(e);
	}

	public ToolException(String msg) {
		super(msg);
	}
}
