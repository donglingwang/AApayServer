package com.bishe.aapay.dto;

import java.io.Serializable;

public class Participant implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private long teamId;
	private long participantId;
	private String name;
	private String phone;
	private double balance;
	private double totalAmount;
	private int userId;
	public Participant() {
		
	}
	public Participant(String name, String phone) {
		super();
		this.name = name;
		this.phone = phone;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getParticipantId() {
		return participantId;
	}
	public void setParticipantId(long participantId) {
		this.participantId = participantId;
	}
	public long getTeamId() {
		return teamId;
	}
	public void setTeamId(long teamId) {
		this.teamId = teamId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}
	/**
	 * ֻҪ������ͬ����Ϊ����������ͬ
	 * �����������ͬ���绰��ͬҲ��Ϊ����������ͬ
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Participant other = (Participant) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			if (phone == null) {
				if (other.phone != null)
					return false;
			} else if (!phone.equals(other.phone))
				return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return "Participant [id=" + id + ", name=" + name + ", phone=" + phone
				+ ", balance=" + balance + ", totalAmount=" + totalAmount + "]";
	}
	
}
