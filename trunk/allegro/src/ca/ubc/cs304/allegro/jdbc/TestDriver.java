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
		try {
			// Test for JDBCManager.select(Table, Map<String, Object>)
			Map<String, Object> conditions = new HashMap<String, Object>();
			List<String> shared = new ArrayList<String>();
			List<Table> tables = new ArrayList<Table>();
			tables.add(Table.Purchase);
			conditions.put("cid", null);
			List<AllegroItem> results = JDBCManager.search(tables, conditions, shared);
			
			for (AllegroItem result : results)
				System.out.println(result.toString() + "\n");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
