package com.zhaoyao.com;

import java.util.Map;

public class NumberThreadMap extends Thread {
	private  Map<Integer, java.util.List<DrugStroeInfo>> drugStroeInfoMap;

	private  Map<String, java.util.List<Delivery>> deliveryMap;
	
	private  Map<String, java.util.List<Number>> numberMap;
	private String key;


	private String name;

	public NumberThreadMap(String name,String key, Map<Integer, java.util.List<DrugStroeInfo>> drugStroeInfoMap,
			Map<String, java.util.List<Delivery>> deliveryMap,
			Map<String, java.util.List<Number>> numberMap) {
		this.name = name;
		this.drugStroeInfoMap = drugStroeInfoMap;
		this.deliveryMap = deliveryMap;
		this.numberMap = numberMap;
		this.key = key;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (deliveryMap.get(key)) {
				int m = 0;
				Delivery deli = null;
				while (numberMap.get(key).isEmpty() || deliveryMap.get(key).isEmpty()) {
					if (numberMap.get(key).isEmpty())
						System.out.println(name +" ===  "+key+" ===  " + "===== ：暂时没有订单--等待");
					else if (deliveryMap.get(key).isEmpty())
						System.out.println(name +" ===  "+key+" ===  " + "===== ：暂时没有配送人员--等待");
					try {
						deliveryMap.get(key).wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					for (Delivery delivery : deliveryMap.get(key)) {
						Integer state = delivery.getState();
						if (state == 1) {
							Integer deliveryId = delivery.getDeliveryId();
							if (deliveryId == null)
								continue;
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
						deliveryMap.get(key).wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}

				Number remove = numberMap.get(key).remove(0);
				System.out.println(name +" ===  "+key+" ===  " + ":   正在配送的订单：==" + remove.toString());
				deliveryMap.get(key).remove(deli);
				System.out.println(name +" ===  "+key+" ===  "+ ":    配送人员：正在配送" + deli.toString());
				deliveryMap.get(key).notifyAll();
			}
		}
	}

}
