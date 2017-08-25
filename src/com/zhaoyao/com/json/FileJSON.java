package com.zhaoyao.com.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

public class FileJSON {
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		String file = FileJSON.class.getResource("").getFile();
		file = file.substring(1, file.length());
//		http://cache.video.iqiyi.com/jp/avlist/202861101/1/
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
			JSONObject jsonObject = new JSONObject(s);
			JSONObject data = jsonObject.getJSONObject("data");
			Iterator iterator = data.keys();
			String outPath = "D:/workspace5/TestJDBC/src/com/zhaoyao/com/json/entity/";
			File file3 = new File(outPath,"Data.java");
			if(!file3.exists() || !file3.isFile())
				file3.createNewFile();
			FileOutputStream fos = new FileOutputStream(file3);
			StringBuffer sbt = new StringBuffer();
			sbt.append("package com.zhaoyao.com.json.entity;\n");
			sbt.append("import java.util.List;\n");
			sbt.append("public class Data {\n");
			
			while (iterator.hasNext()) {
				Object next = iterator.next();
				Object object = data.get(next.toString());
				if(object instanceof String){
					sbt.append("private String "+next+";\n");
				}else if (object instanceof JSONArray) {
					String n = next.toString().substring(0, 1).toUpperCase()+next.toString().substring(1, next.toString().length());
					sbt.append("private List<"+n+"> "+next+";\n");
					File file2 = new File(outPath+""+n+".java");
					if(!file2.exists() || !file2.isFile())
					file2.createNewFile();
					JSONArray jsonArray = new JSONArray(object.toString());
					FileOutputStream outputStream = new FileOutputStream(file2);
					StringBuffer sss = new StringBuffer();
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject obj = jsonArray.getJSONObject(i);
						Iterator keys = obj.keys();
						sss.append("package com.zhaoyao.com.json.entity;\n");
						sss.append("import java.util.List;\n");
						sss.append("public class "+n+" {\n");
						while (keys.hasNext()) {
							Object nex = keys.next();
							sss.append("private String "+nex+";\n");
						}
						break;
					}
					sss.append("\n}\n");
					byte[] bytes = sss.toString().getBytes();
					outputStream.write(bytes, 0, bytes.length);
					outputStream.close();
				}else if (object instanceof JSONObject) {
					
				}else {
					sbt.append("private String "+next+";\n");
				}
			}
			sbt.append("\n}");
			byte[] bytes = sbt.toString().getBytes();
			fos.write(bytes, 0, bytes.length);
			fos.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
