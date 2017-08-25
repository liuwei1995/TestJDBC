package com.zhaoyao.com;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zhaoyao.com.json.entity.Data;

public class DrugStroeInfo {
	private int userId;
	private char sex_1;
	private Character sex_2;
	private boolean isTrue_1;
	private Boolean isTrue_2;
	private float float1;
	private Float float2;
	private double double1;
	private Double double2;
	private long long1;
	private Long long2;
	private byte byte1;
	private Byte byte2;
	private Integer drugStroeId;
	private String drugStroeName;
	private String drugStorePhone;
	private String password;
	private Date createTime;
	private java.sql.Date time;
	private Integer state;
	private List<String> list;
	private List<Data> list_Data;
	private List<Object> list_Object;
	private List<Map<Object, Object>> list_Object_Map;
	private List<List<String>> list_list_s;
	private List<List<List<String>>> list_list_list_s;
	private Map<String, Object> map ;
	private Map<Object, List<String>> map_list;
	
	
	public Map<Object, List<String>> getMap_list() {
		return map_list;
	}
	public void setMap_list(Map<Object, List<String>> map_list) {
		this.map_list = map_list;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public List<Data> getList_Data() {
		return list_Data;
	}
	public void setList_Data(List<Data> list_Data) {
		this.list_Data = list_Data;
	}
	public List<Map<Object, Object>> getList_Object_Map() {
		return list_Object_Map;
	}
	public void setList_Object_Map(List<Map<Object, Object>> list_Object_Map) {
		this.list_Object_Map = list_Object_Map;
	}
	public List<List<List<String>>> getList_list_list_s() {
		return list_list_list_s;
	}
	public void setList_list_list_s(List<List<List<String>>> list_list_list_s) {
		this.list_list_list_s = list_list_list_s;
	}
	public List<Object> getList_Object() {
		return list_Object;
	}
	public void setList_Object(List<Object> list_Object) {
		this.list_Object = list_Object;
	}
	public List<List<String>> getList_list_s() {
		return list_list_s;
	}
	public void setList_list_s(List<List<String>> list_list_s) {
		this.list_list_s = list_list_s;
	}
	public char getSex_1() {
		return sex_1;
	}
	public void setSex_1(char sex_1) {
		this.sex_1 = sex_1;
	}
	public Character getSex_2() {
		return sex_2;
	}
	public void setSex_2(Character sex_2) {
		this.sex_2 = sex_2;
	}
	public boolean isTrue_1() {
		return isTrue_1;
	}
	public void setTrue_1(boolean isTrue_1) {
		this.isTrue_1 = isTrue_1;
	}
	public Boolean getIsTrue_2() {
		return isTrue_2;
	}
	public void setIsTrue_2(Boolean isTrue_2) {
		this.isTrue_2 = isTrue_2;
	}
	public float getFloat1() {
		return float1;
	}
	public void setFloat1(float float1) {
		this.float1 = float1;
	}
	public Float getFloat2() {
		return float2;
	}
	public void setFloat2(Float float2) {
		this.float2 = float2;
	}
	public double getDouble1() {
		return double1;
	}
	public void setDouble1(double double1) {
		this.double1 = double1;
	}
	public Double getDouble2() {
		return double2;
	}
	public void setDouble2(Double double2) {
		this.double2 = double2;
	}
	public long getLong1() {
		return long1;
	}
	public void setLong1(long long1) {
		this.long1 = long1;
	}
	public Long getLong2() {
		return long2;
	}
	public void setLong2(Long long2) {
		this.long2 = long2;
	}
	public byte getByte1() {
		return byte1;
	}
	public void setByte1(byte byte1) {
		this.byte1 = byte1;
	}
	public Byte getByte2() {
		return byte2;
	}
	public void setByte2(Byte byte2) {
		this.byte2 = byte2;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public java.sql.Date getTime() {
		return time;
	}
	public void setTime(java.sql.Date time) {
		this.time = time;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public Integer getDrugStroeId() {
		return drugStroeId;
	}
	public void setDrugStroeId(Integer drugStroeId) {
		this.drugStroeId = drugStroeId;
	}
	public String getDrugStroeName() {
		return drugStroeName;
	}
	public void setDrugStroeName(String drugStroeName) {
		this.drugStroeName = drugStroeName;
	}
	public String getDrugStorePhone() {
		return drugStorePhone;
	}
	public void setDrugStorePhone(String drugStorePhone) {
		this.drugStorePhone = drugStorePhone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "DrugStroeInfo [userId=" + userId + ", sex_1=" + sex_1
				+ ", sex_2=" + sex_2 + ", isTrue_1=" + isTrue_1 + ", isTrue_2="
				+ isTrue_2 + ", float1=" + float1 + ", float2=" + float2
				+ ", double1=" + double1 + ", double2=" + double2 + ", long1="
				+ long1 + ", long2=" + long2 + ", byte1=" + byte1 + ", byte2="
				+ byte2 + ", drugStroeId=" + drugStroeId + ", drugStroeName="
				+ drugStroeName + ", drugStorePhone=" + drugStorePhone
				+ ", password=" + password + ", createTime=" + createTime
				+ ", time=" + time + ", state=" + state + ", list=" + list
				+ "]";
	}

	
}
