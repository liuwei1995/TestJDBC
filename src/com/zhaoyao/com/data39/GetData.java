package com.zhaoyao.com.data39;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import android.net.http.SslCertificate;

import com.zhaoyao.com.http.HttpUtils;
import com.zhaoyao.file.SaveFile;

public class GetData {
//	public static String path = "C:/Users/dell/Desktop/39";
	public static String path = "C:/Users/dell/Desktop/39_新";
	public static ExecutorService fixedThreadPool1 = Executors.newFixedThreadPool(1);  
	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);  
	public static void main(String[] args) {
		
		final HttpUtils httpUtils = HttpUtils.newInstance();
//		testQuan(httpUtils);
		 testBuFen(httpUtils);
//		 danGe(httpUtils);
		for (int i = 0; i < type.length; i++) {
			if(type[i].equals("男性不育")){
				System.out.println(i);
			}
		}
	}
	private static void danGe(final HttpUtils httpUtils) {
		String name = type[6];
		String s = "https://interface.39.net/openapi/app/GetDrugs?curPage=1&app_key=app&word="+name+"&clientType=jyzs&sign=9DFAAD5404FCB6168EA6840DCDFF39E5&limit=20";
//		String s = "https://interface.39.net/openapi/app/GetDrugs?curPage=1&app_key=app&word=%E7%99%BD%E7%BB%86%E8%83%9E%E5%87%8F%E5%B0%91&clientType=jyzs&sign=9DFAAD5404FCB6168EA6840DCDFF39E5&limit=20";
		 final String[] split = s.split("curPage=1");
		 final int curPage = 40;
			byte[] bs = httpUtils.get(split[0]+"curPage="+curPage+split[1]);
			if (bs != null) {
				String string = new String(bs);
				System.out.println(string);
//				String name = "白细胞减少";
				SaveFile.save(path+"/药品_"+name, "药品_"+name+"_"+curPage+".json", string);
			}
	}
	private static int ll = 0;
	private static void testBuFen(final HttpUtils httpUtils) {//艾滋病//白癜风
		final String name = "阿米巴病";///////////////
//		final String name = type[6];////////////////
//		////////////     =============//
		String s = "https://interface.39.net/openapi/app/GetDrugs?curPage=1&app_key=app&word="+name+"&clientType=jyzs&sign=9DFAAD5404FCB6168EA6840DCDFF39E5&limit=100";
//		String s = "https://interface.39.net/openapi/app/GetDrugs?curPage=1&app_key=app&word=%E7%99%BD%E7%BB%86%E8%83%9E%E5%87%8F%E5%B0%91&clientType=jyzs&sign=9DFAAD5404FCB6168EA6840DCDFF39E5&limit=20";
		 final String[] split = s.split("curPage=1");
		 int start = 1;
		 int m = 2000;
		 for (int i = start; i < m; i++) {
			final int curPage = i;
			fixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
//					String s = "https://interface.39.net/openapi/app/GetDrugs?curPage=1&app_key=app&word="+name+"&clientType=jyzs&sign=9DFAAD5404FCB6168EA6840DCDFF39E5&limit=20";
					byte[] bs = httpUtils.get(split[0]+"curPage="+curPage+split[1]);
					if (bs != null) {
						String string = new String(bs);
						JSONObject jsonObject = new JSONObject(string);
						JSONArray jsonArray = jsonObject.getJSONObject("results").getJSONArray("List");
						if(jsonArray == null || jsonArray.length() <= 0){
							if(ll != 0){
								System.err.println("=====================================");
								fixedThreadPool.shutdownNow();
							}else {
								++ll;
								System.out.println(string);
								SaveFile.save(path+"/药品_"+name, "药品_"+name+"_"+curPage+".json", string);
							}
						}else {
							System.out.println(string);
								SaveFile.save(path+"/药品_"+name, "药品_"+name+"_"+curPage+".json", string);
						}
					}
				}
			});
			if(i == m -1){
				System.err.println("-----------------------------------------------------shutdown======"+i);
				fixedThreadPool.shutdown();
			}
		}
	}
	private static void testQuan(final HttpUtils httpUtils) {
		for (int j = 66; j < type.length; j++) {
			final int j_ = j;
			fixedThreadPool1.execute(new Runnable() {
				@Override
				public void run() {
				String s = "https://interface.39.net/openapi/app/GetDrugs?curPage=1&app_key=app&word="+type[j_]+"&clientType=jyzs&sign=9DFAAD5404FCB6168EA6840DCDFF39E5&limit=100";
				 final String[] split = s.split("curPage=1");
				 int start = 1;
				 int m = 2000;
				 for (int i = start; i < m; i++) {
					final int curPage = i;
					 synchronized (fixedThreadPool1) {
						 byte[] bs = httpUtils.get(split[0]+"curPage="+curPage+split[1]);
						 if (bs != null) {
							 String string = new String(bs);
							 System.out.println(string);
							 String name = type[j_];
							 JSONObject jsonObject = new JSONObject(string);
							 JSONArray jsonArray = jsonObject.getJSONObject("results").getJSONArray("List");
							 if(jsonArray == null || jsonArray.length() <= 0){
								 if(ll != 0){
									 System.err.println("=====================================================================================");
									 try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									 ll = 0;
									 break;
								 }else {
									 ++ll;
									 System.out.println(string);
									 SaveFile.save(path+"/药品_"+name, "药品_"+name+"_"+curPage+".json", string);
								 }
							 }else {
								 System.out.println(string);
								 SaveFile.save(path+"/药品_"+name, "药品_"+name+"_"+curPage+".json", string);
							 }
						 }
					 }
					}
				}
			});
			 if(j == type.length -1){
				 System.err.println("-----------------------------------------------------shutdown");
				 fixedThreadPool1.shutdown();
				 fixedThreadPool.shutdown();
			 }
		}
	}

	private static void testQuanChild(final HttpUtils httpUtils,
			final int j_) {
		String s = "https://interface.39.net/openapi/app/GetDrugs?curPage=1&app_key=app&word="+type[j_]+"&clientType=jyzs&sign=9DFAAD5404FCB6168EA6840DCDFF39E5&limit=100";
		 final String[] split = s.split("curPage=1");
		 int start = 1;
		 int m = 2000;
		 for (int i = start; i < m; i++) {
			final int curPage = i;
			fixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					byte[] bs = httpUtils.get(split[0]+"curPage="+curPage+split[1]);
					if (bs != null) {
						String string = new String(bs);
						System.out.println(string);
						String name = type[j_];
						
						JSONObject jsonObject = new JSONObject(string);
						JSONArray jsonArray = jsonObject.getJSONObject("results").getJSONArray("List");
						if(jsonArray == null || jsonArray.length() <= 0){
							if(ll != 0){
								System.err.println("=====================================");
								ll = 0;
							}else {
								++ll;
								System.out.println(string);
								SaveFile.save(path+"/药品_"+name, "药品_"+name+"_"+curPage+".json", string);
							}
						}else {
							System.out.println(string);
								SaveFile.save(path+"/药品_"+name, "药品_"+name+"_"+curPage+".json", string);
						}
					}
				}
			});
			}
	}
	public static class Test{
		public static String path = "C:/Users/dell/Desktop/39_新";
		public ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);  
		private int ll = 0;
		private  HttpUtils httpUtils;
		private int j_;
		public Test(HttpUtils httpUtils,int j_) {
			this.httpUtils =httpUtils;
			this.j_ = j_;
		}
		public void start() {
			testQuanChild(httpUtils, j_);
		}
		private void testQuanChild(final HttpUtils httpUtils,final int j_) {
			String s = "https://interface.39.net/openapi/app/GetDrugs?curPage=1&app_key=app&word="+type[j_]+"&clientType=jyzs&sign=9DFAAD5404FCB6168EA6840DCDFF39E5&limit=20";
			 final String[] split = s.split("curPage=1");
			 int start = 1;
			 int m = 2000;
			 for (int i = start; i < m; i++) {
				final int curPage = i;
				fixedThreadPool.execute(new Runnable() {
					@Override
					public void run() {
						byte[] bs = httpUtils.get(split[0]+"curPage="+curPage+split[1]);
						if (bs != null) {
							String string = new String(bs);
							System.out.println(string);
							String name = type[j_];
							
							JSONObject jsonObject = new JSONObject(string);
							JSONArray jsonArray = jsonObject.getJSONObject("results").getJSONArray("List");
							if(jsonArray == null || jsonArray.length() <= 0){
								if(ll != 0){
									System.err.println("=====================================shutdown----type[j_]==="+j_);
									ll = 0;
									fixedThreadPool.shutdown();
								}else {
									++ll;
									System.out.println(string);
									SaveFile.save(path+"/药品_"+name, "药品_"+name+"_"+curPage+".json", string);
								}
							}else {
								System.out.println(string);
									SaveFile.save(path+"/药品_"+name, "药品_"+name+"_"+curPage+".json", string);
							}
						}
					}
				});
				}
		}
	}
	static String[] type = new String[]{
		"阿米巴病","艾滋病","疤痕","白带异常","白癜风","白细胞减少",
		"白血病","鼻咽癌","扁桃体炎","丙肝","病毒性感冒",
		"产后疾病","痴呆","带状疱疹","胆结石","胆囊炎",
		"低血钾","低血糖","滴虫病","癫痫","多动症","多发性神经炎",
		"耳鸣耳聋","肥胖症","肺癌","风湿疼痛","肝癌",
		"肝纤维化","肝性脑病","肝硬化","高血压","高血脂","更年期综合症","宫颈癌","股骨头坏死",
		"骨癌","骨关节炎","骨质增生","过敏","红斑狼疮","滑精遗精","灰指甲","蛔虫病",
		"肌肉关节痛","脊柱侧弯","甲状腺功能减退","甲状腺功能亢进","甲状腺功能亢进",
		"甲状腺功能亢进","甲状腺瘤","尖锐湿疣","肩周炎","疖肿疥疮","结肠癌","结核病",
		"精神分裂症","颈椎病","酒精肝","酒渣鼻","口舌生疮","溃疡类","类风湿","淋巴癌",
		"淋病","螨虫感染","慢性肾小球炎","男性不育","蛲虫病","脑癌","脑血管病(中风)","内分泌失调","尿崩症",
		"尿道结石","尿毒症","尿路感染","扭伤挫伤冻伤","疟疾","女性不孕"
	};
	static String[] type2 = new String[]{
		"螨虫感染","慢性肾小球炎","男性不育","蛲虫病","脑癌","脑血管病(中风)","内分泌失调","尿崩症",
		"尿道结石","尿毒症","尿路感染","扭伤挫伤冻伤","疟疾","女性不孕"
	};

}
