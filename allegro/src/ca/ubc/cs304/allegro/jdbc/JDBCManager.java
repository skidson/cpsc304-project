package ca.ubc.cs304.allegro.jdbc;

import java.lang.reflect.InvocationTargetException;
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
		ReturnItem, Returns, ShipItem, Shipment, Store, Stored, Supplier};
	
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
	 * Returns the list of results for a query of multiple tables where returned
	 * tuples meet the conditions specified by "key = value" for each entry 
	 * in the passed Map. The entries for the list returned will be the type
	 * indicated by the first Table of the ones queried.
	 * @param tables - the list of tables to query.
	 * @param conditions - conditions "key = value" for tuples returned
	 * @return The result set for this query.
	 * @throws SQLException
	 */
	public static List<AllegroItem> select(List<Table> tables, Map<String, Object> conditions) throws SQLException {
		// REQUIRES: Class names in AllegroItem's package match database's names
		// Submit the query and fetch the results
		
		StringBuilder from = new StringBuilder();
		for (Table table : tables)
			from.append(table.toString() + ", ");
		int index = from.lastIndexOf(", ");
		from.replace(index, index+1, "");
		
		ResultSet results = select(from.toString(), conditions);
		
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
						else if (paramType == Long.class)
							method.invoke(entry, results.getDate(column).getTime());
						else if (paramType == Float.class)
							method.invoke(entry, results.getFloat(column));
					} catch (InvocationTargetException e2) {
						// Column did not exist in result, leave null
					}
				}
				resultList.add((AllegroItem)entry);
				results.next();
			}
			results.close();
			return(resultList);
			
		} catch (Exception e) {
			throw new SQLException(e);
		}
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
		return select(tables, conditions);
	}
	
	// Helper method for use by all select methods
	private static ResultSet select(String tables, Map<String, Object> conditions) throws SQLException {
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
		String query = "SELECT * FROM " + tables;
		if (conditions !=  null && !conditions.isEmpty())
			query += " WHERE " + properties;
		return fetch(query, parameters);
	}
	
	private static Connection connect() throws SQLException {
		if (!registered) {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			registered = true;
		}
		Connection connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
		return connection;
	}
	
	private static ResultSet fetch(String query, List<Object> parameters) throws SQLException {
		Connection connection = connect();
		PreparedStatement statement = connection.prepareStatement(query);
		if (parameters != null)
			finalizeParams(statement, parameters);
		ResultSet result = statement.executeQuery();
		return result;
	}
	
	private static void modify(String query, List<Object> parameters) throws SQLException {
		Connection connection = connect();
		PreparedStatement statement = connection.prepareStatement(query);
		
		// Replace placeholders with SQL appropriate values
		try {
			finalizeParams(statement, parameters);
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
			List<Object> parameters) throws SQLException {
		for (int i = 0; i < parameters.size(); i++) {
			Object parameter = parameters.get(i);
			if (parameter == null)
				statement.setNull(i+1, java.sql.Types.NULL);
			else if (parameter.getClass() == String.class)
				statement.setString(i+1, (String)parameter);
			else if (parameter.getClass() == Integer.class)
				statement.setInt(i+1, (Integer)parameter);
			else if (parameter.getClass() == BigDecimal.class)
				statement.setBigDecimal(i+1, (BigDecimal)parameter);
			else if (parameters.get(i) == Double.class)
				statement.setDouble(i+1, (Double)parameter);
			else if (parameters.get(i) == Date.class)
				statement.setDate(i+1, (Date)parameter);
			else if (parameters.get(i) == (Long)parameter)
				statement.setLong(i+1, (Long)parameter);
		}
	}
}