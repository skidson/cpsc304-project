package ca.ubc.cs304.allegro.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;


public class SQLQuery {
	public enum Method {SELECT, INSERT_INTO, DELETE_FROM, UPDATE}
	public enum Operator {EQUALS, LIKE, IS_NULL, NOT, GREATER_THAN, LESS_THAN}
	public enum Aggregate {MAX, MIN, SUM}
	private Method method;
	private List<Table> tables;
	private Map<String, Class> columns;
	private List<Condition> conditions;
	private List<String> groups;
	private Condition having;
	private List<Object> values = new ArrayList<Object>();
	private Condition set;
	private SQLQuery except;
	
	public SQLQuery(Method method, Table table) {
		this.method = method;
		this.tables = new ArrayList<Table>();
		tables.add(table);
	}
	
	public SQLQuery(Method method, List<Table> tables) {
		this.method = method;
		this.tables = tables;
	}
	
	private String generateQuery() {
		int index;
		StringBuilder query = new StringBuilder(methodToString(method) + " ");
		switch(method) {
		case SELECT:
			if (columns != null && !columns.isEmpty()) {
				Iterator<Map.Entry<String, Class>> iterator = columns.entrySet().iterator();
				while (iterator.hasNext()) 
					query.append(iterator.next().getKey() + ",");
				index = query.lastIndexOf(",");
				query.replace(index, index+1, " ");
			} else
				query.append("* ");
			query.append("FROM ");
		case INSERT_INTO:
		case DELETE_FROM:
		case UPDATE:
			for (Table table : tables)
				query.append(table.toString() + ",");
			index = query.lastIndexOf(",");
			query.replace(index, index+1, "");
			
		}
		switch(method) {
		case UPDATE:
			query.append("SET " + set.column + " = ?");
			values.add(set.value);
			break;
		case INSERT_INTO:
			query.append("VALUES (");
			for (int i = 0; i < values.size(); i++)
				query.append("?,");
			index = query.lastIndexOf(",");
			query.replace(index, index+1, ")");
			break;
		case SELECT:
		case DELETE_FROM:
			if (conditions != null) {
				query.append(" WHERE ");
				for (Condition condition : conditions) {
					query.append(condition.column + " " + operatorToString(condition.operator) +
							" ? AND ");
					values.add(condition.value);
				}
				index = query.lastIndexOf(" AND ");
				query.replace(index, index+5, "");
			}
		}
		
		if (groups != null) {
			query.append(" GROUP BY ");
			for (String group : groups)
				query.append(group + ",");
			index = query.lastIndexOf(",");
			query.replace(index, index+1, "");
		}
		
		if (having != null) {
			query.append(" HAVING ");
			query.append(having.column + " " + operatorToString(having.operator) +
					" ?");
			values.add(having.value);
		}
	
		if (except != null)
			query.append(" EXCEPT " + except.toString());
		
		return query.toString();
	}
	
	public List[] submit() throws SQLException {
		List[] results = null;
		Connection connection = JDBCManager.connect();
		PreparedStatement statement = connection.prepareStatement(generateQuery());
		for (int i = 0; i < values.size(); i++) {
			Object parameter = values.get(i);
			if (parameter == null)
				statement.setNull(i+1, java.sql.Types.NULL);
			else if (parameter.getClass() == String.class) {
				statement.setString(i+1, (String)parameter);
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
		if (method == Method.SELECT)
			results = parseResultSet(statement.executeQuery());
		statement.close();
		return results;
	}
	
	public void where(String column, Object value, Operator operator) {
		if (conditions == null)
			conditions = new ArrayList<Condition>();
		conditions.add(new Condition(column, value, operator));
	}
	
	public void select(String column, Class type) {
		if (columns == null)
			columns = new LinkedHashMap<String, Class>();
		columns.put(column, type);
	}
	
	public void values(List<Object> values) {
		this.values = values;
	}
	
	public void set(String column, Object value) throws SQLException {
		if (method != Method.UPDATE)
			throw new SQLException();
		set = new Condition(column, value, Operator.EQUALS);
	}
	
	public void groupBy(String column) {
		if (groups == null)
			groups = new ArrayList<String>();
		groups.add(column);
	}
	
	public void having(String column, Object value, Operator operator) {
		having = new Condition(column, value, operator);
	}
	
	public void except(SQLQuery query) {
		this.except = query;
	}
	
	private String operatorToString(Operator op) {
		switch(op) {
		case EQUALS:
			return "=";
		case GREATER_THAN:
			return ">";
		case LESS_THAN:
			return "<";
		default:
			return op.toString().replace("_", " ");
		}
	}
	
	private String methodToString(Method method) {
		return method.toString().replace("_", " ");
	}
	
	private List<Object>[] parseResultSet(ResultSet result) throws SQLException {
		List<Object>[] results = null;
		if (columns != null)
			results = new List[columns.size()];
		result.first();
		if (!result.isFirst())
			return null;
		while(!result.isAfterLast()) {
			Iterator<Map.Entry<String, Class>> iterator = columns.entrySet().iterator();
			int i = 0;
			while(iterator.hasNext()) {
				Map.Entry<String, Class> entry = iterator.next();
				if (entry.getValue() == Integer.class)
					results[i].add(result.getInt(entry.getKey()));
				else if (entry.getValue() == String.class)
					results[i].add(result.getString(entry.getKey()));
				else if (entry.getValue() == Date.class)
					results[i].add(result.getDate(entry.getKey()));
				else if (entry.getValue() == Long.class)
					results[i].add(result.getLong(entry.getKey()));
				else if (entry.getValue() == Float.class)
					results[i].add(result.getFloat(entry.getKey()));
				else if (entry.getValue() == Double.class)
					results[i].add(result.getDouble(entry.getKey()));
				else if (entry.getValue() == BigDecimal.class)
					results[i].add(result.getBigDecimal(entry.getKey()));
				i++;
			}
		}
		return results;
	}
	
	private class Condition {
		String column;
		Object value;
		Operator operator;
		
		public Condition(String attribute, Object value, Operator operator) {
			super();
			this.column = attribute;
			this.value = value;
			this.operator = operator;
		}
	}
	
}
