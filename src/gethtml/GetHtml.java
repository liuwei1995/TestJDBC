package gethtml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GetHtml {
	
	public static String FOLDER_NAME = "ChunYuYiSheng";
	
	public static void setFolderName(String folderName) {
		FOLDER_NAME = folderName;
	}
	
	private static File SAVE_PATH = null;
	
	private static long startIndex = 0;
	
	public static void setEndIndex(long endIndex) {
		GetHtml.endIndex = endIndex;
	}

	private static long endIndex = 20000;
	
	private static long intervalTime = 6000;
	
	
	public static void setIntervalTime(long intervalTime) {
		GetHtml.intervalTime = intervalTime;
	}

	public static void setStartIndex(long startIndex) {
		GetHtml.startIndex = startIndex;
	}

	public static void setSavePath(File sAVE_PATH) {
		SAVE_PATH = sAVE_PATH;
	}

	private static File START_INDEX = null;

	private static boolean start = false;
	
	private static boolean isStart = false;
	
	public static void help() {
		 System.out.println("请输入exit  退出");
		 System.out.println("请输入setFolderName 文件夹名\t例如：setFolderName ChunYuYiSheng  设置文件夹名");
		 System.out.println("请输入setSavePath 文件夹路径\t例如：setSavePath C:/Users/dell/Desktop/ 设置文件夹路径 ");
		 System.out.println("请输入reload 重新加载配置  ");
		 System.out.println("请输入start 开始运行  ");
	}
	public static synchronized void reload() {
//		FileSystemView fsv = FileSystemView.getFileSystemView();
//		SAVE_PATH = fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
		String path = GetHtml.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			path = URLDecoder.decode(path, "utf-8");// 转化为utf-8编码  
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		path = path.trim().substring(1);
		SAVE_PATH = new File(path);
		lw_jar(path);
		//		 String[] command =  
		//		{  
		//			"cmd",  
		//		}; 
		//	 Process p = null;
		//try {
		//	p = Runtime.getRuntime().exec(command);
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
		//
	}
	public static void main(String[] args) {
		reload();

		//创建输入对象
		 help();
		 Scanner sc = new Scanner(System.in);
		 System.out.print("请输入命令:");
		  while (sc.hasNextLine()) {
			  String nextLine = sc.nextLine();
			  if (nextLine.equalsIgnoreCase("exit")) {
				  	System.exit(0);
			  }else if (nextLine.equalsIgnoreCase("help")) {
				help();
			  }else if (nextLine.equalsIgnoreCase("reload")) {
				  reload();
			}else if (nextLine.trim().startsWith("setFolderName")) {
					String replace = nextLine.trim().replace("setFolderName", "").trim();
					replace = replace.replace(" ", "");
					if (!replace.equals("")) {
						setFolderName(replace);
						System.out.println("文件夹名字设置成了：  "+replace);
						 System.out.print("输入命令:");
					}else {
						System.out.println("文件夹名字不能为空  ");
						 System.out.print("重新输入命令:");
					}
				}else if (nextLine.trim().startsWith("setSavePath")) {
					String replace = nextLine.trim().replace("setSavePath", "").trim();
					replace = replace.replace(" ", "");
					if (!replace.equals("")) {
						replace = replace.replaceAll("////", "/").trim();
				        System.out.println(replace);
				        java.util.regex.Pattern p = java.util.regex.Pattern.compile("(^//.|^/|^[a-zA-Z])?:?/.+(/$)?");
				        java.util.regex.Matcher m = p.matcher(replace);
				        //不符合要求直接返回
				        if(!m.matches()){
				        	System.out.println("文件夹路径格式不正确 ");
							 System.out.print("重新输入命令:");
				        }else {
				        	File file = new File(replace);
				        	if (!file.exists() || !file.isDirectory()) {
				        		boolean mkdirs = file.mkdirs();
				        	}
				        	setSavePath(file);
				        	System.out.println("文件夹路径设置成了：  "+replace);
				        	 System.out.print("输入命令:");
						}
					}else {
						System.out.println("文件夹路径不能为空  ");
						System.out.print("重新输入命令:");
					}
				}else if (nextLine.trim().equalsIgnoreCase("start")) {
					synchronized (GetHtml.class) {
						if (!isStart) {
							TipConfiguration();
							start = true;
							System.out.println("是否开始 输入   Y 开始  N 取消  ");
							System.out.print("输入命令  Y 开始  N 取消  :");
						}else {
							System.out.println("已经开始了");
							System.out.print("输入命令exit退出:");
						}
						continue;
					}
				}else if (nextLine.equalsIgnoreCase("Y") || nextLine.equalsIgnoreCase("N")) {
					if (start)
					synchronized (GetHtml.class) {
						if (nextLine.equalsIgnoreCase("N")) {
							help();
							System.out.print("输入命令:");
						}else if (nextLine.equalsIgnoreCase("Y")) {
							if (start) {
								isStart = true;
								System.out.println("开始了 ==========================");
								start();
							}
							System.out.print("输入命令exit退出:");
						}
					}
				}else {
					//获取用户输入的字符串
					System.out.println("不存在该命令:"+nextLine);
					System.out.print("重新输入命令:");
				}
			  start = false;
		  }
	}

	private static void TipConfiguration() {
		
		System.out.println("当前的配置为：\n\tsavePath:\t"+SAVE_PATH.toString());
		System.out.println("\tfolderName:\t"+FOLDER_NAME);
		System.out.println("\tstartIndex:\t"+startIndex);
		System.out.println("\tendIndex:\t"+endIndex);
	}
	
	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);  
	
	private static void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (long i = startIndex; i < endIndex; i++) {
					final long m = i;
					fixedThreadPool.execute(new Runnable() {
						@Override
						public void run() {
							getHtmlData(m);
						}
					});
				}
			}
		}).start();
	}

	private static void lw_jar(String path) {
		String sub = path.substring(path.lastIndexOf("/"));
		int lastIndexOf = sub.lastIndexOf(".");
		File jar = null;
		if(lastIndexOf != -1)
		{
			String jarName = sub.substring(sub.lastIndexOf("/")+1,lastIndexOf);
			File file = new File(path);
			jar = new File(file.getParent());
			SAVE_PATH = new File(file.getParent(),"."+jarName);
			if(!SAVE_PATH.exists() || !SAVE_PATH.isDirectory())
				SAVE_PATH.mkdirs();
		}
		else
		{
			SAVE_PATH = new File(path);
			jar = SAVE_PATH;
		}
		File lw_jar = new File(jar,"lw.getHtmlJar.properties");
		System.out.println("lw.getHtmlJar.properties==="+lw_jar);
		if(!lw_jar.exists() || !lw_jar.isFile()){
			StringBuffer sb = new StringBuffer();
			sb.append("Charset=UTF-8\n");
			sb.append("folderName=ChunYuYiSheng\n");
			sb.append("savePath="+SAVE_PATH.getPath().replace("\\", "/")+"\n");
			sb.append("startIndex=0\n");
			sb.append("endIndex=20000\n");
			sb.append("intervalTime=5000\n");
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
				SAVE_PATH = null;
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
			String folderName = p.getProperty("folderName");
			if (folderName != null && !folderName.trim().equals("")) {
				setFolderName(folderName.trim());
			}
			String replace = p.getProperty("savePath");
			replace = replace.replaceAll("////", "/").trim();
	        
	        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("(^//.|^/|^[a-zA-Z])?:?/.+(/$)?");
	        java.util.regex.Matcher m = pattern.matcher(replace);
	        //不符合要求直接返回
	        if(m.matches()){
	        	setSavePath(new File(replace));
	        }
	        String startIndex = p.getProperty("startIndex", 0+"");
			try {
				long parseLong = Long.parseLong(startIndex);
				setStartIndex(parseLong);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			String endIndex = p.getProperty("endIndex", 20000+"");
			try {
				long parseLong = Long.parseLong(endIndex);
				setEndIndex(parseLong);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			String intervalTime = p.getProperty("intervalTime", 5000+"");
			try {
				long parseLong = Long.parseLong(intervalTime);
				setIntervalTime(parseLong);
			} catch (NumberFormatException e) {
				e.printStackTrace();
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
		TipConfiguration();
	}
	public static synchronized void getHtmlData(long q_id) {
		String urlString = "http://weixin.chunyuyisheng.com/pc/article/"+q_id;
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = connection.getInputStream();
				byte[]b = new byte[inputStream.available()];
				inputStream.read(b);
				save(new File(SAVE_PATH, q_id+".html"), new String(b));
			}else {
				appendMethodByFileWriter(new File(SAVE_PATH,"HttpURLConnection_ResponseCode失败.txt"),urlString+"\n");
			}
			try {
				Thread.sleep(intervalTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}catch(Exception e){
			appendMethodByFileWriter(new File(SAVE_PATH,"HttpURLConnection_Exception失败.txt"),urlString+"\n");
		}
		appendMethodByFileWriter(new File(SAVE_PATH,"loadRecord.txt"),urlString+"\n");
	}

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
	/**
	 * B方法追加文件：使用FileWriter
	 * 
	 * @param fileName
	 * @param content
	 */
	public static void appendMethodByFileWriter(File file, String content) {
		if (!file.getParentFile().exists() || !file.getParentFile().isDirectory()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists() || !file.isFile()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileWriter writer = null;
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			 writer = new FileWriter(file, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
	
}
