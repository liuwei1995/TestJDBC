package jar;

import jar.SyncPipe.interfaceSyncPipe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class CMDUtils {
	private final static String[] command = { "cmd"};
	private static Process p;
	private static PrintWriter stdin;
	private static SyncPipe syncPipeInput;
	private static SyncPipe syncPipeError;
	private String Charset = null;
	private String lang_ = "chi_sim";
	

	private StringBuffer sb = new StringBuffer();
	private interfaceSyncPipe pipe;
	private File outPath = null;
	private Map<String, File> mapAddMergeBOXPath = new HashMap<String, File>();
	private Map<String, File> mapAddCreateBOXPath = new HashMap<String, File>();
	public File getSavePath() {
		return outPath;
	}

	public void setSavePath(File savePath) {
		this.outPath = savePath;
	}
	private void lw_jar(String path) {
		String sub = path.substring(path.lastIndexOf("/"));
		int lastIndexOf = sub.lastIndexOf(".");
		File jar = null;
		if(lastIndexOf != -1)
		{
			String jarName = sub.substring(sub.lastIndexOf("/")+1,lastIndexOf);
			File file = new File(path);
			jar = new File(file.getParent());
			outPath = new File(file.getParent(),"."+jarName);
			if(!outPath.exists() || !outPath.isDirectory())
				outPath.mkdirs();
		}
		else
		{
			outPath = new File(path);
			jar = outPath;
		}
		File lw_jar = new File(jar,"lw.jar.properties");
		System.out.println("lw.jar.properties==="+lw_jar);
		if(!lw_jar.exists() || !lw_jar.isFile()){
			StringBuffer sb = new StringBuffer();
			sb.append("Charset=UTF-8\n");
			sb.append("lang=chi_sim\n");
			sb.append("AddCreateBOXPath=\n");
			sb.append("AddMergeBOXPath=\n");
			sb.append("outPath="+outPath.getPath().replace("\\", "/")+"\n");
			FileOutputStream fos = null;
			OutputStreamWriter osw = null;
			BufferedWriter bw = null;
			try {
				fos = new FileOutputStream(lw_jar);
				 osw = new OutputStreamWriter(fos,"UTF-8");
				 bw = new BufferedWriter(osw);
				String[] split = sb.toString().split("\n");
				for (String string : split) {
					bw.write(string);
					bw.newLine();
				}
				bw.flush();
				fos.close();
				osw.close();
				bw.close();
			} catch (IOException e) {
				outPath = null;
			}finally{
				try {
					if(fos != null)fos.close();
				} catch (IOException e) {
				}
				try {
					if(osw != null)osw.close();
				} catch (IOException e) {
				}
				try {
					if(bw != null)bw.close();
				} catch (IOException e) {
				}
			}
		}
		FileInputStream fis = null;
		try {
			 fis = new FileInputStream(lw_jar);
			Properties p = new Properties();  
			//p需要InputStream对象进行读取文件，而获取InputStream有多种方法：  
			//1、通过绝对路径:InputStream is=new FileInputStream(filePath);  
			//2、通过Class.getResourceAsStream(path);  
			//3、通过ClassLoader.getResourceAsStream(path);   
//			String encode = FileTool.getLocalFileEncode(lw_jar);
			p.load(new InputStreamReader(fis,"GBK"));  
			fis.close();  
			String property = p.getProperty("Charset");
			Charset = property;
			String outPaths = p.getProperty("outPath");
			if(outPaths.indexOf("/") != -1){
				if(outPaths != null){
					File file = new File(outPaths);
					if(file.exists() && file.isDirectory())
						outPath = new File(outPaths);
				}
			}else System.err.println("outPath="+"中的\\改为/");
			String AddCreateBOXPath = p.getProperty("AddCreateBOXPath");
			System.out.println("AddCreateBOXPath================="+AddCreateBOXPath);
			if(AddCreateBOXPath != null && !"".equals(AddCreateBOXPath)){
				if(AddCreateBOXPath.indexOf("/") != -1 && AddCreateBOXPath.indexOf("\\") ==-1){
					String[] split = AddCreateBOXPath.split(";");
					for (String string : split) {
						File file = new File(string);
						if(file.exists() && file.isDirectory()){
							mapAddCreateBOXPath.put(file.getPath(), file);
						}
					}
				}else System.err.println("AddCreateBOXPath="+"中的\\改为/");
			}
			String AddMergeBOXPath = p.getProperty("AddMergeBOXPath");
			System.out.println("AddMergeBOXPath================="+AddMergeBOXPath);
			if(AddMergeBOXPath != null && !"".equals(AddMergeBOXPath)){
				if(AddMergeBOXPath.indexOf("/") != -1 && AddMergeBOXPath.indexOf("\\") ==-1){
					String[] split = AddMergeBOXPath.split(";");
					for (String string : split) {
						File file = new File(string);
						if(file.exists() && file.isDirectory()){
							mapAddMergeBOXPath.put(file.getPath(), file);
						}
					}
				}else System.err.println("AddMergeBOXPath="+"中的\\改为/");
			}
			String lang = p.getProperty("lang");
			if(lang != null && !"".equals(lang))
				lang_ = lang;
			System.out.println("outPath:");
			System.out.println("\t"+outPath);
			System.out.println("AddMergeBOXPath:");
			for (String file : mapAddMergeBOXPath.keySet()) {
				System.out.println("\t"+file);
			}
			System.out.println("AddCreateBOXPath:");
			for (String file : mapAddCreateBOXPath.keySet()) {
				System.out.println("\t"+file);
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}finally{
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void start() {
		String path = CMDUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			path = URLDecoder.decode(path, "utf-8");// 转化为utf-8编码  
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		path = path.trim().substring(1);
		lw_jar(path);
		if(outPath == null){
			String sub = path.substring(path.lastIndexOf("/"));
			int lastIndexOf = sub.lastIndexOf(".");
			if(lastIndexOf != -1)
			{
				String jarName = sub.substring(sub.lastIndexOf("/")+1,lastIndexOf);
				outPath = new File(path,"."+jarName);
			}
			else
			{
				outPath = new File(path);
			}
		}
		try {
			p = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
		stdin = new PrintWriter(p.getOutputStream());
		 pipe = new interfaceSyncPipe() {
			@Override
			public void ok(boolean isOk) {
				isOk_ = isOk;
				System.out.println("输入命令:");
			}
		};
		outPrintln();
		syncPipeInput = new SyncPipe(p.getInputStream(), pipe, 1, null);
		syncPipeError = new SyncPipe(p.getErrorStream(), pipe, -1, null);
		if(Charset != null){
			try {
				Charset.getBytes(Charset);
				syncPipeInput.setCharset_(Charset);
				syncPipeError.setCharset_(Charset);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		syncPipeInput.start();
		syncPipeError.start();
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			String next = sc.next();
			next = next.replace("\\", "/");
			if(next.trim().toUpperCase().startsWith("OCRMERGEBOX")){//OCRMergeBOX
				if(mapAddMergeBOXPath.isEmpty())
				{
					System.out.println("\t没有添加要合并的文件夹");
					System.out.println("\t输入命令:  AddMergeBOXPath文件夹绝对路径");
					System.out.println("输入命令:");
					continue;
				}
				else 
				{
					OCRMergeBOX ocrMergeBOX = new OCRMergeBOX(1,outPath,mapAddMergeBOXPath);	
					ocrMergeBOX.setLog(true);
					ocrMergeBOX.setLang(lang_);
					System.out.println("开始合并稍等.......");
					ocrMergeBOX.start();
				}
				
			}else if (next.trim().toUpperCase().startsWith("OCRCREATEBOX")) {//OCRCreateBOX
				if(mapAddCreateBOXPath.isEmpty())
				{
					System.out.println("\t没有添加要合并的文件夹");
					System.out.println("\t输入命令:  AddCreateBOXPath文件夹绝对路径");
					System.out.println("输入命令:");
					continue;
				}else
				{
					OCRCreateBOX ocrCreateBOX = new OCRCreateBOX(1, outPath, mapAddCreateBOXPath);
					ocrCreateBOX.setLog(true);
					ocrCreateBOX.setLang(lang_);
					ocrCreateBOX.start();
				}
			}
			else if (next.trim().toUpperCase().startsWith("ADDMERGEBOXPATH")) {//AddMergeBOXPath
				String setSavePath = next.trim().substring("ADDMERGEBOXPATH".length()).trim();
				File file = new File(setSavePath);
				if(file.exists() && file.isDirectory()){
					mapAddMergeBOXPath.put(file.getPath(), file);
					System.out.println("AddMergeBOXPath  成功：");
					System.out.println("AddMergeBOXPath:");
					for (String f : mapAddMergeBOXPath.keySet()) {
						System.out.println("\t"+f);
					}		
					System.out.println(""+"删除命令：DeleteMergeBOXPath文件夹绝对路径");
					System.out.println("继续添加:  AddMergeBOXPath文件夹绝对路径");
					System.out.println("输入命令:");
				}else {
					System.out.println("文件夹  "+setSavePath+"    不存在");
					System.out.println("输入命令:  AddMergeBOXPath文件夹绝对路径");
					continue;
				}
			}
			else if (next.trim().toUpperCase().startsWith("ADDCREATEBOXPATH")) {//AddCreateBOXPath
				String setSavePath = next.trim().substring("ADDCREATEBOXPATH".length()).trim();
				File file = new File(setSavePath);
				if(file.exists() && file.isDirectory()){
					mapAddCreateBOXPath.put(file.getPath(), file);
					System.out.println("AddCreateBOXPath  成功：");
					System.out.println("AddCreateBOXPath:");
					for (String f : mapAddCreateBOXPath.keySet()) {
						System.out.println("\t"+f);
					}			
					System.out.println(""+"删除命令：DeleteCreateBOXPath文件夹绝对路径");
					System.out.println("继续添加:  AddCreateBOXPath文件夹绝对路径");
					System.out.println("输入命令:");
				}else {
					System.out.println("文件夹  "+setSavePath+"    不存在");
					System.out.println("输入命令:  AddCreateBOXPath文件夹绝对路径");
					continue;
				}
			}
			else if (next.trim().toUpperCase().startsWith("DELETEMERGEBOXPATH")) {//DeleteMergeBOXPath
				String setSavePath = next.trim().substring("DELETEMERGEBOXPATH".length()).trim();
				if(mapAddMergeBOXPath.containsKey(new File(setSavePath).getPath())){
					mapAddMergeBOXPath.remove(new File(setSavePath).getPath());
					System.out.println("\t\t"+"DeleteMergeBOXPath成功\n");
					System.out.println("\t\t"+"剩余路径：");
					for (String file : mapAddMergeBOXPath.keySet()) {
						System.out.println("\t"+file);
					}
					System.out.println("\t"+"删除命令：DeleteMergeBOXPath文件夹绝对路径");
					System.out.println("\t"+"添加命令:  AddMergeBOXPath文件夹绝对路径");
				}else {
					System.err.println("不存在该路径");
				}
				System.out.println("输入命令:");
			}
			else if (next.trim().toUpperCase().startsWith("DELETECREATEBOXPATH")) {//DeleteCreateBOXPath
				String setSavePath = next.trim().substring("DELETECREATEBOXPATH".length()).trim();
				if(mapAddCreateBOXPath.containsKey(new File(setSavePath).getPath())){
					mapAddCreateBOXPath.remove(new File(setSavePath).getPath());
					System.out.println("\t\t"+"DeleteCreateBOXPath成功\n");
					System.out.println("\t\t"+"剩余路径：");
					for (String file : mapAddCreateBOXPath.keySet()) {
						System.out.println("\t"+file);
					}
					System.out.println("\t"+"删除命令：DeleteCreateBOXPath文件夹绝对路径");
					System.out.println("\t"+"添加命令:  AddCreateBOXPath文件夹绝对路径");
				}else {
					System.err.println("不存在该路径");
				}
				System.out.println("输入命令:");
			}
			else if (next.trim().toUpperCase().startsWith("SHOWMERGEBOXPATH")) {//ShowMergeBOXPath
				if(!mapAddCreateBOXPath.isEmpty()){
					for (String file : mapAddMergeBOXPath.keySet()) {
						System.out.println("\t"+file);
					}
					System.out.println("\t"+"删除命令：DeleteMergeBOXPath文件夹绝对路径");
					System.out.println("\t"+"添加命令:  AddMergeBOXPath文件夹绝对路径");
					System.out.println("输入命令:");
				}
				else {
					System.out.println("\t还没有添加路径");
					System.out.println("请输入添加命令:  AddMergeBOXPath文件夹绝对路径");
				}
			}
			else if (next.trim().toUpperCase().startsWith("SHOWCREATEBOXPATH")) {//ShowCreateBOXPath
				if(!mapAddCreateBOXPath.isEmpty()){
					for (String file : mapAddCreateBOXPath.keySet()) {
						System.out.println("\t"+file);
					}
					System.out.println("\t"+"删除命令：DeleteCreateBOXPath文件夹绝对路径");
					System.out.println("\t"+"添加命令:  AddCreateBOXPath文件夹绝对路径");
					System.out.println("输入命令:");
				}
				else {
					System.out.println("\t还没有添加路径");
					System.out.println("请输入添加命令:  AddCreateBOXPath文件夹绝对路径");
				}
			}
			else if (next.trim().toUpperCase().startsWith("SETSAVEPATH")) {//setSavePath
				String setSavePath = next.trim().substring(11).trim();
				File file = new File(setSavePath);
					if(file.exists() && file.isDirectory()){
							outPath = file;
					}else {
						System.out.println("文件夹  "+setSavePath+"    不存在");
						System.out.println("请输入命令:  AddMergeBOXPath文件夹绝对路径或AddCreateBOXPath文件夹绝对路径");
						System.out.println("输入命令:");
						continue;
					}
			}
			else if (next.trim().toUpperCase().startsWith("EXIT")) {//exit
				System.out.println("退出程序完成");
				break;
			}
			else if (next.trim().toUpperCase().startsWith("setCharset_")) {//exit
				String[] split = next.split("_");
				if(split.length != 2){
					System.out.println("不存在该命令："+next.trim());
					System.out.println("重新输入命令:");
				}else {
					try {
						next.getBytes(split[1]);
						syncPipeInput.setCharset_(split[1]);
						syncPipeError.setCharset_(split[1]);
						System.out.println("编码设置为："+split[1]);
						System.out.println("输入命令:");
					} catch (UnsupportedEncodingException e) {
						System.err.println("不存在编码："+split[1]);
						System.out.println("输入命令:");
						continue;
					}
				}
			}
			else if (next.trim().toUpperCase().startsWith("-HELP")) {
				outPrintln();
				System.out.println("输入命令:");
			}
			else {
				System.out.println("不存在该命令："+next.trim());
				System.out.println("重新输入命令:");
				continue;
			}
		}
		sc.close();
		System.exit(0);
	}
	private void outPrintln(){
		System.out.println("-help 帮助");
		System.out.println("如果输出的结果乱码，可以输入命令：setCharset_UTF-8 或  setCharset_GBK 或 setCharset_跟多编码  \n\t\t也可以修改lw.jar.properties   Charset  修改过后重新运行jar");
		System.out.println("添加要合并的box文件夹：  AddMergeBOXPath文件夹绝对路径");
		System.out.println("查看要合并的box文件夹路径： ShowMergeBOXPath");
		System.out.println("删除要合并的box文件夹路径：  DeleteMergeBOXPath文件夹绝对路径");
		System.out.println("开始合并box文件：  OCRMergeBOX");
		System.out.println("添加要创建box文件夹的文件夹：  AddCreateBOXPath文件夹绝对路径");
		System.out.println("查看要创建box文件夹的文件夹路径：  ShowCreateBOXPath");
		System.out.println("删除要创建box文件夹的文件夹路径：  DeleteCreateBOXPath文件夹绝对路径");
		System.out.println("开始创建box文件：  OCRCreateBOX");
	}
//	public void start() {
//		try {
//			p = Runtime.getRuntime().exec(command);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		stdin = new PrintWriter(p.getOutputStream());
//		pipe = new interfaceSyncPipe() {
//			@Override
//			public void ok(boolean isOk) {
//				isOk_ = isOk;
//				System.out.println("输入命令:");
//			}
//		};
//		syncPipeInput = new SyncPipe(p.getInputStream(), pipe, 1, null);
//		syncPipeInput.start();
//		syncPipeError = new SyncPipe(p.getErrorStream(), pipe, -1, null);
//		syncPipeError.start();
////		File canDir = new File("E:/anzhuang/tesseract-ocr/jTessBoxEditorFX-2.0-RC/jTessBoxEditorFX");
//////		File canDir = new File("C:/Users/dell/Desktop/OCR项目/yuyan/heBox");
////		cmd(stdin,p,"cd\\");
////		cmd(stdin,p,""+canDir.getPath().charAt(0)+":");
////		 String[] split2 = canDir.getPath().toString().replace("\\", "__").split("__");
////		 for (int i = 1; i < split2.length; i++) {
//////			 切换到工作目录
////			 cmd(stdin,p,"cd "+split2[i]);
////		}
//		Scanner sc = new Scanner(System.in);
//		System.out.println("如果输出的结果乱码，可以输入命令：setCharset_UTF-8 或  setCharset_GBK 或 setCharset_跟多编码  ");
//		System.out.println("输入命令:");
//		while (sc.hasNext()) {
//			String next = sc.next();
//			if (next.equals("cd")) {
//				sb.append(next + " ");
//				if (sc.hasNext())
//					continue;
//			} else if (next.trim().startsWith("setCharset")) {
//				String[] split = next.trim().split("_");
//				if (split.length == 2) {
//					String charSet = split[1];
//					try {
//						next.getBytes(charSet);
//						syncPipeInput.setCharset_(charSet);
//						syncPipeError.setCharset_(charSet);
//						System.out.println("编码修改为:" + charSet);
//					} catch (UnsupportedEncodingException e) {
//						System.out.println("不存在这种编码：" + charSet);
//						
//						System.out.println("如果修改编码请重新输入：setCharset_编码");
//						continue;
//					}
//					System.out.println("输入命令:");
//					continue;
//				} else {
//					clearSB(sb);
//					System.out.println("命令错误请重新错:setCharset_编码");
//					System.out.println("输入命令:");
//					continue;
//				}
//			} else {
//				String string = sb.toString();
//				if (string.equals("cd ")) {
//					if (next.startsWith("..")) {
//						sb.append(next);
//						printWriter(sb.toString());
//						clearSB(sb);
//						continue;
//					} else if (next.startsWith("/")) {
//						printWriter(sb.toString());
//						clearSB(sb);
//						continue;
//					}else {
//						sb.append(next);
//					}
//				} else {
//					sb.append(next);
//				}
//			}
//			System.out.println("输入的内容是：" + sb.toString());
//			if (!"exit".equals(next)) {
//				printWriter(sb.toString());
//				System.out.println("输入命令:");
//				clearSB(sb);
//				continue;
//			}
//			sb.delete(0, sb.length());
//			System.out.println("结束：");
//			break;
//		}
//		System.out.println("");
//		sc.close();
//		System.exit(0);
//	}

	private void clearSB(StringBuffer sb) {
		if (sb != null)
			sb.delete(0, sb.length());
	}
	boolean isOk_ = true;
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
