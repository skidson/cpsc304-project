package ca.ubc.cs304.allegro.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class Refund implements AllegroItem {
	private Integer retId, receiptId;
	private Date retDate;
	private String name;
	
	public Refund(Integer retId, Integer receiptId, Date retDate, String name) {
		super();
		this.retId = retId;
		this.receiptId = receiptId;
		this.retDate = retDate;
		this.name = name;
	}
	
	public List<Object> getParameters() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(new Integer(retId));
		parameters.add(retDate);
		parameters.add(receiptId);
		parameters.add(name);
		return parameters;
	}

	public Table getTable() {
		return Table.Refund;
	}

	public int getRetId() {
		return retId;
	}
	
	public void setRetId(int retId) {
		this.retId = retId;
	}
	
	public int getReceiptId() {
		return receiptId;
	}
	
	public void setReceiptId(int receiptId) {
		this.receiptId = receiptId;
	}
	
	public Date getRetDate() {
		return retDate;
	}
	
	public void setRetDate(Date retDate) {
		this.retDate = retDate;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
