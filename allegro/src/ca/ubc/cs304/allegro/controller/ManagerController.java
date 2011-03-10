package ca.ubc.cs304.allegro.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ca.ubc.cs304.allegro.model.ProfileManager;
import ca.ubc.cs304.allegro.services.UserService;

@Controller
public class ManagerController {
	@Autowired
	private ProfileManager profileManager;
	
	@RequestMapping("/manager/suppliers")
	public ModelAndView suppliers() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		return new ModelAndView("suppliers", model);
	}
	
	@RequestMapping("/manager/reports")
	public ModelAndView reports() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		return new ModelAndView("reports", model);
	}
	
	@RequestMapping("/manager/shipments")
	public ModelAndView shipments() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		return new ModelAndView("shipments", model);
	}
}
