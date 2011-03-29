package ca.ubc.cs304.allegro.jdbc;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class TestDriver {

	public static void main(String[] args) {
		Integer i = 0;
		Object o = i;
		System.out.println(i.getClass() == Integer.class);
		
		try {
			List<Object> parameters = new ArrayList<Object>();
			parameters.add(new Integer(12345623));
			parameters.add("Justin Beiber: My World 2.0");
			parameters.add("CD");
			parameters.add("Faggotry");
			parameters.add("Homo Records Inc.");
			parameters.add(new Integer(2010));
			parameters.add(new BigDecimal(8.99));
			
			JDBCManager.insert(Table.Item, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
