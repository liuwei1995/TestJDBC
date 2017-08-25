package com.zhaoyao.com.OCR;

import java.io.File;
import java.nio.file.Path;

import org.junit.Test;

import com.zhaoyao.com.OCR.cmd.CMDUtils;

public class TestOcr {
//	private static String jiu = "C:/Users/dell/Desktop/OCR项目/shuzi";
	private static String jiu = "C:/Users/dell/Desktop/OCR项目/ceshi";
	private static String canDir = "C:/Users/dell/Desktop/OCR项目/can";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		new TestOcr().mergeBox();
		new TestOcr().createBox();
	}
	String[] path = new String []{
			jiu,
			"C:/Users/dell/Desktop/OCR项目/shuzi",
			"C:/Users/dell/Desktop/OCR项目/yuyan/20161021/AndroidHeros/.AndroidHeros",
			"C:/Users/dell/Desktop/OCR项目/yuyan/20161024/dizhengxiongmei",
			
	};
	/**
	 * 合并box文件
	 */
	@Test
	public void mergeBox() {
		OCRMergeBOX box = new OCRMergeBOX(1,path);
		box.setLog(true);
		box.start();
	}
	String createBox[] = {
			"C:/Users/dell/Desktop/OCR项目/yuyan/20161024/dizhengxiongmei",
			"C:/Users/dell/Desktop/OCR项目/yuyan/20161021/AndroidHeros"
	};
	@Test
	public void createBox() {
		OCRCreateBOX ocrCreateBOX = new OCRCreateBOX(1,new File("C:/Users/dell/Desktop/OCR项目/yuyan/heBox"),
				createBox
				);
		ocrCreateBOX.setLog(true);
		ocrCreateBOX.start();
	}
	private static final String EOL = System.getProperty("line.separator");
	public static void executeMyCommand2(){
		CMDUtils utils = new CMDUtils();  
		utils.printWriter("cd\\");
//		utils.printWriter("cd C:/Users/dell/Desktop/");
		utils.printWriter("cd E:/anzhuang/tesseract-ocr/Tesseract-OCR");
//		utils.printWriter("cd D:/Android/Android");
		utils.printWriter("dir");
//		utils.printWriter("cd d:");
//		utils.printWriter("cd ..");
//		utils.printWriter("cd ..");
//		utils.printWriter("d:");
//		utils.printWriter("cd Android");
//		utils.printWriter("dir");
//		utils.cmd();
//		utils.close();
	}
	
}
