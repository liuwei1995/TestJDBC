//package test;
//
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.Random;
//
//
//
//
//
//import sun.misc.BASE64Encoder;
//
//
////令牌生产器
//@SuppressWarnings("restriction")
//public class TokenProcessor {
//	private TokenProcessor(){}
//	private static TokenProcessor instance = new TokenProcessor();
//	private static TokenProcessor getInstance(){
//		return instance;
//	}
//	public static void main(String[] args) {
//		String tokeCode = getInstance().generateTokeCode("liuwei");
//		System.out.println("加密后："+tokeCode);
//	}
//	public String generateTokeCode(String data){
//		String value = data+System.currentTimeMillis()+new Random().nextInt()+"";
//		System.out.println("加密前："+value);
//		//获取数据指纹，指纹是唯一的
//		try {
//			MessageDigest md = MessageDigest.getInstance("md5");
//			byte[] b = md.digest(value.getBytes());//产生数据的指纹
//			//Base64编码
//			BASE64Encoder be = new BASE64Encoder();
//			return be.encode(b);//制定一个编码
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
//}