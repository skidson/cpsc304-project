package ca.ubc.cs304.allegro.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;

import ca.ubc.cs304.allegro.model.Item;
import ca.ubc.cs304.allegro.model.Profile;
import ca.ubc.cs304.allegro.model.ProfileManager;

public class UserService {
	private static ProfileManager manager;
	
	public static Map<String, Object> initUserContext(ProfileManager profileManager) {
		Map<String, Object> model = new HashMap<String, Object>();
		manager = profileManager;
		Profile profile = manager.getProfile();
		profile.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		model.put("profile", profile);
		return model;
	}
	
	public static void removeFromCart(int index, Map<String, Object> model) {
		Profile profile = manager.getProfile();
		profile.removeFromCart(index);
		model.put("profile", profile);
	}
	
	public static void addToCart(Item item, Map<String, Object> model) {
		Profile profile = manager.getProfile();
		profile.addToCart(item);
		model.put("profile", profile);
	}
	
}
