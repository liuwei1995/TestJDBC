package com.zhaoyao.com.yiyaowang;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhaoyao.com.http.HttpUtils;

/**
 * 医药网首页的新闻    新闻资讯
 * @author lw  
 * 编写时间: 2017年7月4日上午9:56:37
 * 类说明 :
 */
public class GetDataYiYaoWangTcmKnowledgeNewsInformation {

	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);  
	
	public static String out_path = "C:/Users/dell/Desktop/data/YiYaoWang/新闻资讯/";
	
	public static final String TAG = "新闻资讯";
	
	 public static void main(String[] args) {
//		for (int i = 2; i < 254; i++) {
//			final int m = i;//index250
//			getAndShang("http://www.pharmnet.com.cn/tcm/knowledge/ycrs/index"+m+".html");
//		}
		 
//		 getAndShang("http://www.pharmnet.com.cn/");
		 String get2String = HttpUtils.newInstance().get2String("http://www.zhaoyaoba.com/health/cli/content/getById/3");
		 System.out.println(get2String);
		 
//		 File file = new File(out_path);
//		 for (File f : file.listFiles()) {
//			if (f.isDirectory()) {
//				if (f.listFiles().length != 75) {
//					System.out.println("!= 75 \t"+f.getPath());
//				}
//				for (File ff : f.listFiles()) {
//					if (ff.isFile()) {
//						String name = ff.getName();
//						if (name.equals("GetDataYiYaoWangTcmKnowledgeYCRS.txt")) {
//							List<String> readFileByLinesToList = FileUtils.readFileByLinesToList(ff);
//							ff.delete();
//							if (readFileByLinesToList != null) {
//								for (int i = 0; i < readFileByLinesToList.size(); i++) {
//									String string = readFileByLinesToList.get(i);
//									String[] split = string.split("	");
//									if (split.length != 3) {
//										System.out.println("=====================");
//										continue;
//									}
//									jsoupTo(split[1], split[2], split[0]);
//								}
//							}
//						}else if (name.equals("IOException.txt")) {
//							ff.delete();
//						}
//					}
//				}
//			}
//		}
	}
	 
	 
	 private static synchronized void getAndShang(String url) {
		 try {
				Document document =  Jsoup.connect(url).get();
				Elements elementsByClass_news_list = document.getElementsByClass("news_list");
				if (elementsByClass_news_list.isEmpty()) {
					return;
				}
				Element element = elementsByClass_news_list.get(0);
				Elements elementsByTag_li = element.getElementsByTag("li");
				for (int i = 0; i < elementsByTag_li.size(); i++) {
					Element element_li = elementsByTag_li.get(i);
					Elements elementsByTag_a = element_li.getElementsByTag("a");
					if (elementsByTag_a.isEmpty()) {
						continue;
					}
					String href_a = elementsByTag_a.get(0).attr("href");//http://news.pharmnet.com.cn/news/2017/07/05/473374.html
					
					
					Elements elementsByTag_span = element_li.getElementsByTag("span");
					if (elementsByTag_span.isEmpty()) {
						continue;
					}
					String text_span = "2017-"+elementsByTag_span.get(0).text();//2017-07-05
////					System.out.println(text_span+"\t"+href_a);
//					Map<String, String> map = new HashMap<String, String>();
//					map.put("createtime", text_span);
					getAndShang(href_a, TAG);
				}
//				SaveFile.save(new File(out_path,"elementsByTag_a.txt"), elementsByTag_a.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
	 }
	
	 public static synchronized void getAndShang(String url,String ctags){
		try {
			Document document = Jsoup.connect(url).get();
			Elements elementsByClass = document.getElementsByClass("sm");
			elementsByClass.get(0).remove();
			document.getElementsByClass("footer").remove();
			document.getElementsByClass("newsnav").remove();
			Elements elementsByClass2 = document.getElementsByClass("content");
			Element element = elementsByClass2.get(0);
			
			Map<String, String> map = new HashMap<String, String>();
			
			Elements h1 = element.select("h1").remove();
			String title = h1.text();
			map.put("title", title);
			element.getElementsByClass("bd").remove();
			element.getElementsByClass("ct04").remove();
			element.getElementsByClass("ct042").remove();
			element.getElementsByClass("ct03").remove();
			Element elementById_right = element.getElementById("right");
			if (elementById_right != null) {
				elementById_right.remove();
			}
			
			Elements elementsByTag_strong = element.getElementsByTag("strong");
			for (int i = 0; i < elementsByTag_strong.size(); i++) {
				Element element_strong = elementsByTag_strong.get(i);
				Elements elementsByTag_a = element_strong.getElementsByTag("a");
				for (int j = 0; j < elementsByTag_a.size(); j++) {
					Element element_a = elementsByTag_a.get(j);
					element_a.removeAttr("href");  
					element_a.removeAttr("onclick");  
					String target = element_a.attr("target");
					if (target.equals("_blank")) {
						Elements remove_span = element_a.getElementsByTag("span").remove();
						String text_span = remove_span.text();
						if (elementsByTag_a != null) {
							try {
								elementsByTag_a.remove();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						element_strong.append(text_span);
					}
				}
			}
			Elements ct01 = element.getElementsByClass("ct01").remove();
			ct01.select("a").remove();
			String ct01_text = ct01.text().trim();
			String[] split_ct01_text = ct01_text.split("　");
			String time = split_ct01_text[1];
			map.put("createtime", time);
			map.put("contentsource", split_ct01_text[2]+";"+url);
			Elements a = element.select("a");
			for (int i = 0; i < a.size(); i++) {
				Element element2 = a.get(i);
				String attr = element2.attr("href");
				if (attr.equals("http://news.pharmnet.com.cn")) {
					element2.remove();
				}
			}
			Elements select = element.select("img");
			Elements select2 = select.select("[style]");
			for (int i = 0; i < select2.size(); i++) {
				 select.removeAttr("style");  
			}
			Elements elementsByClass_ct02 = element.getElementsByClass("ct02");
			Element element_ct02 = elementsByClass_ct02.get(0);
			Elements elementsByTag_ct02_strong = element_ct02.getElementsByTag("strong");
			for (int i = 0; i < elementsByTag_ct02_strong.size(); i++) {
				Element element_ct02_strong = elementsByTag_ct02_strong.get(i);
				Elements elementsByTag_img = element_ct02_strong.getElementsByTag("img");
				if (elementsByTag_img.isEmpty()) {
					Element parent = element_ct02_strong.parent();
					if (parent != null) {
						parent.text(parent.text());
						Elements elementsByTag = parent.getElementsByTag("strong");
						try {
							elementsByTag.remove();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			String content = elementsByClass2.toString().replace("医药网", "照耀健康网").replace("浙江网盛生意宝股份有限公司", "照耀网络科技成都有限公司");
			map.put("content", content);
			map.put("ctags", ctags);
//			String post2String = HttpUtils.newInstance().postData2String("http://www.zhaoyaoba.com/health/admin/log/content/saveIndustry", map);
			String post2String = HttpUtils.newInstance().postData2String("http://www.zhaoyaoba.com:80/health/admin/log/content/saveIndustry", map);
//			String post2String = HttpUtils.newInstance().postData2String("http://192.168.0.102:8080/health/admin/log/content/saveIndustry", map);
			System.out.println(post2String+"\nurl:\t"+url);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("\nurl:\t"+url);
		}
}
}
