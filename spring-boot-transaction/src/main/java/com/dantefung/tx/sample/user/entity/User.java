package com.dantefung.tx.sample.user.entity;

import com.dantefung.tx.transaction02.orm.annotation.MyColumn;
import com.dantefung.tx.transaction02.orm.annotation.MyId;
import com.dantefung.tx.transaction02.orm.annotation.MyTableName;
import lombok.Data;

import java.util.Date;

@Data
@MyTableName(tableName = "tb_user")
public class User {

	@MyId
	private int id;
	//@MyColumn(columnName = "`username`")
	@MyColumn
	private String username;
	//@MyColumn(columnName = "`age`")
	@MyColumn
	private int age;
	//@MyColumn(columnName = "ctm")
	@MyColumn
	private Date ctm;

	public User() {
	}

	public User(String username, int age) {
		this.username = username;
		this.age = age;
		this.ctm = new Date();
	}
	// Getter、Setter
}