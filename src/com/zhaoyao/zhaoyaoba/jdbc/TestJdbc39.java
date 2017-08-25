package com.zhaoyao.zhaoyaoba.jdbc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestJdbc39 {

	// 连接MySql数据库，用户名和密码都是root
	static String URL = "jdbc:mysql://localhost:3306/39";
//	static String url = "jdbc:mysql://localhost:3306/miaomiao";
	static String username = "root";
	static String password = "liuwei";
	public static void main(String[] args) {
		// 加载MySql的驱动类
		try {
			Class.forName("com.mysql.jdbc.Driver");
			 Connection con = DriverManager.getConnection(
					 "jdbc:mysql://localhost:3306/39?useUnicode=true&characterEncoding=UTF-8"
					 , username,
					password);
			   
			 
			 File file = new File("C:/Users/dell/Desktop/39_新");
			 File[] listFiles = file.listFiles();
			 for (int j = 0; j < listFiles.length; j++) {
				 File f = listFiles[j];
					if(f.isDirectory()){
						for (File file2 : f.listFiles()) {
							StringBuffer sb_data = new StringBuffer();
							FileInputStream inputStream = null;
							BufferedReader br = null;
					 		try {
								inputStream = new FileInputStream(file2);
								br = new BufferedReader(new InputStreamReader(inputStream));
								String s = "";
								while ((s = br.readLine())!=null) {
									sb_data.append(s);
								}
								br.close();
								inputStream.close();
							} catch (FileNotFoundException e) {
								e.printStackTrace();continue;
							} catch (IOException e) {
								e.printStackTrace();continue;
							}finally{
								if(br != null)
									try {
										br.close();
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								if(inputStream != null)
									try {
										inputStream.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
							}
					 		JSONObject jsonObject = new JSONObject(sb_data.toString());
							JSONArray jsonArray = jsonObject.getJSONObject("results").getJSONArray("List");
							if(jsonArray == null || jsonArray.length() <= 0){
								file2.delete();
								System.out.println("删除了=============="+file2.getPath());
							}else {
								StringBuilder sb = new StringBuilder();
								sb.append("INSERT INTO "+"drug"+" (");
								sb.append("_id,");//2266280,
						        sb.append("avgprice,");//",
						        sb.append("aliascn,");//",
						        sb.append("namecn,");//高效单质银妇用抗菌凝胶",
						        sb.append("newotc,");//3,
						        sb.append("medcare,");//0,
						        sb.append("basemed,");//",
						        sb.append("refdrugcompanyname,");//",
						        sb.append("gongneng,");//适用于缓解细菌性阴道炎、外阴阴道假丝酵母菌病(外阴阴道念珠菌病)、宫颈炎(宫颈肥大)引起的瘙痒、疼痛、白带异常、阴部异味、宫颈糜烂。",
						        sb.append("refdrugbrandname,");//",
						        sb.append("score,");//0,
						        sb.append("listimg,");//",
						        sb.append("titleimg,");//",
						        sb.append("price,");//",
						        sb.append("maxstoreprice,");//",
						        sb.append("minstoreprice,");//",
						        sb.append("indicationlabel,");//",
						        sb.append("refmedicaretype,");//"
						        sb.append("type,");//"
				        
								 int length = sb.length();
								 sb.delete(length-1, length);
								 sb.append(") VALUES ");
									if(jsonArray != null && jsonArray.length() > 0){
										for (int i = 0; i < jsonArray.length(); i++) {
											 sb.append("(");
											JSONObject jsonObj = jsonArray.getJSONObject(i);
//											int int1 = jsonObj.getInt("_id");
//											if(int1 == 2029739){
//												System.out .println(jsonObj.toString());
//											}
											sb.append("'"+jsonObj.get("_id")+"',");//2266280,
											sb.append("'"+jsonObj.get("avgprice")+"',");//"",
											sb.append("'"+jsonObj.get("aliascn")+"',");//"",
											sb.append("'"+jsonObj.getString("namecn").replaceAll("'", "''")+"',");//"高效单质银妇用抗菌凝胶",
											sb.append("'"+jsonObj.get("newotc")+"',");//3,
											sb.append("'"+jsonObj.get("medcare")+"',");//0,
											sb.append("'"+jsonObj.get("basemed")+"',");//"",
											sb.append("'"+jsonObj.get("refdrugcompanyname")+"',");//"",
											sb.append("'"+jsonObj.getString("gongneng").replaceAll("'", "''")+"',");//"适用于缓解细菌性阴道炎、外阴阴道假丝酵母菌病(外阴阴道念珠菌病)、宫颈炎(宫颈肥大)引起的瘙痒、疼痛、白带异常、阴部异味、宫颈糜烂。",
											sb.append("'"+jsonObj.get("refdrugbrandname")+"',");//"",
											sb.append("'"+jsonObj.get("score")+"',");//0,
											sb.append("'"+jsonObj.get("listimg")+"',");//"",
											sb.append("'"+jsonObj.get("titleimg")+"',");//"",
											sb.append("'"+jsonObj.get("price")+"',");//"",
											sb.append("'"+jsonObj.get("maxstoreprice")+"',");//"",
											sb.append("'"+jsonObj.get("minstoreprice")+"',");//"",
											sb.append("'"+jsonObj.get("indicationlabel")+"',");//"",
											sb.append("'"+jsonObj.get("refmedicaretype")+"',");//""
											File parentFile = file2.getParentFile();
											String name = parentFile.getName();
											sb.append("'"+name.split("_")[1]+"'");//""
											sb.append("),");
										}
										int len = sb.length();
						    	    	sb.delete(len-1, len);
									}
						    	  
					    	    	 Statement statement = con.createStatement();
					    	    	 System.out.println(sb.toString());
					    	    	 int executeUpdate = statement.executeUpdate(sb.toString());
					    	    	 System.out.println("executeUpdate : "+executeUpdate+"\n");
							}
						}
					}else {
						
					}
				
			}
			 
//			   ResultSet rs = con.createStatement().executeQuery("show tables;");
				
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
