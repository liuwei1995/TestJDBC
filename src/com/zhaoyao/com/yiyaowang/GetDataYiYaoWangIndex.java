package com.zhaoyao.com.yiyaowang;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhaoyao.com.http.HttpUtils;

public class GetDataYiYaoWangIndex {
	
		 public static void main(String[] args) {
//			String url = "http://news.pharmnet.com.cn/news/2017/06/30/473101.html";
//			for (int i = 0; i < data.length; i++) {
//				getAndShang(data[i]);
//			}
//			getAndShang("http://www.pharmnet.com.cn/fwjs/jspt/index0.html","医药资讯");
//			getAndShang("http://www.pharmnet.com.cn/fwjs/jspt/index1.html","医药资讯");
//			getAndShang("http://www.pharmnet.com.cn/rzzx/rzzx/index0.html","认证资讯");
//			getAndShang("http://www.pharmnet.com.cn/rzzx/rzzx/index1.html","认证资讯");
			 
//			getAndShang("http://news.pharmnet.com.cn/news/gjdt/index0.html","医药国际动态");
//			getAndShang("http://news.pharmnet.com.cn/news/gjdt/index1.html","医药国际动态");
			 
//			getMedicalEquipment("http://www.pharmnet.com.cn/ylqx/news/index0.html","医疗器械");
//			getMedicalEquipment("http://www.pharmnet.com.cn/ylqx/news/index1.html","医疗器械");
			 
			getLaws("http://law.pharmnet.com.cn/laws/list_2-67_5-116_1.html","政策法规");
		}
		 
		 
		private static synchronized void getAndShang(String url,String ctags) {
			String data = HttpUtils.newInstance().get2String(url);
			Document document = Jsoup.parse(data);
			Elements elementsByClass_list = document.getElementsByClass("list");
			Element element_list = elementsByClass_list.get(0);
			Elements elementsByTag_ul = element_list.getElementsByTag("ul");
			Element element_ul = elementsByTag_ul.get(0);
			Elements elementsByTag_li = element_ul.getElementsByTag("li");
			for (int i = 0; i < elementsByTag_li.size(); i++) {
				Element element = elementsByTag_li.get(i);
				Elements elementsByTag_a = element.getElementsByTag("a");
				if (elementsByTag_a.isEmpty()) {
					continue;
				}
				Element element_a0 = elementsByTag_a.get(0);
				String attr_href = element_a0.attr("href");
				GetDataYiYaoWang.getAndShang(attr_href,ctags);
			}
		}
		//医疗器械
		private static synchronized void getMedicalEquipment(String url,String ctags) {
			String data = HttpUtils.newInstance().get2String(url);
			Document document = Jsoup.parse(data);
			Elements elementsByTag_a = document.getElementsByTag("a");
			for (int i = 0; i < elementsByTag_a.size(); i++) {
				Element element_a = elementsByTag_a.get(i);
				Elements elementsByClass = element_a.getElementsByClass("greenbig");
				if (elementsByClass.isEmpty()) {
					continue;
				}
				String attr_href = element_a.attr("href");
				GetDataYiYaoWang.getAndShang(attr_href,ctags);
			}
		}
		private static synchronized void getLaws(String url,String ctags) {
			String data = HttpUtils.newInstance().get2String(url);
			Document document = Jsoup.parse(data);
			Elements elementsByTag_a = document.getElementsByTag("a");
			for (int i = 0; i < elementsByTag_a.size(); i++) {
				Element element_a = elementsByTag_a.get(i);
				Elements elementsByClass = element_a.getElementsByClass("tex");
				if (elementsByClass.isEmpty()) {
					continue;
				}
				String attr_href = element_a.attr("href");
				getShang("http://law.pharmnet.com.cn/laws/"+attr_href,ctags);
			}
		}
		
		public static synchronized void getShang(String url,String ctags) {
			String data = HttpUtils.newInstance().get2String(url);
			data = data.replace("医药网", "照耀健康网").replace("浙江网盛生意宝股份有限公司", "照耀网络科技成都有限公司");
			Document document = Jsoup.parse(data);
			Elements elementsByClass_txt1 = document.getElementsByClass("txt1");
			if (!elementsByClass_txt1.isEmpty()) {
				Element element_txt1 = elementsByClass_txt1.get(0);
				Element parent_txt1 = element_txt1.parent().parent();
				Elements elementsByTag_tr = parent_txt1.getElementsByTag("tr");
				elementsByTag_tr.get(0).remove();
				
				Map<String, String> map = new HashMap<String, String>();

				String title = parent_txt1.getElementsByTag("strong").get(0).text();
				map.put("title", title);
				map.put("content", parent_txt1.toString());
				map.put("ctags", ctags);
				map.put("contentsource", " ;"+url);
				String post2String = HttpUtils.newInstance().postData2String("http://192.168.0.102:8080/health/admin/log/content/saveIndustry", map);
				System.out.println(post2String+"\nurl:\t"+url);
			}
		}
}
