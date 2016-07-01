package com.bishe.aapay.dto;

import java.io.Serializable;

public class PersonalBudget implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private int budgetType;
	private long budgetId;
	private double budgetMoney;
	private String budgetCategory;
	private String budgetTime;
	private String operateTime;
	private String remark;
	private int userId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(long budgetId) {
		this.budgetId = budgetId;
	}
	public int getBudgetType() {
		return budgetType;
	}
	public void setBudgetType(int budgetType) {
		this.budgetType = budgetType;
	}
	public double getBudgetMoney() {
		return budgetMoney;
	}
	public void setBudgetMoney(double budgetMoney) {
		this.budgetMoney = budgetMoney;
	}
	public String getBudgetCategory() {
		return budgetCategory;
	}
	public void setBudgetCategory(String budgetCategory) {
		this.budgetCategory = budgetCategory;
	}
	public String getBudgetTime() {
		return budgetTime;
	}
	public void setBudgetTime(String budgetTime) {
		this.budgetTime = budgetTime;
	}
	public String getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "PersonalBudget [id=" + id + ", budgetType=" + budgetType
				+ ", budgetMoney=" + budgetMoney + ", budgetCategory="
				+ budgetCategory + ", budgetTime=" + budgetTime
				+ ", operateTime=" + operateTime + ", remark=" + remark + "]";
	}
	
}
