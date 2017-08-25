package com.zhaoyao.com.dataGR158;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhaoyao.file.SaveFile;

public class GetDataGRNews {
	
	public static String out_path = "C:/Users/dell/Desktop/data/GuoYu/新闻/";
	
	
	public static void main(String[] args) {
//		http://www.gr158.cn/media/list.shtml?pageIndex=2&perpage=10&channelId=24&categoryId=&moduleId=&searchKeywords=&year=2017&month=
		String url = "http://www.gr158.cn/media/list.shtml?pageIndex=1&perpage=30&channelId=24&categoryId=&moduleId=&searchKeywords=&year=2017&month=";
		try {
			Document document = Jsoup.connect(url).get();
//			Jsoup.parse(HttpUtils.newInstance().get2String(url));
			Elements elementsByClass_describe = document.getElementsByClass("describe");
			for (int i = 0; i < elementsByClass_describe.size(); i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				Element element_describe = elementsByClass_describe.get(i);
				Elements elementsByClass_a_tit = element_describe.getElementsByClass("a-tit");
				if (elementsByClass_a_tit.isEmpty()) {
					continue;
				}
				Element element_a_tit = elementsByClass_a_tit.get(0);
				String href_element_a_tit = element_a_tit.attr("href");
				String text_a_tit = element_a_tit.text();
				map.put("title", text_a_tit);
				Elements elementsByClass_p_con = element_describe.getElementsByClass("p-con");
				if (elementsByClass_p_con.isEmpty()) {
					continue;
				}
				Element element_p_con = elementsByClass_p_con.get(0);
				String href_p_con = element_p_con.attr("href");
				String text_p_con = element_p_con.text();
				
				map.put("generalcontent", text_p_con);
				
				Elements elementsByClass_date = element_describe.getElementsByClass("date");
				if (elementsByClass_date.isEmpty()) {
					continue;
				}
				Element element_date = elementsByClass_date.get(0);
				String text = element_date.text();//    Tue Jul 04 11:19:11 CST 2017
				DateFormat sdf2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy", Locale.US);
				Date parse = sdf2.parse(text);
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(parse);
				map.put("createtime", time);
//				map.put("contentsource", "医药网;"+"http://www.gr158.cn"+href_p_con);
				String ctags = "新闻中心";
				map.put("ctags", ctags);//中医基础
				get(map, "http://www.gr158.cn"+href_p_con);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void get(Map<String, String> map,String url) {
		try {
			String floderName = "新闻中心";
			String fileName = url.replace("http://www.gr158.cn/mth/mtbd/", "").replace(".html", "");
			File file = new File(out_path+floderName,fileName+".json");
			if (file.exists() && file.isFile()) {
				System.out.println("存在 ==== "+file.toString());
				return;
			}
			
			Document document = Jsoup.connect(url).get();
			Elements elementsByClass_content_right = document.getElementsByClass("content-right");
			Element element_right = elementsByClass_content_right.get(0);
			element_right.getElementsByClass("HomeTopics").remove();
			element_right.getElementsByClass("bdsharebuttonbox").remove();
			element_right.getElementsByClass("autograph").remove();
			element_right.getElementsByClass("on-next").remove();
			element_right.getElementsByClass("fabulous").remove();
			element_right.getElementsByClass("recommend").remove();
			element_right.getElementsByClass("tst-label").remove();
			element_right.getElementsByClass("comment").remove();
			Elements remove = element_right.getElementsByClass("new-bar").remove();
			Elements elementsByClass = remove.get(0).getElementsByClass("source");
			if (!elementsByClass.isEmpty()) {
				Element element = elementsByClass.get(0);
				String source = element.text();
				String[] split = source.split("：");
				if (split.length == 2) {
					map.put("contentsource", split[1]+";"+url);
				}else {
					map.put("contentsource", " "+";"+url);
				}
			}else {
				map.put("contentsource", " "+";"+url);
			}
			map.put("content", element_right.getElementsByClass("bd").toString());
			JSONObject jsonObject = new JSONObject(map);
			SaveFile.save(out_path+floderName, fileName+".json", jsonObject.toString());
			System.out.println("成功==="+url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
