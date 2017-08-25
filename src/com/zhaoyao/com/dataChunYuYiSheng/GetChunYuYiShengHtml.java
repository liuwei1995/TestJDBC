package com.zhaoyao.com.dataChunYuYiSheng;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.zhaoyao.file.AppendToFile;
import com.zhaoyao.file.FileUtils;

public class GetChunYuYiShengHtml {
	
	public static String path = "C:/Users/dell/Desktop/ChunYuYiSheng_20000";
	
	public static ExecutorService fixedThreadPool1 = Executors.newFixedThreadPool(1);

	private static Connection conn;  
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/chun_yu_yi_sheng?Unicode=true&characterEncoding=UTF-8";
	static final String USER = "root";
	static final String PASS = "liuwei";
	
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
		File file = new File(path);
		for (File f : file.listFiles()) {
			if(f.isDirectory()){
				for (final File file2 : f.listFiles()) {
					fixedThreadPool1.execute(new Runnable() {
						@Override
						public void run() {
							long start = System.currentTimeMillis();
							String readFileByLines = FileUtils.readFileByLines(file2.toString());
							try {
								JSONObject jsonObject = new JSONObject(readFileByLines);
								int id = jsonObject.getInt("id");
								Map<String, String> map = getJsoupData(id);//21014
								if (map != null && !map.isEmpty()) {
									String insertTableSQL = "INSERT INTO content"  
											+ "(content,title,contentSource,ctags,createtime) VALUES"  
											+ "(?,?,?,?,?)";  
									try {
										PreparedStatement createStatement = conn.prepareStatement(insertTableSQL);
										int mm = 1;
										for (String key : map.keySet()) {
											Object object = map.get(key);
											createStatement.setString(mm, object.toString());
											++mm;
										}
										String ctags = jsonObject.getString("news_type_txt");
										createStatement.setString(mm, ctags); ++mm;
										String actual_created_time = null;
										try {
											actual_created_time = jsonObject.getString("actual_created_time");
											if (actual_created_time == null || actual_created_time.equals("")) {
												actual_created_time = jsonObject.getString("date");
											}
										} catch (Exception e) {
											e.printStackTrace();
											actual_created_time = jsonObject.getString("date");
										}
										
										try {
											Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(actual_created_time);
											createStatement.setTimestamp(5,getCurrentTimeStamp(parse));
										} catch (ParseException e) {
											e.printStackTrace();
											createStatement.setTimestamp(5,getCurrentTimeStamp(new Date()));
										}  
//									content,title,contentSource,ctags,createtime
										boolean execute = createStatement.execute();
										if (!execute) {
											System.out.println("----------------------------------------成功=="+(System.currentTimeMillis() - start));//ctags
											AppendToFile.appendMethodByFileWriter(new File(path,"提交成功.txt"), file2.toString()+"\n");
										}else {
											AppendToFile.appendMethodByFileWriter(new File(path,"提交失败.txt"), file2.toString()+"\n");
											System.err.println("===================================postDownloadJson=============================="+(System.currentTimeMillis() - start));
										}
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}else {
									System.out.println("========================="+(System.currentTimeMillis() - start));
								}
							} catch (JSONException e) {
								e.printStackTrace();
								AppendToFile.appendMethodByFileWriter(new File(path,"提交失败.txt"), file2.toString()+"\n");
							}
							try {
								Thread.sleep(6000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					});
//					===============================================================================
				}
			}
		}
	}
	private static java.sql.Timestamp getCurrentTimeStamp(Date date) {  
	    java.util.Date today = new java.util.Date();  
	    return new java.sql.Timestamp(date.getTime());  
	   
	} 
	public static synchronized Map<String,String> getJsoupData(int q_id) {
		Map<String,String> map = new HashMap<>();
		try {
			String urlString = "http://weixin.chunyuyisheng.com/pc/article/"+q_id;
			HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				byte[]b = new byte[inputStream.available()];
				inputStream.read(b);
				Document doc = Jsoup.parse(new String(b));
//				 Document doc = Jsoup.connect(urlString).timeout(30000).get();
				Elements elementsByClass = doc.getElementsByClass("news-title");
				if (elementsByClass.size() > 0){
					for (int i = 0; i < elementsByClass.size(); i++) {
						String text = elementsByClass.get(i).text();
						map.put("title",text);
					}
					Elements elementsByClass_sub_title_wrapper = doc.getElementsByClass("sub-title-wrapper");
					for (int i = 0; i < elementsByClass_sub_title_wrapper.size(); i++) {
						String text = elementsByClass_sub_title_wrapper.get(i).text();//来源
						map.put("source",text);
					}
					Elements elementsByClass_news_content = doc.getElementsByClass("news-content");
					String s = elementsByClass_news_content.toString();
					
					s = s.replace("data-src","src");
					map.put("content",s);
				}else {
					System.out.println("===============================");
				}
			}else {
				AppendToFile.appendMethodByFileWriter(new File(path,"失败urlString.json"), urlString+"\n");
		    	System.err.println("================================失败\t"+urlString);
			}
		} catch (IOException e) {
			e.printStackTrace();
//             ++m;
			
		}
		 return map;
	}
	    /**
	     * POST请求获取数据
	     */
	    public static String postDownloadJson(String path,String post){
	        URL url = null;
	        PrintWriter printWriter = null;
	        BufferedInputStream bis = null;
	        ByteArrayOutputStream bos = null;
	        try {
	            url = new URL(path);
	            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	            httpURLConnection.setConnectTimeout(30*1000);//连接超时 单位毫秒
	            httpURLConnection.setRequestMethod("POST");// 提交模式
	            // 设置通用的请求属性
	            httpURLConnection.setRequestProperty("accept", "*/*");
	            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
	            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
	            httpURLConnection.setRequestProperty("contentType", "utf-8");
	            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(post.length()));
//	            httpURLConnection.setRequestProperty("Content-type", "text/html");
	            // conn.setReadTimeout(2000);//读取超时 单位毫秒
	            // 发送POST请求必须设置如下两行
	            httpURLConnection.setDoOutput(true);
	            httpURLConnection.setDoInput(true);
	            // 获取URLConnection对象对应的输出流
	            printWriter = new PrintWriter(httpURLConnection.getOutputStream());
	            // 发送请求参数
	            printWriter.write(post);//post的参数 xx=xx&yy=yy
	            // flush输出流的缓冲
	            printWriter.flush();
	            //开始获取数据
	            bis = new BufferedInputStream(httpURLConnection.getInputStream());
	            bos = new ByteArrayOutputStream();
	            int len;
	            byte[] arr = new byte[1024];
	            while((len = bis.read(arr))!= -1){
	                bos.write(arr,0,len);
	                bos.flush();
	            }
	            bos.close();
	            return bos.toString("utf-8");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally{
	            close(printWriter);
	            close(bis);
	            close(bos);
	        }
	        return null;
	    }
	    public static void close(Closeable c) {
	        try {
	            if (c != null) {
	                c.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
