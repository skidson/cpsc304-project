package ca.ubc.cs304.allegro.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class Item implements AllegroItem {
	public enum Type {CD, DVD};
	private enum Category {ROCK, POP, RAP, COUNTRY, CLASSICAL, NEW_AGE, INSTRUMENTAL};
	
	private Integer upc, year, quantity = 1, stock;
	private String title, company;
	private Double sellPrice;
	
	private Type type;
	private Category category;
	
	public Item() {}
	
	public Item(Integer upc, Integer year, String title, String company,
			Double sellPrice, Type type, Category category) {
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
		parameters.add(upc);
		parameters.add(title);
		if (type != null)
			parameters.add(type.toString());
		else
			parameters.add(null);
		if (category != null)
			parameters.add(category.toString().toLowerCase());
		else
			parameters.add(null);
		parameters.add(company);
		parameters.add(year);
		if (sellPrice != null)
			parameters.add(new BigDecimal(sellPrice));
		else
			parameters.add(null);
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

	public String getCategory() {
		String string = category.toString().toLowerCase();
		String first = string.charAt(0) + "";
		string.replaceFirst(first, first.toUpperCase());
		return string;
	}

	public void setCategory(String category) {
		this.category = Category.valueOf(category.replace(" ", "_").toUpperCase());
	}

	public Integer getUpc() {
		return upc;
	}

	public void setUpc(Integer upc) {
		this.upc = upc;
	}

	public Integer getYear() {
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

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
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
				"\nquantity: " + quantity);
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getStock() {
		return stock;
	}
	
}
