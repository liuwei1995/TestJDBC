package com.zhaoyao.gaode;

import java.io.IOException;
import java.net.MalformedURLException;

import com.zhaoyao.gaode.YunTuHttp.Callback;

public class YunApi {
	
	
	
	public static void main(String[] args) {
		YunTuHttp yunTuHttp = new YunTuHttp();
//		yunTuHttp.addParameters("city", "成都");
//		yunTuHttp.addParameters("center", "103.967016,30.535222");
//		yunTuHttp.addParameters("radius", "3000");
//		yunTuHttp.addParameters("sortrule", "_distance:1");//升序_distance:0 降序
//		
//		yunTuHttp.addParameters("limit", "20");
//		yunTuHttp.addParameters("page", "1");//分页索引，当前页数 >=1
//		{center=103.973577,30.535692, pageCount=20, pageIndex=1, city=成都市}
		yunTuHttp.addParameters("center", "103.967016,30.535222");//103.967016,30.535222
		yunTuHttp.addParameters("city", "成都市");
		yunTuHttp.addParameters("sortrule", "_distance:1");//升序_distance:0 降序
			yunTuHttp.addParameters("limit", 20);
			yunTuHttp.addParameters("page", 1);
			yunTuHttp.addParameters("radius", "3000");
		try {
			yunTuHttp.around(new Callback() {
				@Override
				public void result(boolean isSuccess, byte[] b, Exception e) {
					if(isSuccess)
						System.out.println(new String(b));
				}
			});
//			URL url = new URL("http://yuntuapi.amap.com/datasearch/local?"+parameters+"&"+"sig="+sig);
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			
////			map.put("sig", "");//数字签名
//			con.setRequestMethod("GET");
//			
//			con.setDoInput(true); //允许输入流，即允许下载  
//			con.setDoOutput(true); //允许输出流，即允许上传  
//			con.setUseCaches(false); //不使用缓冲  
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
