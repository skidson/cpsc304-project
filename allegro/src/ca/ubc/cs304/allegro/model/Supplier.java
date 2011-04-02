package ca.ubc.cs304.allegro.model;

import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class Supplier implements AllegroItem {
	private String supname, address, city;
	private Integer status;
	
	public Supplier() {}
	
	public Supplier(String supname, String address, String city, Integer status) {
		super();
		this.supname = supname;
		this.address = address;
		this.city = city;
		this.status = status;
	}

	public List<Object> getParameters() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(supname);
		parameters.add(address);
		parameters.add(city);
		parameters.add(status);
		return parameters;
	}

	public Table getTable() {
		return Table.Supplier;
	}

	public String getSupname() {
		return supname;
	}

	public void setSupname(String supname) {
		this.supname = supname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String toString() {
		return ("supname: " + supname + 
				"\naddress: " + address + 
				"\ncity: " + city +
				"\nstatus: " + status);
	}

}
