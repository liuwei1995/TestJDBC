package com.zhaoyao.zhaoyaoba.jdbc.thread;

import java.util.List;

public class DeliveryUserInfoThread extends Thread{
	
	private List<Object> deliveryUserInfoList;

	public DeliveryUserInfoThread(List<Object> deliveryUserInfoList) {
		this.deliveryUserInfoList = deliveryUserInfoList;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (deliveryUserInfoList) {
				while (deliveryUserInfoList.isEmpty()) {
					try {
						System.out.println(this.getName()+":没有数据我要等待");
						deliveryUserInfoList.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Object remove = deliveryUserInfoList.remove(0);
				System.out.println(this.getName()+":我消费了这个：==="+remove);
			}
		}
	}
}
