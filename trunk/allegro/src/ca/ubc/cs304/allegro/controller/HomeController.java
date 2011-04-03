package ca.ubc.cs304.allegro.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import ca.ubc.cs304.allegro.model.ProfileManager;
import ca.ubc.cs304.allegro.services.UserService;

@Controller
public class HomeController {
	@Autowired
	private ProfileManager profileManager;
	
	@RequestMapping("/index/welcome")
	public ModelAndView welcome() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		return new ModelAndView("welcome", model);
	}
	
	@RequestMapping("/index/register")
	public ModelAndView register() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		return new ModelAndView("register", model);
	}
	
	@RequestMapping("/index/debug")
	public ModelAndView debug() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		return new ModelAndView("debug", model);
	}
	
	@RequestMapping("/index/getTable")
	public ModelAndView getTable(@RequestParam("j_table") String table) {
		Map<String, Object> model = UserService.initUserContext(profileManager);

		Table fetch = JDBCManager.getTable(table);
		if(JDBCManager.getTable(table) == null){
			model.put("error", "Error : Could not find table");
			return new ModelAndView("debug", model);
		} else{
			try {
				model.put("rows", JDBCManager.select(JDBCManager.getTable(table)));
				model.put("val", true);
			} catch (SQLException e) {
				model.put("error", "Error : Could not fetch table");
				return new ModelAndView("debug", model);
			}
		}
		return new ModelAndView("debug", model);
	}
	
	@RequestMapping("/index/completeRegistration")
	public ModelAndView completeRegistration(@RequestParam("j_name") String name, @RequestParam("j_username") String username,
								@RequestParam("j_address") String address, @RequestParam("j_phone") String phone, 
								@RequestParam("j_password") String password) {
		
		List<Object> parameters = new ArrayList<Object>();
		Map<String, Object> model = UserService.initUserContext(profileManager);
		if(name.equals("")){
			model.put("error", "Error: name was null");
			return new ModelAndView("register", model);
		}
		if(username.equals("")){
			model.put("error", "Error: username was null");
			return new ModelAndView("register", model);
		}
		if(address.equals("")){
			model.put("error", "Error: address was null");
			return new ModelAndView("register", model);
		}
		try{
			Long.parseLong(phone);
		}catch(NumberFormatException e){
			model.put("error", "Error: Phone contained invalid characters");
			return new ModelAndView("register", model);
		}
		if(password.equals("")){
			model.put("error", "Error: password was null");
			return new ModelAndView("register", model);
		}
		
		parameters.add(username);
		parameters.add(password);
		parameters.add(name);
		parameters.add(address);
		parameters.add(Long.parseLong((phone)));
		try {
			JDBCManager.insert(Table.Customer, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ModelAndView("welcome", model);
	}
	
	@RequestMapping("/manager/home")
	public ModelAndView managerHome() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		UserService.setManagerAccess(true, model);
		UserService.setClerkAccess(false, model);
		UserService.setCustomerAccess(false, model);
		
		try {
			List<AllegroItem> suppliers = JDBCManager.select(Table.Supplier);
			model.put("suppliers", suppliers);
		} catch (SQLException e) {
			model.put("error", "Error: Could not load suppliers");
		}
		
		return new ModelAndView("suppliers", model);
	}
	
	@RequestMapping("/clerk/home")
	public ModelAndView clerkHome() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		UserService.setManagerAccess(false, model);
		UserService.setClerkAccess(true, model);
		UserService.setCustomerAccess(false, model);
		UserService.clearCart(model);
		return new ModelAndView("purchase", model);
	}
	
	@RequestMapping("/customer/home")
	public ModelAndView customerHome() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		UserService.setManagerAccess(false, model);
		UserService.setClerkAccess(false, model);
		UserService.setCustomerAccess(true, model);
		UserService.clearCart(model);
		return new ModelAndView("search", model);
	}
	
}
