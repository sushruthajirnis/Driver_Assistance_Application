package com.sjsu.model;

public class _Appointment {
	private _Company company;
	private Integer id;
	private String title;
	private String startTime;
	
	public _Company getCompany() {
		return company;
	}
	public void setCompany(_Company company) {
		this.company = company;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	

}
