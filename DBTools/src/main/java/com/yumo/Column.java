package com.yumo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Column {
	private String colName;
	private String dataType;
	private String dataLen;
	private boolean isPK;
	private boolean isNull;
	private String defVal;
	private boolean isAuto;
	private String remark;
	private String val;
}
