package ca.ubc.cs304.allegro.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	public ModelAndView performSearch(@RequestParam("j_category") String category, @RequestParam("j_title") String title,
									@RequestParam("j_leadSinger") String leadSinger) {
		
		Map<String, Object> model = UserService.initUserContext(profileManager);
		HashMap<String,Object> hm = new HashMap<String,Object>();
		List<Item> results = new ArrayList<Item>();
		if(!category.equals("all")) hm.put("category", category);
		if(title != null) hm.put("title", title);
		if(leadSinger != null) hm.put("leadSinger", leadSinger);
		
		try {
			if(category.equals("all") && title == null && leadSinger == null){
				results = JDBCManager.select(Table.Item);
			}else{

				results = JDBCManager.select(Table.Item, hm);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.put("itemList", results);
		return new ModelAndView("search", model);
	}
	@RequestMapping("/customer/updateCart")
	public ModelAndView updateCart(@)	
	}
	
	@RequestMapping("/customer/cart")
	public ModelAndView shoppingCart() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		return new ModelAndView("cart", model);
	}
}
