package zhaoyaohealthy;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import zhaoyaohealthy.Drug.DetailsBean;

import com.alibaba.fastjson.JSON;
import com.zhaoyao.com.http.HttpUtils;
import com.zhaoyao.file.FileUtils;
import com.zhaoyao.gaode.MD5Util;

public class Register {
	
	public static void main(String[] args) {
//		try {
//			long string = 17713601564l+1;
//			register(string+"","123456789");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			long string = 17713601564l+1;
//			login(string+"","123456789");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		testRegister();
//		DecimalFormat df = new DecimalFormat(".00");
//		System.out.println(df.format(0.2));
//		secKill();
//		int nextInt = new Random().nextInt(2)+1;
//		System.out.println(nextInt);
		testLogin();
	}
	
	public static ExecutorService fixedThreadPool_register = Executors.newFixedThreadPool(500); 
	public static ExecutorService fixedThreadPool_login = Executors.newFixedThreadPool(500); 
	
	
	
	public static void  testRegister() {
		for (int i = 1; i < 800; i++) {
			final int m = i;
			fixedThreadPool_register.execute(new Runnable() {
				@Override
				public void run() {
					try {
						long string = 17713601564l+m;
						register(string+"","123456789");
						register(string+"","123456789");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			fixedThreadPool_register.execute(new Runnable() {
				@Override
				public void run() {
					try {
						long string = 17713601564l+m;
						register(string+"","123456789");
						register(string+"","123456789");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			fixedThreadPool_register.execute(new Runnable() {
				@Override
				public void run() {
					try {
						long string = 17713601564l+m;
						register(string+"","123456789");
						register(string+"","123456789");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	
	public static final String IP = "http://192.168.0.102:8080";
	
	public static void register(String phonenum,String password) throws Exception {
		String urlString = IP+"/zybb2b/cli/sellUser/register";
		Map<String, String> map = new HashMap<String, String>();
		map.put("phonenum", phonenum);
		map.put("password", password);
		map.put("type", 1+"");
		String createPostData = HttpUtils.createPostData(map);
		String postData2String = HttpUtils.newInstance().postData2String(urlString,createPostData);
		System.out.println(postData2String.equals("")?"空":postData2String);
	}
	
	private static int Random = 5;
	
	public static void testLogin() {
//		for (int i = 1; i < 100; i++) {
//			final int m = i;
//			long string = 17713601564l+m;
//			login(string+"","123456789");
//		}
//		JSONObject jsonObject2 = new JSONObject(map_token);
//		System.out.println(jsonObject2);
//		
		
//		String readFileByLines = FileUtils.readFileByLines(new File("C:/Users/dell/Desktop/新建文件夹 (3)/login - 副本.txt"));
		String readFileByLines = FileUtils.readFileByLines(new File("C:/Users/dell/Desktop/新建文件夹 (3)/login.txt"));
		JSONObject jsonObject = new JSONObject(readFileByLines);
		
		Iterator<String> keys = jsonObject.keys();
		while (keys.hasNext()) {
			String next = keys.next();
			map_token.put(next.toString(), jsonObject.getString(next));
		}
		System.out.println(jsonObject);
		int m = 0;
		for (String key : map_token.keySet()) {
//			if (m != 24) {
//				++m;
//				continue;
//			}
			final String token = map_token.get(key);
//			int nextInt = new Random().nextInt(Random)+1;
//			secKill(token,nextInt);//e15017438825634778
//			if (true) {
//				break;
//			}
//				
			fixedThreadPool_login.execute(new Runnable() {
				@Override
				public void run() {
					int nextInt = new Random().nextInt(Random)+1;
					secKill(token,nextInt);//e15017438825634778
					secKill(token,nextInt);//e15017438825634778
				}
			});
			fixedThreadPool_login.execute(new Runnable() {
				@Override
				public void run() {
					int nextInt = new Random().nextInt(Random)+1;
					System.out.println("==================="+nextInt);
					secKill(token,nextInt);//e15017438825634778
					secKill(token,nextInt);//e15017438825634778
				}
			});
		}
		
//		for (int i = 3; i < 800; i++) {
//			final int m = i;
//			fixedThreadPool_login.execute(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						long string = 17713601564l+m;
//						login(string+"","123456789");
//						login(string+"","123456789");
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//			fixedThreadPool_login.execute(new Runnable() {
//				@Override
//				public void run() {
//					try {
//						long string = 17713601564l+m;
//						login(string+"","123456789");
//						login(string+"","123456789");
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			});
//		}
	}
//	private static List<String> list_token = new ArrayList<String>();
	
	private static Map<String, String> map_token = new HashMap<String, String>();
	
	
	public static void login(String phonenum,String password) {
//		http://192.168.0.102:8080/zybb2b/cli/user/login?call=18783032006&pass=dc483e80a7a0bd9ef71d8cf973673924&token=&mintime=1501738814644
		String urlString = IP+"/zybb2b/cli/user/login";
		Map<String, String> map = new HashMap<String, String>();
		map.put("call", phonenum);
		map.put("token", "");
		map.put("pass", password);
		String createPostData = HttpUtils.createPostData(map);
		String postData2String = HttpUtils.newInstance().postData2String(urlString,createPostData);
		JSONObject jsonObject = new JSONObject(postData2String);
		String token = jsonObject.getString("message");
		map_token.put(phonenum, token);
//		token="+token+"&selluserId="+selluserid+"&drugIds="+drugid+"&nums="+num+"&mintime="+new Date().getTime(),
	}
	
	
//	public static int selluserid = 3;
//	public static int drugid = 42;
//	
//	
//	public static double price = 25.17;
////	TODO   6
	public static int num = 6;
//	
//	public static String actid = "94";
//	public static String acttype = "3";
//	
//	public static int actlimt = 200;
//	
//	
//	public static double actmes  = 24.0;
//	
	
	public static String subtotal = "";
	
	public static void secKill(String token,int num) {
		System.out.println(num);
//		token="+token+"&selluserId="+selluserid+"&drugIds="+drugid+"&nums="+num+"&mintime="+new Date().getTime(),
		String urlString = IP+"/zybb2b/cli/order/genernatePass";
//		String data = "token="+token+"&selluserId="+selluserid+"&drugIds="+drugid+"&nums="+num;
		
		
//		String order = FileUtils.readFileByLines("C:/Users/dell/Desktop/新建文件夹 (3)/drug.txt");
		String order = FileUtils.readFileByLines("C:/Users/dell/Desktop/新建文件夹 (3)/drug - 副本.txt");
		
		JSONObject jsonObject = new JSONObject(order);
		Drug parseEntity = 
				JSON.parseObject(jsonObject.toString(), Drug.class);
//		UserJSON.parseEntity(Drug.class, jsonObject, true);
		DetailsBean detailsBean = parseEntity.getDetails().get(0);
		
		HashMap<String, String> map_genernatePass = new HashMap<String, String>();
		map_genernatePass.put("token", token);
		map_genernatePass.put("selluserId", parseEntity.getSelluserid()+"");
		map_genernatePass.put("drugIds", detailsBean.getDrugid()+"");
		map_genernatePass.put("nums", num+"");
		String createPostData_genernatePass = HttpUtils.createPostData(map_genernatePass);
		byte[] bs = HttpUtils.newInstance().get(urlString,createPostData_genernatePass);
//		String postData2String = HttpUtils.newInstance().postData2String(urlString, createPostData_genernatePass);
		
		StringBuilder sb = new StringBuilder();
		sb.append(parseEntity.getSelluserid());
//		sb.append(selluserid);

		
		sb.append(detailsBean.getDrugid());
//		sb.append(drugid);
		sb.append(String.format("%.2f", detailsBean.getPrice()));
//		sb.append(String.format("%.2f", price));
		sb.append(num);
		sb.append(detailsBean.getActid());
//		sb.append(actid);
		sb.append(detailsBean.getActtype());
//		sb.append(acttype);
		sb.append(detailsBean.getActlimt());
//		sb.append(actlimt);
		sb.append(String.format("%.2f", Double.parseDouble(detailsBean.getActmes())));
//		sb.append(String.format("%.2f", actmes));
		Double totalfee = 0d;
		
		double actmes = Double.parseDouble(detailsBean.getActmes());
		double price = detailsBean.getPrice();
		
		if (num > Integer.parseInt(detailsBean.getActlimt())) {
			detailsBean.getActmes();
			totalfee += Integer.parseInt(detailsBean.getActlimt()) * actmes;
			totalfee += (num - Integer.parseInt(detailsBean.getActlimt())) * price;
		}else {
			totalfee += num * actmes;
		}
		
		sb.append(String.format("%.2f", totalfee));
		sb.append(subtotal = String.format("%.2f", totalfee));
		
		
		String stringMD5 = MD5Util.getStringMD5(sb.toString());
		
		
		
		String urlString_kill = IP+"/zybb2b/cli/order/kill";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("token", token);
		map.put("cliPass", stringMD5);
		
	
		JSONArray jsonArray = jsonObject.getJSONArray("details");
		jsonArray.getJSONObject(0).put("num", num);
		jsonObject.put("details", jsonArray);
		map.put("order", jsonObject.toString());
//		map.put("order", order);
		map.put("actId", detailsBean.getActid());
//		map.put("actId", actid);
		
		String createPostData_kill = HttpUtils.createPostData(map);
		String postData2String_kill = HttpUtils.newInstance().postData2String(urlString_kill, createPostData_kill);
		
		System.out.println(postData2String_kill);
		
	}
//	public static final String order = "{\"couponid\":0,\"selluserid\":3,\"payment\":\"2400.00\",\"paymenttype\":2,\"buyermessage\":\"\",\"buyernick\":\"\",\"endaddressid\":40,\"endaddr\":\"胡佰呈=四川省-成都市-双流县西航港科技企业孵化中心307室、=13281165126=610000\",\"details\":[{\"num\":100,\"imagePath\":\"http://7xloj2.com1.z0.glb.clouddn.com/1482733218549 \",\"drugname\":\"甲硝唑阴道泡腾片\",\"drugid\":42,\"price\":25.17,\"actcontent\":\"秒杀商品,秒杀单价￥24\",\"totalfee\":2400,\"actid\":\"94\",\"acttype\":\"3\",\"actlimt\":\"200\",\"actmes\":\"24.0\",\"actstate\":\"1\",\"standard\":2}],\"hasaftersale\":0}";
	/**
	 * A--------------------------------------------
price.toFixed(2)+num+actid+acttype+actlimt
if(acttype==3||acttype==4){
+String(Number(actmes).toFixed(2));
}else{
+String(actmes);
};
if(actstate==1){
+String(Number(totalfee).toFixed(2));
}else{
+String((Number(price)*Number(num)).toFixed(2));
};
B-----------------------------------------------
drugid
C--------------------------------------------------
selluserid
D--------------------------------------------
subtotal
---------------------------------------------
key=md5(C+B+A+D)

	 */
	
	
	
}
