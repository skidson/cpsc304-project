package ca.ubc.cs304.allegro.model;

import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class Shipment implements AllegroItem {
	private int sid;
	private String supname, sname;
	private long date;
	
	
	
	public List<Object> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}
	public Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSupname() {
		return supname;
	}
	public void setSupname(String supname) {
		this.supname = supname;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	
	
}
