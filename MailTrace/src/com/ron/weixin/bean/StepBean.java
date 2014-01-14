package com.ron.weixin.bean;

public class StepBean {
	String time;
	String station;
	String action;
	String comment;
	
	public StepBean(String time, String station, String action, String comment) {
		super();
		this.time = time;
		this.station = station;
		this.action = action;
		this.comment = comment;
	}
	
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
