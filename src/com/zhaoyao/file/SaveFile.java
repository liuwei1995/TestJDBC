package com.zhaoyao.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveFile {
	
	public static void save(String path,String fileName,String data) {
		File filePath = new File(path);
		if(!filePath.exists() || !filePath.isDirectory()){
			filePath.mkdirs();
		}
		File file = new File(filePath, fileName);
		save(file, data);
	}
	public static void save(File file,String data) {
		File parentFile = file.getParentFile();
		if(!parentFile.exists() || !parentFile.isDirectory()){
			parentFile.mkdirs();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(data.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
	}
}
