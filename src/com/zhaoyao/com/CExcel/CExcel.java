package com.zhaoyao.com.CExcel;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class CExcel {
	
	static String username = "root";
	static String password = "liuwei";
	static String database = "zhaoyaoba";// 数据库名字
	private static Connection con;
	static {
		// 加载MySql的驱动类
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"
					+ database + "?useUnicode=true&characterEncoding=UTF-8",
					username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			String table = "drugstoreinfo";
			final String sql = "SELECT * FROM " + table;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			List<Object> listName = new ArrayList<Object>();
			for (int i = 1; i <= columnCount; i++) {
				// 字段名字
				String columnName = metaData.getColumnName(i);
				listName.add(columnName);
			}
			String path = "E:\\Excel.xls";
			File excelFile = new File(path);
			excelFile.createNewFile(); // 生成文件
			WritableWorkbook workbook = Workbook.createWorkbook(excelFile); // 打开文件

			// 指定sheet的名称
			WritableSheet sheet = workbook.createSheet("第一页 ", 0);
			for (int i = 0; i < listName.size(); i++) {
				Label label = new Label(i, 0, listName.get(i)+"");// 先列后行
				sheet.addCell(label);
				// 将定义好的单元格添加到工作表中
			}

			List<List<Object>> list = new ArrayList<List<Object>>();
			while (rs.next()) {
				List<Object> ls = new ArrayList<Object>();
				for (int i = 1; i <= columnCount; i++) {
					Object object = rs.getObject(i);
					ls.add(object);
				}
				list.add(ls);
			}
			stmt.close();
			rs.close();
			for (int i = 0; i < list.size(); i++) {
				List<Object> li = list.get(i);
				for (int j = 0; j < li.size(); j++) {
					Object object = li.get(j);
					if(object instanceof Integer || object instanceof Double || object instanceof Long){
						Number number = new Number(j, i + 1,Double.valueOf(object.toString()));
						sheet.addCell(number);
					}else {
						Label label = new Label(j, i + 1, li.get(j)+"");// 先列后行
						sheet.addCell(label);
					}
				}
			}

			// 写入数据并关闭文件
			workbook.write();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
