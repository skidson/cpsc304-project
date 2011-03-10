package ca.ubc.cs304.allegro.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import ca.ubc.cs304.allegro.model.ProfileManager;
import ca.ubc.cs304.allegro.services.UserService;

@Controller
public class HomeController {
	@Autowired
	private ProfileManager profileManager;
	
	@RequestMapping("/basic/home")
	public ModelAndView basicHome() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		return new ModelAndView("home", model);
	}
	
}
