package ca.ubc.cs304.allegro.jdbc;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
			List<String> group = new ArrayList<String>();
			List<Table> tables = new ArrayList<Table>();
			Calendar calendar = Calendar.getInstance();
			calendar.set(2011, 4, 1);
			tables.add(Table.Item);
			tables.add(Table.PurchaseItem);
			tables.add(Table.Purchase);
			conditions.put("Item.upc", "PurchaseItem.upc");
			conditions.put("Purchase.purchaseDate", new Date(calendar.getTimeInMillis()));
			group.add("Item.upc");
			
			List<AllegroItem> results = JDBCManager.select("Item.upc AS upc,title,type,category,company,year,sellPrice,SUM(PurchaseItem.quantity) AS quantity",
					tables, conditions, null, group);
			for (AllegroItem result : results)
				System.out.println(result.toString() + "\n");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*List<Table> tables = new ArrayList<Table>();
		tables.add(Table.Item);
		tables.add(Table.PurchaseItem);
		tables.add(Table.Purchase);
		SQLQuery query = new SQLQuery(Method.SELECT, tables);
		query.select("Item.upc", Integer.class);
		query.select("category", String.class);
		query.select("sellPrice", BigDecimal.class);
		query.select("SUM(PurchaseItem.quantity)", Integer.class);
		query.where("Item.upc", "PurchaseItem.upc", Operator.EQUALS);
		query.where("Purchase.purchaseDate", "2011-04-01", Operator.EQUALS);
		query.groupBy("Item.upc");
		
		try {
			List[] results = query.submit();
			int i = 0;
			while(i < results[0].size()) {
				for(List column : results)
					System.out.println(column.get(i));
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
