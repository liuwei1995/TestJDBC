package com.zhaoyao.com.OCR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.jdesktop.swingx.util.OS;

import com.zhaoyao.file.FileTool;
/***
 * OCR 识别
 * @author lw
 * 编写时间: 2016年10月20日下午3:18:15
 * 类说明 :
 */
public class OCR {
	private static final String LANG_OPTION = "-l";  //英文字母小写l，并非数字1
	private static final String EOL = System.getProperty("line.separator");
	private String tessPath = "E://anzhuang/tesseract-ocr/Tesseract-OCR";
	/**
	 * 
	 * @param imageFile
	 * @param lang  语言  默认为chi_sim 中文
	 * @return
	 * @throws Exception
	 */
	public String recognizeText(File imageFile,String lang)throws Exception{
		String name = imageFile.getName();
		String imageFormat = name.substring(name.lastIndexOf(".")+1, name.length());
		File tempImage = ImageIOHelper.createImage(imageFile,imageFormat);
		File outputFile = new File(imageFile.getParentFile(),"output");
		StringBuffer strB = new StringBuffer();
		List<String> cmd = new ArrayList<String>();
		if(OS.isWindowsXP()){
			cmd.add(tessPath+"//tesseract");
		}else if(OS.isLinux()){
			cmd.add("tesseract");
		}else{
			cmd.add(tessPath+"//tesseract");
		}
		cmd.add(imageFile.getName());
        cmd.add(outputFile.getName());
        cmd.add("-l");
        cmd.add(lang == null || "".equals(lang)?"chi_sim":lang);//语言名字
//		cmd.add("eng");
		
		ProcessBuilder pb = new ProcessBuilder();
		pb.directory(imageFile.getParentFile());
		
		cmd.set(1, tempImage.getName());
		pb.command(cmd);
//		[E://anzhuang/tesseract-ocr/Tesseract-OCR//tesseract, mypic0.bmp, output, chi_sim]
		pb.redirectErrorStream(true);
		
		Process process = pb.start();
		//tesseract.exe 1.jpg 1 -l chi_sim
		int w = process.waitFor();
		
		//删除临时正在工作文件
		tempImage.delete();
		
		if(w==0){
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath()+".txt"),"UTF-8"));
			
			String str;
			while((str = in.readLine())!=null){
				strB.append(str).append(EOL);
			}
			in.close();
		}else{
			String msg;
			switch(w){
				case 1:
					msg = "Errors accessing files.There may be spaces in your image's filename.";
					break;
				case 29:
					msg = "Cannot recongnize the image or its selected region.";
					break;
				case 31:
					msg = "Unsupported image format.";
					break;
				default:
					msg = "Errors occurred.";
			}
			tempImage.delete();
			//throw new RuntimeException(msg);
		}
		new File(outputFile.getAbsolutePath()+".txt").delete();
		return strB.toString();
	}
	private static long txtl = 0l;
	private static char[] c = null;
	
	public static void getTxt(String path) {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		try {
			String encode = FileTool.getLocalFileEncode(path);
			fis = new FileInputStream(path);
			isr = new InputStreamReader(fis,"GBK");
//			isr = new InputStreamReader(fis,encode);
			in = new BufferedReader(isr);
			String str;
			StringBuffer strB = new StringBuffer();
			 StringBuffer strBtxt = new StringBuffer();
			while((str = in.readLine())!=null){
				strBtxt.delete(0, strBtxt.length());
				strBtxt.append(str);
				w2:
				while (true) {
					for (int i = 0; i < strBtxt.length(); i++) {
						char charAt = strBtxt.charAt(i);
						int m = charAt;
						if(12288 == m || m == 32 || charAt == '\t'){
							strBtxt.deleteCharAt(i);
							continue w2;
						}
					}
					break ;
				}
				strB.append(strBtxt.toString());
			}
			fis.close();
			isr.close();
			in.close();
			
			c = strB.toString().trim().toCharArray();
			txtl = c.length;
			System.out.println(strB.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(fis != null)
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {if(isr != null)
				isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {if(in != null)
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static String path = "E:/anzhuang/tesseract-ocr/jTessBoxEditorFX-2.0-RC/jTessBoxEditorFX/zuowen/";
	public static void writeFile(String fileName,byte[] b) {
		FileOutputStream fos = null;
		try {
			 fos = new FileOutputStream(path +fileName);
			 fos.write(b, 0, b.length);
			 fos.flush();
			 fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {if(fos != null)
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) {
//		C:/Users/dell/Desktop/OCR项目/yuyan/20161021/AndroidHeros/.AndroidHeros
		path = "C:/Users/dell/Desktop/OCR项目/yuyan/20161021/AndroidHeros/.AndroidHeros/";
//		path = System.getProperty("user.dir")+"/src/";
		int n = 12;
		getTxt(path+"AndroidHeros.font.exp0.txt");
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		try {
			fis = new FileInputStream(path+"AndroidHeros"+n+".font.exp0.box");
			isr = new InputStreamReader(fis,"UTF-8");
			in = new BufferedReader(isr);
			StringBuffer strB = new StringBuffer();
			String str;
			int i = 0 ;
			while((str = in.readLine())!=null){
				if(i < txtl){
					String substring = str.substring(0+1, str.length());
					strB.append(c[i]+substring).append(EOL);
					System.out.println(i+"=="+c[i]+substring);
					++i;
				}else {
					strB.append(str).append(EOL);
				}
			}
			fis.close();
			isr.close();
			in.close();
			System.out.println(strB.toString());
			writeFile("AndroidHeros"+n+".font.exp0.box", strB.toString().getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {if(fis != null)
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {if(isr != null)
				isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {if(in != null)
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
