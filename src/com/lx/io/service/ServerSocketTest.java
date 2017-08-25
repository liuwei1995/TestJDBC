package com.lx.io.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

import com.zhaoyao.com.Delivery;
import com.zhaoyao.com.ExecutorServiceThread;
import com.zhaoyao.com.Number;

public class ServerSocketTest {
	private ServerSocket ss;
	private Socket socket;

	public ServerSocketTest() {
		try {
			ss = new ServerSocket(719);// 建立服务器，监听...
			while (true) {
				socket = ss.accept();
				// 获取客户端IP地址
				String remoteIP = socket.getInetAddress().getHostAddress();
				// 获取客户端连接端口
				String remotePort = ":" + socket.getLocalPort();
				// 读取客户端输入
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				String line = in.readLine();
				line = new String(line.getBytes("iso8859-1"),"UTF-8");
				if(line == null){
					
				}else {
					if(line.indexOf("?") != -1) {
						int indexOf = line.indexOf("?");
						line = line.substring(indexOf+1, line.length());
					}
					if(line.startsWith("GET", 0))
						line = line.replace("GET", "").trim();
					if(line.startsWith("/favicon.ico", 0))
						line = line.replace("/favicon.ico", "").trim();
					if(line.startsWith("/favicon.ico", 0))
						line = line.replace("/favicon.ico", "").trim();
					if(line.startsWith("/ HTTP/1.1", 0) || line.endsWith("HTTP/1.1"))
						line = line.replace("HTTP/1.1", "").replace("/", "").trim();
					if(line.startsWith("/favicon.ico", 0))
						line = line.replace("/favicon.ico", "").trim();
						if (line.contains("&")) {
							String[] split = line.split("&");
							if(split.length == 2){
							String string = split[1];
							if(string.trim().equals("delivery")){
								Delivery delivery = new Delivery();
								delivery.setState(1);
								delivery.setDeliveryId(new Random().nextInt(100+1));
								delivery.setDeliveryName("liwei");
								ExecutorServiceThread.getInstance().addDeliveryListMap(split[0], delivery);
//								ExecutorServiceThread.getInstance().addDeliveryList(delivery);
							}else if(string.trim().equals("number")){
								final Number number = new Number();
								number.setNumberCode(new Date().getTime()+""+new Random().nextInt(10000+1));
//								ExecutorServiceThread.getInstance().addNumber(number);
								ExecutorServiceThread.getInstance().addNumberMap(split[0], number);
							}
							}
						}
				}
//				synchronized (ExecutorServiceThread.getInstance().deliveryList) {
////					ExecutorServiceThread.getInstance().deliveryList.notifyAll();
//				}
				// 将服务器端信息发往客户端
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				
				out.println(line);
				out.close();
				in.close();
			}
		} catch (IOException ex) {
			System.out.println(ex.getCause());
		}
	}

	public static void main(String[] args) {
		ExecutorServiceThread.getInstance().start();
		new ServerSocketTest();
	}
}