package ca.ubc.cs304.allegro.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ca.ubc.cs304.allegro.model.ProfileManager;
import ca.ubc.cs304.allegro.services.UserService;

@Controller
public class HomeController {
	@Autowired
	private ProfileManager profileManager;
	
	@RequestMapping("/index/home")
	public ModelAndView home(@RequestParam("role") String role) {
		if (role.equals("manager"))
			profileManager.setManagerAccess(true);
		if (role.equals("clerk"))
			profileManager.setClerkAccess(true);
		if (role.equals("customer"))
			profileManager.setCustomerAccess(true);
		
		switch(profileManager.getProfile().getPermissionLevel()) {
		case 1:
			return clerkHome();
		case 2:
			return managerHome();
		default:
			return customerHome();
		}
	}
	
	@RequestMapping("/manager/home")
	public ModelAndView managerHome() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		
		// TODO duplicate supplyController behaviour here
		
		return new ModelAndView("suppliers", model);
	}
	
	@RequestMapping("/clerk/home")
	public ModelAndView clerkHome() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		
		// TODO duplicate purchaseController behaviour here
		
		return new ModelAndView("purchase", model);
	}
	
	@RequestMapping("/customer/home")
	public ModelAndView customerHome() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		
		// TODO duplicate searchController behaviour here
		
		return new ModelAndView("search", model);
	}
	
}
