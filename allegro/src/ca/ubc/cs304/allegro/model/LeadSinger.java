package ca.ubc.cs304.allegro.model;

import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class LeadSinger implements AllegroItem {
	private Integer upc;
	private String name;
	
	public LeadSinger() {}

	public LeadSinger(Integer upc, String name) {
		super();
		this.upc = upc;
		this.name = name;
	}

	public List<Object> getParameters() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(upc);
		parameters.add(name);
		return parameters;
	}

	public Table getTable() {
		return Table.LeadSinger;
	}

	public Integer getUpc() {
		return upc;
	}

	public void setUpc(Integer upc) {
		this.upc = upc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return ("upc: " + upc + 
				"\nname: " + name);
	}

}
