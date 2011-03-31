package ca.ubc.cs304.allegro.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class Purchase implements AllegroItem {
	private int receiptId;
	private long cardNum, expire, date, expectedDate, deliveredDate;
	private String cid, sname;
	
	public Purchase() {}
	
	public Purchase(int receiptId, long cardNum, long expire, long date,
			long expectedDate, long deliveredDate, String cid, String sname) {
		super();
		this.receiptId = receiptId;
		this.cardNum = cardNum;
		this.expire = expire;
		this.date = date;
		this.expectedDate = expectedDate;
		this.deliveredDate = deliveredDate;
		this.cid = cid;
		this.sname = sname;
	}

	public List<Object> getParameters() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(new Integer(receiptId));
		parameters.add(new Date(date));
		parameters.add(sname);
		parameters.add(cardNum);
		parameters.add(new Date(expire));
		parameters.add(new Date(expectedDate));
		parameters.add(new Date(deliveredDate));
		return parameters;
	}
	
	public Table getTable() {
		return Table.Purchase;
	}
	
	public int getReceiptId() {
		return receiptId;
	}
	
	public void setReceiptId(Integer receiptId) {
		this.receiptId = receiptId;
	}
	
	public long getCardNum() {
		return cardNum;
	}
	
	public void setCardNum(Long cardNum) {
		this.cardNum = cardNum;
	}
	
	public long getExpire() {
		return expire;
	}
	
	public void setExpire(Long expire) {
		this.expire = expire;
	}
	
	public long getDate() {
		return date;
	}
	
	public void setDate(Long date) {
		this.date = date;
	}
	
	public long getExpectedDate() {
		return expectedDate;
	}
	
	public void setExpectedDate(Long expectedDate) {
		this.expectedDate = expectedDate;
	}
	
	public long getDeliveredDate() {
		return deliveredDate;
	}
	
	public void setDeliveredDate(Long deliveredDate) {
		this.deliveredDate = deliveredDate;
	}
	
	public String getCid() {
		return cid;
	}
	
	public void setCid(String cid) {
		this.cid = cid;
	}
	
	public String getSname() {
		return sname;
	}
	
	public void setSname(String sname) {
		this.sname = sname;
	}
	
	
	
}
