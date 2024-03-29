package ca.ubc.cs304.allegro.jdbc;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ca.ubc.cs304.allegro.model.AllegroItem;

public class JDBCManager {
	private static final String HOST = "jdbc:mysql://128.189.169.25:3306/cs304";
	private static final String USERNAME = "cs304";
	private static final String PASSWORD = "allegro";
	private static boolean registered = false;
	
	public enum Table { Customer, HasSong, Item, LeadSinger, Purchase, PurchaseItem,
		RefundItem, Refund, ShipItem, Shipment, Store, Stored, Supplier};
	
	/**
	 * Manually inserts a tuple to the indicated table containing values
	 * specified by parameters. Parameters are order-dependant; to skip a
	 * value, set it to null.
	 * @param table
	 * @param parameters
	 * @throws SQLException
	 */
	public static void insert(Table table, List<Object> parameters) throws SQLException {
		modify("INSERT INTO " + table.toString() + 
				" VALUES " + initParams(parameters.size()), parameters);
	}
	
	/**
	 * Inserts this item to it's appropriate table.
	 * @param item
	 * @throws SQLException
	 */
	public static void insert(AllegroItem item) throws SQLException {
		insert(item.getTable(), item.getParameters());
	}
	
	public static void update(Table table, String attribute, Object value,
			 Map<String, Object> conditions) throws SQLException {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(value);
		StringBuilder query = new StringBuilder("UPDATE " + table.toString() + 
				" SET " + attribute + " = ?");
		if (conditions != null && !conditions.isEmpty()) {
			query.append(" WHERE ");
			Iterator<Map.Entry<String, Object>> iterator = conditions.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> entry = iterator.next();
				query.append(entry.getKey() + " = ?");
				parameters.add(entry.getValue());
				if(iterator.hasNext())
					query.append(" AND ");
			}
		}
		modify(query.toString(), parameters);
	}
	
	/**
	 * Deletes the tuples in the table that match all conditions "key = value" in the passed Map.
	 * @param table - the table to remove tuples from.
	 * @param conditions - the conditions for which to delete a given tuple.
	 * @throws SQLException
	 */
	public static void delete(Table table, Map<String, Object> conditions) throws SQLException {
		StringBuilder properties = new StringBuilder();
		List<Object> parameters = new ArrayList<Object>();
		Iterator<Map.Entry<String, Object>> iterator = conditions.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> condition = iterator.next();
			properties.append(condition.getKey() + " = ?");
			parameters.add(condition.getValue());
			if (iterator.hasNext())
				properties.append(" AND ");
		}
		
		modify("DELETE FROM " + table.toString() + 
				" WHERE " + properties, parameters);
	}
	
	/**
	 * Returns the list of results for a query of multiple tables where returned
	 * tuples meet the conditions specified by "key LIKE value" for each entry 
	 * in the passed Map. The entries for the list returned will be the type
	 * indicated by the first Table of the ones queried.
	 * @param tables - the list of tables to query.
	 * @param conditions - conditions "key LIKE value" for tuples returned
	 * @return The result set for this query.
	 * @throws SQLException
	 */
	public static List<AllegroItem> search(List<Table> tables, Map<String, Object> conditions) throws SQLException {
		return search(tables, conditions, null);
	}
	
	/**
	 * Returns the list of results for a query of multiple tables where returned
	 * tuples meet the conditions specified by "key LIKE value" for each entry 
	 * in the passed Map. The entries for the list returned will be the type
	 * indicated by the first Table of the ones queried.
	 * @param tables - the list of tables to query.
	 * @param conditions - conditions "key LIKE value" for tuples returned
	 * @param shared - shared keys to match between tables
	 * @return The result set for this query.
	 * @throws SQLException
	 */
	public static List<AllegroItem> search(List<Table> tables, Map<String, Object> conditions, List<String> shared) throws SQLException {
		return search(tables, conditions, shared, null);
	}
	
	public static List<AllegroItem> search(List<Table> tables, Map<String, Object> conditions, List<String> shared, List<String> group) throws SQLException {
		return select(null, tables, conditions, shared, group, false);
	}
	
	public static List<AllegroItem> search(Table table, Map<String, Object> conditions) throws SQLException {
		List<Table> tables = new ArrayList<Table>();
		tables.add(table);
		return search(tables, conditions, null);
	}
	
	
	/**
	 * Fetches the entire table from the database.
	 * @param table - the table to fetch.
	 * @return The list of items in the fetched table. These items are safe to typecast
	 * to the class representing the table specified.
	 * @throws SQLException
	 */
	public static List<AllegroItem> select(Table table) throws SQLException {
		return select(table, null);
	}
	
	/**
	 * Fetches tuples from the indicated table that meet the conditions specified by
	 * "key = value" for each entry in the passed Map.
	 * @param table - the table to fetch tuples from.
	 * @param conditions - conditions "key = value" for tuples returned
	 * @return The list of tuples fetched. These items are safe to typecast
	 * to the class representing the table specified.
	 * @throws SQLException
	 */
	public static List<AllegroItem> select(Table table, Map<String, Object> conditions) throws SQLException {
		List<Table> tables = new ArrayList<Table>();
		tables.add(table);
		return select(tables, conditions, null);
	}
	
	/**
	 * Returns the list of results for a query of multiple tables where returned
	 * tuples meet the conditions specified by "key = value" for each entry 
	 * in the passed Map. The entries for the list returned will be the type
	 * indicated by the first Table of the ones queried.
	 * @param tables - the list of tables to query.
	 * @param conditions - conditions "key = value" for tuples returned
	 * @return The result set for this query.
	 * @throws SQLException
	 */
	public static List<AllegroItem> select(List<Table> tables, Map<String, Object> conditions, List<String> shared) throws SQLException {
		return select(tables, conditions, shared, null);
	}
	
	public static List<AllegroItem> select(Table table, Map<String, Object> conditions, List<String> shared) throws SQLException {
		List<Table> tables = new ArrayList<Table>();
		tables.add(table);
		return select(tables, conditions, shared, null);
	}
	
	public static List<AllegroItem> select(List<Table> tables, Map<String, Object> conditions, 
			List<String> shared, List<String> group) throws SQLException {
		return select(null, tables, conditions, shared, group, true);
	}
	
	public static List<AllegroItem> select(String select, List<Table> tables, Map<String, Object> conditions, 
			List<String> shared, List<String> group) throws SQLException {
		return select(select, tables, conditions, shared, group, true);
	}
	
	private static List<AllegroItem> select(String select, List<Table> tables, Map<String, Object> conditions, 
			List<String> shared, List<String> group, boolean exact) throws SQLException {
		// REQUIRES: Class names in AllegroItem's package match database's names
		// Submit the query and fetch the results
		
		ResultSet results = fetch(select, tables, conditions, shared, group, exact);
		
		// Get the package directory of database items we will instantiate
		String directory = AllegroItem.class.getPackage().getName() + ".";
		
		List<AllegroItem> resultList = new ArrayList<AllegroItem>();
		
		try {
			
			// Generate the list of setters for the class we are instantiating
			List<Method> setters = new ArrayList<Method>();
			for (Method method : Class.forName(directory + tables.get(0).toString()).getMethods()) {
				if (method.getName().startsWith("set"))
					setters.add(method);
			}
			
			// For each row in the table, construct an object of type indicated by Table
			results.first();
			if (!results.isFirst())
				return resultList;
			while(!results.isAfterLast()) {
				// Instantiate a new class for the type of table we will retrieve
				AllegroItem entry = (AllegroItem) Class.forName(directory + tables.get(0).toString()).newInstance();
				
				for (Method method : setters) {
					String column = method.getName().replace("set", "").toLowerCase();
					Class paramType = method.getParameterTypes()[0];
					try {
						if (paramType == Integer.class)
							method.invoke(entry, results.getInt(column));
						else if (paramType == String.class)
							method.invoke(entry, results.getString(column));
						else if (paramType == Date.class)
							method.invoke(entry, results.getDate(column));
						else if (paramType == Long.class)
							method.invoke(entry, results.getLong(column));
						else if (paramType == Float.class)
							method.invoke(entry, results.getFloat(column));
						else if (paramType == Double.class)
							method.invoke(entry, results.getDouble(column));
						else if (paramType == BigDecimal.class)
							method.invoke(entry, results.getBigDecimal(column));
					} catch (Exception e2) {
						// Column did not exist in result, leave null
					}
				}
				resultList.add((AllegroItem)entry);
				results.next();
			}
			results.close();
			return(resultList);
			
		} catch (Exception e) {
			try {
				results.close();
			} catch (Exception e2) {}
			throw new SQLException(e);
		}
	}
	
	// Helper for use by all select methods
	private static ResultSet fetch(String select, List<Table> tables, Map<String, Object> conditions, List<String> shared, List<String> group, boolean exact) throws SQLException {
		List<Object> parameters = new ArrayList<Object>();
		if (select == null)
			select = "*";
		// Parse table names
		StringBuilder from = new StringBuilder();
		for (Table table : tables)
			from.append(table.toString() + ", ");
		int index = from.lastIndexOf(", ");
		from.replace(index, index+1, "");
		StringBuilder query = new StringBuilder("SELECT " + select + " FROM " + from);
		if ((conditions !=  null && !conditions.isEmpty()) || (shared != null && !shared.isEmpty()))
			query.append(" WHERE ");
		
		// Parse non-shared key constraints
		if (conditions != null && !conditions.isEmpty()) {
			Iterator<Map.Entry<String, Object>> iterator = conditions.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, Object> condition = iterator.next();
				if (condition.getValue() != null) {
					if (exact)
						query.append(condition.getKey() + " = ");
					else
						query.append(condition.getKey() + " LIKE ");
					if (condition.getValue().toString().contains("."))
						query.append(condition.getValue());
					else {
						query.append("?");
						parameters.add(condition.getValue());
					}
				} else {
					query.append(condition.getKey() + " IS ?");
					parameters.add(condition.getValue());
				}
				if (iterator.hasNext() || (shared != null && !shared.isEmpty()))
					query.append(" AND ");
			}
		}
			
		if (shared != null && !shared.isEmpty()) {
			// Parse shared-key constraints
			for (String key : shared) {
				for (int i = 1; i < tables.size(); i++) {
					query.append(tables.get(0).toString() + "." + key + " = " + 
							tables.get(i) + "." + key);
					if (i != tables.size()-1)
						query.append(" AND ");
				}
			}
		}
		
		if (group != null && !group.isEmpty()) {
			query.append(" GROUP BY ");
			for (String attribute : group) {
				if (attribute.contains("."))
					query.append(attribute + ", ");
				else
					query.append("'" + attribute + "'" + ", ");
			}
			index = query.lastIndexOf(", ");
			query.replace(index, index+1, "");
		}
		
		Connection connection = connect();
		PreparedStatement statement = connection.prepareStatement(query.toString());
		if (parameters != null)
			finalizeParams(statement, parameters, exact);
		ResultSet result = statement.executeQuery();
		return result;
	}
	
	public static Table getTable(String table){
		
		for(Table c : Table.values()){
			if(c.name().equals(table))
				return c;
		}
		
		return null;
	}
	
	protected static Connection connect() throws SQLException {
		if (!registered) {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			registered = true;
		}
		Connection connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
		return connection;
	}
	
	private static void modify(String query, List<Object> parameters) throws SQLException {
		Connection connection = connect();
		PreparedStatement statement = connection.prepareStatement(query);
		
		// Replace placeholders with SQL appropriate values
		try {
			finalizeParams(statement, parameters, true);
			statement.executeUpdate();			
			statement.close();
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}
	
	// Generate a list of placeholders given the number of parameters
	private static String initParams(int num) {
		StringBuilder query = new StringBuilder("(");
		for (int i = 0; i < num; i++)
			query.append("?,");
		int index = query.lastIndexOf(",");
		query.replace(index, index+1, ")");
		return query.toString();
	}
	
	private static void finalizeParams(PreparedStatement statement, 
			List<Object> parameters, boolean exact) throws SQLException {
		for (int i = 0; i < parameters.size(); i++) {
			Object parameter = parameters.get(i);
			if (parameter == null)
				statement.setNull(i+1, java.sql.Types.NULL);
			else if (parameter.getClass() == String.class) {
				if (exact)
					statement.setString(i+1, (String)parameter);
				else
					statement.setString(i+1, "%" + (String)parameter + "%");
			} else if (parameter.getClass() == Integer.class)
				statement.setInt(i+1, (Integer)parameter);
			else if (parameter.getClass() == BigDecimal.class)
				statement.setBigDecimal(i+1, (BigDecimal)parameter);
			else if (parameter.getClass() == Double.class)
				statement.setDouble(i+1, (Double)parameter);
			else if (parameter.getClass() == Date.class)
				statement.setDate(i+1, (Date)parameter);
			else if (parameter.getClass() == Long.class)
				statement.setLong(i+1, (Long)parameter);
		}
	}
	
	
}