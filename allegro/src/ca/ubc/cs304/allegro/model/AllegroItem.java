package ca.ubc.cs304.allegro.model;

import java.io.Serializable;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public interface AllegroItem extends Serializable {
	public List<Object> getParameters();
	public Table getTable();
}
