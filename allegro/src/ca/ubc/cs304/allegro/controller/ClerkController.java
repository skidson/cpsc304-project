package ca.ubc.cs304.allegro.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
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
			JDBCManager.insert(purchase);
			for (Item item : cart) {
				int inStock = TransactionService.checkQuantity(item, store);
				if (inStock < item.getQuantity()) {
					model.put("error", "Error: Current quantity of '" + item.getTitle() + "'(" + 
							item.getUpc() + ") is " + inStock);
					return new ModelAndView("checkout", model);
				}
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
		return new ModelAndView("refund", model);
	}
	@RequestMapping("/clerk/finalizeRefund")
	public ModelAndView finalizeRefund(@RequestParam("j_receiptID") int receiptID) {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		
		Calendar currentDate = Calendar.getInstance();
		Date date = new Date(currentDate.getTimeInMillis());
		conditions.put("receiptId", receiptID);
		try {
			Purchase purchase = (Purchase)(JDBCManager.select(Table.Purchase, conditions)).get(0);
			

			Date purchaseDate = purchase.getPurchaseDate();
			System.out.println(purchaseDate.toString());
			if((date.getTime() - purchaseDate.getTime()*1000*60*60*24) > 15){
				model.put("expired", true);
			}
			if(purchase.getCardNum() == null)
				model.put("type", "cash");
			else
				model.put("type", "credit");
			
		//FIXME COMPARE DATES, CALCULATE AMOUNT, SPEW SHIT
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("refund", model);
	}
	
}