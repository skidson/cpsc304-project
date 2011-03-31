package ca.ubc.cs304.allegro.model;

import java.util.ArrayList;
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
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(sname);
		parameters.add(address);
		parameters.add(type.toString().toLowerCase());
		return parameters;
	}

	public Table getTable() {
		return Table.Store;
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
