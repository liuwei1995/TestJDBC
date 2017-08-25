package com.zhaoyao.com.http;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestHttp {
	public static void main(String[] args) {
		HttpUtils httpUtils = HttpUtils.newInstance();
		byte[] bs = httpUtils.get("http://search.anccnet.com/searchResult2.aspx?keyword=替硝唑片");
		try {
			String string =new String(bs, "gb2312");//zhaoyaoHealthy
			System.out.println(string);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		
//		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);  
//		for (long i = 0; i < 20000; i++) {
//			final long index = i;
//			fixedThreadPool.execute(new Runnable() {  
//				@Override  
//				public void run() {  
//					HttpUtils httpUtils = HttpUtils.newInstance();
//					httpUtils.post("https://www.baidu.com/"+index+"&password="+index);
////					httpUtils.POST("http://192.168.1.115:8080/zybb2b/admin/user/login?name="+index+"&password="+index);
//					System.out.println("========"+index);  
//				}  
//			});  
//		}
//		byte[] post = httpUtils.POST("http://192.168.1.115:8080/zybb2b/admin/user/login?name=1&password=1");
////		byte[] post = httpUtils.POST("http://kaoshiftp.book118.com//%E5%B0%8F%E5%AD%A6%E5%88%9D%E4%B8%AD/%E5%B0%8F%E5%AD%A6/%E4%B8%AD%E6%96%87%E5%AD%97%E5%85%B8.txt");
//		if(post != null){
//			File file = new File("C:/Users/dell/Desktop/字典.txt");
//			FileOutputStream fos = null;
//			try {
//				fos = new FileOutputStream(file);
//				fos.write(post, 0, post.length);
//				fos.flush();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}finally{
//				try {
//					if(fos != null)
//					fos.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
	}
}
