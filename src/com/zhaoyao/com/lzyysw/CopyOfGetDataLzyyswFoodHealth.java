package com.zhaoyao.com.lzyysw;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.filechooser.FileSystemView;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhaoyao.file.AppendToFile;
import com.zhaoyao.file.SaveFile;

/**
 * 老中医    药膳养生
 * @author lw  
 * 编写时间: 2017年7月4日上午9:56:37
 * 类说明 :
 */
public class CopyOfGetDataLzyyswFoodHealth {

	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);  
	
	public static String out_path = "C:/Users/dell/Desktop/data/老中医/药膳养生/";
	
	public static String http = "http://www.lzyysw.com/"+"yaoshan/";
	static{
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File homeDirectory = fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
		out_path = new File(homeDirectory,"data/老中医/药膳养生/").toString()+"/";
		System.out.println(out_path);
	}
	 public static void main(String[] args) {
		 System.err.println(out_path);
//		for (int i = 1; i < 3; i++) {
//			getAndShang(http+"list/2/"+i,"药膳养生");// 不孕不育调理药膳
//		}
//		for (int i = 1; i < 3; i++) {
//			getAndShang(http+"list/3/"+i,"药膳养生");// 鼻炎调理药膳
//		}
//		for (int i = 1; i < 3; i++) {
//			getAndShang(http+"list/4/"+i,"药膳养生");// 跌打损伤调理药膳
//		}
//		for (int i = 1; i < 3; i++) {
//			getAndShang(http+"list/5/"+i,"药膳养生");// 关节炎调理药膳
//		}
//		for (int i = 1; i < 3; i++) {
//			getAndShang(http+"list/6/"+i,"药膳养生");// 骨质疏松调理药膳
//		}
//		for (int j = 1; j < 3; j++) {
//			getAndShang(http+"list/6/"+j,"药膳养生");// 骨质疏松调理药膳
//		}
		for (int i = 34; i < 38; i++) {
			for (int j = 1; j < 10; j++) {
				getAndShang(http+"list/"+i+"/"+j,"四季药膳");// 骨质疏松调理药膳
			}
		}
		
	}
	 
	 
	 private static synchronized void getAndShang(String url) {
		 try {
				Document document =  Jsoup.connect(url).get();
				Element elementById_archivesList = document.getElementById("archivesList");
				Element elementById_archivesHeader = document.getElementById("archivesHeader");
				elementById_archivesHeader.getElementsByClass("a").remove();
				String floderName = null;
				try {
					floderName = elementById_archivesHeader.text().split(" > ")[1];
				} catch (Exception e) {
					e.printStackTrace();
				}
				Elements elementsByTag_li = elementById_archivesList.getElementsByTag("li");
				for (int i = 0; i < elementsByTag_li.size(); i++) {
					Element element_li = elementsByTag_li.get(i);
					Elements elementsByTag_a = element_li.getElementsByTag("a");
					if (elementsByTag_a.isEmpty()) {
						continue;
					}
					String href_a = elementsByTag_a.get(0).attr("href");
					href_a = "http://www.lzyysw.com"+href_a;
					String text_a = elementsByTag_a.get(0).text();
					Elements elementsByTag_span = element_li.getElementsByTag("span");
					if (elementsByTag_span.isEmpty() || elementsByTag_span.size() != 1) {
						continue;
					}
					String text_span = elementsByTag_span.get(0).text();
					jsoupTo(floderName,text_span.split(" ")[0],url,href_a);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	 }
	 private static synchronized void getAndShang(String url,String outFolderName) {
		 try {
			 Document document =  Jsoup.connect(url).get();
			 Element elementById_archivesList = document.getElementById("archivesList");
			 Element elementById_archivesHeader = document.getElementById("archivesHeader");
			 elementById_archivesHeader.getElementsByClass("a").remove();
			 String floderName = null;
			 try {
				 floderName = elementById_archivesHeader.text().split(" > ")[1];
			 } catch (Exception e) {
				 e.printStackTrace();
			 }
			 Elements elementsByTag_li = elementById_archivesList.getElementsByTag("li");
			 for (int i = 0; i < elementsByTag_li.size(); i++) {
				 Element element_li = elementsByTag_li.get(i);
				 Elements elementsByTag_a = element_li.getElementsByTag("a");
				 if (elementsByTag_a.isEmpty()) {
					 continue;
				 }
				 String href_a = elementsByTag_a.get(0).attr("href");
				 href_a = "http://www.lzyysw.com"+href_a;
				 String text_a = elementsByTag_a.get(0).text();
				 Elements elementsByTag_span = element_li.getElementsByTag("span");
				 if (elementsByTag_span.isEmpty() || elementsByTag_span.size() != 1) {
					 continue;
				 }
				 String text_span = elementsByTag_span.get(0).text();
				 jsoupTo(outFolderName+"/"+floderName,text_span.split(" ")[0],url,href_a);
			 }
		 } catch (IOException e) {
			 e.printStackTrace();
		 }
	 }
	public static void jsoupTo(String floderName,String time,final String parentUrl,final String url) {
		if (floderName == null || floderName.equals("")) {
			floderName = "index";
		}
		int lastIndexOf = parentUrl.lastIndexOf("/");
		if (lastIndexOf != -1) {
			try {
				String substring = parentUrl.substring(lastIndexOf+1);
				int indexOf = substring.indexOf(".");
				if (indexOf != -1) {
					floderName += "/"+substring.substring(0, indexOf);
				}else {
					floderName += "/"+substring;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		floderName += "/"+time;
//		if (floderName == null || floderName.equals("")) 
		String fileName = null;
		if (url.equals("http://www.pharmnet.com.cn/tcm/knowledge/ycrs/1713/%C3%A9%B2%D4%CA%F5.html")) {
			fileName = floderName+"_"+System.currentTimeMillis();
		}else {
			fileName = url.replace(http+"detail/", "").replace(".html", "");
		}
		File file = new File(out_path+floderName, fileName+".json");
		if (file.exists() && file.isFile()) {
			System.out.println("存在====="+"\t"+parentUrl+"\t"+url);
			return;
		}
		System.out.println("开始-----"+"\t"+parentUrl+"\t"+url);
		try {
			Document document =  Jsoup.connect(url).get();
			Element elementById_archivesTitle = document.getElementById("archivesTitle");
			Element elementById_archivesInfo = document.getElementById("archivesInfo");// 发布时间：2017-07-01 15:53:29 浏览：240 来源：互联网
			Element elementById_archivesContent = document.getElementById("archivesContent");
			
			Elements elementsByTag = elementById_archivesContent.getElementsByTag("a");
			for (int i = 0; i < elementsByTag.size(); i++) {
				Element element = elementsByTag.get(i);
				boolean equals = element.text().equals("点击购买");
				if (equals) {
					Elements elementsByTag_p = elementById_archivesContent.getElementsByTag("p");
					elementsByTag_p.get(1).remove();
					elementsByTag_p.get(2).remove();
					break;
				}
			}
			
			String text_archivesInfo = elementById_archivesInfo.text().trim();
			String[] split = text_archivesInfo.split(" ");
			String createtime = split[0].split("：")[1] + " "+split[1];
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("title", elementById_archivesTitle.text());
			map.put("createtime", createtime);
			String[] split2 = split[3].split("：");
			if (split2.length == 2) {
				map.put("contentsource", split[3].split("：")[1]+";"+url);
			}else {
				map.put("contentsource", " "+";"+url);
			}
			map.put("content", elementById_archivesContent.toString());
			map.put("ctags", "奇方妙剂");//
			
			JSONObject jsonObject = new JSONObject(map);
			SaveFile.save(out_path+floderName, fileName+".json", jsonObject.toString());
			System.out.println("成功-----"+"\t"+parentUrl+"\t"+url);
		} catch (IOException e) {
			e.printStackTrace();
			AppendToFile.appendMethodByFileWriter(new File(out_path+floderName,"IOException.txt"), "\t"+parentUrl+"\t"+url+"\n");
		}
	}
	 
}
