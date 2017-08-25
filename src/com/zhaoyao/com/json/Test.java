package com.zhaoyao.com.json;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
		
//	public static void main(String[] args) {
//		get();
//	}
	@org.junit.Test
	public static void get() {
		try {
			URL url = new URL("http://cache.video.iqiyi.com/jp/avlist/202861101/1/");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream inputStream = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			StringBuffer sb = new StringBuffer();
			while ((s = br.readLine())!=null) {
				sb.append(s);
			}
			s = sb.toString().replace("var tvInfoJs=", "");
			System.out.println(s);
			JSON json = new JSON(s);
			json.parseJsonObject(json.map, 0, s);
			Map map = (Map) json.map.get("bmsg");
			Object object = map.get("f");
			System.out.println(object);
//			json.parseJsonArray(json.list, 0, s);
			System.out.println(json.map.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	static Map<Object, Object> map = new HashMap<Object, Object>();
	StringBuffer sb = new StringBuffer();
	static Map<Integer, Character> mapc = new HashMap<Integer, Character>();
	static List<Character> list = new ArrayList<Character>();
	static List<Map<Integer, Character>> listMap = new ArrayList<Map<Integer, Character>>();
	 /**
     * @param args
     */
    public static void main(String[] args) {
    	try {
			FileInputStream fis = new FileInputStream("D:/workspace5/TestJDBC/src/json.txt");
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			StringBuffer sb = new StringBuffer();
			while ((s = br.readLine())!=null) {
				sb.append(s);
			}
			br.close();
			fis.close();
			int len = 1000;
			long start = 0;
			long end = 0;
			 start = System.currentTimeMillis();
			for (int i = 0; i < len; i++) {
				org.json.JSONObject jsonObject = new org.json.JSONObject(sb.toString());
				jsonObject.toString();
			}
			 end = System.currentTimeMillis();
			System.err.println("1==="+(end-start));
			start = System.currentTimeMillis();
			for (int i = 0; i < len; i++) {
				JSONObject jsonObject = new JSONObject(sb.toString());
				jsonObject.toString();
			}
			end = System.currentTimeMillis();
			System.err.println("2==="+(end-start));
			
			start = System.currentTimeMillis();
			for (int i = 0; i < len; i++) {
				Object jsonObject = com.alibaba.fastjson.JSON.parse(sb.toString());
				jsonObject.toString();
			}
			end = System.currentTimeMillis();
			System.err.println("3==="+(end-start));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
}
