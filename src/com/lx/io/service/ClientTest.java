package com.lx.io.service;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class ClientTest {

	public ClientTest() {
		Socket socket = null ;
		try {
			System.out.println("Trytoconnectto127.0.0.1:10000");
			// 向服务器发出连接请求
			while (true) {
				 socket = new Socket("127.0.0.1", 719);
				System.out.println("TheServerConnected!");
				System.out.println("输入发送内容给:");
				// 读取用户输入信息
				BufferedReader line = new BufferedReader(new InputStreamReader(
						System.in));
				String readLine = line.readLine();
				if("end".equals(readLine))break;
				System.out.println("发送的内容是："+readLine+"\n");
				// 输出从服务器端获得的信息
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(readLine);
				// 读取服务器端信息
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));
				System.out.println("服务器返回的内容是："+in.readLine()+"");
				out.close();
				in.close();
			}
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}finally{
			try {
				if(socket != null)
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args) {
		new ClientTest();
	}
}