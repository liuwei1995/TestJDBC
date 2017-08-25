package com.zhaoyao.com;

import java.util.Date;

public class Delivery {
	private Integer deliveryId;
	private String deliveryName;
	private String deliveryPhone;
	private String password;
	private Date createTime;
	private Integer state;
	public Integer getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Integer deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public String getDeliveryPhone() {
		return deliveryPhone;
	}
	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
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
		return "Delivery [deliveryId=" + deliveryId + ", deliveryName="
				+ deliveryName + ", deliveryPhone=" + deliveryPhone
				+ ", password=" + password + ", createTime=" + createTime
				+ ", state=" + state + "]";
	}
	
}
