package com.zhaoyao.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class TxTDrugFile {
	public static void main(String[] args) {
		
		File file = new File("C:/Users/dell/Desktop/cs_ocr_新文档 2017-02-17_1.txt");
		if(file.exists() && file.isFile()){
			String encode = FileTool.getLocalFileEncode(file);
			try {
//				】
				char m = '【';
				char i = '】';
				InputStreamReader reader = new InputStreamReader(new FileInputStream(file), encode);
				BufferedReader br = new BufferedReader(reader);
//				药品名称
				StringBuilder sb = new StringBuilder();
				Map<String, String> map = new HashMap<String, String>();
				String key = "";
				String s = "";
				while ((s = br.readLine()) != null) {
					int indexOf = s.indexOf(""+m);
					if(indexOf != -1 || s.indexOf(":") != -1){
						if(!key.equals("")){
							map.put(key, sb.toString());
							sb.delete(0, sb.length());
						}
					}
					if(indexOf != -1){
						if(s.indexOf(""+i) != -1){
							sb.append(s.substring(s.indexOf(""+i)));
							key = s.substring(indexOf,s.indexOf(""+i)-1);
						}else {
							
						}
					}else if (s.indexOf(""+i) != -1) {
						
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
