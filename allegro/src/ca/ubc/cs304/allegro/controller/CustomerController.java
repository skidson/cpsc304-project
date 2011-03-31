package ca.ubc.cs304.allegro.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
									@RequestParam(value="j_leadSinger", required=false) String leadSinger) {
		
		Map<String, Object> model = UserService.initUserContext(profileManager);
		HashMap<String,Object> hm = new HashMap<String,Object>();
		List<AllegroItem> results = new ArrayList<AllegroItem>();

		if(!category.equals("all"))
			hm.put("category", category);
		
		if(!title.equals(""))
			hm.put("title", title);
		
		if(!leadSinger.equals(""))
			hm.put("name", leadSinger);
		
		try {
			if(category.equals("all") && title.equals("") && leadSinger.equals("")){
				results = JDBCManager.select(Table.Item);
			}else if(!leadSinger.equals("")){
				List<Table> tables = new ArrayList<Table>();
				List<String> shared = new ArrayList<String>();
				shared.add("upc");
				tables.add(Table.Item);
				tables.add(Table.LeadSinger);
				results = JDBCManager.select(tables, hm, shared);
			}else{
				results = JDBCManager.select(Table.Item, hm);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public ModelAndView checkout(@RequestParam("totalPrice") float price){
		Map<String, Object> model = UserService.initUserContext(profileManager);
		boolean checkout = true;
		model.put("checkout", checkout);
		model.put("finalPrice", price);
		return new ModelAndView("cart", model);
	}
	
	@RequestMapping("customer/removeItem")
	public ModelAndView removeItem(@RequestParam("upc") int upc, @RequestParam("index") int index){
		Map<String, Object> model = UserService.initUserContext(profileManager);
		UserService.removeFromCart(index, model);
		
		return new ModelAndView("redirect:/customer/cart", model);
	}
	
	@RequestMapping("customer/finalize")
	public ModelAndView finalizePurchase(@RequestParam("j_cardnum") long cardnum, 
										@RequestParam("j_expYear") int year,
										@RequestParam("j_expMonth") int month){
		Map<String, Object> model = UserService.initUserContext(profileManager);
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);
		cal.getTimeInMillis();
		return new ModelAndView("receipt", model);
	}
}
