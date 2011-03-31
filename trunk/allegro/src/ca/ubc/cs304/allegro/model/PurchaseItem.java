package ca.ubc.cs304.allegro.model;

import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class PurchaseItem implements AllegroItem {
	private Integer receiptId, upc, quantity;
	
	public PurchaseItem() {}

	public PurchaseItem(Integer receiptId, Integer upc, Integer quantity) {
		super();
		this.receiptId = receiptId;
		this.upc = upc;
		this.quantity = quantity;
	}

	public List<Object> getParameters() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(receiptId);
		parameters.add(upc);
		parameters.add(quantity);
		return parameters;
	}

	public Table getTable() {
		return Table.PurchaseItem;
	}

	public Integer getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Integer receiptId) {
		this.receiptId = receiptId;
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
	
	public String toString() {
		return ("recieptId: " + receiptId +
				"\nupc: " + upc +
				"\nquantity: " + quantity);
	}

}
