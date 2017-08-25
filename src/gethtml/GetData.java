package gethtml;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
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
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONObject;


public class GetData {


	public static String FOLDER_NAME = "ChunYuYiSheng";
	
	public static void setFolderName(String folderName) {
		FOLDER_NAME = folderName;
	}
	
	private static File SAVE_PATH = null;
	
	private static long startIndex = 0;
	
	public static void setEndIndex(long endIndex) {
		GetData.endIndex = endIndex;
	}

	private static long endIndex = 20000;
	
	private static long intervalTime = 6000;
	
	
	public static void setIntervalTime(long intervalTime) {
		GetData.intervalTime = intervalTime;
	}

	public static void setStartIndex(long startIndex) {
		GetData.startIndex = startIndex;
	}

	public static void setSavePath(File sAVE_PATH) {
		SAVE_PATH = sAVE_PATH;
	}

	private static File START_INDEX = null;

	private static boolean start = false;
	
	private static boolean isStart = false;
	
	public static void help() {
		 System.out.println("请输入exit  退出");
		 System.out.println("请输入reload 重新加载配置  ");
		 System.out.println("请输入start 开始运行  ");
	}

	private static void TipConfiguration() {
		System.out.println("当前的配置为：\n\tsavePath:\t"+SAVE_PATH.toString());
		System.out.println("\tfolderName:\t"+FOLDER_NAME);
		System.out.println("\tstartIndex:\t"+startIndex);
		System.out.println("\tendIndex:\t"+endIndex);
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
		File lw_jar = new File(jar,"lw.getDataJar.properties");
		System.out.println("lw.getHtmlJar.properties==="+lw_jar);
		if(!lw_jar.exists() || !lw_jar.isFile()){
			StringBuffer sb = new StringBuffer();
			sb.append("Charset=UTF-8\n");
			sb.append("folderName=ChunYuYiSheng\n");
			sb.append("savePath="+SAVE_PATH.getPath().replace("\\", "/")+"\n");
			sb.append("startIndex=90000\n");
			sb.append("endIndex=100000\n");
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
	
	
//	public static String path = "C:/Users/dell/Desktop/39";
//	public static String path = "C:/Users/dell/Desktop/ChunYuYiSheng_50854";
//	public static ExecutorService fixedThreadPool1 = Executors.newFixedThreadPool(1);  
//	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);  
	public static void main(String[] args) {
		 Scanner sc = new Scanner(System.in);
		 reload();
		 System.out.print("请输入命令:");
		  while (sc.hasNextLine()) {
			  String nextLine = sc.nextLine();
			  if (nextLine.equalsIgnoreCase("exit")) {
				  	System.exit(0);
			  }else if (nextLine.equalsIgnoreCase("help")) {
				help();
			  }else if (nextLine.equalsIgnoreCase("reload")) {
				  reload();
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
								  getData();
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
	private static void getData() {
		for (long i = startIndex; i < endIndex; i++) {
			long q_id = i;
			String url = "http://api.chunyuyisheng.com/api/news/"+q_id;
			byte[] bs = commit(url,"GET");
			if (bs != null) {
				String string = new String(bs);
				JSONObject jsonObject = new JSONObject(string);
				Set keySet = jsonObject.keySet();
				Iterator iterator = keySet.iterator();
				JSONObject new_object = new JSONObject();
				while (iterator.hasNext()) {
					Object next = iterator.next();
					new_object.put(next.toString(), jsonObject.get(next.toString()));
				}
				if (new_object.length() > 0) {
					Object data = new_object.get("date");
					Object id = new_object.get("id");
					String news_type_txt = new_object.getString("news_type_txt");//育儿
					String typeString = news_type_txt;//title
					save(SAVE_PATH.toString()+"/"+typeString, ""+typeString+"_"+data+"_"+id+".json", new_object.toString());
					System.out.println("id="+q_id+"====="+new_object.toString());
				}else {
					System.err.println("id="+q_id+"-------------------------------------------------------------没有数据");
				}
			}else {
				System.err.println("id="+q_id+"=================================bs==============================没有数据");
				appendMethodByFileWriter(new File(SAVE_PATH,"err空.txt"),url+"\n");
			}
			appendMethodByFileWriter(new File(SAVE_PATH,"loadRecord.txt"),url+"\n");
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

	private static synchronized byte[] commit(String hurl,String method){
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			URL url = new URL(hurl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 
			connection.setRequestMethod(method);
			connection.setReadTimeout(30*000);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.connect();
			if(HttpURLConnection.HTTP_OK == connection.getResponseCode()){
				is = connection.getInputStream();
				baos = new ByteArrayOutputStream(is.available());
				byte[] b = new byte[1024];
				int number = 0;
				while ((number = is.read(b)) != -1) {
					baos.write(b, 0, number);
				}
				return baos.toByteArray();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			File file = new File("C:/Users/dell/Desktop/ChunYuYiSheng_20000","ChunYuYiSheng.txt");
			appendMethodByFileWriter(file, hurl+"\n");
		}finally{
			try {
				if(is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(baos != null){
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
