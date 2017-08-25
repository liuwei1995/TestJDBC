package com.zhaoyao.tuixiangzi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Test {
	private static String ren = "0";
	private static String xiang = "1";
	
	private static List<String> list = new ArrayList<String>();
	private static Map<String, Integer> map = new HashMap<String, Integer>();
	
	private static final String KONG = "*";
	private static final String KONG_GE = "  ";
	private static final String DOU_HAO = ",";
	
	
	public static void main1(String[] args) {
		int b = 30;
		int h = 20;
		int w = 10;
		int xiangzi_shu = new Random().nextInt(10)+1;
		System.out.println("箱子前数量："+xiangzi_shu);
		if(xiangzi_shu <= 2)xiangzi_shu = 2;
		System.out.println("箱子后数量："+xiangzi_shu);
		HashMap<Integer, String> hashMap = new HashMap<Integer, String>(xiangzi_shu);
		
		HashMap<String, Integer> hashMap_xiangzi = new HashMap<String, Integer>(xiangzi_shu+1);
		HashMap<String, Integer> hashMap_ren = new HashMap<String, Integer>(1);
		
		HashMap<Integer, String> hashMap_weizhi = new HashMap<Integer, String>(xiangzi_shu+1);
		hashMap_weizhi.put(0, (new Random().nextInt(w)+1) +DOU_HAO + (new Random().nextInt(h)+1));
		hashMap_xiangzi.put(hashMap_weizhi.get(0), 0);
		
		for (int i = 1; i <= xiangzi_shu; i++) {
			int x_x = new Random().nextInt(w)+1;
			int x_y = new Random().nextInt(h)+1;
			while (hashMap_weizhi.containsKey(x_x+DOU_HAO+x_y)) {
				 x_x = new Random().nextInt(w)+1;
				 x_y = new Random().nextInt(h)+1;
			}
			hashMap_xiangzi.put(x_x+DOU_HAO+x_y, i);
			hashMap_weizhi.put(i, x_x+DOU_HAO+x_y);
		}
		
		System.out.println("箱子最初的位置："+hashMap_xiangzi.toString());
		System.out.println("人最初的位置："+hashMap_weizhi.get(0));
		StringBuilder sb = new StringBuilder();
		for (int y = 1; y <= h; y++) {
			sb.append("\n");
			for (int x = 1; x <= w; x++) {
				if(hashMap_xiangzi.containsKey(x+","+y)){
					Integer integer = hashMap_xiangzi.get(x+","+y);
					if(integer == 0){
						sb.append(ren+KONG_GE);
					}else {
						sb.append(xiang+KONG_GE);
					}
				}else {
					sb.append(KONG+KONG_GE);
				}
			}
		}
		System.out.println("排列：\n"+sb.toString());
	}
	
	private static int ren_ = 1;
	private static int xiangzi_ = 2;
	
	static int h = 10;
	static int w = 10;
	public static void main(String[] args) {
		int xiangzi_shu = new Random().nextInt(10)+1;
		System.out.println("箱子前数量："+xiangzi_shu);
		if(xiangzi_shu <= 4)xiangzi_shu = 4;
		System.out.println("箱子后数量："+xiangzi_shu);
		
		HashMap<String, Integer> hashMap_xiangzi = new HashMap<String, Integer>(xiangzi_shu+1);
		
		
		int[][] weizhi = new int[w][h];
		
		int w_ren = new Random().nextInt(w);
		 while (w_ren == 0 || w_ren == (w - 1)) {
			 w_ren = new Random().nextInt(w);
		 }
		int h_ren = new Random().nextInt(h);
		while (h_ren == 0 || h_ren == (h - 1)) {
			h_ren = new Random().nextInt(h);
		 }
		weizhi[w_ren][h_ren] = ren_;
		
		Map<Integer, String> map_xiangzi = new HashMap<Integer, String>();
		
		Map<Integer, int[][]> xiangzi_zoudeweizhiMap = new HashMap<Integer, int[][]>();
		for (int i = 1; i <= xiangzi_shu; i++) {
			int x_x = new Random().nextInt(w);
			 while (x_x == 0 || x_x == (w - 1)) {
				 x_x = new Random().nextInt(w);
			 }
			int x_y = new Random().nextInt(h);
			while (x_y == 0 || x_y == (h - 1)) {
				 x_y = new Random().nextInt(h);
			 }
			while (weizhi[x_x][x_y] != 0) {
				 x_x = new Random().nextInt(w);
				 while (x_x == 0 || x_x == (w - 1)) {
					 x_x = new Random().nextInt(w);
				 }
				 while (x_y == 0 || x_y == (h - 1)) {
					 x_y = new Random().nextInt(h);
				 }
			}
			
			hashMap_xiangzi.put(x_x+DOU_HAO+x_y, i);
			
			
			weizhi[x_x][x_y] = xiangzi_;
			map_xiangzi.put(i, x_x+DOU_HAO+x_y);
			
			int [][] value =  new int[w][h];
			value[x_x][x_y] = xiangzi_;
			xiangzi_zoudeweizhiMap.put(i, value);
		}
		
		System.out.println("箱子最初的位置："+hashMap_xiangzi.toString());
		StringBuilder sb = new StringBuilder();
		for (int[] is : weizhi) {
			sb.append("\n");
			for (int i : is) {
				if (i == xiangzi_) {
					sb.append(xiang+KONG_GE);
				}else if (i == ren_) {
					sb.append(ren+KONG_GE);
				}else {
					sb.append(KONG+KONG_GE);
				}
			}
		}
		System.out.println("排列：\n"+sb.toString());
		
		
		int xiangzi_number = 1;
		while (xiangzi_number <= map_xiangzi.size()) {
			tuixiangzi(xiangzi_number,map_xiangzi,xiangzi_zoudeweizhiMap);
			
			int[][] is = xiangzi_zoudeweizhiMap.get(xiangzi_number);
			StringBuilder sb2 = new StringBuilder();
			for (int[] is2 : is) {
				sb2.append("\n");
				for (int i : is2) {
					if (i == xiangzi_) {
						sb2.append(xiang+KONG_GE);
					}else if (i == ren_) {
						sb2.append(ren+KONG_GE);
					}else {
						sb2.append(KONG+KONG_GE);
					}
				}
			}
			System.out.println(sb2.toString());
			++xiangzi_number;
		}
		int[][] is = xiangzi_zoudeweizhiMap.get(xiangzi_number-1);
		StringBuilder sb2 = new StringBuilder();
		for (int[] is2 : is) {
			sb2.append("\n");
			for (int i : is2) {
				if (i == xiangzi_) {
					sb2.append(xiang+KONG_GE);
				}else if (i == ren_) {
					sb2.append(ren+KONG_GE);
				}else {
					sb2.append(KONG+KONG_GE);
				}
			}
		}
		System.out.println(sb2.toString());
	}
	public static void tuixiangzi(int xiangzi_number,Map<Integer, String> map_xiangzi,Map<Integer, int[][]> xiangzi_zoudeweizhiMap) {
		String string_x_y = map_xiangzi.get(xiangzi_number);
		String[] split = string_x_y.split(DOU_HAO);
		int x = Integer.valueOf(split[0]);
		int y = Integer.valueOf(split[1]);
		int b = 20;
		List<String> list = new ArrayList<String>(4);
		int t = -1;
		for (int i = 0; i < b; i++) {
			list.add(0+"");
			list.add(1+"");
			list.add(2+"");
			list.add(3+"");
			if(t == 0){
				list.remove(1+"");
			}
			else if (t == 1) {
				list.remove(0+"");
			}
			else if (t == 2) {
				list.remove(3+"");
			}
			else if (t == 3) {
				list.remove(2+"");
			}
			while (true) {
				int size = list.size();
				if(size == 0){
					System.out.println("=========================");
					break;
				}
				int type = new Random().nextInt(list.size());
				type = Integer.valueOf(list.get(type));
				int[][] map = xiangzi_zoudeweizhiMap.get(xiangzi_number);
				if(type == 0){//上
					if(y-1 < 0 || map[x][y-1] != 0){
						list.remove(type+"");
						continue;
					}
					t = type;
					y = y - 1;
					map[x][y] = xiangzi_;
				}else if (type == 1) {//下
					if(y+1 >= h || map[x][y+1] != 0){
						list.remove(type+"");
						continue;
					}
					t = type;
					y = y + 1;
					map[x][y] = xiangzi_;
					System.out.println(xiangzi_number+"：下");
				}else if (type == 2) {//左
					if(x-1 < 0 || map[x-1][y] != 0){
						list.remove(type+"");
						continue;
					}
					t = type;
					x = x - 1;
					map[x][y] = xiangzi_;
					System.out.println(xiangzi_number+"：左");
				}else if (type == 3) {//右
					if(x+1 >= w || map[x+1][y] != 0){
						list.remove(type+"");
						continue;
					}
					t = type;
					x = x + 1;
					map[x][y] = xiangzi_;
					System.out.println(xiangzi_number+"：右");
				}
				break;
			}
			list.clear();
		}
	}
}
