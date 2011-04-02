package ca.ubc.cs304.allegro.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ca.ubc.cs304.allegro.jdbc.JDBCManager;
import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;
import ca.ubc.cs304.allegro.model.AllegroItem;
import ca.ubc.cs304.allegro.model.Item;
import ca.ubc.cs304.allegro.model.ProfileManager;
import ca.ubc.cs304.allegro.model.ShipItem;
import ca.ubc.cs304.allegro.model.Shipment;
import ca.ubc.cs304.allegro.model.Stored;
import ca.ubc.cs304.allegro.model.Supplier;
import ca.ubc.cs304.allegro.services.TransactionService;
import ca.ubc.cs304.allegro.services.UserService;

@Controller
public class ManagerController {
	@Autowired
	private ProfileManager profileManager;
	private static final int MAX_SHIPMENT_NUM = 10000000;
	private static final Integer ACTIVE = 0;
	
	@RequestMapping("/manager/suppliers")
	public ModelAndView suppliers() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		try {
			model.put("suppliers", JDBCManager.select(Table.Supplier));
		} catch (SQLException e) {
			model.put("error", "Error: Could not load suppliers");
		}
		return new ModelAndView("suppliers", model);
	}
	
	@RequestMapping("/manager/addSupplier")
	public ModelAndView addSupplier(@RequestParam("in_supname") String supName,
									@RequestParam("in_city") String city,
									@RequestParam("in_address") String address,
									@RequestParam("in_status") int status){
		Map<String, Object> model = UserService.initUserContext(profileManager);
		try {
			JDBCManager.insert(new Supplier(supName, address, city, status));
			List<AllegroItem> suppliers = JDBCManager.select(Table.Supplier);
			model.put("suppliers", suppliers);
		} catch (SQLException e) {
			model.put("error", "Error: Could not add supplier");
		}
		return new ModelAndView("suppliers", model);
	}
	
	@RequestMapping("/manager/removeSupplier")
	public ModelAndView removeSupplier(@RequestParam("supname") String supName) {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("supname", supName);
		try {
			JDBCManager.delete(Table.Supplier, conditions);
			List<AllegroItem> suppliers = JDBCManager.select(Table.Supplier);
			model.put("suppliers", suppliers);
		} catch (SQLException e) {
			model.put("error", "Error: Could not remove supplier");
		}
		return new ModelAndView("suppliers", model);
	}
	
	@RequestMapping("/manager/reports")
	public ModelAndView reports() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		try {
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("type", "store");
			model.put("stores", JDBCManager.select(Table.Store, conditions));
		} catch (SQLException e) {
			model.put("error", "Error: Could not load suppliers");
		}
		return new ModelAndView("reports", model);
	}
	
	@RequestMapping("/manager/storeReport")
	public ModelAndView generateReport(@RequestParam("in_sname") String sname,
			@RequestParam("in_year") Integer year,
			@RequestParam("in_month") Integer month,
			@RequestParam("in_day") Integer day) {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(year, month-1, day);
			
			List<Table> tables = new ArrayList<Table>();
			tables.add(Table.Item);
			tables.add(Table.PurchaseItem);
			tables.add(Table.Purchase);
			
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("sname", sname);
			conditions.put("Item.upc", "PurchaseItem.upc");
			conditions.put("Purchase.purchaseDate", new Date(calendar.getTimeInMillis()));
			List<AllegroItem> allItems = JDBCManager.select(tables, conditions, null);
			
			conditions.clear();
			conditions.put("type", "store");
			model.put("stores", JDBCManager.select(Table.Store, conditions));
			model.put("reportDate", year + "-" + month + "-" + day);
			
			if (allItems.isEmpty()) {
				model.put("error", "No purchases found");
				return new ModelAndView("reports", model);
			} else
				model.put("store", sname);
			
			// Group items by UPC and sum their quantities
			List<Item> items = new ArrayList<Item>();
			for(AllegroItem allItem : allItems) {
				Item item = (Item)allItem;
				for (Item processed : items) {
					if (processed.getUpc() == item.getUpc()) {
						item.setQuantity(item.getQuantity()+processed.getQuantity());
						items.remove(processed);
						break;
					}
				}
				items.add(item);
				System.out.println(item);
			}
			
			// Generate a list of grouped item lists
			List<List<Item>> groups = new ArrayList<List<Item>>();
			List<Item> group = new ArrayList<Item>();
			groups.add(group);
			group.add(items.remove(0));
			for (Item item : items) {
				boolean match = false;
				for (List<Item> groupList : groups) {
					if (item.getCategory().equalsIgnoreCase(groupList.get(0).getCategory())) {
						groupList.add(item);
						match = true;
						break;
					}
				}
				if (!match) {
					group.add(item);
					groups.add(group);
				}
			}
			model.put("groups", groups);
			
		} catch (SQLException e) {
			model.put("error", "Error: Could not generate report");
		}
		return new ModelAndView("reports", model);
	}
	
	@RequestMapping("/manager/shipments")
	public ModelAndView shipments() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		try {
			model.put("shipments", JDBCManager.select(Table.Shipment));
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("type", "store");
			model.put("stores", JDBCManager.select(Table.Store, conditions));
			conditions.clear();
			conditions.put("status", ACTIVE);
			model.put("suppliers", JDBCManager.select(Table.Supplier, conditions));
		} catch (SQLException e) {
			model.put("error", "Error: Could not load shipments");
		}
		return new ModelAndView("shipments", model);
	}
	
	// TODO
	@RequestMapping("/manager/createShipment")
	public ModelAndView createShipment(@RequestParam("in_supname") String supname,
			@RequestParam("in_sname") String sname,
			@RequestParam("in_year") int year,
			@RequestParam("in_month") int month,
			@RequestParam("in_day") int day) {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		Integer sid = (new Random()).nextInt(MAX_SHIPMENT_NUM);
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1, day);
		try {
			JDBCManager.insert(new Shipment(sid, supname, sname, new Date(calendar.getTimeInMillis())));
			model.put("shipments", JDBCManager.select(Table.Shipment));
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("status", 0);
			model.put("suppliers", JDBCManager.select(Table.Supplier, conditions));
			conditions.clear();
			conditions.put("type", "store");
			model.put("stores", JDBCManager.select(Table.Store, conditions));
			model.put("sid", sid);
		} catch (SQLException e) {
			model.put("error", "Error: Could not load shipments");
		}
		model.put("edit", true);
		return new ModelAndView("shipments", model);
	}
	
	@RequestMapping("/manager/addShipItem")
	public ModelAndView addShipItem(@RequestParam("in_sid") Integer sid,
			@RequestParam("in_upc") String upc,
			@RequestParam("in_supPrice") String supPrice,
			@RequestParam("in_quantity") String quantity) {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		
		try {
			JDBCManager.insert(new ShipItem(sid, TransactionService.sanitizeInt(upc),
					TransactionService.sanitizeMoney(supPrice),
					TransactionService.sanitizeInt(quantity)));
			Map<String, Object> conditions = new HashMap<String, Object>();
			
			model.put("shipments", JDBCManager.select(Table.Shipment));
			
			model.put("stores", JDBCManager.select(Table.Store, conditions));
			
			conditions.clear();
			conditions.put("status", ACTIVE);
			model.put("suppliers", JDBCManager.select(Table.Supplier, conditions));
			
			conditions.clear();
			conditions.put("sid", sid);
			model.put("shipItems", JDBCManager.select(Table.ShipItem, conditions));
			
		} catch (SQLException e) {
			model.put("error", "Error: Could not load shipments");
		} catch (IOException e) {
			model.put("error", "Error: Could not add item to shipment");
		}
		model.put("edit", true);
		model.put("sid", sid);
		return new ModelAndView("shipments", model);
	}
	
	@RequestMapping("/manager/viewShipment")
	public ModelAndView viewShipment(@RequestParam("sid") Integer sid) {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		try {
			model.put("shipments", JDBCManager.select(Table.Shipment));
			model.put("sid", sid);
			
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("sid", sid);
			model.put("shipItems", JDBCManager.select(Table.ShipItem, conditions));
			
			conditions.clear();
			conditions.put("status", ACTIVE);
			model.put("suppliers", JDBCManager.select(Table.Supplier, conditions));
			
			conditions.clear();
			model.put("stores", JDBCManager.select(Table.Store));
		} catch (SQLException e) {
			model.put("error", "Error: Could not load shipment");
		}
		return new ModelAndView("shipments", model);
	}
	
	@RequestMapping("/manager/receiveShipment")
	public ModelAndView receiveShipment(@RequestParam("sid") String sid) {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		try {
			model.put("shipments", JDBCManager.select(Table.Shipment));
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("sid", Integer.parseInt(sid));
			
			// Store shipment data locally
			Shipment shipment = (Shipment)JDBCManager.select(Table.Shipment, conditions).get(0);
			List<AllegroItem> shipItems = JDBCManager.select(Table.ShipItem, conditions);
			
			// Add shipItem quantities to store's stock
			for (AllegroItem rawItem : shipItems) {
				ShipItem shipItem = (ShipItem)rawItem;
				Integer stock = TransactionService.checkStock(shipItem.getUpc(), shipment.getSname());
				if (stock > 0) {
					TransactionService.updateStock(shipItem.getUpc(), shipment.getSname(),
							stock + shipItem.getQuantity());
				} else
					JDBCManager.insert(new Stored(shipment.getSname(), shipItem.getUpc(), shipItem.getQuantity()));
				
				// Ensure the item's sell price is atleast 20% greater than supplier's price
				conditions = new HashMap<String, Object>();
				conditions.put("upc", shipItem.getUpc());
				Item item = (Item)JDBCManager.select(Table.Item, conditions).get(0);
				if (item.getSellPrice() < shipItem.getSupPrice().doubleValue()*1.2)
					JDBCManager.update(Table.Item, "sellPrice", new BigDecimal(shipItem.getSupPrice().doubleValue()*1.2), conditions);
			}
			
			// Delete shipment data from database
			conditions = new HashMap<String, Object>();
			conditions.put("sid", Integer.parseInt(sid));
			JDBCManager.delete(Table.ShipItem, conditions);
			JDBCManager.delete(Table.Shipment, conditions);
			
			model.put("stores", JDBCManager.select(Table.Store));
			conditions.clear();
			conditions.put("status", ACTIVE);
			model.put("suppliers", JDBCManager.select(Table.Supplier, conditions));
			model.put("shipments", JDBCManager.select(Table.Shipment));
			model.put("message", "Shipment #" + shipment.getSid() + " successfully received");
			
		} catch (Exception e) {
			model.put("error", "Error: Could not receive shipment");
		}
		return new ModelAndView("shipments", model);
	}
	
}
