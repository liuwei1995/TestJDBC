package test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.zhaoyao.util.QRCodeUtil;
import com.swetake.util.Qrcode;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
public class Test<T> {
	  public static String DI(Exception e) {
	        return Debug.name(e)+"|"+Debug.line(e)+"|"+Debug.fun(e)+"|";
	    }
	  
	  public static void main(String[] args)
	    {
	   // write your code here
//	        String content="";
//	        Scanner sc=new Scanner(System.in);
//	        System.out.println("input your information,and ends with ok");
//	       while(! content.endsWith("ok")){
//	            content=content+"\n";
//	           content=content.concat(sc.next());
//	       }
//
//	        content=content.substring(1,content.length()-2);
//	       // content=content.replaceAll("ok","");
//
//	        System.out.println("input your filename ends with EnterKey");
//	        String filename=sc.next();
//	        String filepath="D:\\"+filename+".png";
//	        new Test().drawQRCODE("content", "C:/Users/dell/Desktop/drawqrcode.png");
//	        System.out.println("draw QR_code successfullly!");
		  Debug<TestEntity<String>> test = new Debug<TestEntity<String>>();
		  test.name();
//		  Type[] parameterizedTypes = getParameterizedTypes(test);
//		  System.out.println(parameterizedTypes);
//		  Class<?> analysisClazzInfo = analysisClazzInfo(test);

	    }
	  
	  public static void name(Class<?> clazz) {
		
	  }
	  
	  private static Class<?> analysisClazzInfo(Object object){
		  
	        return null;
	    }
	  public static Type[] getParameterizedTypes(Object object) {
		    Type superclassType = object.getClass().getGenericSuperclass();
		    if (!ParameterizedType.class.isAssignableFrom(superclassType.getClass())) {
		        return null;
		    }
		    return ((ParameterizedType)superclassType).getActualTypeArguments();
		}
	    public void drawQRCODE(String content,String filepath){
	        try {
	            Qrcode qrcode=new Qrcode();

	            qrcode.setQrcodeErrorCorrect('M');
	            qrcode.setQrcodeEncodeMode('B');
	            qrcode.setQrcodeVersion(15);
	            int width= 235;
	            int height=235;
	            BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	            Graphics2D g2=image.createGraphics();
	            g2.setBackground(Color.WHITE);
	            g2.clearRect(0,0,235,235);
	            g2.setColor(Color.BLACK);

	            byte[] contentbytes=content.getBytes("utf-8");
	            boolean[][] codeout= qrcode.calQrcode(contentbytes);
	            for (int i = 0; i <codeout.length; i++) {
	                for (int j = 0; j < codeout.length; j++) {

	                    if (codeout[j][i]) g2.fillRect(j*3+2,i*3+2,3,3);
	                }
	            }
	            g2.dispose();
	            image.flush();
	            File imgFile = new File(filepath);
	            ImageIO.write(image, "png", imgFile);
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	    }
//	public static void main(String[] args) {
//		  String text = "你好";   
//	        int width = 100;   
//	        int height = 100;   
//	        String format = "png";   
//	        Hashtable hints= new Hashtable();   
//	        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   
//	         BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height,hints);   
//	         File outputFile = new File("new.png");   
////		for (long i = 1; i < 2000; i++) {
////			if(i%1 == 0 && i%2 == 1 && i%3 == 0 && i%4 == 1 && (i)%5 == 4 && i%6 == 3 && i%7 == 0 && i%8 == 1 && i%9 == 0){
////				System.err.println("这个数就是"+i);
////				break;
////			}
////		}
////		tesss();
////		try {
////			List<String> signaturesFromApk = getSignaturesFromApk(new File("D:/workspace5/zhaoyaoba/bin/zhaoyaoba.apk"));
////			System.out.println(signaturesFromApk);
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
//		
//	}
	public static void tesss() {
		System.out.println(DI(new Exception("kkkkkdd")));
	}
	
//	public static void main(String[] args) {
//		TestEntity entity = new TestEntity();
//		entity.setName("你好");
//		entity.setAge(2);
//		try {
////			name(entity,"getName","setName");
//			name(TestEntity.class, "getName","setName");
//		} catch (NoSuchMethodException e) {
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	public static <T> void name(Class<T> t,String ...name) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, ClassNotFoundException {
		Class<?> forName2 = Class.forName(t.getName());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "map1");
		Object newInstance3 = forName2.newInstance();
		Field[] declaredFields = newInstance3.getClass().getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			declaredFields[i].setAccessible(true);
			String name2 = declaredFields[i].getName();
			Class<?> type = declaredFields[i].getType();
			String str  = name2.substring(0,1).toUpperCase();
			str += name2.substring(1, name2.length());
			Method method = newInstance3.getClass().getMethod("set"+(str), type);
			method.invoke(newInstance3, 1);
			declaredFields[i].setAccessible(false);
		}
		System.out.println(declaredFields);
		Method setName = newInstance3.getClass().getMethod(name[1], String.class);
		Object invoke3 = setName.invoke(newInstance3, "ninini");
		System.out.println(invoke3);
		Method method = newInstance3.getClass().getMethod(name[0]);
		Object invoke = method.invoke(newInstance3);
		System.out.println(invoke);
		Method method2 = newInstance3.getClass().getMethod(name[1],String.class);
		Object invoke2 = method2.invoke(newInstance3, "不好");
		System.out.println(invoke2);
		Object invo = method.invoke(newInstance3);
		System.out.println(invo);
		
	}
	/** 
	   * 从APK中读取签名 
	   * @param file 
	   * @return 
	   * @throws IOException 
	   */
	  public static List<String> getSignaturesFromApk(File file) throws IOException { 
	    List<String> signatures=new ArrayList<String>(); 
	    JarFile jarFile = new JarFile(file); 
	    try { 
	      JarEntry je = jarFile.getJarEntry("AndroidManifest.xml"); 
	      byte[] readBuffer=new byte[8192]; 
	      Certificate[] certs=loadCertificates(jarFile, je, readBuffer); 
	      if(certs != null) { 
	        for(Certificate c: certs) { 
	          String sig=toCharsString(c.getEncoded()); 
	          signatures.add(sig); 
	        } 
	      } 
	    } catch(Exception ex) { 
	    } 
	    return signatures; 
	  } 
	 
	 /** 
	   * 加载签名 
	   * @param jarFile 
	   * @param je 
	   * @param readBuffer 
	   * @return 
	   */
	  private static Certificate[] loadCertificates(JarFile jarFile, JarEntry je, byte[] readBuffer) { 
	    try { 
	      InputStream is=jarFile.getInputStream(je); 
	      while(is.read(readBuffer, 0, readBuffer.length) != -1) { 
	      } 
	      is.close(); 
	      return je != null ? je.getCertificates() : null; 
	    } catch(IOException e) { 
	    } 
	    return null; 
	  } 
	  
	/** 
	   * 将签名转成转成可见字符串 
	   * @param sigBytes 
	   * @return 
	   */
	  private static String toCharsString(byte[] sigBytes) { 
	    byte[] sig=sigBytes; 
	    final int N=sig.length; 
	    final int N2=N * 2; 
	    char[] text=new char[N2]; 
	    for(int j=0; j < N; j++) { 
	      byte v=sig[j]; 
	      int d=(v >> 4) & 0xf; 
	      text[j * 2]=(char)(d >= 10 ? ('a' + d - 10) : ('0' + d)); 
	      d=v & 0xf; 
	      text[j * 2 + 1]=(char)(d >= 10 ? ('a' + d - 10) : ('0' + d)); 
	    } 
	    return new String(text); 
	  } 
}
