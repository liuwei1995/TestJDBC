package com.zhaoyao.zhaoyaoba.jdbc;

import java.util.Date;
@InjectTable(tableName = "t_drug")
public class Drug {
	
	@InjectTable(primarykey = true)
	private Integer id;
	
	private int code;
	
	@InjectTable(ColumnName = "drugName")
	private String name;
	
	private Date createTime;
	private int age;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "Drug [id=" + id + ", code=" + code + ", name=" + name
				+ ", createTime=" + createTime + ", age=" + age + "]";
	}
	
}
