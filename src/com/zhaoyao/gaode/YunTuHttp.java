package com.zhaoyao.gaode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
/**
 * http://lbs.amap.com/yuntu/reference/cloudsearch/#t1
 * @author lw
 * 编写时间: 2016年10月8日下午1:40:28
 * 类说明 :
 */
public class YunTuHttp{
	
	public static final String GET = "GET";
	public static final String POST = "POST";
	
	public static final String tableId = "57eca7b82376c15ffea5e6e5";
//	
	public static final String key = "29aa46df8e49b2b13986a67fa795c615";
//	私钥
	public static final String siyue = "317d1971d68fa548d17bb16884663003";
//	本地检索请求
	public static final String local = "http://yuntuapi.amap.com/datasearch/local";
//	周边检索请求 http://yuntuapi.amap.com/datasearch/around? parameters center
	public static final String around = "http://yuntuapi.amap.com/datasearch/around";
	private String path = "";
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	private final Map<Object, Object> map = new HashMap<Object, Object>();
	
	public <K, V>  Map<Object, Object> addParameters(K k,V v) {
		map.put(k, v);
		return map;
	}
	private Callback callback;
	
	public Callback getCallback() {
		return callback;
	}
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	public interface Callback{
		void result(boolean isSuccess,byte[] b,Exception e) throws Exception ;
	}
	/**
	 * sortrule
	 * <li>sortrule=字段名:1 （升序）； 
	 * 	sortrule=字段名:0 （降序）； 
	 * 	<li>
	 * 	<li>
	 * 	<li>
	 * @throws Exception 
	 */
	public void around(Callback callback) throws Exception {
		map.put("key", key);
		map.put("tableid", tableId);
		this.callback = callback;
		String parameters = spliceParameters();
		System.out.println(parameters);
		String sig = MD5Util.getStringMD5(parameters+siyue);
		GET(around,parameters, sig);
	}
	public void local(Callback callback) throws Exception {
		this.callback = callback;
		String parameters = spliceParameters();
		String sig = MD5Util.getStringMD5(parameters+siyue);
		GET(local,parameters, sig);
	}
	private void GET(String path,String parameters,String sig) throws Exception{
		http(path,parameters, sig, GET);
	}
	private void http(String path,String parameters,String sig,String RequestMethod) throws Exception{
		URL url = null;
		try {
			if("".equals(parameters))
				url = new URL(path+"sig="+sig);
			else 
				 url = new URL(path+"?"+parameters+"&"+"sig="+sig);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			if(callback != null)
			callback.result(false, null,e);
			return;
		}
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
			if(callback != null)
				callback.result(false, null,e);
			return;
		}
		try {
			con.setRequestMethod(RequestMethod);
		} catch (ProtocolException e) {
			e.printStackTrace();
			if(callback != null)
				callback.result(false, null,e);
			return;
		}
		con.setDoInput(doinput); //允许输入流，即允许下载  
		con.setDoOutput(dooutput); //允许输出流，即允许上传  
		con.setUseCaches(usecaches); //不使用缓冲  
		try {
			con.connect();
		} catch (IOException e) {
			e.printStackTrace();
			if(callback != null)
				callback.result(false, null,e);
			return;
		}
		  int code=con.getResponseCode();
          if (code!=200) {
        	  if(callback != null)
  				callback.result(false, null,new Exception("网络连接失败"));
  			return; 
          }
		InputStream is = null;
		ByteArrayOutputStream output = null;
		try {
			is = con.getInputStream();
			 output = new ByteArrayOutputStream();
			byte [] b = new byte[is.available()];
			int n = 0;
			while ((n = is.read(b)) != -1) {
				output.write(b, 0, n);
			}
			if(callback != null)
				callback.result(true, output.toByteArray(),null);
		} catch (IOException e) {
			e.printStackTrace();
			if(callback != null)
				callback.result(false, null,e);
			return;
		}finally{
			try {
				if(is != null)is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(output != null){
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private boolean dooutput = true;
	/**
	 * 允许输出流，即允许上传  
	 * @param dooutput
	 */
	public void setDoOutput(boolean dooutput) {
		this.dooutput = dooutput;
	}
	private boolean doinput = true;
	/**
	 * 允许输出流，即允许上传  
	 * @param doinput
	 */
	public void setDoInput(boolean doinput) {
		this.doinput = doinput;
	}
	private boolean usecaches = false;
	/**
	 * 使用缓冲  
	 * @param usecaches
	 */
	public void setUseCaches(boolean usecaches) {
		this.usecaches = usecaches;
	}
	protected String spliceParameters() {
		if(map.isEmpty())return "";
		Set<Object> keySet = sort(map);
		final StringBuffer sb = new StringBuffer();
		for (Object key : keySet) {
        	sb.append(key+"="+map.get(key)+"&");
        }
        sb.delete(sb.length()-1, sb.length());
		return sb.toString();
	}
	protected Set<Object> sort(Map<Object, Object> map)   { 
		final List<Object>   list   =   new   ArrayList<Object>(map.keySet()); 
        Collections.sort(list,   new   Comparator<Object>()   { 
                public   int   compare(Object   a,   Object   b)   { 
                        return   a.toString().toLowerCase().compareTo(b.toString() 
                                .toLowerCase()); //升序
                } 
        }); 
        final Set<Object>  keySet = new  TreeSet<Object>(list); 
		return keySet;
} 
}
