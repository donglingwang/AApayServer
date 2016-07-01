package com.bishe.aapay.dto;

import java.io.Serializable;

public class Payment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private long paymentId;
	/*
	 * ĳ�����ѵı��
	 */
	private long consumptionId;
	/*
	 * ������Ա��ID
	 */
	private long partId;
	/*
	 * �渶��
	 */
	private double advanceMoney;
	private int userId;
	public long getId() {
		return id;
	}
	public long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getConsumptionId() {
		return consumptionId;
	}
	public void setConsumptionId(long consumptionId) {
		this.consumptionId = consumptionId;
	}
	public long getPartId() {
		return partId;
	}
	public void setPartId(long partId) {
		this.partId = partId;
	}
	public double getAdvanceMoney() {
		return advanceMoney;
	}
	public void setAdvanceMoney(double advanceMoney) {
		this.advanceMoney = advanceMoney;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
}
