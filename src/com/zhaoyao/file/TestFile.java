package com.zhaoyao.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestFile {
	// 连接MySql数据库，用户名和密码都是root
	static String URL = "jdbc:mysql://localhost:3306/zhaoyaoba";
//	static String url = "jdbc:mysql://localhost:3306/miaomiao";
	static String username = "root";
	static String password = "liuwei";
	private static Connection con;
	static{
		// 加载MySql的驱动类
					try {
						Class.forName("com.mysql.jdbc.Driver");
						 con = DriverManager.getConnection(
								 "jdbc:mysql://localhost:3306/zhaoyaoba?useUnicode=true&characterEncoding=UTF-8"
								 , username,
								password);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
	}
	private static ExecutorService NumberInfoFixedThreadPool = Executors.newFixedThreadPool(5);
	public static void main(String[] args) {
		String filePath = "C:/Users/dell/Desktop/中国常用汉字3500个/中国常用汉字3500个.txt";
		String localFileEncode = FileTool.getLocalFileEncode(new File(filePath));
		StringBuffer sb = new StringBuffer();
 		try {
			FileInputStream inputStream = new FileInputStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,localFileEncode));
			String s = "";
			while ((s = br.readLine())!=null) {
				sb.append(s);
			}
			br.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 		try {
			String drugCnName = new String(sb.toString().getBytes(), "UTF-8");
			final char[] charArray = drugCnName.toCharArray();
			for (int i = 0; i < charArray.length; i++) {
				final int m = i;
				NumberInfoFixedThreadPool.execute(new Runnable() {
					@Override
					public void run() {
//						try {
//							Statement statement = con.createStatement();
//							statement.executeUpdate("INSERT INTO chinesecharacters (word) VALUES ('"+charArray[m]+"')");
//							System.out.println("INSERT INTO chinesecharacters (word) VALUES ('"+charArray[m]+"')");
//						} catch (SQLException e) {
//							System.err.println("INSERT INTO chinesecharacters (word) VALUES ('"+charArray[m]+"')");
//						}
					}
				});
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	}
}
