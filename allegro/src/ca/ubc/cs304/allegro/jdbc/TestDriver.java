package ca.ubc.cs304.allegro.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class TestDriver {

	public static void main(String[] args) {
		Integer i = 0;
		Object o = i;
		System.out.println(i.getClass() == Integer.class);
		
		try {
			// Test for JDBCManager.insert()
			/*List<Object> parameters = new ArrayList<Object>();
			parameters.add(new Integer(12345623));
			parameters.add("Justin Beiber: My World 2.0");
			parameters.add("CD");
			parameters.add("Faggotry");
			parameters.add("Homo Records Inc.");
			parameters.add(new Integer(2010));
			parameters.add(new BigDecimal(8.99));
			
			JDBCManager.insert(Table.Item, parameters);*/
			
			// Test for JDBCManager.delete()
			/*Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("cid", "steve");
			JDBCManager.delete(Table.Customer, conditions);*/
			
			// Test for JDBCManager.select()
			ResultSet results = JDBCManager.select(Table.Customer);
			results.first();
			while(!results.isAfterLast()) {
				System.out.println(results.getString("cid") + "\t" + 
						results.getString("password") + " \t" +
						results.getString("name") + "\t" + 
						results.getString("address") + "\t" + 
						results.getString("phone"));
				results.next();
			}
			results.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
