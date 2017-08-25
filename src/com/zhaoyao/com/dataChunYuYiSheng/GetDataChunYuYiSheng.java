package com.zhaoyao.com.dataChunYuYiSheng;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;
import org.junit.Test;

import com.zhaoyao.com.http.HttpUtils;
import com.zhaoyao.file.SaveFile;

public class GetDataChunYuYiSheng {
//	public static String path = "C:/Users/dell/Desktop/39";
	public static String path = "C:/Users/dell/Desktop/ChunYuYiSheng_50854";
	public static ExecutorService fixedThreadPool1 = Executors.newFixedThreadPool(1);  
	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);  
	public static void main(String[] args) {
		
		final HttpUtils httpUtils = HttpUtils.newInstance();
		getData(httpUtils,96589);
//		getData(37285);
	}
	private static void getData(final HttpUtils httpUtils,int start) {
		
		for (int i = start; i < 20*10000; i++) {
			int q_id = i;
//			int q_id = 101971;
			String url = "http://api.chunyuyisheng.com/api/news/"+q_id;
			byte[] bs = httpUtils.get(url);
			if (bs != null) {
				String string = new String(bs);
				JSONObject jsonObject = new JSONObject(string);
				Set keySet = jsonObject.keySet();
				Iterator iterator = keySet.iterator();
				JSONObject new_object = new JSONObject();
				while (iterator.hasNext()) {
					Object next = iterator.next();
					new_object.put(next.toString(), jsonObject.get(next.toString()));
				}
				if (new_object.length() > 0) {
					Object data = new_object.get("date");
					Object id = new_object.get("id");
					String news_type_txt = new_object.getString("news_type_txt");//育儿
					String typeString = news_type_txt;//title
					SaveFile.save(path+"/"+typeString, ""+typeString+"_"+data+"_"+id+".json", new_object.toString());
					System.out.println("id="+q_id+"====="+new_object.toString());
				}else {
					System.err.println("id="+q_id+"-------------------------------------------------------------没有数据");
				}
			}else {
				System.err.println("id="+q_id+"=================================bs==============================没有数据");
			}
		}
	}
	@Test
	private static void getData(int index) {
		final HttpUtils httpUtils = HttpUtils.newInstance();
		int q_id = index;
//		int q_id = 101971;
		String url = "http://api.chunyuyisheng.com/api/news/"+q_id;
		byte[] bs = httpUtils.get(url);
		if (bs != null) {
			String string = new String(bs);
			JSONObject jsonObject = new JSONObject(string);
			Set keySet = jsonObject.keySet();
			Iterator iterator = keySet.iterator();
			JSONObject new_object = new JSONObject();
			while (iterator.hasNext()) {
				Object next = iterator.next();
				new_object.put(next.toString(), jsonObject.get(next.toString()));
			}
			if (new_object.length() > 0) {
				Object data = new_object.get("date");
				Object id = new_object.get("id");
				String source = new_object.getString("source");//仲景健康
				String typeString = source;//title
				SaveFile.save(path+"/"+typeString, ""+typeString+"_"+data+"_"+id+".json", new_object.toString());
				System.out.println("id="+q_id+"====="+new_object.toString());
			}else {
				System.err.println("id="+q_id+"-------------------------------------------------------------没有数据");
			}
		}else {
			System.err.println("id="+q_id+"=================================bs==============================没有数据");
		}
	}
}
