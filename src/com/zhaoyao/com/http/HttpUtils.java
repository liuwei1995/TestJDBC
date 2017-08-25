package com.zhaoyao.com.http;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhaoyao.file.AppendToFile;

public class HttpUtils {
	
	private HttpUtils() {
	}
	public static HttpUtils newInstance() {
		return new HttpUtils();
	}
	public synchronized byte[] post(String url){
		return commit(url, "POST");
	}
	public synchronized byte[] get(String url){
		return commit(url, "GET");
	}
	public synchronized byte[] get(String url,String createPostData_genernatePass){
		return commit(url+"?"+createPostData_genernatePass, "GET");
	}

	private synchronized byte[] commit(String hurl,String method){
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			URL url = new URL(hurl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
			connection.setRequestMethod(method);
			connection.setReadTimeout(30*000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.connect();
			if(HttpURLConnection.HTTP_OK == connection.getResponseCode()){
				is = connection.getInputStream();
				baos = new ByteArrayOutputStream(is.available());
				byte[] b = new byte[1024];
				int number = 0;
				while ((number = is.read(b)) != -1) {
					baos.write(b, 0, number);
				}
				return baos.toByteArray();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			File file = new File("C:/Users/dell/Desktop/ChunYuYiSheng_20000","ChunYuYiSheng.txt");
			AppendToFile.appendMethodByFileWriter(file, hurl+"\n");
		}finally{
			try {
				if(is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(baos != null){
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static final String CHARSET = "gb2312";
	
	public synchronized String post2String(String url){
		return commit2String(url, "POST",CHARSET);
	}
	public synchronized String post2String(String url,String charset){
		return commit2String(url, "POST",charset);
	}
	public synchronized String get2String(String url){
		return commit2String(url, "GET",CHARSET);
	}
	public synchronized String get2String(String url,String charset){
		return commit2String(url, "GET",charset);
	}
	
	
	private synchronized String commit2String(String hurl,String method,String charset){
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			URL url = new URL(hurl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
			connection.setRequestMethod(method);
			connection.setReadTimeout(30*000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko");
			connection.connect();
			if(HttpURLConnection.HTTP_OK == connection.getResponseCode()){
				is = connection.getInputStream();
				StringBuffer buffer = new StringBuffer();
				byte[] bytes = new byte[1024]; 
				List<String> list = connection.getHeaderFields().get("Content-Type");
				String string = list.get(0);
				String[] split = string.split(";");
				if (split.length > 1) {
					for(int n ; (n = is.read(bytes))!=-1 ; ){ 
						buffer.append(new String(bytes,0,n,split[1].split("=")[1]));
					}
				}else {
					for(int n ; (n = is.read(bytes))!=-1 ; ){ 
						buffer.append(new String(bytes,0,n,charset));
					}
				}
				return buffer.toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
//			File file = new File("C:/Users/dell/Desktop/ChunYuYiSheng_20000","ChunYuYiSheng.txt");
//			AppendToFile.appendMethodByFileWriter(file, hurl+"\n");
		}finally{
			try {
				if(is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(baos != null){
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	public  String createURLEncoderPostData(Map<String, String> map) {
		 StringBuilder sb = new StringBuilder();
	        for (String key : map.keySet()) {
	            Object object = map.get(key);
	            String replace = object.toString()
	            .replace("%", "%25")
	            .replace("+", "%2B")
	            .replace(" ", "%20")
	            .replace("/", "%2F")
	            .replace("?", "%2B")
	            .replace("#", "%23")
	            .replace("&", "%26")
	            .replace("=", "%3D")
	            ;
	            try {
					sb.append(key+"="+URLEncoder.encode(replace,"UTF-8")+"&");
				} catch (UnsupportedEncodingException e) {
					return URLEncoder.encode(replace);
				}
	        }
	        StringBuilder deleteCharAt = sb.deleteCharAt(sb.length()-1);
	        return deleteCharAt.toString();
	}
	public static  String createPostData(Map<String, String> map) {
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			Object object = map.get(key);
			sb.append(key+"="+(object == null?object:object.toString())+"&");
		}
		StringBuilder deleteCharAt = sb.deleteCharAt(sb.length()-1);
		return deleteCharAt.toString();
	}
	  /**
     * POST请求获取数据
     */
    public  String postData2String(String path,Map<String, String> map){
    	return postData2String(path,createURLEncoderPostData(map));
    }
    /**
     * POST请求获取数据
     */
    public  String postData2String(String path,String data){
    	return data2String(path, "POST", data);
    }
    public  String getData2String(String path,String data){
    	return data2String(path, "GET", data);
    }
    
	private  String data2String(String path,String method,String data){
	    URL url = null;
	    PrintWriter printWriter = null;
	    BufferedInputStream bis = null;
	    ByteArrayOutputStream bos = null;
	    try {
	        url = new URL(path);
	        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	        httpURLConnection.setConnectTimeout(30*1000);//连接超时 单位毫秒
	        httpURLConnection.setRequestMethod(method);// 提交模式
	        // 设置通用的请求属性
	        httpURLConnection.setRequestProperty("accept", "*/*");
	        httpURLConnection.setRequestProperty("connection", "Keep-Alive");
	        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
	        httpURLConnection.setRequestProperty("contentType", "utf-8");
	        httpURLConnection.setRequestProperty("Cookie", "JSESSIONID=E9B7493DBEDC7104F4F8709BF09640E3");
	        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length()));
	        httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
	//            httpURLConnection.setRequestProperty("Content-type", "text/html");
	        // conn.setReadTimeout(2000);//读取超时 单位毫秒
	        // 发送POST请求必须设置如下两行
	        httpURLConnection.setDoOutput(true);
	        httpURLConnection.setDoInput(true);
	        
	        // Post 请求不能使用缓存
	        httpURLConnection.setUseCaches(false);
	        
//	        // 要注意的是connection.getOutputStream会隐含的进行connect。
//	        httpURLConnection.connect();
	        // 获取URLConnection对象对应的输出流
	        printWriter = new PrintWriter(httpURLConnection.getOutputStream());
	        // 发送请求参数
	        printWriter.write(data);//post的参数 xx=xx&yy=yy
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
    static String ip = "http://napi.uc.cn/3/classes/topic/search";
    static String url = "http://napi.uc.cn/3/classes/topic/search?_app_id=hottopic&_fetch=1&_fetch_incrs=1&_sort=_lists.score%3Adesc&_lists=_lists.list_id%3A%22index_promotion%22&_objects=active_time%3A%5B*+TO+%222017-8-18+09%3A26%3A00%22%5D&_select=url%2Ctitle%2Ccover%2Ctag%2Cdesc%2Ccomment_total%2Cincrs_flag%2Clist_name%2Cpost_type";
   // http://napi.uc.cn/3/classes/topic/search?_app_id=hottopic&_fetch=1&_fetch_incrs=1&_sort=_lists.score%3Adesc&_lists=_lists.list_id%3A%22index_promotion%22&_objects=active_time%3A%5B*+TO+%222017-8-18+09%3A26%3A00%22%5D&_select=url%2Ctitle%2Ccover%2Ctag%2Cdesc%2Ccomment_total%2Cincrs_flag%2Clist_name%2Cpost_type 
    public static void main(String[] args) {
    	try {

			Map<String, String> map = new HashMap<String, String>();
			map.put("_app_id","hottopic");
			map.put("_fetch","1");
			map.put("_fetch_incrs","1");
			map.put("_sort","_lists.score:desc");
			map.put("_lists","_lists.list_id:\"index_promotion\"");
			map.put("_objects","active_time:[* TO \"2017-8-18 09:26:00\"]");
			map.put("_select","url,title,cover,tag,desc,comment_total,incrs_flag,list_name,post_type");
			
			String createPostData = HttpUtils.newInstance().createURLEncoderPostData(map);
			String postData2String = HttpUtils.newInstance().getData2String(ip, createPostData);
			System.out.println(postData2String);
			if (true) {
				return;
			}
			
			System.out.println(url);
			StringBuilder sb = new StringBuilder();
			String decode = URLDecoder.decode(url,"UTF-8");
			System.out.println(decode);
			int lastIndexOf = decode.lastIndexOf("?");
			String substring = decode.substring(lastIndexOf+1);
			String[] split = substring.split("&");
			for (String string : split) {
				String[] strings = string.split("=");
				map.put(strings[0], strings[1]);
				sb.append("map.put(");
				sb.append("\"");
				sb.append(strings[0]);
				sb.append("\"");
				sb.append(",");
				
				sb.append("\"");
				sb.append(strings[1]);
				sb.append("\"");
				sb.append(");\n");
				
//				sb.append("map.put(strings[0], strings[1])"+"\n");
			}
			System.out.println(sb.toString());
    	} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
