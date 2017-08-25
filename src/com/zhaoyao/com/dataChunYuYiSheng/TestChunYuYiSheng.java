package com.zhaoyao.com.dataChunYuYiSheng;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

import com.zhaoyao.file.AppendToFile;
import com.zhaoyao.file.FileUtils;

public class TestChunYuYiSheng {
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/chun_yu_yi_sheng?Unicode=true&characterEncoding=UTF-8";
	static final String USER = "root";
	static final String PASS = "liuwei";
	private static Connection conn;
	
	
	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);
	
	
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
//		insert();
		name();
	}
	public static void name() {
		java.util.List<Object> list = new ArrayList<Object>();
		StringBuilder sb_sql = new StringBuilder();
		
		StringBuilder douHao = new StringBuilder();
		String fileName = "C:/Users/dell/Desktop/ChunYuYiSheng_50854";
		List<String> linesToList = FileUtils.readFileByLinesToList(new File(fileName,"失败.txt"));
		for (int i = 0; i < linesToList.size(); i++) {
			File file = new File(linesToList.get(i));
			if (!file.exists() || !file.isFile()) {
				continue;
			}
			sb_sql.delete(0, sb_sql.length());
			douHao.delete(0, douHao.length());
			list.clear();
			
			
			String readFileByLines = FileUtils.readFileByLines(file);
			JSONObject jsonObject = new JSONObject(readFileByLines);
			@SuppressWarnings("unchecked")
			Iterator<String> keys = jsonObject.keys();
			sb_sql.append("INSERT INTO json(");
			douHao.append("(");
			while (keys.hasNext()) {
				String next = keys.next();
				if (next.equalsIgnoreCase("yuedu_type")
						||next.equalsIgnoreCase("doctor_id")
						||next.equalsIgnoreCase("volunteer_activity_id")
						||next.equalsIgnoreCase("is_original")
						||next.equalsIgnoreCase("content")
						) {
					continue;
				}
				sb_sql.append(next+",");
				douHao.append("?,");
				list.add(jsonObject.get(next));
			}
			sb_sql.replace(sb_sql.length()-1, sb_sql.length(), ") VALUES ");
			
			douHao.replace(douHao.length()-1, douHao.length(), ") ");
			sb_sql.append(douHao);
			PreparedStatement prepareStatement = null;
			try {
				prepareStatement = conn.prepareStatement(sb_sql.toString());
				for (int j = 0; j < list.size(); j++) {
					prepareStatement.setString(j+1, list.get(j).toString());
				}
				boolean execute = prepareStatement.execute();
				if (!execute) {
					System.out.println("成功:\t"+file.toString());
				}else {
					System.out.println("成功:\t"+file.toString());
					AppendToFile.appendMethodByFileWriter(new File(file.getParentFile(),"失败.txt"), file.toString()+"\n");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				AppendToFile.appendMethodByFileWriter(new File(fileName,"SQLException 失败.txt"), file.toString()+"\n");
			}finally{
				if (prepareStatement != null) {
					try {
						prepareStatement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			AppendToFile.appendMethodByFileWriter(new File(file.getParentFile(),"load.txt"), file.toString()+"\n");
		}
	}
	private static void insert() {
		String fileName = "C:/Users/dell/Desktop/ChunYuYiSheng_50854";
		File fileP = new File(fileName);
		File[] listFiles = fileP.listFiles();
		java.util.List<Object> list = new ArrayList<Object>();
		StringBuilder sb_sql = new StringBuilder();
		
		StringBuilder douHao = new StringBuilder();
		for (File file2 : listFiles) {
			if (file2.exists() && file2.isDirectory()) {
				for (File file : file2.listFiles()) {
					if (!file.isFile()) {
						continue;
					}
					sb_sql.delete(0, sb_sql.length());
					douHao.delete(0, douHao.length());
					list.clear();
					String readFileByLines = FileUtils.readFileByLines(file);
					JSONObject jsonObject = new JSONObject(readFileByLines);
					@SuppressWarnings("unchecked")
					Iterator<String> keys = jsonObject.keys();
					sb_sql.append("INSERT INTO json(");
					douHao.append("(");
					while (keys.hasNext()) {
						String next = keys.next();
						sb_sql.append(next+",");
						douHao.append("?,");
						list.add(jsonObject.get(next));
					}
					sb_sql.replace(sb_sql.length()-1, sb_sql.length(), ") VALUES ");
					
					douHao.replace(douHao.length()-1, douHao.length(), ") ");
					sb_sql.append(douHao);
					PreparedStatement prepareStatement = null;
					try {
						 prepareStatement = conn.prepareStatement(sb_sql.toString());
						for (int i = 0; i < list.size(); i++) {
							prepareStatement.setString(i+1, list.get(i).toString());
						}
						boolean execute = prepareStatement.execute();
						if (!execute) {
							System.out.println("成功:\t"+file.toString());
						}else {
							System.out.println("成功:\t"+file.toString());
							AppendToFile.appendMethodByFileWriter(new File(file.getParentFile(),"失败.txt"), file.toString()+"\n");
						}
					} catch (SQLException e) {
						e.printStackTrace();
						AppendToFile.appendMethodByFileWriter(new File(file.getParentFile(),"SQLException 失败.txt"), file.toString()+"\n");
					}finally{
						if (prepareStatement != null) {
							try {
								prepareStatement.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
					AppendToFile.appendMethodByFileWriter(new File(file.getParentFile(),"load.txt"), file.toString()+"\n");
				}
			}
		}
	}
	public static void createTable(String fileName) {
		String readFileByLines = FileUtils.readFileByLines(new File(fileName,"女性_2017-06-27_103057.json"));
		if (readFileByLines != null) {
			try {
				JSONObject jsonObject = new JSONObject(readFileByLines);
				@SuppressWarnings("unchecked")
				Iterator<String> keys = jsonObject.keys();
				String sql = "CREATE TABLE json (id INTEGER not NULL," ;
				while (keys.hasNext()) {
					String next = keys.next();
					sql += " "+next +" VARCHAR(255),";
				}
				sql += " PRIMARY KEY ( id )";
				java.sql.Statement stmt = conn.createStatement();
				stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
