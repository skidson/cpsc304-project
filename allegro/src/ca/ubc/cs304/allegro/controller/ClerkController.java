package ca.ubc.cs304.allegro.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ca.ubc.cs304.allegro.jdbc.JDBCManager;
import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;
import ca.ubc.cs304.allegro.model.AllegroItem;
import ca.ubc.cs304.allegro.model.Item;
import ca.ubc.cs304.allegro.model.ProfileManager;
import ca.ubc.cs304.allegro.model.Purchase;
import ca.ubc.cs304.allegro.model.PurchaseItem;
import ca.ubc.cs304.allegro.model.Refund;
import ca.ubc.cs304.allegro.model.RefundItem;
import ca.ubc.cs304.allegro.services.TransactionService;
import ca.ubc.cs304.allegro.services.UserService;

@Controller
public class ClerkController {
	@Autowired
	private ProfileManager profileManager;
	public static final int RECEIPT_ID_MAX = 1000000;
	private static final String CREDIT = "CREDIT", CASH = "CASH";
	
	@RequestMapping("/clerk/purchase")
	public ModelAndView purchase() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		return new ModelAndView("purchase", model);
	}
	
	@RequestMapping("/clerk/removePurchase")
	public ModelAndView removePurchase(@RequestParam("index") int index) {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		try {
			UserService.removeFromCart(index, model);
		} catch (Exception e) {}
		return new ModelAndView("purchase", model);
	}
	
	@RequestMapping("/clerk/checkout")
	public ModelAndView checkout() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		try {
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("type", "store");
			List<AllegroItem> stores = JDBCManager.select(Table.Store, conditions);
			model.put("stores", stores);
		} catch (SQLException e) {
			model.put("error", "Error: Could not access store list");
		}
		return new ModelAndView("checkout", model);
	}
	
	@RequestMapping("/clerk/finalize")
	public ModelAndView finalize(@RequestParam("method") String method,
			@RequestParam(value = "in_cash", required = false) String cash, 
			@RequestParam(value = "in_store", required = false) String store,
			@RequestParam(value = "in_cardNum", required = false) String cardNum,
			@RequestParam(value = "in_expYear", required = false) int expYear,
			@RequestParam(value = "in_expMonth", required = false) int expMonth) {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		List<Item> cart = UserService.getShoppingCart(model);
		
		try {
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("type", "store");
			List<AllegroItem> stores = JDBCManager.select(Table.Store, conditions);
			model.put("stores", stores);
		} catch (SQLException e) {
			model.put("error", "Error: Could not access store list");
		}
		
		// Persist unused text field entries
		if (cash != null)
			model.put("cash", cash);
		
		double total = 0;
		for (Item item : cart)
			total += item.getSellPrice() * ((double)item.getQuantity());
		
		try {
			if (method.trim().equalsIgnoreCase(CASH) && TransactionService.sanitizeMoney(cash) < total) {
				model.put("error", "Error: Tender is less than balance");
				return new ModelAndView("checkout", model);
			}
		} catch (IOException e) {
			model.put("error", "Error: Invalid cash amount");
			model.put("cash", cash);
			return new ModelAndView("checkout", model);
		}
		
		Date date = new Date(System.currentTimeMillis());
		int receiptId = (new Random()).nextInt(RECEIPT_ID_MAX);
		
		Purchase purchase = new Purchase(receiptId,
				null, null, date, null, date, null, store);
		
		if (method.trim().equalsIgnoreCase(CREDIT)) {
			try {
				purchase.setCardNum(TransactionService.sanitizeCardNum(cardNum));
			} catch (IOException e) {
				model.put("error", "Error: Invalid Card #");
				return new ModelAndView("checkout", model);
			}
			Calendar expiry = Calendar.getInstance();
			expiry.set(expYear, expMonth, 1);
			purchase.setExpire(new Date(expiry.getTimeInMillis()));
		}
		
		try {
			for (Item item : cart) {
				int inStock = TransactionService.checkStock(item, store);
				if (inStock < item.getQuantity()) {
					model.put("error", "Error: Current quantity of '" + item.getTitle() + "'(" + 
							item.getUpc() + ") is " + inStock);
					return new ModelAndView("checkout", model);
				}
			}
			
			// Store the purchase and its items
			JDBCManager.insert(purchase);
			for (Item item : cart) {
				JDBCManager.insert(new PurchaseItem(receiptId, item.getUpc(), 
						item.getQuantity()));
			}
			
		} catch (SQLException e) {
			model.put("error", "Error: Error processing transaction");
			return new ModelAndView("checkout", model);
		}
		
		if (method.trim().equalsIgnoreCase(CASH))
			model.put("paid", cash);
		else {
			String hiddenCardNum = purchase.getCardNum().toString();
			hiddenCardNum = hiddenCardNum.substring(hiddenCardNum.length()-5);
			purchase.setCardNum(Long.parseLong(hiddenCardNum));
		}
		
		model.put("purchase", purchase);
		model.put("items", cart);
		UserService.clearCart(model);
		return new ModelAndView("receipt", model);
	}
	
	@RequestMapping("/clerk/addPurchase")
	public ModelAndView addPurchase(@RequestParam("in_upc") String upc, @RequestParam("in_qty") String qty) {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		if (!upc.equals("") && !qty.equals("")) {
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("upc", Integer.parseInt(upc.trim()));
			try {
				Item item = (Item)JDBCManager.select(Table.Item, conditions).get(0);
				item.setQuantity(Integer.parseInt(qty.trim()));
				UserService.addToCart(item, model);
			} catch (Exception e) {}
		}
		return new ModelAndView("purchase", model);
	}
	
	
	@RequestMapping("/clerk/refund")
	public ModelAndView refund(){
		Map<String, Object> model = UserService.initUserContext(profileManager);
		model.put("basic", true);
		try {
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("type", "store");
			List<AllegroItem> stores = JDBCManager.select(Table.Store, conditions);
			model.put("stores", stores);
		} catch (SQLException e) {
			model.put("error", "Error: Could not access store list");
		}
		return new ModelAndView("refund", model);
	}
	@RequestMapping("/clerk/finalizeRefund")
	public ModelAndView finalizeRefund(@RequestParam("j_receiptID") String in_receiptID,
										@RequestParam("j_store") String sname) {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		
		int receiptID = 0;
		try {
			receiptID = TransactionService.sanitizeReceiptID(in_receiptID);
		} catch (IOException e1) {
			model.put("error", "Invalid receiptID enetered");
			return new ModelAndView("refund", model);
		}
		
		Calendar currentDate = Calendar.getInstance();
		Date date = new Date(currentDate.getTimeInMillis());
		conditions.put("receiptId", receiptID);
		try {
			Purchase purchase = (Purchase)(JDBCManager.select(Table.Purchase, conditions)).get(0);
			if(purchase == null){
				model.put("error", "Sorry, no purchase was found with the receiptID entered");
				return new ModelAndView("refund", model);
			}
			List<AllegroItem> purchaseItems = JDBCManager.select(Table.PurchaseItem, conditions);

			Date purchaseDate = purchase.getPurchaseDate();
			if((date.getTime() - purchaseDate.getTime()*1000*60*60*24) > 15) {
				model.put("error", "Sorry, your purchase is more than 15 days old and can no longer be returned!");
				return new ModelAndView("refund", model);
			}
			if(purchase.getCardNum() == null)
				model.put("type", "cash");
			else
				model.put("type", "credit");
			double totalPrice = 0;
			conditions.clear();
			
			for(AllegroItem item : purchaseItems){
				conditions.put("upc", item.getParameters().get(1));
				Item dbItem = (Item)JDBCManager.select(Table.Item, conditions).get(0);
				totalPrice+= dbItem.getSellPrice() * (Integer)item.getParameters().get(2);
				conditions.clear();
			}
			model.put("totalPrice", totalPrice);
			
			int retid = new Random().nextInt(RECEIPT_ID_MAX);
			JDBCManager.insert(new Refund(new Integer(retid), new Integer(receiptID), new Date(Calendar.getInstance().getTimeInMillis()), sname));
			model.put("retID", retid);
			
			List<Item> returnedItems = new ArrayList<Item>();
			for(AllegroItem item : purchaseItems){
				conditions.put("upc", item.getParameters().get(1));
				Item dbItem = (Item)JDBCManager.select(Table.Item, conditions).get(0);
				returnedItems.add(dbItem);
				JDBCManager.insert(new RefundItem(new Integer(retid), dbItem.getUpc(), (Integer)item.getParameters().get(2)));
				
				TransactionService.updateStock(dbItem, sname, (TransactionService.checkStock(dbItem, "sname") + (Integer)item.getParameters().get(2)));
				conditions.clear();
			}
			model.put("items", returnedItems);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		model.put("basic", false);
		return new ModelAndView("refund", model);
	}
	
}