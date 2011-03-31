package ca.ubc.cs304.allegro.model;

import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class Store implements AllegroItem {
	public enum Type {STORE, WAREHOUSE};
	
	private String sname, address;
	private Type type;
	
	public Store() {}

	public Store(String sname, String address, Type type) {
		super();
		this.sname = sname;
		this.address = address;
		this.type = type;
	}

	public List<Object> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	
	
}
