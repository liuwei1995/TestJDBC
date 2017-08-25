package com.zhaoyao.zhaoyaoba.jdbc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPoolExecutorTest {
	private static List<Object> list = new ArrayList<Object>();
	static  ExecutorService cachedThreadPool = Executors.newCachedThreadPool();  
	static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(20);  
	static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);  

    private static void doSomething(int id) {  
        System.out.println("start do " + id + " task …");  
        try {  
            Thread.sleep(1000 * 2);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        System.out.println("start do " + id + " finished.");  
    }  
      
    public static void main(String[] args) {  
        ExecutorService executorService = Executors.newFixedThreadPool(2);  
            //创建了一个线程池，里面有2个线程  
  
        //通过submit()方法，提交一个Runnable的实例，这个实例将交由线程池中空闲的线程执行。  
        executorService.submit(new Runnable() {  
            public void run() {  
                doSomething(1);  
            }  
        });  
  
        executorService.execute(new Runnable() {  
            public void run() {  
                doSomething(2);  
            }  
        });  
  
        executorService.shutdown();  
         
        System.out.println(">>main thread end.");   
    }  
	  static class Name{
		  private long age;

		public long getAge() {
			return age;
		}

		public void setAge(long age) {
			this.age = age;
		}

		public Name(long age) {
			super();
			this.age = age;
		}
		  
	  }
	// 锁  
	    private final Lock lock = new ReentrantLock();  
	    
	 // 仓库满的条件变量  
	    private final Condition full = lock.newCondition();  
	  
	    // 仓库空的条件变量  
	    private final Condition empty = lock.newCondition();  
	  public static Object test() {
		  for (int i = 0; i < 2000; i++) {
			  final int m = i;
			  fixedThreadPool.execute(new Runnable() {
				@Override
				public void run() {
					synchronized (this) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(m+"\n");
						list.add(m);
					}
				}
			});
		  }
		  return "dddddddddddddddddddd";
	}
	  
	  public void ScheduledExecutorService(){
		  
	  }
	  /**
	   * 创建一个定长线程池，支持定时及周期性任务执行。延迟执行示例代码如下：
	   */
	  public void ExecutorService(final int m){
		  scheduledThreadPool.schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println(m);
			}
		}, 3, TimeUnit.SECONDS);
	  }
	  
}
