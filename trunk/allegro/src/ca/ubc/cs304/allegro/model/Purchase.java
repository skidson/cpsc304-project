package ca.ubc.cs304.allegro.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class Purchase implements AllegroItem {
	private Integer receiptId;
	private Long cardNum, expire;
	private Date date, expectedDate, deliveredDate;
	private String cid, sname;
	
	public Purchase() {}
	
	public Purchase(Integer receiptId, Long cardNum, Long expire, Long date,
			Long expectedDate, Long deliveredDate, String cid, String sname) {
		super();
		this.receiptId = receiptId;
		this.cardNum = cardNum;
		this.expire = expire;
		if (date != null)
			this.date = new Date(date);
		if (expectedDate != null)
			this.expectedDate = new Date(expectedDate);
		if (deliveredDate != null)
			this.deliveredDate = new Date(deliveredDate);
		this.cid = cid;
		this.sname = sname;
	}

	public List<Object> getParameters() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(receiptId);
		parameters.add(date);
		parameters.add(cid);
		parameters.add(sname);
		parameters.add(cardNum);
		parameters.add(expire);
		parameters.add(expectedDate);
		parameters.add(deliveredDate);
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
		return date.getTime();
	}
	
	public void setDate(Long date) {
		if (date != null)
			this.date = new Date(date);
	}
	
	public long getExpectedDate() {
		return expectedDate.getTime();
	}
	
	public void setExpectedDate(Long expectedDate) {
		if (expectedDate != null)
			this.expectedDate = new Date(expectedDate);
	}
	
	public long getDeliveredDate() {
		return deliveredDate.getTime();
	}
	
	public void setDeliveredDate(Long deliveredDate) {
		if (deliveredDate != null)
			this.deliveredDate = new Date(deliveredDate);
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
