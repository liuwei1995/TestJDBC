package com.zhaoyao.com.yiyaowang;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhaoyao.com.http.HttpUtils;

public class GetDataYiYaoWang {
	
		public static String[] data = {
			"http://news.pharmnet.com.cn/news/2017/06/26/472726.html"
			,"http://news.pharmnet.com.cn/news/2017/06/30/473101.html"
			,"http://news.pharmnet.com.cn/news/2017/06/27/472799.html"
			,"http://news.pharmnet.com.cn/news/2017/06/26/472726.html"
		};
		 public static void main(String[] args) {
//			String url = "http://news.pharmnet.com.cn/news/2017/06/30/473101.html";
//			for (int i = 0; i < data.length; i++) {
//				getAndShang(data[i]);
//			}
			 getAndShang("http://news.pharmnet.com.cn/news/2017/07/03/473190.html","行业动态");
		}
		public static synchronized void getAndShang(String url,String ctags){
	//		String data = HttpUtils.newInstance().get2String(url);
	//		data = data.replace("医药网", "照耀健康网").replace("浙江网盛生意宝股份有限公司", "照耀网络科技成都有限公司");
	//		Document document = Jsoup.parse(data);
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
				String post2String = HttpUtils.newInstance().postData2String("http://192.168.0.102:8080/health/admin/log/content/saveIndustry", map);
				System.out.println(post2String+"\nurl:\t"+url);
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("\nurl:\t"+url);
			}
	}
}
