package com.zhaoyao.com.yiyaowang;

import java.io.File;
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
import com.zhaoyao.file.AppendToFile;

/**
 * 中医知识
 * @author lw  TCM basis
 * 编写时间: 2017年7月4日上午9:56:37
 * 类说明 :
 */
public class GetDataYiYaoWangTcmKnowledge {

	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);  
	public static String[] da = {
		"http://www.pharmnet.com.cn/tcm/knowledge/detail/127523.html"
			,"http://www.pharmnet.com.cn/tcm/knowledge/detail/127448.html"
//			http://www.pharmnet.com.cn/tcm/knowledge/detail/125818.html
			,"http://www.pharmnet.com.cn/tcm/knowledge/detail/127429.html"
			,"http://www.pharmnet.com.cn/tcm/knowledge/detail/127420.html"
			,"http://www.pharmnet.com.cn/tcm/knowledge/detail/127404.html"
	}; 
	 public static void main(String[] args) {
//		getAndShang("http://www.pharmnet.com.cn/tcm/knowledge/zyzs/index.html","中医知识");
//		for (int i = 100; i < 141; i++) {
//			final int m = i;
//			getAndShang("http://www.pharmnet.com.cn/tcm/knowledge/zyzs/index"+m+".html","中医知识");
//		}
//		 String data = "望诊望舌	http://www.pharmnet.com.cn/tcm/knowledge/zyzs/index44.html	http://www.pharmnet.com.cn/tcm/knowledge/detail/127156.html";
//		 getAndShang1(data,"中医知识");
//		jsoupTo("http://www.pharmnet.com.cn/tcm/knowledge/detail/127499.html");
	}
	 
	 
	private static synchronized void getAndShang1(String data,String ctags) {
		try {
//			
			String[] split = data.split("	");
			Document document =  Jsoup.connect(split[1]).get();
			Elements elementsByClass_blackno = document.getElementsByClass("blackno");
			for (int i = 0; i < elementsByClass_blackno.size(); i++) {
				Element element_blackno = elementsByClass_blackno.get(i);
				Element blackno_parent_parent_parent = element_blackno.parent();
				if (blackno_parent_parent_parent == null || (blackno_parent_parent_parent =  blackno_parent_parent_parent.parent()) == null
						|| (blackno_parent_parent_parent =  blackno_parent_parent_parent.parent()) == null
						) {
					continue;
				}
				Elements elementsByClass_yellow = blackno_parent_parent_parent.getElementsByClass("yellow");
				if (elementsByClass_yellow.isEmpty()) {
					continue;
				}
				if (!element_blackno.attr("href").equals(split[2])) {
					continue;
				}
				jsoupTo(split[1],element_blackno.attr("href"), element_blackno.text(),elementsByClass_yellow.text());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static synchronized void getAndShang(String url,String ctags) {
		try {
			Document document =  Jsoup.connect(url).get();
			Elements elementsByClass_blackno = document.getElementsByClass("blackno");
			for (int i = 0; i < elementsByClass_blackno.size(); i++) {
				Element element_blackno = elementsByClass_blackno.get(i);
				Element blackno_parent_parent_parent = element_blackno.parent();
				if (blackno_parent_parent_parent == null || (blackno_parent_parent_parent =  blackno_parent_parent_parent.parent()) == null
						|| (blackno_parent_parent_parent =  blackno_parent_parent_parent.parent()) == null
						) {
					continue;
				}
				Elements elementsByClass_yellow = blackno_parent_parent_parent.getElementsByClass("yellow");
				if (elementsByClass_yellow.isEmpty()) {
					continue;
				}
				jsoupTo(url,element_blackno.attr("href"), element_blackno.text(),elementsByClass_yellow.text());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static synchronized void jsoupTo(final String parentUrl,final String url,final String title,final String generalcontent) {
		fixedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Document document =  Jsoup.connect(url).get();
					Element elementById_fontsize = document.getElementById("fontsize");
					if (elementById_fontsize == null) {
						System.err.println("elementById_fontsize == null\t"+document.toString());
						AppendToFile.appendMethodByFileWriter(new File("C:/Users/dell/Desktop/ChunYuYiSheng_50854","GetDataYiYaoWangTcmKnowledge.txt"), title+"\t"+parentUrl+"\t"+url+"\n");
						try {
							Thread.sleep(1200);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return;
					}
					Element element = elementById_fontsize.parent();
					if (element == null 
							|| (element = element.parent()) == null
							|| (element = element.parent()) == null
							|| (element = element.parent()) == null
							) {
						go("jsoupTo element == null");
						return;
					}
					Elements elementsByTag_tr = element.getElementsByTag("tr");
					elementsByTag_tr.get(5).remove();
					elementsByTag_tr.get(4).remove();
					elementsByTag_tr.get(0).remove();
					elementsByTag_tr.get(1).remove();
					elementsByTag_tr.get(2).remove();
					
					element.removeAttr("width");  
					element.removeAttr("height");  
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("title", title);
//					map.put("createtime", time);
					map.put("generalcontent", generalcontent);
					map.put("contentsource", "医药网;"+url);
					map.put("content", element.toString());
					map.put("ctags", "中医知识");
					upload(url,map);
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(1200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private static synchronized void upload(String url,Map<String, String> map) {
//		"http://192.168.0.102:8080/health/admin/log/content/saveIndustry"
		String ip = "http://192.168.0.102:8080/health/";
		String path = "admin/log/content/save";
		String post2String = HttpUtils.newInstance().postData2String(ip+path, map);
		System.out.println(post2String+"\nurl:\t"+url);
	}
	public static void go(Object object){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for(StackTraceElement s : stackTrace){
            System.out.println("类名：" + s.getClassName() + "  ,  java文件名：" + s.getFileName() + ",  当前方法名字：" + s.getMethodName() + ""
                    + " , 当前代码是第几行：" + s.getLineNumber() + ", \n\t:" +(object != null ? object.toString() : null));
        }
    }
}
