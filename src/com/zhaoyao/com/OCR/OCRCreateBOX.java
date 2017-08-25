package com.zhaoyao.com.OCR;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.zhaoyao.com.OCR.cmd.SyncPipe;
import com.zhaoyao.com.OCR.cmd.SyncPipe.interfaceSyncPipe;
/**
 * OCR BOX 	文件合并 
 * @author lw
 * 编写时间: 2016年10月20日下午3:17:44
 * 类说明 :
 */
public class OCRCreateBOX {
//	创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。示例代码如下：
	public static final Map<String, String> mapfont_properties = new HashMap<String, String>();
//	private static Map<Object, File> mapBox = new HashMap<Object, File>();
//	private static Map<Object, File> mapTr = new HashMap<Object, File>();
	private String lang = "chi_sim";
	private static Map<Object, File> mapImag = new HashMap<Object, File>();
	private static File dataDir = null;//"C:/Users/dell/Desktop/OCR项目/ceshi";
	private static File[] dataDirShuZu = null;//"C:/Users/dell/Desktop/OCR项目/ceshi";
	private static File canDir = null;// "C:/Users/dell/Desktop/OCR项目/can";
	private boolean isCopy = false;
	private boolean isLog = false;
	private interfaceSyncPipe pipe;
	private int type_;
	private File savePath = null;
	
	/**
	 * 
	 * @param filePath
	 * @param type  1  box  2  tr
	 */
	public OCRCreateBOX(int type,File savePath,String ...filePath) {
		this.savePath = savePath;
		if(filePath == null || filePath.length <= 0)return;
		dataDirShuZu = new File[filePath.length];
		for (int i = 0; i < filePath.length; i++) {
			dataDirShuZu[i] = new File(filePath[i]);
		}
		dataDir = dataDirShuZu[0];
		if((type_= type) == 2){
		}else if ((type_= type) == 1) {
			copyImageFile();
		}
	}
	public OCRCreateBOX(int type,File savePath,File filePath) {
		this(type,savePath,filePath.getPath());
	}
	
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public boolean isLog() {
		return isLog;
	}
	public void setLog(boolean isLog) {
		this.isLog = isLog;
	}
	private  void copyImageFile() {
		Map<Object, File> imag = new HashMap<Object, File>();
		for (File f : dataDirShuZu) {
			for (File file : f.listFiles())  
			{  
				if(!file.isFile())continue;
				String name = file.getName();
				if(name.indexOf(".") == -1)continue;
				String substring = name.substring(name.lastIndexOf(".")+1, name.length());
				String first = name.substring(0, name.lastIndexOf("."));
				if(substring != null && (substring.equals("png") || substring.equals("jpg") || substring.equals("bmp") || substring.equals("tif"))){
					imag.put(first, file);
				}
			}  
		}
		File canDirectory = null;
		if(savePath != null){
			canDirectory = savePath;
		}else
		  canDirectory = new File(dataDir, "."+dataDir.getName());
		 for (Object key : imag.keySet()) {
			 File from = imag.get(key);
				String name = from.getName();
				String frist = name;
				int n = 0;
				while (name.indexOf(".") != -1) {
					++n;
					frist = name = name.substring(0, name.indexOf("."));
				}
				File to = null;
				if(n != 3){
					String ne = frist+".font.exp0"+from.getName().substring(from.getName().lastIndexOf("."));
					to = new File(canDirectory,ne);
				}else
					to = new File(canDirectory,from.getName());
			 if(to.exists() && to.isFile()){
				 mapImag.put(to.getName().substring(0,to.getName().lastIndexOf(".")), to);
				 continue;
			 }
			 try {
				 FileUtils.copyFile(from, to);
				 mapImag.put(to.getName().substring(0,to.getName().lastIndexOf(".")), to);
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
		 }
		 canDir = canDirectory;
		 isCopy = true;
	}
	/**
	 * 1、先生成相对应的 .tr 文件   chi_sim.font.exp0.box  fuhao.arial.exp0.box
	 */
	public void start() {
		if(!isCopy)throw new NullPointerException("  文件没有拷贝完成");
		 String[] command =  
			{  
				"cmd",  
			}; 
		 Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		 PrintWriter stdin = null;
		 
		try {
			stdin = new PrintWriter(new OutputStreamWriter(p.getOutputStream(),"GBK"));
			pipe = new SyncPipe.interfaceSyncPipe() {
				@Override
				public void ok(boolean isOk) {
					isOk_ = isOk;
				}
			};
			if(isLog){
				new SyncPipe(p.getErrorStream(),pipe,-1,"GBK").start();
				new SyncPipe(p.getInputStream(),pipe,1,"GBK").start();
			}
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		} 
		cmd(stdin,p,"cd\\");
		cmd(stdin,p,""+canDir.getPath().charAt(0)+":");
		 String[] split = canDir.getPath().toString().replace("\\", "__").split("__");
		 for (int i = 1; i < split.length; i++) {
//			 切换到工作目录
			 cmd(stdin,p,"cd "+split[i]);
		}
		 for (Object key : mapImag.keySet()) {
//			 tesseract num.font.exp0.tif num.font.exp0 batch.nochop makebox
			 File boxfile = new File(mapImag.get(key).getParent(),key+".box");
			 if(boxfile.exists() && boxfile.isFile())continue;
			 cmd(stdin,p,"tesseract "+mapImag.get(key).getName()+" "+key+" batch.nochop makebox");
		}
		 cmd(stdin,p,"dir");
		 stdin.close();
		 System.out.println(sb.toString());
	}
	boolean dir = false;
	boolean isOk_ = true;
	private StringBuffer sb = new StringBuffer();
	
	public void cmd(final PrintWriter stdin,Process p,final String cmd) {
		synchronized (pipe) {
			try {
				while (!isOk_) {
					pipe.wait();
					continue;
				}
				stdin.println(cmd);
				stdin.flush();
				sb.append(cmd+"\t\n");
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				pipe.notifyAll();
			}
		}
	}
}
