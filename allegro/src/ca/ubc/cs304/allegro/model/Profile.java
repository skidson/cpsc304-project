package ca.ubc.cs304.allegro.model;

import java.io.Serializable;

public class Profile implements Serializable {
	private static final long serialVersionUID = -799432430115623170L;
	private boolean manager = false;
	private boolean clerk = false;
	private boolean customer = false;
	private String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Profile() {}

	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	public boolean isClerk() {
		return clerk;
	}

	public void setClerk(boolean clerk) {
		this.clerk = clerk;
	}

	public boolean isCustomer() {
		return customer;
	}

	public void setCustomer(boolean customer) {
		this.customer = customer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
