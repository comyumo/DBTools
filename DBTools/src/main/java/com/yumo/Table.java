package com.yumo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Table {

	private String tableID;
	private String tableName;
	private List<Column> columns;
}
