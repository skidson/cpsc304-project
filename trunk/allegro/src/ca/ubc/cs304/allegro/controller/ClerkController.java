package ca.ubc.cs304.allegro.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ca.ubc.cs304.allegro.jdbc.JDBCManager;
import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;
import ca.ubc.cs304.allegro.model.Item;
import ca.ubc.cs304.allegro.model.ProfileManager;
import ca.ubc.cs304.allegro.services.UserService;

@Controller
public class ClerkController {
	@Autowired
	private ProfileManager profileManager;
	
	@RequestMapping("/clerk/purchase")
	public ModelAndView purchase() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		return new ModelAndView("purchase", model);
	}
	
	@RequestMapping("/clerk/addPurchase")
	public ModelAndView addPurchase(@RequestParam("in_upc") String upc, @RequestParam("in_qty") String qty) {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		if (!upc.equals("") && !qty.equals("")) {
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("upc", Integer.parseInt(upc));
			try {
				Item item = (Item)JDBCManager.select(Table.Item, conditions).get(0);
				item.setQuantity(Integer.parseInt(qty));
				UserService.addToCart(item, model);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("purchase", model);
	}
	
	@RequestMapping("/clerk/refund")
	public ModelAndView refund() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		return new ModelAndView("refund", model);
	}
}