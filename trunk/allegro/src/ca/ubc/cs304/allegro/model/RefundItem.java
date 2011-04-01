package ca.ubc.cs304.allegro.model;

import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class RefundItem implements AllegroItem {
	private Integer retid, upc, quantity;

	public RefundItem() {}
	
	public RefundItem(Integer retid, Integer upc, Integer quantity) {
		super();
		this.retid = retid;
		this.upc = upc;
		this.quantity = quantity;
	}

	public List<Object> getParameters() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(retid);
		parameters.add(upc);
		parameters.add(quantity);
		return parameters;
	}

	public Table getTable() {
		return Table.RefundItem;
	}

	public Integer getRetid() {
		return retid;
	}

	public void setRetid(Integer retid) {
		this.retid = retid;
	}

	public Integer getUpc() {
		return upc;
	}

	public void setUpc(Integer upc) {
		this.upc = upc;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
