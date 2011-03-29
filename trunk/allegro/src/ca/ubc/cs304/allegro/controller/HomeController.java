package ca.ubc.cs304.allegro.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ca.ubc.cs304.allegro.jdbc.JDBCManager;
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
	
	@RequestMapping("/index/test")
	public ModelAndView test() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		JDBCManager database = new JDBCManager();
		try {
			database.show_item();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ModelAndView("welcome", model);
	}
	
	
	@RequestMapping("/manager/home")
	public ModelAndView managerHome() {
		profileManager.setManagerAccess(true);
		profileManager.setClerkAccess(false);
		profileManager.setCustomerAccess(false);
		Map<String, Object> model = UserService.initUserContext(profileManager);
		// TODO duplicate supplyController behaviour here
		
		return new ModelAndView("suppliers", model);
	}
	
	@RequestMapping("/clerk/home")
	public ModelAndView clerkHome() {
		profileManager.setManagerAccess(false);
		profileManager.setClerkAccess(true);
		profileManager.setCustomerAccess(false);
		Map<String, Object> model = UserService.initUserContext(profileManager);
		
		// TODO duplicate purchaseController behaviour here
		
		return new ModelAndView("purchase", model);
	}
	
	@RequestMapping("/customer/home")
	public ModelAndView customerHome() {
		profileManager.setManagerAccess(false);
		profileManager.setClerkAccess(false);
		profileManager.setCustomerAccess(true);
		Map<String, Object> model = UserService.initUserContext(profileManager);
		
		// TODO duplicate searchController behaviour here
		
		return new ModelAndView("search", model);
	}
	
}
