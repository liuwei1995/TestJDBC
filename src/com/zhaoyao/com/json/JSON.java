package com.zhaoyao.com.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class JSON {
	
	protected String data;
	protected final List<Character> listc = new ArrayList<Character>();
	protected final Map<Object, Object> map = new HashMap<Object, Object>();
	protected final Integer length;
	protected final Map<Long, Character> mapChar = new HashMap<Long, Character>();
	protected final  Stack<Character> sc=new Stack<Character>();
	protected final List<Object> list = new ArrayList<Object>();
	protected final char[] c;
	public JSON(String data) {
		this.data = data;
		length = data.length();
		c = data.toCharArray();
	}
	
	
	public Integer parseJsonObject(final Map<Object, Object> map,int start,String str) {
		final StringBuffer sbkey = new StringBuffer();
		final List<Object> listkey = new ArrayList<Object>();
		boolean isf = false;
		for (int i = start; i < length; i++) {
			switch (c[i]) {
			case '[':
				 sc.push(c[i]);
				 if(isf){
					 final List<Object> list = new ArrayList<Object>();
					 int parseJsonArray = parseJsonArray(list, i+1, data);
					 i = parseJsonArray;
					 Object object = listkey.get(listkey.size()-1);
                     map.put(object, list);
                     listkey.remove(object);
                     sbkey.delete(0, sbkey.length());
                     isf = false;
				 }
				break;
			case '{':
				 sc.push(c[i]);
				 if(isf){
					 Map<Object, Object> mapd = new HashMap<Object, Object>();
					 Integer parseJsonObject = parseJsonObject(mapd,i+1,data);
					 i = parseJsonObject;
					 Object object = listkey.get(listkey.size()-1);
                     map.put(object, mapd);
                     listkey.remove(object);
                     sbkey.delete(0, sbkey.length());
                     isf = false;
				 }
				break;
			case '"':
				 if (!sc.isEmpty() && sc.peek()=='"') {
	                    sc.pop();
	                    if(isf){
	                    	Object object = listkey.get(listkey.size()-1);
		                    map.put(object, sbkey.toString());
		                    listkey.remove(object);
		                    sbkey.delete(0, sbkey.length());
		                    isf = false;
	                    }else {
	                    	map.put(sbkey.toString(), "");
	                    	listkey.add(sbkey.toString());
	                    	sbkey.delete(0, sbkey.length());
						}
	                }else {
	                	sc.push(c[i]);
					}
				break;
			case ']':
				 if (sc.peek()=='[') {
	                    sc.pop();
	                }
				 isf = false;
				break;
			case '}':
				 if (!sc.isEmpty() && sc.peek()=='{') {
	                    sc.pop();
	                    if(!sc.empty()){
	                    	if(listkey.size() != 0){
	                    		Object object = listkey.get(listkey.size()-1);
	                    		map.put(object, sbkey.toString());
	                    		listkey.remove(object);
	                    		sbkey.delete(0, sbkey.length());
	                    	}
	                    	return i;
	                    }
	                }
				 isf = false;
				break;
			case ':':
				isf = true;
				break;
			case ',':
				if(isf){
					Object object = listkey.get(listkey.size()-1);
                    map.put(object, sbkey.toString());
                    listkey.remove(object);
                    sbkey.delete(0, sbkey.length());
                    isf = false;
				}
				break;

			default:
				sbkey.append(c[i]);
				break;
			}
		}
		return 0;
	}
	public int parseJsonArray(final List<Object> list,int start,String arr) {
		final StringBuffer sbkey = new StringBuffer();
		boolean isf = false;
		for (int i = start; i < length; i++) {
			switch (c[i]) {
			case '[':
				 sc.push(c[i]);
				 if(isf){
					 final List<Object> listd = new ArrayList<Object>();
					 int parseJsonArray = parseJsonArray(listd, i+1, data);
					 i = parseJsonArray;
                     list.add(listd);
                     sbkey.delete(0, sbkey.length());
                     isf = false;
				 }
				break;
			case '{':
				 final Map<Object, Object> mapd = new HashMap<Object, Object>();
				 final Integer parseJsonObject = parseJsonObject(mapd,i,data);
				 i = parseJsonObject;
				 list.add(mapd);
				 sbkey.delete(0, sbkey.length());
				 isf = false;
				break;
			case '"':
				 if (!sc.isEmpty() && sc.peek()=='"') {
	                    sc.pop();
	                    list.add(sbkey.toString());
	                    sbkey.delete(0, sbkey.length());
	                }else {
	                	sc.push(c[i]);
					}
				break;
			case ']':
				 if (sc.peek()=='[') {
	                    sc.pop();
	                    if(!sc.empty()){
	                    	list.add(sbkey.toString());
	                    	sbkey.delete(0, sbkey.length());
	                    	return i;
	                    }
	                }
				 isf = false;
				break;
			case '}':
				 isf = false;
				break;
			case ':':
				isf = true;
				break;
			case ',':
				if(isf){
                    list.add(sbkey.toString());
                    sbkey.delete(0, sbkey.length());
                    isf = false;
				}
				break;

			default:
				sbkey.append(c[i]);
				break;
			}
		}
		return 0;
	}
}
