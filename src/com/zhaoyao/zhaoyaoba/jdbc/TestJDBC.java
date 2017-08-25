package com.zhaoyao.zhaoyaoba.jdbc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.mysql.jdbc.DatabaseMetaData;
public class TestJDBC<T> {

	// 连接MySql数据库，用户名和密码都是root
	static String URL = "jdbc:mysql://localhost:3306/zyb";
//	static String url = "jdbc:mysql://localhost:3306/miaomiao";
	static String username = "root";
	static String password = "liuwei";
//	static String URL = "jdbc:mysql://localhost:3306/zhaoyaoba";
//	static String password = "liuwei";
	/**数据库的表名**/
	public static String [] test_table = new String[]{"notice","after_sale","city","content"
	,"content_category"	
	,"county"	
	,"coupons"	
	,"drug"	
	,"drug_category"	
	,"drug_comment"	
	,"end_address"	
	,"end_coupons_relation"	
	,"end_needs"	
	,"end_qualifications"	
	,"end_user"	
	,"feedback"	
	,"helper_center"	
	,"inner_group"	
	,"inner_permission_relation"	
	,"inner_user"	
	,"instructions_head"	
	,"instructions_value"	
	,"notice"	
	,"order_drug_relationship"	
	,"permissions_menu"	
	,"province"	
	,"sell_drug"	
	,"sell_notice"	
	,"sell_user"	
	,"seller_activity"	
	,"seller_needs"	
	
	};
	public static void main(String[] args){
		Connection con = null;
		try {
			// 加载MySql的驱动类
			Class.forName("com.mysql.jdbc.Driver");
			 con = DriverManager.getConnection(
					 "jdbc:mysql://localhost:3306/zyb?useUnicode=true&characterEncoding=UTF-8"
					 , username,
					password);
		       
		       ResultSet rs = con.createStatement().executeQuery("show tables;");
		       List<String> list = new ArrayList<String>();
		       while (rs.next()) {
		    	   list.add(rs.getString(1));
		    	}
		       test_table = new String[list.size()];
		       for (int i = 0; i < list.size(); i++) {
		    	   test_table[i] = list.get(i);
		       }
		       txt(con,"zyb");
//			 copy(con);
//			 if(true)return;
		       if(false)
			 red(con);
		} catch (ClassNotFoundException e) {
			System.out.println("驱动加载失败！");
			e.printStackTrace();
		} catch (SQLException se) {
			System.out.println("数据库连接失败！");
			se.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(con!=null)
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(fos!=null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		 
	}
	/**
	 * 所有表的名字和表的列名  保存到文件fileName里面
	 * @param con
	 * @param fileName
	 * @throws IOException
	 */
	private static void txt(Connection con,String fileName) throws IOException {
		try {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < test_table.length; i++) {
				sb.append("----------------------------------------\n");
				Statement statement = con.createStatement();
				ResultSet rs = statement.executeQuery("SELECT* FROM "+test_table[i] +" LIMIT 0,1");
				sb.append(test_table[i]+"\n\n");
				ResultSetMetaData metaData = rs.getMetaData();
				int columnCount = metaData.getColumnCount();
				for (int j = 1; j < columnCount; j++) {
					String columnName = metaData.getColumnName(j);
					sb.append(columnName+"\n");
				}
				statement.close();
				rs.close();
			}
			byte[] bytes = sb.toString().getBytes();
			fos = new FileOutputStream(new File("C:/Users/dell/Desktop/entity/"+fileName+".txt"));
			InputStream is = new ByteArrayInputStream(bytes);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine())!=null) {
				fos.write(s.getBytes());
				String newline = System.getProperty("line.separator");
				//TODO 使换行符转为字节数组
				//byte [] newLine="/r/n".getBytes();
				fos.write(newline.getBytes());
			}
				 fos.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static  StringBuffer buffer = new StringBuffer();
	private static FileOutputStream fos;
	public static void red(Connection con) throws SQLException, InterruptedException, IOException {
//		for (int i = 0; i < test_table.length; i++) {
//			final String sql = "SELECT * FROM "+test_table[i];
//			startCallableStatement(con, sql);
//		}
		String table = "consignee_info";
//		final String sql = "SELECT *FROM pro_med_39jkw_zxy_product WHERE id = 1";
		final String sql = "SELECT * FROM "+table;
		startCallableStatement(con, sql);
//		druginfo
		if(table.contains("_")){
			String[] split = table.split("_");
			table = "";
			for (int i = 0; i < split.length; i++) {
				String s = split[i];
				table += s.substring(0, 1).toUpperCase()+s.substring(1, s.length());
			}
		}
		 System.out.println(buffer.toString());
		  fos = new FileOutputStream(new File("C:/Users/dell/Desktop/entity/"+table+".java"));
		 byte[] bytes = buffer.toString().getBytes();
		 InputStream is = new ByteArrayInputStream(bytes);
		 InputStreamReader isr = new InputStreamReader(is);
		 BufferedReader br = new BufferedReader(isr);
		 String s = "";
		 while ((s = br.readLine())!=null) {
			 fos.write(s.getBytes());
			 String newline = System.getProperty("line.separator");
			//TODO 使换行符转为字节数组
			 //byte [] newLine="/r/n".getBytes();
			 fos.write(newline.getBytes());
		}
		 fos.close();
	}
	public static void copy(Connection con) throws SQLException, IOException {
		 StringBuffer buffer = new StringBuffer();
		String table = "delivery_user_info";
		final String sql = "SELECT * FROM "+table;
		Statement stmt = con.createStatement() ;   
	       ResultSet rs = stmt.executeQuery(sql);
	       ResultSetMetaData metaData = rs.getMetaData();
	       int columnCount = metaData.getColumnCount();
	       if(columnCount>0){
	    	   String tableName = metaData.getTableName(1);
	    	   buffer.append(tableName+"=============================\n");
	       }
	       List<String> list = new ArrayList<String>();
	       for (int i = 1; i <= columnCount; i++) {
	    	   //字段名字
	    	   String columnName = metaData.getColumnName(i);
	    	   String columnTypeName = metaData.getColumnTypeName(i);
	    	   int columnDisplaySize = metaData.getColumnDisplaySize(i);
	    	   while (columnName.contains("_")) {
	    		   String[] split = columnName.split("_");
	    		   String s = "";
	    		   for (int j = 0; j < split.length; j++) {
	    			   if(j == 0)
	    				   s += split[j];
	    			   else{
	    				   s += split[j].substring(0,1).toUpperCase() + split[j].substring(1, split[j].length());
	    			   }
	    		   }
	    		   columnName = s;
	    	   }
	    	   buffer.append("@Column(name=\""+columnName+"\",length = "+columnDisplaySize+")"+"\n");
	    	   if(columnTypeName.equals("VARCHAR")){
	    		   buffer.append("private String "+columnName+" ;"+"\n");
	    	   }else  if(columnTypeName.equals("INT")||"BIGINT".equals(columnTypeName)){
	    		   buffer.append("private int "+columnName+" ;"+"\n");
	    	   }else {
	    		   buffer.append("private String "+columnName+" ;"+"\n");
	    	   }
	    	   list.add(columnName);
	       }
	       byte[] bytes = buffer.toString().getBytes();
			 InputStream is = new ByteArrayInputStream(bytes);
			 InputStreamReader isr = new InputStreamReader(is);
			 BufferedReader br = new BufferedReader(isr);
			 String s = "";
			 FileOutputStream fos = new FileOutputStream(new File("C:/Users/dell/Desktop/entity/test"+table+".txt"));
			 while ((s = br.readLine())!=null) {
				 fos.write(s.getBytes());
				 String newline = System.getProperty("line.separator");
				//TODO 使换行符转为字节数组
				 //byte [] newLine="/r/n".getBytes();
				 fos.write(newline.getBytes());
			}
			 fos.close();
//			 StringBuilder sb = new StringBuilder();
//			 sb.append("INSERT INTO "+table+" (");
//			 for (int i = 0; i < list.size(); i++) {
//				 sb.append(""+list.get(i)+",");
//			}
//			 int length = sb.length();
//			 sb.delete(length-1, length);
//			 sb.append(") VALUES ");
			 Connection connn = DriverManager.getConnection(
	    			 "jdbc:mysql://localhost:3306/zhaoyaoba?useUnicode=true&characterEncoding=UTF-8"
	    			 , username,
						password);
	       while(rs.next())
    	   {
	    	   StringBuilder sb = new StringBuilder();
				 sb.append("INSERT INTO "+"deliveryUserInfo"+" (");
				 for (int i = 0; i < list.size(); i++) {
					 String string = list.get(i);
					 if("drugStoreId".equals(string))continue;
					 sb.append(""+string+",");
				}
				 int length = sb.length();
				 sb.delete(length-1, length);
				 sb.append(") VALUES ");
	    	   sb.append("(");
    	    for(int m=1;m<=columnCount;m++)
    	    {
    	    	//TODO  获取字段里面的值
    	    	String value = rs.getString(m);
    	    	if(value != null && value.contains("'")){
    	    		String ss = "";
    	    		String[] split = value.split("'");
    	    		for (int i = 0; i < split.length; i++) {
    	    			ss += split[i];
					}
    	    		value = ss;
    	    	}
    	    	String string = 
    	    			list.get(m-1);
//    	    	metaData.getColumnName(m);
    	    	System.out.println("string======"+string);
    	    	 if("drugStoreId".equals(string))continue;
//    	    	if ("getNewTime".equals(string)
//    	    			||"commentNewTime".equals(string)
//    	    			||"cancelTime".equals(string)
//    	    			||"setupNewTime".equals(string)
//    	    			) {
//    	    		if(value == null|| "".equals(value)|| "null".equals(value)){
//    	    			value = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
//    	    		}
//				}
    	    	System.out.println(list.get(m-1)+" : "+value+"\n");
    	    	sb.append("'"+value+"',");
    	    }
    	    	int len = sb.length();
    	    	sb.delete(len-1, len);
    	    	sb.append(")");
//    	    	sb.append("),");
    	    	 Statement statement = connn.createStatement();
    	    	 int executeUpdate = statement.executeUpdate(sb.toString());
    	    	 System.out.println("executeUpdate : "+executeUpdate+"\n");
    	   }
//	       	int len = sb.length();
//	    	sb.delete(len-1, len);
//	    	String string = sb.toString();
//	    	System.out.println(string+"\n");
//	    	 Connection connn = DriverManager.getConnection(
//	    			 "jdbc:mysql://localhost:3306/zhaoyaoba"
//	    			 , username,
//						password);
	    	 con.close();
//	    	 Statement statement = connn.createStatement();
//	    	 int executeUpdate = statement.executeUpdate(string);
//	    	 System.out.println("executeUpdate : "+executeUpdate+"\n");
	}
	public static void startCallableStatement(Connection con,String sql) throws SQLException {
			Statement stmt = con.createStatement() ;   
	       ResultSet rs = stmt.executeQuery(sql) ;
//	       ResultSet rs = stmt.executeQuery("SELECT * FROM instructions_value") ;
	       ResultSetMetaData metaData = rs.getMetaData();
	       int columnCount = metaData.getColumnCount();
//	       StringBuffer buffer = new StringBuffer();
	       String tableName = "";
	       if(columnCount>0){
	    	    tableName = metaData.getTableName(1);
//	    	   buffer.append(tableName+"=============================\n");
	       }
//	       TODO 
	       buffer.append("package com.zykj.vo.drug;\n");
	       buffer.append("import java.io.Serializable;\n");
	       buffer.append("import javax.persistence.Column;\n");
	       buffer.append("import javax.persistence.Entity;\n");
	       buffer.append("import javax.persistence.GeneratedValue;\n");
	       buffer.append("import javax.persistence.GenerationType;\n");
	       buffer.append("import javax.persistence.Id;\n");
	       buffer.append("import javax.persistence.Table;\n");
	       buffer.append("\n");
	       buffer.append("@Entity\n");
	       if(tableName!=null && tableName.contains("_")){
	    	   String[] split = tableName.split("_");
	    	   tableName = "";
	    	   for (int i = 0; i < split.length; i++) {
	    		   String s = split[i];
	    		   s = s.substring(0, 1).toUpperCase()+s.substring(1, s.length());
	    		   tableName += s;
			}
	       }
	       buffer.append("@Table(name = \""+tableName+"\")\n");
	       buffer.append("public class "+tableName+" implements Serializable {\n\n\n");
	       buffer.append("@Id\n@GeneratedValue(strategy = GenerationType.AUTO)\n\n");
	       
//	       buffer.append("\n\n\n");
//	       buffer.append("}\n");
//	       TODO  遍历有多要字段      角标必须要从  1 开始  
	       for (int i = 1; i <= columnCount; i++) {
	    	   //字段名字
	    	   String columnName = metaData.getColumnName(i);
	    	   String columnTypeName = metaData.getColumnTypeName(i);
	    	   int columnDisplaySize = metaData.getColumnDisplaySize(i);
	    	   while (columnName.contains("_")) {
//	    			   int indexOf = columnName.indexOf("_");
//	    			   columnName = columnName.substring(indexOf, indexOf+1).replace("_", "").toUpperCase();
	    		   String[] split = columnName.split("_");
	    		   String s = "";
	    		   for (int j = 0; j < split.length; j++) {
	    			   if(j == 0)
	    				   s += split[j];
	    			   else{
	    				   s += split[j].substring(0,1).toUpperCase() + split[j].substring(1, split[j].length());
	    			   }
	    		   }
	    		   columnName = s;
	    	   }
	    	   buffer.append("@Column(name=\""+columnName+"\",length = "+columnDisplaySize+")"+"\n");
	    	   if(columnTypeName.equals("VARCHAR")){
	    		   buffer.append("private String "+columnName+" ;"+"\n");
	    	   }else  if(columnTypeName.equals("INT")||"BIGINT".equals(columnTypeName)){
	    		   buffer.append("private int "+columnName+" ;"+"\n");
	    	   }else {
	    		   buffer.append("private String "+columnName+" ;"+"\n");
	    	   }
//	    	   buffer.append(columnName+": "+columnTypeName+":"+columnDisplaySize+"\n");
//	    	   int columnType = metaData.getColumnType(i);
//	    	   String columnTypeName = metaData.getColumnTypeName(i);
//	    	   String columnClassName = metaData.getColumnClassName(i);
//	    	   String catalogName = metaData.getCatalogName(i);
//	    	   String columnClassName2 = metaData.getColumnClassName(i);
//	    	   String schemaName = metaData.getSchemaName(i);
//	    	   System.out.println(i+"------------------\n"+columnClassName
//	    			   +"\ncolumnName===="+columnName
//	    			   +"\ncolumnType===="+columnType
//	    			   +"\ncolumnTypeName==="+columnTypeName
//	    			   +"\ncatalogName==="+catalogName
//	    			   +"\ncolumnClassName2==="+columnClassName2
//	    			   +"\n"+"schemaName==="+schemaName
//	    			   );
	       }
	       buffer.append("\n\n\n");
	       buffer.append("}\n");
//	       System.out.println(buffer.toString());
//	       TODO  遍历获取每条数据的数据   是否还有下一条
//	       while(rs.next())
//    	   {
//    	    for(int m=1;m<=columnCount;m++)
//    	    {
//    	    	//TODO  获取字段里面的值
//    	     System.out.print(rs.getString(m));
//    	     System.out.print("\t\n");
//    	    }
//    	   }
	       
	}
	
	public static final String columnTypeName_INT = "INT";
	public static final String columnTypeName_VARCHAR = "VARCHAR";
	public static final String columnTypeName_DATE = "DATE";
	public static void name() {
		Statement  stmt = null;
		Connection   conn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver"); //注册驱动  
				//获取连接字符串   
       conn = DriverManager.getConnection(URL,username,password);  
      stmt =  conn.createStatement();  
//      String  query="create  table gubiao (open float,high float,low float,close float,amount integer,money float)";//创建数据库属性  
      String sqlString = "select * from gubiao where id = object_id(N\'[dbo].[tablename]\') and OBJECTPROPERTY(id, N\'IsUserTable\') = 1";
      ResultSet rs = stmt.executeQuery(sqlString);
      System.out.println(rs.getCursorName());
      stmt.close();  
      conn.close();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				if(conn!=null)
					try {
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				if (stmt!=null) {
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} 
		
	}
	public static Connection conn;
	public static void createTable(){
		Statement  stmt = null;
			try {
       conn = getConnection(URL,username,password);
      stmt =  conn.createStatement();  
      String sqlString = "select count(*) from gubiao";
      boolean execute = stmt.execute(sqlString);
      if (execute) {
//    	  stmt.executeQuery("");
      }
      System.out.println(execute);
      String  query="create  table gubiao (ID int PRIMARY  key,open float,high float,low float,close float,amount integer,money float)";//创建数据库属性  
//      String  query="create  table  gubiao (open float,high float,low float,close float,amount integer,money float)";//创建数据库属性  
      stmt.executeUpdate(query);  
      stmt.close();  
      conn.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}finally{
				if(conn!=null)
					try {
						conn.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				if (stmt!=null) {
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} 
	}
	public void insert() throws SQLException {
		 Connection   conn = DriverManager.getConnection(URL,username,password);  
         Statement  s=  conn.createStatement();  
        //insert into gubiao(low ,high) select 1,2 UNION select 1,3
         String r1="insert  into gubiao value()";//在股票数据库表中插入数据  
         s.executeUpdate(r1);  
        
         s.close();  
         conn.close();  
	}
	
	public void update() throws SQLException {
		 Connection   conn = DriverManager.getConnection(URL,username,password);  
		//查询数据库并把数据表的内容输出到屏幕上  
        Statement  s = conn.createStatement();  
        String sql = "UPDATE  gubiao  set money=? where  zeh =?";
        s.executeUpdate(sql);
        ResultSet  rs = s.executeQuery("select * from  gubiao");  
        while(rs.next()){  
            System.out.println(rs.getString("")+"\t"+rs.getString("")+"\t"+rs.getString("")+"\t"+rs.getString("")+"\t"+rs.getString("")+"\t"+rs.getString("")+"\t"+rs.getString("")+"\t"+rs.getString(""));  
            //输出该行数据  
        }
        
        s.close();  
        conn.close();  
	}
	/**驱动是否注册成功*/
	public static boolean jdbcDriver = false;
	/**注册驱动  */
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			jdbcDriver = true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/***
	 * 创建查询语句sql
	 * @param table_name  表名
	 * @param columnName  要查询的字段
	 * @return
	 */
	public static String createSql_Select_(String table_name,String ...columnName) {
		String sql = "SELECT * ";
		for (int i = 0; i < columnName.length; i++) {
			if (i==0){
				sql = sql.replace("*", "");
			}else {
				sql += ",";
			}
			sql += columnName[i];
		}
		return sql +=" FROM "+table_name;
	}
	public static ResultSetMetaData getResultSetMetaData_Select_(String sql) throws SQLException {
		ResultSetMetaData metaData = getMetaData(getResultSetByQuery(getStatement(getConnection()), sql));
		return metaData;
	}
	@Test
	public static void named() throws InstantiationException, IllegalAccessException, SQLException {
		List<Drug> query = JDBC.query(Drug.class, "t_drug", null, "id", new Integer[]{5,6});
		System.out.println(query.toString());
	}
	public static void main1(String[] args) {
//		try {
//			ResultSet resultSetByQuery = getResultSetByQuery(getStatement(getConnection()), createSql_Select_(test_table[4]));
//			ResultSetMetaData select_ = getResultSetMetaData_Select_(createSql_Select_(test_table[4], "parentId","isParent"));
//			System.out.println(select_.getColumnCount());
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			queryBy_(new Drug());
////			queryBy_(Drug.class);
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		try {
//			JDBC.createTable(Drug.class);
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		try {
//			List<Object[]> list = new ArrayList<Object[]>();
//			for (int i = 0; i < 10; i++) {
//				String[] s = new String[]{i+"",""+i,"drugName:"+i};
//				list.add(s);
//			}
//			int insert = JDBC.insert(JDBC.createInsertSql("t_drug", new String[]{"id","code","drugName"}, list));
//			System.out.println(insert);
//			System.out.println(System.currentTimeMillis());
//			List<Drug> query = JDBC.query(Drug.class, "t_drug", null, "id", null);
////			List<Drug> query = JDBC.query(Drug.class, "t_drug", null, "id", new Integer[]{5,6});
//			System.out.println(query.toString());
//			System.out.println(System.currentTimeMillis());
//			boolean deleteManyDataById = JDBC.DeleteManyDataById("gubiao", "ID", 48,49);
////			System.out.println(deleteManyDataById);
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		String columnBy = JDBC.createQuerySpecifiedColumnBy("t_drug", null, new String[]{"code","id"}, new Object[]{3,"5"},new Object[]{4,"6"});
//		System.out.println(columnBy);
//		try {
//			List<Drug> query = JDBC.query(Drug.class, "t_drug", null, "id", null);
//			ResultSet resultSet = JDBC.querySpecifiedColumn(columnBy);
//			ResultSetMetaData metaData = resultSet.getMetaData();
//			while (resultSet.next()) {
//				for (int i = 1; i <= metaData.getColumnCount(); i++) {
//					int columnType = metaData.getColumnType(i);
//					if (columnType==12) {
//						String string = resultSet.getString(i);
//						System.out.println(metaData.getColumnName(i)+":\t"+string);
//					}else if (columnType==4) {
//						int int1 = resultSet.getInt(i);
//						System.out.println(metaData.getColumnName(i)+":\t"+int1);
//					}
//				}
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String intoSql = JDBC.createSelectIntoSql("state", "t_drug", "drug");
//		System.out.println(intoSql);
//		String sql = JDBC.createJoinSql("t_drug",new String[]{"drug"}, new String[]{"t_drug.id"}, new String[]{"1"},new String[]{"t_drug.id","drug.drugId"});
////				createJoinSql("t_drug", new String[]{"drug"}, new String[]{"t_drug.id","drug.drugId"});
//		System.out.println(sql);
//		String createLIMIT = JDBC.createLimitSql("table_name", new String[]{"code"}, 5, 5);
//		System.out.println(createLIMIT);
//		try {
//			String createUpdateSql = JDBC.createUpdateSql("t_drug", new String[]{"createTime"}, new String[]{new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())}, new String[]{"id"}, new String[]{"7"});
//			int executeUpdate2 = JDBC.createStatement().executeUpdate(createUpdateSql);
//			System.out.println(executeUpdate2);
////			int executeUpdate = JDBC.createStatement().executeUpdate(JDBC.createDeleteSql("t_drug", "id", new Integer[]{1}));
//////			int executeUpdate = JDBC.createStatement().executeUpdate(JDBC.createDeleteSql("t_drug", "id", 1,2,3,4,5,6));
////			System.out.println(executeUpdate);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		try {
//			List<Object> list = JDBC.query(new String[]{"drug_code"}, "drug_info", new String[]{"drug_code"}, "drug_id", new Integer[]{23});
//			System.out.println(list.toString());
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		String columnBy = JDBC.createQuerySpecifiedColumnBy("drug_info", new String[]{"drug_code"}, new String[]{"drug_id"}, new Integer[]{23});
//		try {
//			ResultSet resultSet = JDBC.querySpecifiedColumn(columnBy);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public static <T> void queryBy_(T t) throws InstantiationException, IllegalAccessException {
//		Field[] fields = t.getClass().asSubclass(t.getClass()).getFields();
//		Class<?> asSubclass = t.getClass().asSubclass(t.getClass());
//		Field[] fields = asSubclass.getDeclaredFields();
//		Object newInstance = t.getClass().cast(t).getClass().newInstance();
//		Class<? extends Object> clazz = newInstance.getClass();
		Class<? extends Object> clazz = t.getClass();
		Field[] fields = clazz.getDeclaredFields();// 获得Activity中声明的字段
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			System.out.println(field.toString());
			boolean annotationPresent = field.isAnnotationPresent(InjectTable.class);
			System.out.println(annotationPresent+"\n");
			if (annotationPresent) {
				InjectTable annotation = field.getAnnotation(InjectTable.class);
				System.out.println(annotation.ColumnName()+"\n");
			}
			boolean annotationPresent2 = clazz.isAnnotationPresent(InjectTable.class);
			System.out.println(annotationPresent2+"\n");
			if (annotationPresent2) {
				InjectTable annotation2 = clazz.getAnnotation(InjectTable.class);
				String tableName = annotation2.tableName();
				System.out.println(tableName+"\n");
			}
		}
	}
	private static void testSql() throws SQLException {
		ResultSetMetaData metaData = getMetaData(getResultSetByQuery(getStatement(getConnection()), createSql_Select_(test_table[0])));
		if(metaData!=null){
			int columnCount = metaData.getColumnCount();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = metaData.getColumnName(i);
				String columnTypeName = metaData.getColumnTypeName(i);
				System.out.println("columnName:"+columnName+"\tcolumnTypeName:"+columnTypeName+"\n");
			}
		}
	}
	/**
	 *  连接MySql数据库，用户名和密码都是root
		@param static String url = "jdbc:mysql://localhost:3306/miaomiao";
		@param 	static String username = "root";
		@param 	static String password = "liuwei";
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection(String ...URL_username_password) throws SQLException {
		if(URL_username_password.length == 3){
			return DriverManager.getConnection(URL_username_password[0],URL_username_password[1],URL_username_password[2]);  
		}else if (URL_username_password.length == 2) {
			return DriverManager.getConnection(URL_username_password[0],URL_username_password[1],password);  
		}else if (URL_username_password.length == 1) {
			return DriverManager.getConnection(URL_username_password[0],username,password);  
		}
		return DriverManager.getConnection(URL,username,password);  
	}
	public static Statement createStatement(Connection con) throws SQLException {
		return con.createStatement();
	}
	public static DatabaseMetaData getDatabaseMetaData(Connection conn) throws SQLException {
		return (DatabaseMetaData) conn.getMetaData();
	}
	public static ResultSetMetaData getMetaData(ResultSet rs) throws SQLException {
		return rs.getMetaData();
	}
	public static ResultSet getResultSetByQuery(Statement stmt,String sql) throws SQLException {
		return stmt.executeQuery(sql) ;
	}
	public static Statement getStatement(Connection con) throws SQLException {
		return con.createStatement();
	}
}
