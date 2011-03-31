package ca.ubc.cs304.allegro.model;

import java.util.ArrayList;
import java.util.List;

import ca.ubc.cs304.allegro.jdbc.JDBCManager.Table;

public class HasSong implements AllegroItem {
	private Integer upc;
	private String title;
	
	public HasSong() {}
	
	public HasSong(Integer upc, String title) {
		super();
		this.upc = upc;
		this.title = title;
	}

	public List<Object> getParameters() {
		List<Object> parameters = new ArrayList<Object>();
		parameters.add(upc);
		parameters.add(title);
		return parameters;
	}

	public Table getTable() {
		return Table.HasSong;
	}

	public Integer getUpc() {
		return upc;
	}

	public void setUpc(Integer upc) {
		this.upc = upc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
