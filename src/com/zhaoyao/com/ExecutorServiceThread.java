package com.zhaoyao.com;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceThread {
	
	
	public final java.util.List<DrugStroeInfo> drugStroeInfoList = new ArrayList<DrugStroeInfo>();
	
	public final java.util.List<Delivery> deliveryList = new ArrayList<Delivery>();
	
	public final java.util.List<Number> numberList = new ArrayList<Number>();
	
	public final Map<Integer, java.util.List<DrugStroeInfo>> drugStroeInfoMap = new HashMap<Integer, java.util.List<DrugStroeInfo>>();
	
	public final Map<String, java.util.List<Delivery>> deliveryMap = new HashMap<String, java.util.List<Delivery>>();
	
	public final Map<String, java.util.List<Number>> numberMap = new HashMap<String, java.util.List<Number>>();
	
	public static ExecutorServiceThread executorServiceThread = new ExecutorServiceThread();
	
	private ExecutorServiceThread() {
	}
	public static ExecutorServiceThread getInstance() {
		return executorServiceThread;
	}
	/**
	 * 一直创建线程  
	 * <li>创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程
	 */
	public static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	/**
	 * 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
	 */
	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);  
	
	/***
	 * 创建一个定长线程池，支持定时及周期性任务执行。延迟执行
	 */
	public static  ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);    
	
	public void addNumber(final Number number) {
		synchronized (deliveryList) {
			Statement statement = JDBC.getInstance().getStatement();
			try {
				boolean execute = statement.execute("INSERT INTO number (numberCode,drugStroeId,createTime) VALUES ('"+new Random().nextInt(10000+1)+"','"+1+"',NOW())");
				System.out.println(execute);
				numberList.add(number);
				System.out.println("订单来了 --------"+number.getNumberCode()+"----------------释放锁");
				deliveryList.notifyAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
//		scheduledThreadPool.execute(new Runnable() {
//			@Override
//			public void run() {
//				synchronized (deliveryList) {
//					Statement statement = JDBC.getInstance().getStatement();
//					try {
//						boolean execute = statement.execute("INSERT INTO number (numberCode,drugStroeId,createTime) VALUES ('"+new Random().nextInt(10000+1)+"','"+1+"',NOW())");
//						System.out.println(execute);
//						numberList.add(number);
//						System.out.println("订单来了 ------------------------释放锁");
//						deliveryList.notifyAll();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//					
//				}
//			}
//		});
	}
	
	public void addDeliveryList(final Delivery delivery) {
//		scheduledThreadPool.equals(new Runnable() {
//			@Override
//			public void run() {
//				synchronized (deliveryList) {
//					deliveryList.add(delivery);
//					System.out.println("配送人员来了   释放锁");
//					deliveryList.notifyAll();
//				}
//			}
//		});
		synchronized (deliveryList) {
			deliveryList.add(delivery);
			System.out.println("配送人员   "+delivery.getDeliveryId()+"   来了   释放锁");
			deliveryList.notifyAll();
		}
	}
	public void addDeliveryListMap(final String key,final Delivery delivery) {
		synchronized (deliveryMap.get(key)) {
			System.out.println("配送人员   "+delivery.getDeliveryId()+"   来了   释放锁");
			deliveryMap.get(key).add(delivery);
			deliveryMap.get(key).notifyAll();
		}
	}
	
	public void addNumberMap(final String key,final Number number) {
		synchronized (deliveryMap.get(key)) {
			Statement statement = JDBC.getInstance().getStatement();
			try {
				boolean execute = statement.execute("INSERT INTO number (numberCode,drugStroeId,createTime) VALUES ('"+new Random().nextInt(10000+1)+"','"+1+"',NOW())");
				System.out.println(execute);
				numberMap.get(key).add(number);
				System.out.println(key+" ==  的 =="+"订单来了 --------"+number.getNumberCode()+"----------------释放锁");
				deliveryMap.get(key).notifyAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	public void start() {
//		NumberThread numberThread1 = new NumberThread("1",numberList,deliveryList,drugStroeInfoList);
//		numberThread1.start();
//		NumberThread numberThread2 = new NumberThread("2",numberList,deliveryList,drugStroeInfoList);
//		numberThread2.start();
		deliveryMap.put("cd", new ArrayList<Delivery>());
		deliveryMap.put("sh", new ArrayList<Delivery>());
		numberMap.put("cd", new ArrayList<Number>());
		numberMap.put("sh", new ArrayList<Number>());
		NumberThreadMap numberThreadMap1 = new NumberThreadMap("1", "cd", drugStroeInfoMap, deliveryMap, numberMap);
		NumberThreadMap numberThreadMap2 = new NumberThreadMap("2", "sh", drugStroeInfoMap, deliveryMap, numberMap);
		numberThreadMap1.start();
		numberThreadMap2.start();
	}
	 
	 /**
		 * 表示延迟1秒后每3秒执行一次。
		 * @param args
		 */
	public static void onE_SECONDS_Back_IntervalSeconds_ExecuteOnce() {
		  ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);  
		  scheduledThreadPool.scheduleAtFixedRate(new Runnable() {  
		   public void run() {  
		    System.out.println("delay 1 seconds, and excute every 3 seconds");  
		   }  
		  }, 1, 3, TimeUnit.SECONDS); 
	}
}
