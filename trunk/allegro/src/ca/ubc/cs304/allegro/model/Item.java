package ca.ubc.cs304.allegro.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class Item implements AllegroItem {
	public enum Type {CD, DVD};
	private enum Category {ROCK, POP, RAP, COUNTRY, CLASSICAL, NEW_AGE, INSTRUMENTAL};
	
	private int upc, year, quantity = 1;
	private String title, company;
	private float sellPrice;
	
	private Type type;
	private Category category;
	
	public Item() {}
	
	public Item(int upc, int year, String title, String company,
			float sellPrice, Type type, Category category) {
		super();
		this.upc = upc;
		this.year = year;
		this.title = title;
		this.company = company;
		this.sellPrice = sellPrice;
		this.type = type;
		this.category = category;
	}

	public List<Object> getParameters() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(new Integer(upc));
		parameters.add(title);
		parameters.add(type.toString());
		parameters.add(category.toString().toLowerCase());
		parameters.add(company);
		parameters.add(new Integer(year));
		parameters.add(new BigDecimal(sellPrice));
		return parameters;
	}

	public Table getTable() {
		return Table.Item;
	}

	public Type getType() {
		return type;
	}

	public void setType(String type) {
		this.type = Type.valueOf(type.replace(" ", "_").toUpperCase());
	}

	/*public String getCategory() {
		String string = category.toString().toLowerCase();
		String first = string.charAt(0);
		string.replaceFirst(string.charAt(0), string.charAt(0))
		return string;
	}*/

	public void setCategory(String category) {
		this.category = Category.valueOf(category.replace(" ", "_").toUpperCase());
	}

	public int getUpc() {
		return upc;
	}

	public void setUpc(Integer upc) {
		this.upc = upc;
	}

	public int getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public float getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Float sellPrice) {
		this.sellPrice = sellPrice;
	}
	
	public String toString() {
		return ("upc: " + upc + 
				"\ntitle: " + title + 
				"\ntype: " + type + 
				"\ncategory: " + category + 
				"\ncompany: " + company +
				"\nyear: " + year + 
				"\nsellPrice: " + sellPrice +
				"\nQuantity: " + quantity);
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}
	
}
