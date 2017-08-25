package com.zhaoyao.com.yiyaowang;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhaoyao.file.AppendToFile;
import com.zhaoyao.file.FileUtils;
import com.zhaoyao.file.SaveFile;

/**
 * 药材认识
 * @author lw  TCM basis
 * 编写时间: 2017年7月4日上午9:56:37
 * 类说明 :
 */
public class GetDataYiYaoWangTcmKnowledgeYCRS {

	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);  
	
	public static String out_path = "C:/Users/dell/Desktop/data/YiYaoWang/药材认识/";
	
	 public static void main(String[] args) {
//		for (int i = 2; i < 254; i++) {
//			final int m = i;//index250
//			getAndShang("http://www.pharmnet.com.cn/tcm/knowledge/ycrs/index"+m+".html");
//		}
		 
		 getAndShang("http://www.pharmnet.com.cn/tcm/knowledge/ycrs/index250.html");
		 
		 
		 
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
				Elements elementsByClass_red = document.getElementsByClass("red");
				Element element = elementsByClass_red.get(0);
				Element red_parent = element.parent();
				if (red_parent == null 
						|| (red_parent = red_parent.parent()) == null//td
						|| (red_parent = red_parent.parent()) == null//tr/td
						|| (red_parent = red_parent.parent()) == null//tbody/tr/td
						|| (red_parent = red_parent.parent()) == null//table/tbody/tr/td
						|| (red_parent = red_parent.parent()) == null//td/table/tbody/tr/td
						) {
					return;
				}
				Elements elementsByTag_table = red_parent.getElementsByTag("table");
				elementsByTag_table.get(0).remove();
				elementsByTag_table.get(2).remove();
				Elements elementsByTag_a = red_parent.getElementsByTag("a");
				for (int i = 0; i < elementsByTag_a.size(); i++) {
					Element element_a = elementsByTag_a.get(i);
					jsoupTo(url,element_a.attr("href"), element_a.text());
				}
				SaveFile.save(new File(out_path,"elementsByTag_a.txt"), elementsByTag_a.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
	 }
	
	private static synchronized void jsoupTo(final String parentUrl,final String url,final String title) {
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
				String fileName = null;
				if (url.equals("http://www.pharmnet.com.cn/tcm/knowledge/ycrs/1713/%C3%A9%B2%D4%CA%F5.html")) {
					fileName = floderName+"_"+System.currentTimeMillis();
				}else {
					fileName = url.replace("http://www.pharmnet.com.cn/tcm/knowledge/detail/", "").replace(".html", "");
				}
				File file = new File(out_path+floderName, fileName+".json");
				if (file.exists() && file.isFile()) {
					System.out.println("存在====="+title+"\t"+parentUrl+"\t"+url);
					return;
				}
				System.out.println("开始-----"+title+"\t"+parentUrl+"\t"+url);
				try {
					Document document =  Jsoup.connect(url).get();
					Elements elementsByClass_yellow1 = document.getElementsByClass("yellow1");
					Element elementById_fontsize = document.getElementById("fontsize");
					if (elementById_fontsize == null) {
						System.err.println("等一秒-----"+title+"\t"+parentUrl+"\t"+url);
						AppendToFile.appendMethodByFileWriter(new File(out_path+floderName,GetDataYiYaoWangTcmKnowledgeYCRS.class.getSimpleName()+".txt"), title+"\t"+parentUrl+"\t"+url+"\n");
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
