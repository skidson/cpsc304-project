package ca.ubc.cs304.allegro.controller;

import java.sql.SQLException;
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
			List<AllegroItem> suppliers = JDBCManager.select(Table.Supplier);
			model.put("suppliers", suppliers);
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
	public ModelAndView createShipment() {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		Integer sid = (new Random()).nextInt(MAX_SHIPMENT_NUM);
		model.put("sid", sid);
		
		/*Shipment shipment = new Shipment(sid, )
		
		try {
			model.put("shipments", JDBCManager.select(Table.Shipment));
			conditions.clear();
			conditions.put("status", 0);
			model.put("suppliers", JDBCManager.select(Table.Supplier, conditions));
		} catch (SQLException e) {
			model.put("error", "Error: Could not load shipments");
		}*/
		return new ModelAndView("shipments", model);
	}
	
	@RequestMapping("/manager/viewShipment")
	public ModelAndView viewShipment(@RequestParam("sid") String sid) {
		Map<String, Object> model = UserService.initUserContext(profileManager);
		try {
			model.put("shipments", JDBCManager.select(Table.Shipment));
			model.put("sid", sid);
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("sid", Integer.parseInt(sid));
			model.put("shipItems", JDBCManager.select(Table.ShipItem, conditions));
			conditions.clear();
			conditions.put("status", ACTIVE);
			model.put("suppliers", JDBCManager.select(Table.Supplier, conditions));
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
				if (item.getSellPrice() < shipItem.getSupPrice()*1.2)
					JDBCManager.update(Table.Item, "sellPrice", (shipItem.getSupPrice()*1.2), conditions);
			}
			
			// Delete shipment data from database
			conditions = new HashMap<String, Object>();
			conditions.put("sid", Integer.parseInt(sid));
			JDBCManager.delete(Table.ShipItem, conditions);
			JDBCManager.delete(Table.Shipment, conditions);
			
			model.put("shipments", JDBCManager.select(Table.Shipment));
			model.put("message", "Shipment #" + shipment.getSid() + " successfully received");
			
		} catch (Exception e) {
			model.put("error", "Error: Could not receive shipment");
		}
		return new ModelAndView("shipments", model);
	}
	
}
