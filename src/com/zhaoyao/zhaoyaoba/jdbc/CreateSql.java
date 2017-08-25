package com.zhaoyao.zhaoyaoba.jdbc;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.List;

public class CreateSql implements JDBCinterface{
	/***
	 * 创建table  的  sql
	 * @param cls 类.class
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public synchronized static <T> String createTableSql(Class<T> cls) throws InstantiationException, IllegalAccessException {
		String  sql = "";
		Object newInstance = cls.newInstance();
		Class<? extends Object> clazz = newInstance.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String typeName = field.getType().getName();
			if(typeName.equals(String.class.getName().toString())){
				typeName = "VARCHAR"+"("+20+")";
			}else if (typeName.equals(Integer.class.getName().toString())) {
				typeName = "int";
			}else if (typeName.equals(Date.class.getName().toString())
					||typeName.equals(java.util.Date.class.getName().toString())) {
				typeName = "DATE";
			}else if (typeName.equals(Float.class.getName().toString())) {
				typeName = "FLOAT";
			}
			if(field.isAnnotationPresent(InjectTable.class)){
				InjectTable annotation = field.getAnnotation(InjectTable.class);
				if(typeName.equals(String.class.getName().toString())){
					int length = annotation.length();
					typeName = "VARCHAR"+"("+(length<0?length=20:length)+")";
				}
				if (!annotation.primarykey()) {
					String columnName = annotation.ColumnName();
					if (columnName!=null&&!columnName.equals("")) {
						sql += " "+columnName+" "+typeName +",";
					}else {
						String name = field.getName();
						sql += " "+name+" "+typeName +",";
					}
				}else {
					String columnName = annotation.ColumnName();
					if (columnName!=null&&!columnName.equals("")) {
						sql += " "+columnName+" "+typeName +" AUTO_INCREMENT PRIMARY  key,";
					}else {
						String name = field.getName();
						sql += " "+name+" "+typeName +" AUTO_INCREMENT PRIMARY  key,";
					}
				}
			}else {
				String name = field.getName();
				sql += " "+name+" "+typeName +",";
			}
		}
		int lastIndexOf = sql.lastIndexOf(",");
		sql = sql.substring(0, lastIndexOf);
		sql = "("+sql+")"; 
		boolean annotationPresent = clazz.isAnnotationPresent(InjectTable.class);
		if (annotationPresent) {
			InjectTable annotation = clazz.getAnnotation(InjectTable.class);
			String tableName = annotation.tableName();
			sql = "create  table "+tableName+" "+sql;
		}
		return sql;
	}
	/***
	 * 创建批量插入数据  sql 
	 * @param tableName  表名字  gubiao
	 * @param columnName  new String[]{"low" ,"high"}
	 * @param columnContent  new String[]{里面填写  columnName  对应的值};
	 * @return  insert into gubiao(low ,high) select 1,2 UNION select 1,3
	 */
	public static String createInsertSql(final String tableName,final String[] columnName,final List<Object[]> columnContent) {
		String sql = INSERT_INTO+tableName;
		if(columnName==null||columnName.length == 0)throw new NullPointerException("columnName  null  || columnName.length == 0");
		String columnNames = "";
		for (int i = 0; i < columnName.length; i++) {
			columnNames += columnName[i] + COMMA;
		}
		columnNames = LEFT_BRACKET+columnNames.substring(0, columnNames.lastIndexOf(","))+RIGHT_BRACKET;
		sql += columnNames;
		if(columnContent==null||columnContent.size() == 0)throw new NullPointerException("columnContent  null  || columnContent.size == 0");
		String columnContents = "__";
		for (int i = 0; i < columnContent.size(); i++) {
			Object[] array = columnContent.get(i);
			columnContents += UNION + SELECT;
			String contents = "";
			for (int j = 0; j < array.length; j++) {
				contents +=" "+ SINGLE_MARK.trim()+array[j]+SINGLE_MARK.trim()+COMMA;
			}
			contents = contents.substring(0, contents.lastIndexOf(","));
			columnContents += contents;
		}
		columnContents = columnContents.replace("__"+UNION, "");
		sql += columnContents;
		return sql;
	}
	/***
	 * 创建查询指定列的数据
	 * @param table_name  表名字
	 * @param query_column_name  要查询的字段名字  ，为  null 是查询全部字段
	 * @param conditionName   条件字段名字      如果为 null  参数：condition 也应该为null  就是无条件查询
	 * @param condition		条件  有几个 conditionName  就要几个condition
	 * <li>{@inheritDoc String columnBy = JDBC.createQuerySpecifiedColumnBy("table_name", null, new String[]{"code","id"}, new Object[]{3,"5"},new Object[]{4,"6"});}
	 * @return	 SELECT  *  FROM table_name WHERE code IN  ( '3' , '5' )  AND id IN  ( '4' , '6' ) 
	 */
	public static String createQuerySpecifiedColumnBy(final String table_name,final String[] query_column_name,final String[] conditionName,final Object[] ...condition) {
		String sql = SELECT;
		if(query_column_name == null||query_column_name.length==0){
			sql = SELECT+STAR_KEY+FROM+table_name;
		}else {
			if("".equals(query_column_name[0]))throw new NullPointerException("query_column_name  不能填入空字符串   如果不填就填入  null");
			for (int i = 0; i < query_column_name.length; i++) {
				sql += query_column_name[i]+COMMA;
			}
			sql += table_name;
		}
		String s = WHERE+"__";
		if(condition.length != conditionName.length)throw new IndexOutOfBoundsException("condition.length != conditionName.length   两者是对应的 必须相等");
		for (int i = 0; i < conditionName.length; i++) {
			s += AND+conditionName[i]+ IN +LEFT_BRACKET;
			Object[] objects = condition[i];
			for (int j = 0; j < objects.length; j++) {
				s += SINGLE_MARK.trim()+objects[j]+SINGLE_MARK.trim()+COMMA;
			}
			s = s.substring(0, s.lastIndexOf(COMMA)) +RIGHT_BRACKET;
		}
		return sql += s.replace("__"+AND, "");
	}
	
	public static String createDistinctSql(String table_name,String column_name) {
		String sql = "";
		sql = SELECT +DISTINCT+column_name+FROM+table_name;
		return sql;
	}
	
	/***
	 * 创建分页查询的  sql
	 * @param tableName		表名字
	 * {@link #createLimitSql(String, String[], Integer, Integer)}
	 * @param start		开始数目
	 * @param end  每页好多条
	 * <li>分页查询   每页五条 (0,5] (5,5]  (10,5],(15,5]
	 * <li>{@inheritDoc String createLIMIT = JDBC.createLIMIT("table_name", null, 5, 5);}
	 * @return	 SELECT  *  FROM table_name LIMIT 5 , 5
	 */
	public static String createLimitSql(final String tableName,final Integer start,final Integer end) {
		return createLimitSql(tableName, null, start, end);
	}
	/***
	 * 创建分页查询的  sql
	 * @param tableName		表名字
	 * @param query_column_name  要查询的字段名字  ，为  null 是查询全部字段
	 * @param start		开始数目
	 * @param end  每页好多条
	 * <li>分页查询   每页五条 (0,5] (5,5]  (10,5],(15,5]
	 * <li>{@inheritDoc String createLIMIT = JDBC.createLIMIT("table_name", null, 5, 5);}
	 * @return	 SELECT  *  FROM table_name LIMIT 5 , 5
	 */
	public static String createLimitSql(final String tableName,final String[] query_column_name,final Integer start,final Integer end) {
		String sql = SELECT;
		if(query_column_name==null||query_column_name.length==0){
			sql +=STAR_KEY+FROM+tableName+LIMIT+start+COMMA+end;
		}else {
			for (int i = 0; i < query_column_name.length; i++) {
				sql += query_column_name[i]+COMMA;
			}
			sql = sql.substring(0, sql.lastIndexOf(COMMA))+FROM+tableName+LIMIT+start+COMMA+end;
		}
		return sql;
	}
	
	
	private static String createQuerySql(String tableName, String[] queryColumnName,
			String conditionColumnName, Object[] arg) {
		String sql = "";
		if (queryColumnName==null||queryColumnName.length==0) {
			sql = SELECT+STAR_KEY+FROM+tableName+SPACE;
		}else {
			sql = SELECT;
			for (int i = 0; i < queryColumnName.length; i++) {
				sql += queryColumnName[i]+COMMA;
			}
			sql = sql.substring(0, sql.lastIndexOf(COMMA))+SPACE+tableName+SPACE;
		}
		if (arg != null && arg.length !=0 && conditionColumnName!=null && !"".equals(conditionColumnName)) {
			sql += WHERE + SPACE +conditionColumnName+SPACE+IN+LEFT_BRACKET;
			if(arg[0] instanceof String)
				for (int i = 0; i < arg.length; i++) {
					sql += arg[i]+COMMA;
				}
			if(arg[0] instanceof Integer)
				for (int i = 0; i < arg.length; i++) {
					sql += SINGLE_MARK+arg[i]+SINGLE_MARK+COMMA;
				}
			sql = sql.substring(0, sql.lastIndexOf(COMMA))+RIGHT_BRACKET;
		}
		return sql;
	}
}
