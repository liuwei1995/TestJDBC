package com.zhaoyao.com.dataGR158;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhaoyao.com.http.HttpUtils;

public class GetDataGR {
//	http://www.gr158.cn/
	public static final String HTTP = "http://www.gr158.cn";
	public static void main2(String[] args) {
		String javaHome = System.getProperty("java.home");  

	       String arch = System.getProperty("sun.arch.data.model");  

	       System.out.println(javaHome);  

	       System.out.println(arch);

	       try {
			Runtime.getRuntime().exec("cmd.exe   /C   start "+javaHome+"/bin/java.exe -jar "+"C:/Users/dell/Desktop/getHtml.jar");
		} catch (IOException e) {
			e.printStackTrace();
		}
	       
//	       GetDataGR getDataGR = new GetDataGR();
//	       getDataGR.setCmd("cmd");
//	       getDataGR.writeCmd("cmd");
////	       getDataGR.writeCmd(javaHome+"/bin/java.exe -jar "+"C:/Users/dell/Desktop/getHtml.jar");
//	       String readCmd = getDataGR.readCmd();
//	       System.out.println(readCmd);
	}
	
	
	 private java.lang.Process p; 
	 private InputStream is;
	 private OutputStream os;
	 private BufferedWriter bw;
	 private BufferedReader br;
	 private ProcessBuilder pb;
	 private InputStream stdErr;
	 
	//获取Process的输入，输出流
	 public void setCmd(String cmd) {
	  try {
	   p = Runtime.getRuntime().exec(cmd);
	   os = p.getOutputStream();
	   is = p.getInputStream();
	   stdErr = p.getErrorStream();
	  } catch (IOException e) {
	   System.err.println(e.getMessage());
	  }
	 }
	 //向Process输出命令
	 public void writeCmd(String cmd) {
	  try {
	   bw = new BufferedWriter(new OutputStreamWriter(os));
	   bw.write(cmd);
	   bw.newLine();
	   bw.flush();
	   bw.close();
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	 }
	 //读出Process执行的结果
	 public String readCmd() {
	  StringBuffer sb = new StringBuffer();
	  br = new BufferedReader(new InputStreamReader(is));
	  String buffer = null;
	  try {
	   while ((buffer = br.readLine()) != null) {
	    sb.append(buffer + "\n");
	   }
	   System.out.println(p.waitFor());
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	  return sb.toString();
	 }
	 //将命令一股脑塞入list中
	 public LinkedList<String> doCmd(LinkedList<String> lists) {
	  LinkedList<String> list = new LinkedList<String>();
	  for (String s : lists) {
	   writeCmd(s);
	   list.add(readCmd());
	  }
	  return list;
	 }
	
	
	
	
	
	
	
	
	
	
	
//	 http://www.gr158.cn/content/list.shtml?pageIndex=2&perpage=10&channelId=103&categoryId=31&moduleId=69&searchKeywords=&tagName=&year=&month=
		 public static void main(String[] args) {
			String url = "http://www.gr158.cn/content/list.shtml?pageIndex=2&perpage=10&channelId=103&categoryId=31&moduleId=69&searchKeywords=&tagName=&year=&month=";
			byte[] bs = HttpUtils.newInstance().get(url);
			Document document = Jsoup.parse(new String(bs));
			Elements elementsByClass = document.getElementsByClass("describe");
//			Elements elementsByClass = document.getElementsByClass("list-con cl");
			for (int i = 0; i < elementsByClass.size(); i++) {
				Element element = elementsByClass.get(i);
				Elements elements = element.getElementsByClass("a-tit");
				String attr = elements.get(0).attr("href");
				try {
					Document document_content = Jsoup.connect(HTTP+attr).get();
					Elements elementsByClass_content_right = document_content.getElementsByClass("content-right");
					Elements removeClass = elementsByClass_content_right.select("div").removeClass("recommend")
					.select("div").removeClass("on-next")
					.select("div").select("p").removeClass("autograph").removeClass("tst-label");
					removeClass.remove(0);
					removeClass.remove(removeClass.size() - 1);
					removeClass.remove(removeClass.size() - 1);
					removeClass.remove(removeClass.size() - 1);
					System.out.println(removeClass.toString().replace("<p style=\"text-indent:2em;\" class=\"\">", "<p>"));
					// <p class="autograph">(责编：)</p> 
//					  <p class="tst-label">本文标签： <a href="/tag/医保" target="_blank">医保</a> </p> 
//					Elements removeAttr = elementsByClass_content_right.select("div").removeClass("comment");
//					Elements removeClass2 = removeAttr.select("div").removeClass("on-next");
//					Elements remove = removeClass2.select("div").select("a").removeClass("");
//					System.out.println(remove);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		 
		 
}
