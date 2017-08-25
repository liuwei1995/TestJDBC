package com.zhaoyao.zhaoyaoba.jdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.FIELD,ElementType.TYPE})
//表示用在字段上
@Retention(RetentionPolicy.RUNTIME)
//表示在生命周期是运行时
public @interface InjectTable {
	
	/**表的名字*/
	public String tableName() default "";
	
	/**字段的长度*/
	public int length() default 20;
	
	/**是否是主键*/
	public boolean primarykey() default false;
	
	/**是否作为字段*/
	public boolean isColumn() default true;
	
	/***字段名字*/
	public String ColumnName() default "";
}
