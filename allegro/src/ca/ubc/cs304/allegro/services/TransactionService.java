package ca.ubc.cs304.allegro.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ubc.cs304.allegro.jdbc.JDBCManager;
import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;
import ca.ubc.cs304.allegro.model.Item;
import ca.ubc.cs304.allegro.model.Stored;

public class TransactionService {
	private static final int NUM_CREDIT_CARD_DIGITS = 16;
	
	public static long sanitizeCardNum(String cardNum) throws IOException {
		if (cardNum != null) {
			cardNum = cardNum.trim();
			try {
				if (authorizeCredit(cardNum))
					return Long.parseLong(cardNum);
			} catch (NumberFormatException e) {
				throw new IOException(e);
			}
		}
		throw new IOException();
	}
	
	public static int sanitizeReceiptID(String receiptID) throws IOException {
		if (receiptID != null) {
			receiptID = receiptID.trim();
			try {
				return Integer.parseInt(receiptID);
			} catch (NumberFormatException e) {
				throw new IOException(e);
			}
		}
		throw new IOException();
	}
	
	public static double sanitizeMoney(String value) throws IOException {
		if (value != null) {
			if (value.equals(""))
				return(0.0);
			value = value.trim();
			try {
				return Double.parseDouble(value);
			} catch (Exception e) {
				throw new IOException(e);
			}
		}
		throw new IOException();
	}
	
	public static boolean authorizeCredit(String cardNum) {
		// Implement credit card company authorization here
		if (cardNum.length() == NUM_CREDIT_CARD_DIGITS)
			return true;
		return false;
	}
	
	public static int checkStock(Item item, String store) {
		return checkStock(item.getUpc(), store);
	}
	
	public static int checkStock(Integer upc, String store) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		List<String> shared = new ArrayList<String>();
		conditions.put("upc", upc);
		try {
			return ((Stored)JDBCManager.select(Table.Stored, conditions, shared).get(0)).getStock();
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static void updateStock(Integer upc, String store, Integer quantity) {
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("upc", upc);
		try {
			JDBCManager.update(Table.Stored, "stock", new Integer(quantity), conditions);
		} catch (Exception e) {
			
		}
	}
	
	public static void updateStock(Item item, String store, int quantity){
		updateStock(item.getUpc(), store, quantity);
	}
	
}
