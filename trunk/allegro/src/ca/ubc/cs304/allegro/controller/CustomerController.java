package ca.ubc.cs304.allegro.controller;

import java.sql.Date;
import java.sql.ResultSet;
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
import ca.ubc.cs304.allegro.model.LeadSinger;
import ca.ubc.cs304.allegro.model.ProfileManager;
import ca.ubc.cs304.allegro.model.Purchase;
import ca.ubc.cs304.allegro.model.PurchaseItem;
import ca.ubc.cs304.allegro.services.TransactionService;
import ca.ubc.cs304.allegro.services.UserService;

@Controller
public class CustomerController {
	@Autowired
	private ProfileManager profileManager;
	
	@RequestMapping("/customer/search")
	public ModelAndView search() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		return new ModelAndView("search", model);
	}
	
	@RequestMapping("/customer/performSearch")
	public ModelAndView performSearch(@RequestParam("j_category") String category, @RequestParam(value="j_title", required=false) String title,
									@RequestParam(value="j_leadSinger", required=false) String leadSinger,
									@RequestParam(value="j_songName", required=false) String songName) {
		
		Map<String, Object> model = UserService.initUserContext(profileManager);
		HashMap<String,Object> hm = new HashMap<String,Object>();
		List<AllegroItem> results = new ArrayList<AllegroItem>();
		System.out.println(category);
		System.out.println(title);
		System.out.println(songName);
		System.out.println(leadSinger);
		
		if(!category.equals("All"))
			hm.put("category", category);
		
		if(!title.equals(""))
			hm.put("title", title);
		
		if(!leadSinger.equals(""))
			hm.put("name", leadSinger);
		
		if(!songName.equals(""))
			hm.put("HasSong.title", songName);
		
		try {
			if(category.equals("All") && title.equals("") && leadSinger.equals("") && songName.equals("")){
				results = JDBCManager.select(Table.Item);
			}else if(!leadSinger.equals("") && !songName.equals("")){
				List<Table> tables = new ArrayList<Table>();
				List<String> shared = new ArrayList<String>();
				shared.add("upc");
				tables.add(Table.Item);
				tables.add(Table.LeadSinger);
				tables.add(Table.HasSong);
				results = JDBCManager.search(tables, hm, shared);
			}else if(!leadSinger.equals("")){
				List<Table> tables = new ArrayList<Table>();
				List<String> shared = new ArrayList<String>();
				shared.add("upc");
				tables.add(Table.Item);
				tables.add(Table.LeadSinger);
				results = JDBCManager.search(tables, hm, shared);
			}else if(!songName.equals("")){
				List<Table> tables = new ArrayList<Table>();
				List<String> shared = new ArrayList<String>();
				List<String> groupBy = new ArrayList<String>();
				groupBy.add("upc");
				shared.add("upc");
				tables.add(Table.Item);
				tables.add(Table.HasSong);
				results = JDBCManager.search(tables, hm, shared, groupBy);
			}else{
				results = JDBCManager.search(Table.Item, hm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(results.toString());
		model.put("itemList", results);
		return new ModelAndView("searchResults", model);
	}
	@RequestMapping("/customer/updateCart")
	public ModelAndView updateCart(@RequestParam("j_quantity") int quantity, @RequestParam("upc") int upc){
	Map<String, Object> model = UserService.initUserContext(profileManager);
	HashMap<String,Object> params = new HashMap<String, Object>();
	params.put("upc", upc);
	try {
		Item item = (Item)(JDBCManager.select(Table.Item, params).get(0));
		item.setQuantity(quantity);
		UserService.addToCart(item, model);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return new ModelAndView("cart", model);
	}
	
	@RequestMapping("/customer/cart")
	public ModelAndView shoppingCart() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		return new ModelAndView("cart", model);
	}
	
	@RequestMapping("/customer/checkout")
	public ModelAndView checkout(){
		Map<String, Object> model = UserService.initUserContext(profileManager);
		
		List<Item> cart = UserService.getShoppingCart(model);
		for (Item item : cart) {
			int inStock = TransactionService.checkQuantity(item, "Fraser Highway");
			if (inStock < item.getQuantity()) {
				if(inStock > 0){
					UserService.updateQuantity(model, inStock, item);
					model.put("error", "Error: Current quantity of '" + item.getTitle() + "'(" + 
							item.getUpc() + ") is " + inStock + ". You have been giving all the remaining quantity");
					boolean checkout = true;
					model.put("checkout", checkout);
					return new ModelAndView("cart", model);
				}
				model.put("error", "Error: Current quantity of '" + item.getTitle() + "'(" + 
						item.getUpc() + ") is " + inStock);
				return new ModelAndView("cart", model);
			}
			
		
		}
		
		boolean checkout = true;
		model.put("checkout", checkout);
		return new ModelAndView("cart", model);
	}
	
	@RequestMapping("customer/removeItem")
	public ModelAndView removeItem(@RequestParam("index") int index){
		Map<String, Object> model = UserService.initUserContext(profileManager);
		UserService.removeFromCart(index, model);
		
		return new ModelAndView("redirect:/customer/cart", model);
	}
	
	@RequestMapping("customer/finalize")
	public ModelAndView finalizePurchase(@RequestParam("j_cardnum") long cardnum, 
										@RequestParam("j_expYear") int year,
										@RequestParam("j_expMonth") int month){
		Map<String, Object> model = UserService.initUserContext(profileManager);
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		int purchasesProcessedPerDay = 3;
		Calendar cal = Calendar.getInstance();
		Calendar expectedDate = Calendar.getInstance();
		int receiptID =  new Random().nextInt(1000000);
		try{
			conditions.put("deliveredDate", null);
			conditions.put("sname", "Warehouse");
			List<AllegroItem> purchaseResults = JDBCManager.select(Table.Purchase, conditions);
			int outstandingDeliveries = purchaseResults.size();
			expectedDate.setTimeInMillis(expectedDate.getTimeInMillis() +(outstandingDeliveries/purchasesProcessedPerDay)*1000*60*60*24);
			cal.set(year, month, 1);
			Purchase purchase = new Purchase(receiptID, cardnum, new Date(cal.getTimeInMillis())
					, new Date(System.currentTimeMillis()), new Date(expectedDate.getTimeInMillis()), null, profileManager.getProfile().getUsername(), "Fraser Highway");
			JDBCManager.insert(purchase);
		} catch (SQLException e){
			e.printStackTrace();
		}
		
		

		List<Item> cart = profileManager.getProfile().getShoppingCart();
		
		for(int i = 0; i < cart.size(); i++){
			PurchaseItem items = new PurchaseItem(receiptID, cart.get(i).getUpc(), cart.get(i).getQuantity());
			try {
				JDBCManager.insert(items);
				TransactionService.updateStock(cart.get(i), "Fraser HighWay", (TransactionService.checkQuantity(cart.get(i), "Fraser HighWay") - cart.get(i).getQuantity()));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		UserService.clearCart(model);
		model.put("date", expectedDate.getTime().toString());
		return new ModelAndView("echeckout", model);
	}
	
	@RequestMapping("/customer/item")
	public ModelAndView getItemPage(@RequestParam("upc") int upc){
		Map<String, Object> model = UserService.initUserContext(profileManager);
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		
		conditions.put("upc", upc);
		
		try {
			Item item = (Item) JDBCManager.select(Table.Item, conditions).get(0);
			LeadSinger ls = (LeadSinger) JDBCManager.select(Table.LeadSinger, conditions).get(0);
			List<AllegroItem> songs = JDBCManager.select(Table.HasSong, conditions);
			System.out.println(item.toString());
			System.out.println(ls.toString());
			System.out.println(songs.toString());
			
			model.put("item", item);
			model.put("leadSinger", ls);
			model.put("songs", songs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ModelAndView("itemPage", model);
	}
}
