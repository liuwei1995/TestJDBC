package com.zhaoyao.xml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class XmlToXml {
	public static void main(String[] args) {
		File file = new File("D:/workspace5/ICaiBoxApp/res/layout");
		StringBuilder sb = new StringBuilder();
		if(!file.exists() || !file.isDirectory())return ;
		String[] list = file.list();
		for (int i = 0; i < list.length; i++) {
			File filePath = new File(list[i]);
			int indexOf = filePath.getName().indexOf(".");
			if(indexOf != -1){
				String substring = filePath.getName().substring(indexOf);
				if(substring.equals(".xml")){
					sb.append("\n");
					sb.append("AndroidManifestConver.exe ");
					sb.append(file.getPath()+"\\"+filePath.getName()+" ");
					sb.append(file.getPath()+"\\"+filePath.getName()+" ");
				}
				
			}
		}
		if(sb.length() <= 0)return ;
		FileOutputStream fos = null;
		try {
			File outFile = new File(file,file.getName()+".xml");
			fos = new FileOutputStream(outFile);
			InputStream is = new ByteArrayInputStream(sb.toString().getBytes());
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
