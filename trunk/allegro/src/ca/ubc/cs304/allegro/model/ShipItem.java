package ca.ubc.cs304.allegro.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class ShipItem implements AllegroItem {
	private Integer sid, upc, quantity;
	private BigDecimal supPrice;
	
	public ShipItem() {}
	
	public ShipItem(Integer sid, Integer upc, Double supPrice, Integer quantity) {
		super();
		this.sid = sid;
		this.upc = upc;
		this.quantity = quantity;
		if (supPrice != null)
			this.supPrice = new BigDecimal(supPrice);
	}

	public List<Object> getParameters() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(sid);
		parameters.add(upc);
		parameters.add(supPrice);
		parameters.add(quantity);
		return parameters;
	}

	public Table getTable() {
		return Table.ShipItem;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
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

	public Double getSupPrice() {
		return supPrice.doubleValue();
	}

	public void setSupPrice(BigDecimal supPrice) {
		this.supPrice = supPrice;
	}
	
	public String toString() {
		return ("sid: " + sid + 
				"\nupc: " + upc + 
				"\nquantity: " + quantity +
				"\nsupPrice: " + supPrice);
	}
	
}
