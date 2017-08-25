package com.zhaoyao.com;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class JDBC {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/login";
	static final String USER = "root";
	static final String PASS = "liuwei";

	private static Connection conn;
	
	private JDBC() {
	}
	
	private static JDBC jdbc = new JDBC();
	
	public static JDBC getInstance() {
		return jdbc;
	}
	public Connection getConnection() {
		return conn;
	}
	public Statement getStatement() {
		try {
			return conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		try {
			User user = getUser(User.class,"17713601564");
			System.out.println(user.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	public static <T> T getUser(Class<T> clazz,String phone) throws SQLException, InstantiationException, IllegalAccessException {
		String sql = "SELECT *FROM `user` WHERE phone = "+phone;
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		T newInstance = clazz.newInstance();
		Field[] fields = newInstance.getClass().getDeclaredFields();
		Map<String, Field> map = new HashMap<String, Field>();
		for (Field field : fields) {
			if(field.isSynthetic())continue;
			map.put(field.getName(), field);
		}
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();
		while (resultSet.next()) {
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnName(i);
				if(!map.containsKey(columnName))continue;
				Field field = map.get(columnName);
				field.setAccessible(true);
				Class<?> type = field.getType();
				if(type == String.class){
					field.set(newInstance, resultSet.getString(i));
				}else if (type == Integer.class || type == int.class) {
					field.set(newInstance, resultSet.getInt(i));
				}else if (type == Date.class || type == java.util.Date.class) {
					field.set(newInstance, resultSet.getDate(i));
				}continue;
			}
		}
		return newInstance;
	}
}
