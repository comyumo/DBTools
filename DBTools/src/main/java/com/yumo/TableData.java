package com.yumo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableData {
	private String tableID;
	private String tableName;
	private List<Column> cols;
	private List<List<Column>> datas;
}
