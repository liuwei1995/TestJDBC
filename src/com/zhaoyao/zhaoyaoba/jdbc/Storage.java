package com.zhaoyao.zhaoyaoba.jdbc;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 仓库类Storage实现缓冲区
 * 
 * Email:530025983@qq.com
 * 
 * @author MONKEY.D.MENG 2011-03-15
 * 
 */
public class Storage
{
	// 仓库最大存储量
	private final int MAX_SIZE = 100;

	// 仓库存储的载体
	private static LinkedList<Object> list = new LinkedList<Object>();

	// 锁
	private final Lock lock = new ReentrantLock();

	// 仓库满的条件变量
	private final Condition full = lock.newCondition();

	// 仓库空的条件变量
	private final Condition empty = lock.newCondition();

	// 生产num个产品
	public void produce(int num)
	{
		// 获得锁
		lock.lock();

		// 如果仓库剩余容量不足
		while (list.size() + num > MAX_SIZE)
		{
			System.out.println("【要生产的产品数量】:" + num + "/t【库存量】:" + list.size()
			        + "/t暂时不能执行生产任务!");
			try
			{
				// 由于条件不满足，生产阻塞
				full.await();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		// 生产条件满足情况下，生产num个产品
		for (int i = 1; i <= num; ++i)
		{
			list.add(new Object());
		}

		System.out.println("【已经生产产品数】:" + num + "/t【现仓储量为】:" + list.size());

		// 唤醒其他所有线程
		full.signalAll();
		empty.signalAll();

		// 释放锁
		lock.unlock();
	}

	// 消费num个产品
	public void consume(int num)
	{
		// 获得锁
		lock.lock();

		// 如果仓库存储量不足
		while (list.size() < num)
		{
			System.out.println("【要消费的产品数量】:" + num + "/t【库存量】:" + list.size()
			        + "/t暂时不能执行生产任务!");
			try
			{
				// 由于条件不满足，消费阻塞
				empty.await();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		// 消费条件满足情况下，消费num个产品
		for (int i = 1; i <= num; ++i)
		{
			list.remove();
		}

		System.out.println("【已经消费产品数】:" + num + "/t【现仓储量为】:" + list.size());

		// 唤醒其他所有线程
		full.signalAll();
		empty.signalAll();

		// 释放锁
		lock.unlock();
	}

	// set/get方法
	public int getMAX_SIZE()
	{
		return MAX_SIZE;
	}

	public LinkedList<Object> getList()
	{
		return list;
	}

	public void setList(LinkedList<Object> list)
	{
		this.list = list;
	}
	
	public static void main(String[] args)
	{
		name();
		
	}
	public static void testThr(Object o) {
		synchronized (list) {
			--m;
			System.out.println(o+"========maide biao 当前票数 == "+m);
		}
	}
	static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);  
	private static int m = 1000;
	public static void name() {
		fixedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				while (m>0) {
					testThr("t1");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		fixedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				while (m>0) {
					testThr("t2");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		fixedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				while (m>0) {
					testThr("t3");
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}