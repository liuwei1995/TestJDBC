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
 * 老中医    中医养生 
 * @author lw  
 * 编写时间: 2017年7月4日上午9:56:37
 * 类说明 :
 */
public class GetDataLzyyswTCMHealth {

	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);  
	
	public static String out_path = "C:/Users/dell/Desktop/data/老中医/中医养生/";
	
	static{
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File homeDirectory = fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
		out_path = new File(homeDirectory,"data/老中医/中医养生/").toString()+"/";
		System.out.println(out_path);
	}
	 public static void main(String[] args) {
		 System.err.println(out_path);
//		for (int i = 1; i < 4; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/8/"+i);//方剂大全
//		}

//		for (int i = 1; i < 6; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/9/"+i);//煎煮大法
//		}
//		for (int i = 1; i < 6; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/10/"+i);//用药禁忌
//		}
//		for (int i = 1; i < 10; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/11/"+i);//偏方秘方
//		}
//		for (int i = 1; i < 10; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/12/"+i);//中药视野
//		}
//		for (int i = 1; i < 7; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/13/"+i);//中成药坛
//		}
//		for (int i = 1; i < 7; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/14/"+i);//奇方妙剂
//		}
//		for (int i = 1; i < 4; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/15/"+i);// 疾病方药
//		}
//		for (int i = 1; i < 19; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/16/"+i,"望闻问切");// 望闻问切
//		}
		 
		 
//		for (int i = 1; i < 3; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/17/"+i,"望闻问切");// 百问其诊
//		}
//		for (int i = 1; i < 3; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/18/"+i,"望闻问切");// 切脉知病
//		}
//		for (int i = 1; i < 2; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/19/"+i,"望闻问切");// 小儿四诊
//		}
//		for (int i = 1; i < 3; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/20/"+i,"望闻问切");// 舌像图解
//		}
//		for (int i = 1; i < 3; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/21/"+i,"望闻问切");//  自诊自疗
//		}
//		for (int i = 1; i < 3; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/22/"+i,"望闻问切");//  望神色
//		}
//		for (int i = 1; i < 4; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/23/"+i,"望闻问切");//  望五官
//		}
//		for (int i = 1; i < 6; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/24/"+i,"望闻问切");//  望其它
//		}
//		for (int i = 1; i < 2; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/25/"+i,"望闻问切");// 听声音·闻气味
//		}
		 
		 
//		for (int i = 1; i < 8; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/26/"+i,"中医专题");// 中医专题
//		}
		 
//		for (int i = 1; i < 6; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/28/"+i,"中医养生");//白领养生
//		}
//		for (int i = 1; i < 8; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/29/"+i,"中医养生");//男人养生
//		}
//		for (int i = 1; i < 15; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/30/"+i,"中医养生");//女人养生
//		}
//		
//		for (int i = 1; i < 8; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/31/"+i,"中医养生");//老人养生
//		}
//		for (int i = 1; i < 5; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/32/"+i,"中医养生");//小儿健康
//		}
//		for (int i = 1; i < 24; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/33/"+i,"中医养生");//养生有道
//		}
//		for (int i = 1; i < 8; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/34/"+i,"中医养生");// 中医与养生文化
//		}
//		for (int i = 1; i < 6; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/35/"+i,"中医养生");// 休闲与养生
//		}
//		for (int i = 1; i < 8; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/36/"+i,"中医养生");// 居家与养生
//		}
//		for (int i = 1; i < 26; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/37/"+i,"中医养生");// 饮食与养生
//		}
//		
////		针灸推拿
//		for (int i = 1; i < 36; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/38/"+i,"针灸推拿");// 针灸推拿
//		}
//		for (int i = 1; i < 2; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/39/"+i,"针灸推拿");// 误区提醒
//		}
//		
//		for (int i = 1; i < 8; i++) {
//			getAndShang("http://www.lzyysw.com/zhongyi/list/40/"+i,"针灸推拿");// 刮痧拔罐
//		}
		for (int i = 1; i < 3; i++) {
			getAndShang("http://www.lzyysw.com/zhongyi/list/42/"+i,"针灸推拿");// 针灸减肥
		}
		for (int i = 1; i < 3; i++) {
			getAndShang("http://www.lzyysw.com/zhongyi/list/43/"+i,"针灸推拿");// 疾病针灸
		}
		for (int i = 1; i < 3; i++) {
			getAndShang("http://www.lzyysw.com/zhongyi/list/44/"+i,"针灸推拿");// 耳穴奇谈
		}
		for (int i = 1; i < 3; i++) {
			getAndShang("http://www.lzyysw.com/zhongyi/list/45/"+i,"针灸推拿");// 动态前沿
		}
		for (int i = 1; i < 7; i++) {
			getAndShang("http://www.lzyysw.com/zhongyi/list/46/"+i,"针灸推拿");// 针刺艾灸
		}
		for (int i = 1; i < 15; i++) {
			getAndShang("http://www.lzyysw.com/zhongyi/list/47/"+i,"针灸推拿");// 推拿按摩
		}
		
//		getAndShang("http://www.lzyysw.com/zhongyi/list/10/"+3);//用药禁忌
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
			fileName = url.replace("http://www.lzyysw.com/zhongyi/detail/", "").replace(".html", "");
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
