package ca.ubc.cs304.allegro.jdbc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;
import ca.ubc.cs304.allegro.model.AllegroItem;
import ca.ubc.cs304.allegro.model.Stored;
import ca.ubc.cs304.allegro.services.TransactionService;

public class TestDriver {

	public static void main(String[] args) {
		try {
			// Test for JDBCManager.select(Table, Map<String, Object>)
			Map<String, Object> conditions = new HashMap<String, Object>();
			List<String> shared = new ArrayList<String>();
			List<String> group = new ArrayList<String>();
			List<Table> tables = new ArrayList<Table>();
			
			conditions.put("sname", "Granville");
			conditions.put("upc", 100000002);
			Integer stock = ((Stored)JDBCManager.select(Table.Stored, conditions)
					.get(0)).getStock();
			
			List<AllegroItem> results = JDBCManager.select(Table.ShipItem, conditions);
			for (AllegroItem result : results)
				System.out.println(result.toString() + "\n");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
