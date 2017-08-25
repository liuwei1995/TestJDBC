package com.zhaoyao.zhaoyaoba.jdbc.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TestThread {
	private static ExecutorService fixedThreadPool = Executors
			.newFixedThreadPool(1);
	private static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
	public static void main(String[] args) {
		final List<Object> deliveryUserInfoList = new ArrayList<Object>();
		NumberInfoThread t1 = new NumberInfoThread(deliveryUserInfoList);
		DeliveryUserInfoThread t2 = new DeliveryUserInfoThread(deliveryUserInfoList);
		t1.start();
		t2.start();
		System.out.println("-----------------------");
		
		while (true) {
			synchronized (deliveryUserInfoList) {
				while (deliveryUserInfoList.size()>=0) {
					fixedThreadPool.execute(new Runnable() {
						@Override
						public void run() {
							
						}
					});
				}
				
			}
		}
//		scheduledThreadPool.schedule(new Runnable() {
//			@Override
//			public void run() {
//				int m = 3;
//				while (m>0) {
//					synchronized (deliveryUserInfoList) {
//						for (int i = 0; i <500; i++) {
//							deliveryUserInfoList.add("我是 ："+m+"=="+i);
//						}
//						System.out.println("==============唤醒所有的线程");
//						deliveryUserInfoList.notifyAll();
//					}
//					try {
//						Thread.sleep(10*1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//					--m;
//				}
//			}
//		}, 5, TimeUnit.SECONDS);
	}
}
