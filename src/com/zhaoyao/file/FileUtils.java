package com.zhaoyao.file;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	/**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(String fileName) {
        File file = new File(fileName);
        return readFileByLines(file);
    }
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
	    public static String readFileByLines(File file) {
	    	return readFileByLines(file,"gbk");
	    }
    	public static String readFileByLines(File file, String charsetName) {
    	StringBuilder sb = new StringBuilder();
    	InputStreamReader read = null;
    	BufferedReader reader = null;
    	try {
    		 read = new InputStreamReader(new FileInputStream(file),charsetName);       
    		reader = new BufferedReader(read);
    		String tempString = null;
    		// 一次读入一行，直到读入null为文件结束
    		while ((tempString = reader.readLine()) != null) {
    			sb.append(tempString);
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		close(reader);
    		close(reader);
    	}
    	return sb.toString();
    }
    public static List<String> readFileByLinesToList(File file) {
    	List<String> list = new ArrayList<String>();
//    	FileReader fileReader = null;
    	BufferedReader reader = null;
    	InputStreamReader isr  = null;
    	try {
//    		fileReader = new FileReader(file);
//    		reader = new BufferedReader(fileReader);
    		 isr = new InputStreamReader(new FileInputStream(file), "UTF-8");
    		 reader = new BufferedReader(isr);
    		String tempString = null;
    		// 一次读入一行，直到读入null为文件结束
    		while ((tempString = reader.readLine()) != null) {
    			list.add(tempString);
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
    		close(reader);
    		close(isr);
    	}
    	return list;
    }
	public static void close(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
