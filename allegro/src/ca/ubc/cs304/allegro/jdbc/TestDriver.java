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
			
			conditions.put("status", new Integer(0));
			
			List<AllegroItem> results = JDBCManager.select(Table.Supplier, conditions);
			for (AllegroItem result : results)
				System.out.println(result.toString() + "\n");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
