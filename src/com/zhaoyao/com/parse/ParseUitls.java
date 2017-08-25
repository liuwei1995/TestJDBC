package com.zhaoyao.com.parse;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseUitls {
	
	
	public static <T, K, V> T MapToEntity(Map<K, V> map,Class<T> clazz) {
		if(clazz == null)return null;
		if(map == null)
			try {
				return clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		Field[] fields = clazz.getDeclaredFields();
		List<Field> list = new ArrayList<Field>();
		for (Field field : fields) {
			if(field.isEnumConstant())continue;
			if(field.isSynthetic())continue;
			if("serialVersionUID".equals(field.getName()))continue;
			list.add(field);
		}
		fields = list.toArray(new Field[0]);
		try {
			T obj = clazz.newInstance();
			for (Field field : list) {
				String name = field.getName();
				if(!map.containsKey(name))continue;
				field.setAccessible(true);
				Class<?> type = field.getType();
				V v = map.get(name);
				try {
					if(type == String.class){
						if(v.getClass() == type)
						field.set(obj, v);
						else 
						field.set(obj, v+"");
					}else if(type == int.class){
						if(v != null)
							field.set(obj, Integer.parseInt(v.toString()));
					}else if (type == Integer.class) {
						field.set(obj, v==null?v:Integer.valueOf(v.toString()));
					}else if (type == long.class) {
						if(v != null)
							field.set(obj, Long.parseLong(v.toString()));
					}else if (type == Long.class) {
						field.set(obj, v==null?v:Long.valueOf(v.toString()));
					}else if (type == double.class) {
						if(v != null)
							field.set(obj, Double.parseDouble(v.toString()));
					}else if (type == Double.class) {
						field.set(obj, v==null?v:Double.valueOf(v.toString()));
					}else if (type == boolean.class) {
						if(v != null)
							field.set(obj, Boolean.parseBoolean(v.toString()));
					}else if (type == Boolean.class) {
						field.set(obj, v==null?v:Boolean.valueOf(v.toString()));
					}else if (type == char.class) {
						if(v == char.class)
							field.set(obj,v);
						else if (v instanceof Character) {
							Character c = (Character) v;
							field.set(obj,c.charValue());
						}
					}else if (type == Character.class) {
						if(v instanceof Character || v == char.class)
							field.set(obj,v);
					}else if (type == byte.class) {
						if(v != null)
							field.set(obj, Byte.parseByte(v.toString()));
					}else if (type == Byte.class) {
						field.set(obj, v==null?v:Byte.valueOf(v.toString()));
					}else if (type == float.class) {
						if(v != null)
							field.set(obj, Float.parseFloat(v.toString()));
					}else if (type == Float.class) {
						field.set(obj, v==null?v:Float.valueOf(v.toString()));
					}else if (type == Date.class || type == java.sql.Date.class) {
						if(v == null || v instanceof java.sql.Date || (v instanceof Date && type == Date.class)){
							field.set(obj,v);
						}else if (v instanceof Date && type == java.sql.Date.class) {
							Date d = (Date) v;
							field.set(obj,new java.sql.Date(d.getTime()));
						}else if(v instanceof String || v instanceof Long){
							Date parse = null;
							try {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								 parse = sdf.parse(v.toString());
							} catch (ParseException e) {
								e.printStackTrace();
								try {
									parse = DateFormat.getInstance().parse(v.toString());
								} catch (ParseException e1) {
									e1.printStackTrace();
								}
							}
							if(parse != null){
								if(type == Date.class){
									field.set(obj,parse);
								}else if (type == java.sql.Date.class) {
									field.set(obj,new java.sql.Date(parse.getTime()));
								}
							}
							
						}
					}else if (type == List.class) {
						Type genericType = field.getGenericType();
						Type parameterizedType = getListParameterizedType(genericType);
						if(parameterizedType != null){
							List<?> list2 = null;
							if(v instanceof List){
								list2 = toList(parameterizedType, (List) v);
								if(list2 != null){
									field.set(obj, list2);
								}else {
									Class<? extends Type> class1 = parameterizedType.getClass();
									System.out.println(class1);
									String string = parameterizedType.toString().replace("class", "").trim();
									try {
										Class<?> forName = Class.forName(string);
										System.out.println(forName);
//										TODO   
									} catch (ClassNotFoundException e) {
										e.printStackTrace();
									}
								}
							
							}
						}
					}else if (type == Map.class) {
						Type genericType = field.getGenericType();
						Type[] mapParameterizedType = getMapParameterizedType(genericType);
						if(mapParameterizedType != null){
							if(v instanceof Map){
								Map<?, ?> m = (Map<?, ?>) v;
								Map<?, ?> map2 = toMap(mapParameterizedType, m);
								field.set(obj, map2);
							}
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();continue;
				} catch (IllegalAccessException e) {
					e.printStackTrace();continue;
				}
			}
			return obj;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @param genericType    field.getGenericType();
	 * @return   获取第一个泛型类型
	 */
	public static Type getListParameterizedType(Type genericType) {
//		Type genericType = field.getGenericType();
		if(genericType instanceof ParameterizedType){
			ParameterizedType tt = (ParameterizedType) genericType;
			Type[] actualTypeArguments = tt.getActualTypeArguments();
			if(actualTypeArguments == null || actualTypeArguments.length == 0)return null;
			Type type2 = actualTypeArguments[0];//获取第一个泛型类型
			return type2;
		}
		return null;
	}
	public static Type[] getMapParameterizedType(Type genericType) {
//		Type genericType = field.getGenericType();
		if(genericType instanceof ParameterizedType){
			ParameterizedType tt = (ParameterizedType) genericType;
			Type[] actualTypeArguments = tt.getActualTypeArguments();
//			if(actualTypeArguments == null || actualTypeArguments.length == 0)return null;
//			Type type2 = actualTypeArguments[0];//获取第一个泛型类型
			return actualTypeArguments;
		}
		return null;
	}
	public static Type getRawType(Type genericType) {
//		Type genericType = field.getGenericType();
		if(genericType instanceof ParameterizedType){
			ParameterizedType tt = (ParameterizedType) genericType;
			Type[] actualTypeArguments = tt.getActualTypeArguments();
			if(actualTypeArguments == null || actualTypeArguments.length == 0)return null;
			Type rawType = tt.getRawType();
			return rawType;
		}
		return null;
	}
	
	public static Map<?, ?> toMap(Type []type,Map<?, ?> map) throws JSONException {
		if(type == null || type.length != 2 || map == null)return null;
		TypeVariable<?>[] typeParameters = map.getClass().getTypeParameters();
		
		if(typeParameters == null) return null;
//		for (Object typeVariable : map.keySet()) {
//			Class<? extends Object> class1 = typeVariable.getClass();
//			System.out.println(class1);
//			Object object = map.get(typeVariable);
//			Class<? extends Object> class2 = object.getClass();
//			TypeVariable<?>[] typeParameters2 = class2.getTypeParameters();
//			System.out.println(typeParameters2);
//		}
		for (int i = 0; i < typeParameters.length; i++) {
			Type Parameters = typeParameters[i].getBounds()[0];
			if(Parameters != type[i]){
				System.out.println();
				return null;
			}
		}
		return map;
	}
	public static List<?> toList(Type type,List<?> list_) throws JSONException {
		if(type == null)return null;
		if (type == String.class) {
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < list_.size(); i++)
			{
				Object jsonArrayObject = list_.get(i);
				if((jsonArrayObject instanceof JSONArray) || (jsonArrayObject instanceof JSONObject))break;
				list.add(jsonArrayObject.toString());
			}
			return list;
		}else if(type == Integer.class || type == int.class)
		{
			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < list_.size(); i++)
			{
				Object jsonArrayObject = list_.get(i);
				if((jsonArrayObject instanceof JSONArray) || (jsonArrayObject instanceof JSONObject))break;
				list.add(Integer.valueOf(jsonArrayObject.toString()));
			}
			return list;
		}else if (type == Double.class || type == double.class) {
			List<Double> list = new ArrayList<Double>();
			for (int i = 0; i < list_.size(); i++)
			{
				Object jsonArrayObject = list_.get(i);
				if((jsonArrayObject instanceof JSONArray) || (jsonArrayObject instanceof JSONObject))break;
				list.add(Double.valueOf(jsonArrayObject.toString()));
			}
			return list;
		}else if (type == Long.class || type == long.class) {
			List<Long> list = new ArrayList<Long>();
			for (int i = 0; i < list_.size(); i++)
			{
				Object jsonArrayObject = list_.get(i);
				if((jsonArrayObject instanceof JSONArray) || (jsonArrayObject instanceof JSONObject))break;
				list.add(Long.valueOf(jsonArrayObject.toString()));
			}
			return list;
		}else if (type == Short.class || type == short.class) {
			List<Short> list = new ArrayList<Short>();
			for (int i = 0; i < list_.size(); i++)
			{
				Object jsonArrayObject = list_.get(i);
				if((jsonArrayObject instanceof JSONArray) || (jsonArrayObject instanceof JSONObject))break;
				list.add(Short.valueOf(jsonArrayObject.toString()));
			}
			return list;
		}else if (type == Object.class) {
			List<Object> list = new ArrayList<Object>();
			for (int i = 0; i < list_.size(); i++)
			{
				list.add(list_.get(i));
			}
			return list;
		}else if (type instanceof List || type == List.class) {
			List<List> list = new ArrayList<List>();
			for (int i = 0; i < list_.size(); i++)
			{
				Object jsonArrayObject = list_.get(i);
				if((jsonArrayObject instanceof JSONArray) || (jsonArrayObject instanceof JSONObject) || !(jsonArrayObject instanceof List))break;
				list.add((List)jsonArrayObject);
			}
			return list;
		}else if (type instanceof Map || type == Map.class) {
			List<Map> list = new ArrayList<Map>();
			for (int i = 0; i < list_.size(); i++)
			{
				Object jsonArrayObject = list_.get(i);
				if((jsonArrayObject instanceof JSONArray) || (jsonArrayObject instanceof JSONObject) || !(jsonArrayObject instanceof Map))break;
				list.add((Map)jsonArrayObject);
			}
			return list;
		}else if (type == byte.class || type == Byte.class) {
			List<Byte> list = new ArrayList<Byte>();
			for (int i = 0; i < list_.size(); i++)
			{
				Object jsonArrayObject = list_.get(i);
				if((jsonArrayObject instanceof JSONArray) || (jsonArrayObject instanceof JSONObject))break;
				list.add(Byte.valueOf(jsonArrayObject.toString()));
			}
			return list;
		}else if (Date.class == type || java.sql.Date.class == type) {
			List<Date> list = new ArrayList<Date>();
			for (int i = 0; i < list_.size(); i++)
			{
				Object jsonArrayObject = list_.get(i);
				if((jsonArrayObject instanceof JSONArray) || (jsonArrayObject instanceof JSONObject))break;
				try {
					Date parse = DateFormat.getInstance().parse(jsonArrayObject.toString());
					list.add(parse);
				} catch (ParseException e) {
					e.printStackTrace();break;
				}
			}
			return list;
		}else if (Boolean.class == type || boolean.class == type) {
			List<Boolean> list = new ArrayList<Boolean>();
			for (int i = 0; i < list_.size(); i++)
			{
				Object jsonArrayObject = list_.get(i);
				if((jsonArrayObject instanceof JSONArray) || (jsonArrayObject instanceof JSONObject))break;
				list.add(Boolean.valueOf(jsonArrayObject.toString()));
			}
			return list;
		}else {
			Type rawType = getRawType(type);
			if(rawType == null)return null;
			return toList(rawType, list_);
		}
	}
	
}
