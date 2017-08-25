package com.zhaoyao.com.parse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhaoyao.com.DrugStroeInfo;
import com.zhaoyao.com.json.entity.Data;

public class TestParse {
	public static void main(String[] args) {
//		DrugStroeInfo d = new DrugStroeInfo();
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 3; i++) {
			list.add(""+i);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<List<String>> list_list_s = new ArrayList<List<String>>();
		List<List<List<String>>> list_list_list_s = new ArrayList<List<List<String>>>();
		map.put("list", list);
		list_list_s.add(list);
		
		
		List<Data> list_Data = new ArrayList<Data>();
		map.put("list_Data", list_Data);
		
		
		map.put("list_list_s", list_list_s);
		map.put("list_Object", list_list_s);
		list_list_list_s.add(list_list_s);
		map.put("list_list_list_s", list_list_list_s);
		
		List<Map<Object, Object>> list_Object_Map = new ArrayList<Map<Object,Object>>();
		
		Map<Object, Object> bject_Map = new HashMap<Object, Object>();
		bject_Map.put("list_Object", list_list_s);
		list_Object_Map.add(bject_Map);
		
		map.put("list_Object_Map", list_Object_Map);
		map.put("password", "123456");
		map.put("createTime", new java.sql.Date(System.currentTimeMillis()));
		map.put("time", new Date());
		map.put("userId", 1);
		map.put("drugStroeId", 2);
		map.put("sex_1", new Character('5'));
		map.put("sex_2", '5');
		map.put("isTrue_1", new Boolean(true));
		map.put("isTrue_2",false );
		map.put("double1", new Double(2.2));
		map.put("double2", 1.1);
		map.put("long1", null);
		map.put("long2",new Long(22));
		byte b = 92;
		map.put("byte1", b);
		map.put("byte2", new Byte((byte) 33));
		map.put("map", bject_Map);
		
		 Map<Object, List<String>> map_list = new HashMap<Object, List<String>>();
		 map_list.put("mapmap", list);
		map.put("map_list", map_list);
		DrugStroeInfo mapToEntity = ParseUitls.MapToEntity(map, DrugStroeInfo.class);
		if(mapToEntity != null)
		System.out.println(mapToEntity.toString());
		
	}
}
