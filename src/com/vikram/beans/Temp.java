package com.vikram.beans;

public class Temp {
	
	private String firstName;
	
	private String lastnName;


	public Temp(String firstName, String lastnName) {
		this.firstName = firstName;
		this.lastnName = lastnName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastnName() {
		return lastnName;
	}

	public void setLastnName(String lastnName) {
		this.lastnName = lastnName;
	}
}
