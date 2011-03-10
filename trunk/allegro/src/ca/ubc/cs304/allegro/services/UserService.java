package ca.ubc.cs304.allegro.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;

import ca.ubc.cs304.allegro.model.Profile;
import ca.ubc.cs304.allegro.model.ProfileManager;

public class UserService {
	
	public static Map<String, Object> initUserContext(ProfileManager profileManager) {
		Map<String, Object> model = new HashMap<String, Object>();
		Profile profile = profileManager.getProfile();
		profile.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		model.put("profile", profile);
		return model;
	}
	
}
