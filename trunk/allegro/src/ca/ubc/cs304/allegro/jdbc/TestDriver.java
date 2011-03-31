package ca.ubc.cs304.allegro.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;
import ca.ubc.cs304.allegro.model.AllegroItem;

public class TestDriver {

	public static void main(String[] args) {
		Integer i = 0;
		Object o = i;
		System.out.println(i.getClass() == Integer.class);
		
		try {
			// Test for JDBCManager.select(Table, Map<String, Object>)
			/*Map<String, Object> conditions = new HashMap<String, Object>();
			List<String> shared = new ArrayList<String>();
			List<Table> tables = new ArrayList<Table>();
			tables.add(Table.Item);
			tables.add(Table.LeadSinger);
			shared.add("upc");
			conditions.put("name", "Spice Girls");
			List<AllegroItem> results = JDBCManager.select(tables, conditions, shared);*/
			
			List<AllegroItem> results = JDBCManager.select(Table.Item);
			
			for (AllegroItem result : results)
				System.out.println(result.toString() + "\n");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
