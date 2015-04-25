package com.sjsu.model;

import java.util.List;

public class _User{
	
	private List<_Appointment> appointment;
	private _Role role;
	private int id;
	private String name;
	private String userName;
	private String password;

		public _User(List<_Appointment> appointment, _Role role, Integer id,
			String name, String userName, String password, String email) {
		super();
		this.appointment = appointment;
		this.role = role;
		this.id = id;
		this.name = name;
		this.userName = userName;
		this.password = password;
		
	}

	public List<_Appointment> getAppointment() {
		return appointment;
	}

	public void setAppointment(List<_Appointment> appointment) {
		this.appointment = appointment;
	}

	public _Role getRole() {
		return role;
	}

	public void setRole(_Role role) {
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
