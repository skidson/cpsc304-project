package ca.ubc.cs304.allegro.model;

import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class Customer implements AllegroItem {
	private String name, cid, password, address;
	private Long phone;
	
	public Customer() {}
	
	public Customer(String name, String cid, String password, String address,
			Long phone) {
		super();
		this.name = name;
		this.cid = cid;
		this.password = password;
		this.address = address;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}
	
	public List<Object> getParameters() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(cid);
		parameters.add(password);
		parameters.add(name);
		parameters.add(address);
		parameters.add(phone);
		return parameters;
	}
	
	public Table getTable() {
		return Table.Customer;
	}
	
	public String toString() {
		return ("cid: " + cid + 
				"\npassword: " + password + 
				"\nname: " + name + 
				"\naddress: " + address + 
				"\nphone: " + phone);
	}
}
