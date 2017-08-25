package com.zhaoyao.com;


public class NumberThread extends Thread {
	
	private java.util.List<DrugStroeInfo> drugStroeInfoList;
	private  java.util.List<Delivery> deliveryList;
	private java.util.List<Number> numberList;
	
	private String name;
	
	public NumberThread(String name,java.util.List<Number> numberList,java.util.List<Delivery> deliveryList,java.util.List<DrugStroeInfo> drugStroeInfoList) {
		this.drugStroeInfoList = drugStroeInfoList;
		this.deliveryList = deliveryList;
		this.name = name;
		this.numberList = numberList;
	}
	
	@Override
	public void run() {
		while (true) {
			synchronized (deliveryList) {
				int m = 0;
				Delivery deli = null;
				while (numberList.isEmpty() || deliveryList.isEmpty()) {
						if(numberList.isEmpty())
						System.out.println(name+"===== ：暂时没有订单--等待");
						else if(deliveryList.isEmpty())
						System.out.println(name+"===== ：暂时没有配送人员--等待");
						try {
								deliveryList.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						for (Delivery delivery : deliveryList) {
							Integer state = delivery.getState();
							if(state == 1)
							{
								Integer deliveryId = delivery.getDeliveryId();
								if(deliveryId == null)continue;
								m = delivery.getDeliveryId();
								deli = delivery;
								break;
							}
						}
				}
				if (m == 0) {
					++m;
					try {
						System.out.println("没有空闲的配送人员  等待");
						deliveryList.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}
				
				Number remove = numberList.remove(0);
				System.out.println(name +":   正在配送的订单：=="+remove.toString());
				deliveryList.remove(deli);
				System.out.println(name+":    配送人员：正在配送"+deli.toString());
				deliveryList.notifyAll();
			}
		}
	}
	
}
