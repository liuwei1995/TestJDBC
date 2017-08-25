package com.zhaoyao.com.yiyaowang;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhaoyao.file.AppendToFile;
import com.zhaoyao.file.SaveFile;

/**
 * 中医基础
 * @author lw  TCM basis
 * 编写时间: 2017年7月4日上午9:56:37
 * 类说明 :
 */
public class GetDataYiYaoWangTcmKnowledgeBasis {

	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);  
	
	public static String out_path = "C:/Users/dell/Desktop/data/YiYaoWang/";
	
	public static String[] da = {
		"http://www.pharmnet.com.cn/tcm/knowledge/detail/125818.html"
//			,"http://www.pharmnet.com.cn/tcm/knowledge/detail/127448.html"
//			http://www.pharmnet.com.cn/tcm/knowledge/detail/125818.html
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
//		 String readFileByLines = FileUtils.readFileByLines(out_path+"index/"+"127593.json");
//		 JSONObject jsonObject = new JSONObject(readFileByLines);
//		 String string = jsonObject.getString("content");
//		 System.out.println(string);
		for (int i = 141; i < 162; i++) {
			final int m = i;
//			getAndShang("http://www.pharmnet.com.cn/tcm/knowledge/zyzs/index"+m+".html");
		}
//		 getAndShang("http://www.pharmnet.com.cn/tcm/knowledge/zyjc/index.html");
	}
	 
	 
	 private static synchronized void getAndShang(String url) {
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
				int lastIndexOf = parentUrl.lastIndexOf("/");
				String floderName = "index";
				if (lastIndexOf != -1) {
					try {
						String substring = parentUrl.substring(lastIndexOf+1);
						int indexOf = substring.indexOf(".");
						if (indexOf != -1) {
							floderName = substring.substring(0, indexOf);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (floderName == null || floderName.equals("")) {
					floderName = "index";
				}
				String fileName = url.replace("http://www.pharmnet.com.cn/tcm/knowledge/detail/", "").replace(".html", "");
				File file = new File(out_path+floderName, fileName+".json");
				if (file.exists() && file.isFile()) {
					return;
				}
				try {
					Document document =  Jsoup.connect(url).get();
					Elements elementsByClass_yellow1 = document.getElementsByClass("yellow1");
					Element elementById_fontsize = document.getElementById("fontsize");
					if (elementById_fontsize == null) {
						System.err.println("elementById_fontsize == null\t"+document.toString());
						AppendToFile.appendMethodByFileWriter(new File(out_path+floderName,"GetDataYiYaoWangTcmKnowledge.txt"), title+"\t"+parentUrl+"\t"+url+"\n");
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
					String ctags = elementsByClass_yellow1.text();
					map.put("ctags", ctags);//中医基础
					JSONObject jsonObject = new JSONObject(map);
					SaveFile.save(out_path+floderName, fileName+".json", jsonObject.toString());
				} catch (IOException e) {
					e.printStackTrace();
					AppendToFile.appendMethodByFileWriter(new File(out_path+floderName,"IOException.txt"), title+"\t"+parentUrl+"\t"+url+"\n");
				}
				try {
					Thread.sleep(1200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void go(Object object){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for(StackTraceElement s : stackTrace){
            System.out.println("类名：" + s.getClassName() + "  ,  java文件名：" + s.getFileName() + ",  当前方法名字：" + s.getMethodName() + ""
                    + " , 当前代码是第几行：" + s.getLineNumber() + ", \n\t:" +(object != null ? object.toString() : null));
        }
    }
}
