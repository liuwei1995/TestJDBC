package com.zhaoyao.com.OCR.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.zhaoyao.com.OCR.cmd.SyncPipe.interfaceSyncPipe;

public class CMDUtils {
	private PrintWriter stdin = null;
	private  Process p = null;
	private String path_;
	private String[] command =  
		{  
			"cmd",  
		}; 
	public CMDUtils(File filePath) {
		this();
		if(filePath != null)
		path_ = filePath.getPath();
	}
	public CMDUtils(String path) {
		this();
		path_ = path;
	}
	private java.util.List<String> list = new ArrayList<String>();
	private StringBuffer path = new StringBuffer();
	public CMDUtils() {
		 try {
			p = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		 interfaceSyncPipe pipe = new SyncPipe.interfaceSyncPipe() {
			 
			@Override
			public void ok(boolean isOk) {
				
			}
		};
		 new Thread(new SyncPipe(p.getErrorStream(), pipe,-1,"GBK")).start();  
         new Thread(new SyncPipe(p.getInputStream(), pipe,1,"GBK")).start();  
		 stdin = new PrintWriter(p.getOutputStream()); 
		 if(path_ != null && !"".equals(path_.trim())){
			 stdin.println("cd "+path_);   
			 path.append(path_);
		     stdin.flush();
		 }else {
//			 stdin.println("cd c:"); 
//			 path.append("c:");
//		     stdin.flush();
		}
	}
	private boolean flag = true;
	/**以下可以输入自己想输入的cmd命令*/  
	public boolean printWriter(String cmd) {
		/**以下可以输入自己想输入的cmd命令*/  
		if(cmd == null || "".equals(cmd)){
			System.out.println("cmd == null");
			return false;
		}
        stdin.println(cmd);                   
        stdin.flush();
        return true;
	}
	public  void cmd() {
		 while (flag) {
				InputStream is = System.in;
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				StringBuffer sb = new StringBuffer();
				String data = "";
				try {
					if((data = br.readLine()) != null){
						sb.append(data);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				String replace = sb.toString().replace("\\", "/");
				sb.delete(0, sb.length()).append(replace);
				if ("EXIT".equals(sb.toString().toUpperCase())) {
					flag = false;
					close();
					System.out.print(sb.toString());
				}
				else{
					if(sb.toString().startsWith("/"))sb.deleteCharAt(0);
					if(sb.charAt(1) == ':'){
						stdin.println("cd "+sb.charAt(0));                   
						stdin.flush();
						path.append(""+sb.charAt(0)+"://");
					}else if (sb.charAt(1) == '.') {
						
					}else {
						path.append("/"+sb.toString());
						stdin.println(path.toString());                   
						stdin.flush();
					}
				}
			}
	}
	/**
	 * 回到根目录
	 */
	public void Root() {
		if(stdin == null)return;
		stdin.println("cd\\");                   
        stdin.flush();
	}
	public void close() {
		if(stdin != null)
			stdin.close(); 
	}
}
