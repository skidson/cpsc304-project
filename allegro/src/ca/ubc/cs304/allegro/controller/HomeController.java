package ca.ubc.cs304.allegro.controller;

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
	
	@RequestMapping("/index/completeRegistration")
	public ModelAndView completeRegistration(@RequestParam("j_name") String name, @RequestParam("j_username") String username,
								@RequestParam("j_address") String address, @RequestParam("j_phone") String phone, 
								@RequestParam("j_password") String password) {
		
		List<Object> parameters = new ArrayList<Object>();
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
		Map<String, Object> model = UserService.initUserContext(profileManager);
		return new ModelAndView("welcome", model);
	}
	
	@RequestMapping("/manager/home")
	public ModelAndView managerHome() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		UserService.setManagerAccess(true, model);
		UserService.setClerkAccess(false, model);
		UserService.setCustomerAccess(false, model);
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
