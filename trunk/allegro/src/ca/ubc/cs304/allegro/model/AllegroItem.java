package ca.ubc.cs304.allegro.model;

import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public interface AllegroItem {
	public List<Object> getParameters();
	public Table getTable();
}
