package ca.ubc.cs304.allegro.model;

public class ProfileManager {
	private Profile profile;
	
	public ProfileManager() {}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Profile getProfile() {
		return profile;
	}
	
	public void setManagerAccess(boolean access) {
		profile.setManager(access);
	}
	
	public void setClerkAccess(boolean access) {
		profile.setClerk(access);
	}
	
	public void setCustomerAccess(boolean access) {
		profile.setCustomer(access);
	}
	
	public void addToCart(Item item) {
		profile.addToCart(item);
	}
	
	public void removeFromCart(Item item) {
		profile.removeFromCart(item);
	}
	
}
