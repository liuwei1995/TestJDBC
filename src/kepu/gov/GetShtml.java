package kepu.gov;



import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetShtml {
	
	
	private static Properties loadProperties() {
		
		String path = GetShtml.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			path = URLDecoder.decode(path, "utf-8");// 转化为utf-8编码  
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		path = path.trim().substring(1);
		String sub = path.substring(path.lastIndexOf("/"));
		int lastIndexOf = sub.lastIndexOf(".");
		File jar = null;
		if(lastIndexOf != -1)
		{
			String jarName = sub.substring(sub.lastIndexOf("/")+1,lastIndexOf);
			File file = new File(path);
			jar = new File(file.getParent());
			savePath = new File(file.getParent(),"."+jarName).toString();
		}else {
			savePath = new File(path).toString();
			jar = new File(path);
		}
		System.out.println("SAVE_PATH:\t"+savePath);
		File lw_jar = new File(jar,"lw.Load_configuration.properties");
		if (!lw_jar.exists() || !lw_jar.isFile()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("Charset", "UTF-8");
			map.put("loadPropertiesName", "lw.html.properties");
			saveProperties(lw_jar, map);
		}
		Properties properties = getProperties(lw_jar);
		if (properties == null) {
			throw new NullPointerException(lw_jar.toString()+"  文件 不存在");
		}
		String property = properties.getProperty("loadPropertiesName","lw.html.properties");
		if (!new File(jar,property).exists() || !new File(jar,property).isFile()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("Charset", "UTF-8");
			map.put("requestMethod", "GET");
			map.put("httpUrl", "http://www.baidu.com");
			map.put("savePath", savePath);
			saveProperties(new File(jar,property), map);
		}
		System.out.println("savePath前\t"+savePath);
		String savePath = properties.getProperty("savePath",GetShtml.savePath);
		savePath = savePath.replaceAll("////", "/").trim();
		System.out.println("savePath后\t"+savePath);
		File file = new File(savePath);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
		System.out.println("结束\t"+savePath);
		return getProperties(new File(jar,property));
	}
	private static synchronized boolean saveProperties(File lw_jar,Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		if (map != null) {
			for (String key : map.keySet()) {
				sb.append(key+"="+map.get(key)+"\n");
			}
			sb.append("#step数字=getElementsByClass=className"+"\n");
			sb.append("#step数字=getElementsByTag=TagName"+"\n");
			sb.append("#step数字=getElementById=Id"+"\n");
			sb.append("#step数字=elementsGet=index"+"\n");
//			TODO  
		}
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
		} catch (IOException e) {
			
		}finally{
			close(fos,osw,bw);
		}
		return false;
	}
	private static synchronized Properties getProperties(File lw_jar) {
		Properties p = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		try {
			 fis = new FileInputStream(lw_jar);
			 p = new Properties();  
			//p需要InputStream对象进行读取文件，而获取InputStream有多种方法：  
			//1、通过绝对路径:InputStream is=new FileInputStream(filePath);  
			//2、通过Class.getResourceAsStream(path);  
			//3、通过ClassLoader.getResourceAsStream(path);   
//				p.load(new InputStreamReader(fis,"GBK"));  
//			String encode = getLocalFileEncode(lw_jar);
			 isr = new InputStreamReader(fis,"GBK");
			p.load(isr);  
			close(fis,isr);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}finally{
			close(fis,isr);
		}
		return p;
	}
	
	private static String httpUrl = null;
	private static String savePath = null;
	
	private static String requestMethod = null;
	
	
	public static synchronized Properties reload() {
		Properties loadProperties = loadProperties();
		if (loadProperties != null) {
			System.out.println(loadProperties.toString());
//			httpUrl=http://www.baidu.com, savePath=D:/workspace5/TestJDBC/bin/.lw.html.properties, Charset=UTF-8
			httpUrl = (String) loadProperties.remove("httpUrl");
			System.out.println("savePath前\t"+savePath);
			String savePath = (String) loadProperties.remove("savePath");
			savePath = savePath.replaceAll("////", "/").trim();
			System.out.println("savePath后\t"+savePath);
			requestMethod = (String) loadProperties.remove("requestMethod");
			loadProperties.remove("Charset");
		}
		System.out.println("savePath\t"+savePath);
		if (httpUrl == null) {
			throw new NullPointerException("httpUrl  == null");
		}
		if (savePath == null) {
			String path = GetShtml.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			try {
				path = URLDecoder.decode(path, "utf-8");// 转化为utf-8编码  
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			savePath = path.trim().substring(1);
		}
		if (requestMethod == null) {
			requestMethod = "GET";
		}
		return loadProperties;
	}
	
	public static void main(String[] args) {
		Properties reload = reload();
////		String url = "http://www.gr158.cn/content/list.shtml?pageIndex=2&perpage=10&channelId=103&categoryId=31&moduleId=69&searchKeywords=&tagName=&year=&month=";
//		String url = "http://m.gr158.cn/infoNews/1392.html";
////		String url = "http://www.kepu.gov.cn/index/datas/Policy/201706/t20170616_2919680.shtml";
		try {
			Document html = null;
			if (requestMethod.equalsIgnoreCase("GET")) {
				html = Jsoup.connect(httpUrl).get();
			}else if (requestMethod.equalsIgnoreCase("POST")) {
				html = Jsoup.connect(httpUrl).post();
			} else {
				throw new NullPointerException("requestMethod == null || !GET  || !POST");
			}
			List<String> list = new ArrayList<String>();
			
			StringBuilder sb = new StringBuilder();
			sb.append("当前的配置为：\n");
			sb.append("\thttpUrl="+httpUrl+"\n");
			sb.append("\trequestMethod="+requestMethod+"\n");
			sb.append("\tsavePath="+savePath+"\n");
			sb.append("执行步骤为：\n");
			for (Object key : reload.keySet()) {
				list.add(reload.get(key).toString());
				sb.append("\t"+key+"="+reload.get(key.toString())+"\n");	
			}
			System.out.println(sb.toString());
			Element element = html;
			Elements elements = null;
			for (int i = list.size()-1; i >= 0; i--) {
				String string = list.get(i);
				if (string.indexOf("=") != -1) {
					String[] split = string.split("=");
					if (split.length != 2) {
						throw new NullPointerException(string+"\tgetElementsByClass=news-detail-page");
					}else {
						if (split[0].equalsIgnoreCase("getElementsByClass")) {
							elements = getElementsByClass(element, split[1]);
						}
						else if (split[0].equalsIgnoreCase("getElementById")) {
							element = getElementById(element, split[1]);
						}
						else if (split[0].equalsIgnoreCase("getElementsByTag")) {
							elements = getElementsByTag(element, split[1]);
						}
						else if (split[0].equalsIgnoreCase("elementsGet")) {
							try {
								element = elementsGet(elements, Integer.valueOf(split[1]));
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			System.out.println("savePath\t"+savePath);
			File file = new File(savePath,new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date())+".txt");
			System.out.println(file.toString());
			save(file,element.toString());
//			String property = reload.getProperty("getElementsByClass");
//			Elements elementsByClass = html.getElementsByClass("news-detail-page");
//			Element element = elementsByClass.get(0);
//			Elements elementsByTag = element.getElementsByTag("p");
//			for (int i = 0; i < elementsByTag.size(); i++) {
//				Element element2 = elementsByTag.get(i);
//				element2.removeAttr("style");
//				element2.attr("style", "margin-bottom:20px; text-indent:2em;");
//				System.out.println(element2.toString());
//			}
//			System.out.println(elementsByClass.toString());
		} catch (IOException e) {
			e.printStackTrace();
			save(new File(savePath,"IOException.txt"), e.toString());
		}
	}
	
	private static Elements getElementsByClass(Element element, String string) {
		return element.getElementsByClass(string);
	}
	private static Elements getElementsByTag(Element element, String tag) {
		return element.getElementsByTag(tag);
	}
	private static Element elementsGet(Elements elements, int index) {
		return elements.get(index);
	}
	
	public static synchronized Element getElementById(Element html,String id) {
		return html.getElementById(id);
	}
	
	public static void close(Closeable ...io) {
		if (io != null) {
			for (int i = 0; i < io.length; i++) {
				try {
					if (io[i] != null) {
						io[i].close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
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
