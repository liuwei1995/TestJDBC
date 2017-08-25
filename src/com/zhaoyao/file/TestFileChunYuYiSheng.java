package com.zhaoyao.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestFileChunYuYiSheng {
	public static void main(String[] args) {
		File file = new File("C:/Users/dell/Desktop/春雨医生新");
		for (File f : file.listFiles()) {
			if(f.isDirectory()){
				for (File file2 : f.listFiles()) {
					StringBuffer sb = new StringBuffer();
					FileInputStream inputStream = null;
					BufferedReader br = null;
			 		try {
						inputStream = new FileInputStream(file2);
						br = new BufferedReader(new InputStreamReader(inputStream));
						String s = "";
						while ((s = br.readLine())!=null) {
							sb.append(s);
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
			 		JSONObject jsonObject = new JSONObject(sb.toString());
					JSONArray jsonArray = jsonObject.getJSONObject("results").getJSONArray("List");
					if(jsonArray == null || jsonArray.length() <= 0){
						file2.delete();
						System.out.println("删除了=============="+file2.getPath());
					}
				}
			}else {
				
			}
		}
	}
}
