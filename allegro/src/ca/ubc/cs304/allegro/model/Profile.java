package ca.ubc.cs304.allegro.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Profile implements Serializable {
	private static final long serialVersionUID = -799432430115623170L;
	private boolean manager = false;
	private boolean clerk = false;
	private boolean customer = false;
	private String username;
	private List<Item> shoppingCart = new ArrayList<Item>();
	
	public Profile() {}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Item> getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(List<Item> shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public void addToCart(Item item){
		if(shoppingCart.size() == 0)
			shoppingCart.add(item);
		else{
			for(Item cartItem : shoppingCart){
				if(cartItem.getUpc().equals(item.getUpc()))
					cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
				else
					shoppingCart.add(item);
			}
		}
		
	}
	
	public void clearCart(){
		shoppingCart = new ArrayList<Item>();
	}
	
	public void removeFromCart(int index) {
		shoppingCart.remove(index);
	}

	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean access) {
		this.manager = access;
	}

	public boolean isClerk() {
		return clerk;
	}

	public void setClerk(boolean access) {
		this.clerk = access;
	}

	public boolean isCustomer() {
		return customer;
	}

	public void setCustomer(boolean access) {
		this.customer = access;
	}
	
	public int getPermissionLevel() {
		int level = 0;
		if (clerk)
			level++;
		if(manager)
			level++;
		return level;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
