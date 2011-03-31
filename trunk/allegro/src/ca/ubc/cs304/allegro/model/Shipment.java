package ca.ubc.cs304.allegro.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class Shipment implements AllegroItem {
	private Integer sid;
	private String supname, sname;
	private Date date;
	
	public Shipment() {}
	
	public Shipment(Integer sid, String supname, String sname, Date date) {
		super();
		this.sid = sid;
		this.supname = supname;
		this.sname = sname;
		this.date = date;
	}
	
	public List<Object> getParameters() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(sid);
		parameters.add(supname);
		parameters.add(sname);
		parameters.add(date);
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
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
}
