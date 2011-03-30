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

		if(!category.equals("all")){
			System.out.println("cat was  + " + category);
			hm.put("category", category);
		}
		if(!title.equals("")){
			System.out.println("title was not blank");
			hm.put("title", title);
		}
		if(!leadSinger.equals("")){
			System.out.println("leadSinger was not blanks");
			hm.put("leadSinger", leadSinger);
		}
		
		try {
			if(category.equals("all") && title.equals("") && leadSinger.equals("")){
				System.out.println("I EMPTY ONE");
				results = JDBCManager.select(Table.Item);
				results.toString();
			}else{
				System.out.println("I HAS PARAMS");
				results = JDBCManager.select(Table.Item, hm);
				results.toString();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(leadSinger);
		System.out.println(title);
		model.put("itemList", results);
		return new ModelAndView("searchResults", model);
	}
	@RequestMapping("/customer/updateCart")
	public ModelAndView updateCart(){
	Map<String, Object> model = UserService.initUserContext(profileManager);
	return new ModelAndView("cart", model);
	}
	
	@RequestMapping("/customer/cart")
	public ModelAndView shoppingCart() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		return new ModelAndView("cart", model);
	}
}
