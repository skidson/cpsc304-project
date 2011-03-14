package ca.ubc.cs304.allegro.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCManager {
	public void register() {
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
