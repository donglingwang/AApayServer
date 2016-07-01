package com.bishe.aapay.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * һ�����ѵ�POJO
 * @author DongLing
 *
 */
public class Consumption implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*���*/
	private long id;
	private long consumptionId;
	/*����*/
	private String name;
	/*�Ŷ�ID*/
	private long teamId;
	/*�Ŷ�����*/
	private String teamName;
	/*���ѷ�ʽ*/
	private String consumptionMode;
	/*���ѽ��*/
	private double money;
	/*��������*/
	private String type;
	/*����ʱ��*/
	private String time;
	/*������Ա*/
	private List<Map<String, String>> participantList;
	
	private int participantNum;
	/*��ע*/
	private String remark;
	/*�Ƿ���*/
	private String payOFF;
	private String operateTime;
	private int userId;
	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public Consumption() {
		// TODO Auto-generated constructor stub
	}
	
	public long getId() {
		return id;
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
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getConsumptionMode() {
		return consumptionMode;
	}
	public void setConsumptionMode(String consumptionMode) {
		this.consumptionMode = consumptionMode;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public List<Map<String, String>> getParticipantList() {
		return participantList;
	}
	public void setParticipantList(List<Map<String, String>> participantList) {
		this.participantList = participantList;
	}
	public int getParticipantNum() {
		return participantNum;
	}
	public void setParticipantNum(int participantNum) {
		this.participantNum = participantNum;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPayOFF() {
		return payOFF;
	}
	public void setPayOFF(String payOFF) {
		this.payOFF = payOFF;
	}
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Consumption [id=" + id + ", name=" + name
				+ ", teamName=" + teamName + ", consumptionMode="
				+ consumptionMode + ", money=" + money + ", type=" + type
				+ ", time=" + time + ", participantList=" + participantList
				+ ", remark=" + remark + "]";
	}

}
