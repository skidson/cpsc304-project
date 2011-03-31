package ca.ubc.cs304.allegro.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class Shipment implements AllegroItem {
	private int sid;
	private String supname, sname;
	private long date;
	
	public List<Object> getParameters() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(new Integer(sid));
		parameters.add(supname);
		parameters.add(sname);
		parameters.add(new Date(date));
		return parameters;
	}
	public Table getTable() {
		return Table.Shipment;
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
