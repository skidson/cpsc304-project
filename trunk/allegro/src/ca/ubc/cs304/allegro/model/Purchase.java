package ca.ubc.cs304.allegro.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class Purchase implements AllegroItem {
	private Integer receiptId;
	private Long cardNum;
	private Date date, expectedDate, deliveredDate, expire;
	private String cid, sname;
	
	public Purchase() {}
	
	public Purchase(Integer receiptId, Long cardNum, Date expire, Date date,
			Date expectedDate, Date deliveredDate, String cid, String sname) {
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
	
	public Date getExpire() {
		return expire;
	}
	
	public void setExpire(Date expire) {
		this.expire = expire;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getExpectedDate() {
		return expectedDate;
	}
	
	public void setExpectedDate(Date expectedDate) {
			this.expectedDate = expectedDate;
	}
	
	public Date getDeliveredDate() {
		return deliveredDate;
	}
	
	public void setDeliveredDate(Date deliveredDate) {
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
	
	public String toString() {
		return ("receiptId: " + receiptId + 
				"\ndate: " + date + 
				"\ncid: " + cid + 
				"\nsname: " + sname + 
				"\ncardNum: " + cardNum +
				"\nexpire: " + expire + 
				"\nexpectedDate: " + expectedDate + 
				"\ndeliveredDate: " + deliveredDate);
	}
	
}
