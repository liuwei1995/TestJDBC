package com.zhaoyao.com;

import java.util.Date;

public class Number {
	private Integer numberId;
	private String numberCode;
	private Date createTime;
	private Integer drugStroeId;
	public Integer getNumberId() {
		return numberId;
	}
	public void setNumberId(Integer numberId) {
		this.numberId = numberId;
	}
	public String getNumberCode() {
		return numberCode;
	}
	public void setNumberCode(String numberCode) {
		this.numberCode = numberCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getDrugStroeId() {
		return drugStroeId;
	}
	public void setDrugStroeId(Integer drugStroeId) {
		this.drugStroeId = drugStroeId;
	}
	@Override
	public String toString() {
		return "Number [numberId=" + numberId + ", numberCode=" + numberCode
				+ ", createTime=" + createTime + ", drugStroeId=" + drugStroeId
				+ "]";
	}
	
}
