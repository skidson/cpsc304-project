package ca.ubc.cs304.allegro.services;

import java.util.HashMap;
import java.util.List;
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
	
	public static List<Item> getShoppingCart(Map<String, Object> model) {
		return getProfile(model).getShoppingCart();
	}
	
	public static void removeFromCart(int index, Map<String, Object> model) {
		Profile profile = getProfile(model);
		profile.removeFromCart(index);
		model.put("profile", profile);
	}
	
	public static void addToCart(Item item, Map<String, Object> model) {
		Profile profile = getProfile(model);
		profile.addToCart(item);
		model.put("profile", profile);
	}
	
	public static void clearCart(Map<String, Object> model) {
		Profile profile = getProfile(model);
		profile.clearCart();
		model.put("profile", profile);
	}
	
	public static void clearCart(ProfileManager manager, Map<String, Object> model) {
		Profile profile = manager.getProfile();
		profile.clearCart();
		model.put("profile", profile);
	}
	
	public static void updateQuantity(Map<String, Object> model, int quantity, Item item){
		Profile profile = getProfile(model);
		profile.updateQuantity(quantity, item);
		model.put("profile", profile);
	}
	
	public static void setManagerAccess(boolean access, Map<String, Object> model) {
		Profile profile = getProfile(model);
		profile.setManager(access);
		model.put("profile", profile);
	}
	
	public static void setClerkAccess(boolean access, Map<String, Object> model) {
		Profile profile = getProfile(model);
		profile.setClerk(access);
		model.put("profile", profile);
	}
	
	public static void setCustomerAccess(boolean access, Map<String, Object> model) {
		Profile profile = getProfile(model);
		profile.setCustomer(access);
		model.put("profile", profile);
	}
	
	public void setProfileManager(ProfileManager profileManager) {
		manager = profileManager;
	}
	
	private static Profile getProfile(Map<String, Object> model) {
		Profile profile = null;
		try {
			profile = (Profile) model.get("profile");
		} catch (Exception e) {
			model.putAll(initUserContext(manager));
			profile = (Profile) model.get("profile");
		}
		return profile;
	}
	
}
