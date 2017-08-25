package com.zhaoyao.zhaoyaoba.jdbc.util;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Enumeration;

public class EncodeUtil {
	public static void test() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		String pass="liuwei!@#$%^&*()qwertyuiop";
		String name="C:/Users/dell/Desktop/linxins.keystore";
		FileInputStream in=new FileInputStream(name);
		KeyStore ks=KeyStore.getInstance("JKS");           
		ks.load(in,pass.toCharArray());
		Enumeration e=ks.aliases( );
		while(e.hasMoreElements()) {
		System.out.println(e.nextElement());
		}
	}
	public static void main(String[] args) {
		try {
			test();
		} catch (KeyStoreException e1) {
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (CertificateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if(true)return;
			BufferedReader br = new BufferedReader(new FileReader(
					new File("C:/Users/dell/Documents/Tencent Files/1281659061/FileRecv/MobileFile/AndroidManifest.xml")
					));
			String s=br.readLine();
			br.close();
			System.out.println(s);
//			convertWindows_1252FileToUTFFile(
//					"C:/Users/dell/Documents/Tencent Files/1281659061/FileRecv/MobileFile/AndroidManifest.xml",
//					"C:/Users/dell/Documents/Tencent Files/1281659061/FileRecv/MobileFile/AndroidManifest1.xml");
//			outputCharset();
			name("C:/Users/dell/Documents/Tencent Files/1281659061/FileRecv/MobileFile/AndroidManifest.xml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void name(String filePath) throws IOException {
		String encode = "UTF-8";// 默认编码
		File file = new File(filePath);
		//cpdetector_1.0.10.jar
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
		detector.add(new ParsingDetector(false));
		// 用到antlr.jar、chardet.jar
		detector.add(JChardetFacade.getInstance());
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		
		Charset set = null;
		try {
			set = detector.detectCodepage(file.toURI().toURL());// 检测文件编码
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (set != null) {
			encode = set.name();
		}
		InputStream inputStream = new FileInputStream(file);
		InputStreamReader inputReader = new InputStreamReader(inputStream,
				encode);
		BufferedReader bufferedReader = new BufferedReader(inputReader);
		String temp = null;
		while ((temp = bufferedReader.readLine())!=null) {
//			temp = temp.decode("windows-1252");
			byte[] bytes = temp.getBytes(encode);
			String string = new String(bytes, "UTF-8");
			System.out.println(string+"\n");
		}
	}
	private static void outputCharset(){
		        Constructor[] ctors = Console.class.getDeclaredConstructors();
		        Constructor ctor = null;
		         for (int i = 0; i < ctors.length; i++) {
		             ctor = ctors[i];
		             if (ctor.getGenericParameterTypes().length == 0)
		               break;
		       }
	         try {
		             ctor.setAccessible(true);
		             Console c = (Console) ctor.newInstance();
		             Field f = c.getClass().getDeclaredField("cs");
		            f.setAccessible(true);
		             System.out.println(String.format("Console charset:%s",
		                     f.get(c)));
		             System.out.println(String.format("Charset.defaultCharset():%s",
		                     Charset.defaultCharset()));
		         } catch (Exception x) {
		             x.printStackTrace();
		         }
		     }
	
	public static void convertWindows_1252FileToUTFFile(String srcFileName, String destFileName) throws IOException {//把GBK文件转换为UTF文件
	    BufferedReader br = null;
	    BufferedWriter bw = null;
	    try {
	        br = new BufferedReader(new InputStreamReader(new FileInputStream(
	                srcFileName), "windows-1252"));
	        bw = new BufferedWriter(new OutputStreamWriter(
	                new FileOutputStream(destFileName), "UTF-8"));
	        String line = null;
	        while ((line = br.readLine()) != null) {
	            bw.write(line);
	            bw.newLine();
	        }
	    } catch (Exception e) {
	    } finally {
	        try {
	            if (br != null)
	                br.close();
	            if (bw != null)
	                bw.close();
	        } catch (IOException e) {
	        }
	    }
	    name("C:/Users/dell/Documents/Tencent Files/1281659061/FileRecv/MobileFile/AndroidManifest1.xml");
	}
}
