package ca.ubc.cs304.allegro.model;

import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class Stored implements AllegroItem {
	private String sname;
	private Integer upc, stock;
	
	public Stored() {}

	public Stored(String sname, Integer upc, Integer stock) {
		super();
		this.sname = sname;
		this.upc = upc;
		this.stock = stock;
	}
	
	public List<Object> getParameters() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(sname);
		parameters.add(upc);
		parameters.add(stock);
		return parameters;
	}

	public Table getTable() {
		return Table.Stored;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public Integer getUpc() {
		return upc;
	}

	public void setUpc(Integer upc) {
		this.upc = upc;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	public String toString() {
		return ("sname: " + sname + 
				"\nupc: " + upc + 
				"\nstock: " + stock);
	}
	
}
