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
import ca.ubc.cs304.allegro.model.Item;
import ca.ubc.cs304.allegro.model.Stored;

public class TestDriver {

	public static void main(String[] args) {
		try {
			// Test for JDBCManager.select(Table, Map<String, Object>)
			Map<String, Object> conditions = new HashMap<String, Object>();
			List<String> shared = new ArrayList<String>();
			List<String> group = new ArrayList<String>();
			List<Table> tables = new ArrayList<Table>();
			Calendar calendar = Calendar.getInstance();
			calendar.set(2011, 3, 2);
			tables.add(Table.Item);
			tables.add(Table.PurchaseItem);
			tables.add(Table.Purchase);
			
			conditions.clear();
			conditions.put("Item.upc", "PurchaseItem.upc");
			conditions.put("PurchaseItem.receiptId", "Purchase.receiptId");
			conditions.put("Purchase.purchaseDate", new Date(calendar.getTimeInMillis()));
			List<AllegroItem> allItems = JDBCManager.select(tables, conditions, null);
		
			System.out.println("****** RAW ******:\n");
			for (AllegroItem allItem : allItems)
				System.out.println(allItem.toString() + "\n");
			
			// Group items by UPC and sum their quantities
			List<Item> items = new ArrayList<Item>();
			for(AllegroItem allItem : allItems) {
				Item item = (Item)allItem;
				for (Item processed : items) {
					if (processed.getUpc().equals(item.getUpc())) {
						item.setQuantity(item.getQuantity()+processed.getQuantity());
						items.remove(processed);
						break;
					}
				}
				items.add(item);
			}
			
			System.out.println("****** SUMMED ******:\n");
			for (AllegroItem item : items)
				System.out.println(item.toString() + "\n");
			
			// Only maintain the specified number of items
			while (items.size() > 5)
				items.remove(items.size()-1);
			
			String select = "SUM(stock) AS stock";
			tables.clear();
			tables.add(Table.Stored);
			for (Item item : items) {
				conditions.clear();
				conditions.put("upc", item.getUpc());
				item.setStock(((Stored)JDBCManager.select(select, tables, conditions, null, null).get(0)).getStock());
			}
			
			List<Item> sorted = new ArrayList<Item>();
			while (!items.isEmpty()) {
				int max = 0;
				for (int i = 0; i < items.size(); i++)
					if (items.get(i).getStock() > items.get(max).getStock())
						max = i;
				sorted.add(items.remove(max));
			}
			
			System.out.println("****** SORTED ******:\n");
			for (AllegroItem sort : sorted)
				System.out.println(sort.toString() + "\n");
			
//			List<AllegroItem> results = JDBCManager.select(select, tables, conditions, null, group);
//			for (AllegroItem result : results)
//				System.out.println(result.toString() + "\n");
			
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
