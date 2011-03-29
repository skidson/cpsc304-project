package ca.ubc.cs304.allegro.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class JDBCManager {
	private static final String HOST = "jdbc:mysql://128.189.169.25:3306/cs304";
	private static final String USERNAME = "cs304";
	private static final String PASSWORD = "allegro";
	
	Connection connection;
	
	public JDBCManager() {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Connection connect() throws SQLException {
		Connection connection = null;
		connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
		return connection;
	}
	
	public void insert(String table, List<Object> parameters) throws SQLException {
		Connection connection = connect();
		
		String query = "INSERT INTO " + table + " VALUES (";
		
		PreparedStatement statement = connection.prepareStatement(query);
		statement.executeUpdate();			
		connection.commit();
		statement.close();
	}

	public void insert_item(int upc, String title, String type, String category, String company, int year, double sellprice){
		int jupc = upc;
		String jtitle = title;
		String jtype = type;
		String jcategory = category;
		String jcompany = company;
		int jyear = year;
		BigDecimal jdec;
		PreparedStatement statement;

		double dsellprice = sellprice;
		jdec = BigDecimal.valueOf(dsellprice);

		try {

			//check for existing tuple first before continuing with insertion
			statement = connection.prepareStatement("SELECT * FROM Item WHERE upc = ?");
			statement.setInt(1, upc);
			int rowCount = statement.executeUpdate();

			if (rowCount != 0) {
				System.out.println("\n ERROR! Item where UPC:" + upc + " already exists!");
				return;
			}

			statement = connection.prepareStatement("INSERT INTO Item VALUES (?,?,?,?,?,?,?)");
			statement.setInt(1, jupc);
			statement.setString(2, jtitle);
			statement.setString(3, jtype);
			statement.setString(4, jcategory);
			statement.setString(5, jcompany);
			statement.setInt(6,jyear);
			statement.setBigDecimal(7, jdec);

			statement.executeUpdate();			
			// commit work 
			connection.commit();
			statement.close();

		} catch (SQLException e){ /* Dealing with SQL exceptions?? */ }
	}

	public void insert_leadsinger(int upc, String name){

		int jupc = upc;
		String jname = name;
		PreparedStatement ps;

		try {

			//check for existing tuple first before continuing with insertion
			ps = connection.prepareStatement("SELECT * FROM LeadSinger WHERE upc = ? AND name = ?");
			ps.setInt(1, upc );
			ps.setString(2, jname);

			int rowCount = ps.executeUpdate();

			if (rowCount != 0)
			{
				System.out.println("\n ERROR! LeaderSinger where upc :" + upc +" and Name"+ name + " already exists!");
				return;
			}

			ps = connection.prepareStatement("INSERT INTO LeadSinger VALUES (?,?)");
			ps.setInt(1, jupc);
			ps.setString(2, jname);

			ps.executeUpdate();			
			// commit work 
			connection.commit();
			ps.close();

		} catch (SQLException e) { /* Dealing with SQL exceptions?? */ }
	}

	public void insert_hassong(int upc, String title){

		int jupc = upc;
		String jtitle = title;
		PreparedStatement ps;

		try {

			//check for existing tuple first before continuing with insertion
			ps = connection.prepareStatement("SELECT * FROM HasSong WHERE upc = ? AND title = ?");
			ps.setInt(1, upc );
			ps.setString(2, jtitle);
			int rowCount = ps.executeUpdate();

			if (rowCount != 0)
			{
				System.out.println("\n ERROR! Has Song with title:" + jtitle +" and UPC:" + jupc + " already exists!");
				return;
			}

			ps = connection.prepareStatement("INSERT INTO HasSong VALUES (?,?)");
			ps.setInt(1, jupc);
			ps.setString(2, jtitle);

			ps.executeUpdate();			
			// commit work 
			connection.commit();
			ps.close();

		} catch (SQLException e) { /* Dealing with SQL exceptions?? */ }
	}

	public void insert_supplier(String supname, String address, String city, int status){

		String jsupname = supname;
		String jaddress = address;
		String jcity = city;
		int jstatus = status;
		PreparedStatement ps;

		try {

			//check for existing tuple first before continuing with insertion
			ps = connection.prepareStatement("SELECT * FROM Supplier WHERE supname = ?");
			ps.setString(1, supname );
			int rowCount = ps.executeUpdate();

			if (rowCount != 0)
			{
				System.out.println("\n ERROR! Supplier where supname :" + jsupname + " already exists!");
				return;
			}

			ps = connection.prepareStatement("INSERT INTO Supplier VALUES (?,?,?,?)");

			ps.setString(1, jsupname);
			ps.setString(2, jaddress);
			ps.setString(3, jcity);
			ps.setInt(4, status);

			ps.executeUpdate();			
			// commit work 
			connection.commit();
			ps.close();

		} catch (SQLException e) { /* Dealing with SQL exceptions?? */}
	}

	public void insert_shipment(int sid, String supname, String sname, String date){

		PreparedStatement ps; 

		String sdate = date;  // Must be in "yyyy-mm-dd" format!
		Date jdate = Date.valueOf(sdate);      	

		try {
			//check for existing tuple first before continuing with insertion
			ps = connection.prepareStatement("SELECT * FROM Shipment WHERE sid = ? AND supname = ? AND sname = ?");
			ps.setInt(1, sid );
			ps.setString(2, supname );
			ps.setString(3, sname );

			int rowCount = ps.executeUpdate();

			if (rowCount != 0)
			{
				System.out.println("\n Shipment with ID:"+ sid + "and Supplier name:"+ supname +" with name:"+ sname + " already exists!");
				return;
			}


			ps = connection.prepareStatement("INSERT INTO Shipment VALUES (?,?,?,?)");
			ps.setInt(1, sid);
			ps.setString(2, supname);
			ps.setString(3, sname);
			ps.setDate(4, jdate);

			ps.executeUpdate();			
			// commit work 
			connection.commit();
			ps.close();

		} catch (SQLException e) {}	
	}

	public void insert_shipitem(int sid, int upc, double supprice, int quantity){

		PreparedStatement ps;

		BigDecimal jsupprice = BigDecimal.valueOf(supprice);

		try {

			//check for existing tuple first before continuing with insertion
			ps = connection.prepareStatement("SELECT * ShipItem WHERE sid = ? AND upc = ?");
			ps.setInt(1, sid );
			ps.setInt(2, upc );

			int rowCount = ps.executeUpdate();

			if (rowCount != 0)
			{
				System.out.println("\n ERROR! Shipment item with sid " + sid + " and UPC:"+ upc + " already  exists!"); 
				return;
			}

			ps = connection.prepareStatement("INSERT INTO ShipItem VALUES (?,?,?,?)");

			ps.setInt(1, sid);
			ps.setInt(2, upc);
			ps.setBigDecimal(3, jsupprice);
			ps.setInt(4, quantity);

			ps.executeUpdate();			
			// commit work 
			connection.commit();
			ps.close();	

		} catch (SQLException e) {}

	}

	public void insert_store(String sname, String address, String type ){

		PreparedStatement ps; 

		try {

			//check for existing tuple first before continuing with insertion
			ps = connection.prepareStatement("SELECT * FROM Store WHERE sname = ?");
			ps.setString(1, sname );
			int rowCount = ps.executeUpdate();

			if (rowCount != 0)
			{
				System.out.println("\n ERROR! Store where sname :" + sname + " already exists!");
				return;
			}


			ps  = connection.prepareStatement("INSERT INTO Store VALUES (?,?,?)");

			ps.setString(1, sname);
			ps.setString(2, address);
			ps.setString(3, type);

			ps.executeUpdate();			
			// commit work 
			connection.commit();
			ps.close();

		} catch (SQLException e) {}

	}

	public void insert_stored(String sname, int upc, int stock){

		PreparedStatement ps;

		try {

			//check for existing tuple first before continuing with insertion
			ps = connection.prepareStatement("SELECT * FROM Stored WHERE sname = ? AND upc = ?");
			ps.setString(1, sname );
			ps.setInt(2, upc );

			int rowCount = ps.executeUpdate();

			if (rowCount != 0)
			{
				System.out.println("\n ERROR! Stored with sname:" + sname +"and UPC:"+ upc + " already exists!");
				return;
			}


			ps = connection.prepareStatement("INSERT INTO Stored VALUES (?,?,?)");
			ps.setString(1, sname);
			ps.setInt(2, upc);
			ps.setInt(3, stock);

			ps.executeUpdate();			
			// commit work 
			connection.commit();
			ps.close();

		} catch (SQLException e) {}


	}

	public void insert_purchase(int receiptid, String date, int cid, String sname,
			int card_num, String expire, String expecteddate,
			String delivereddate){

		Date jdate = Date.valueOf(date);
		Date jexpire = Date.valueOf(expire);
		Date jexpecteddate = Date.valueOf(expecteddate);
		Date jdelivereddate = Date.valueOf(delivereddate);

		PreparedStatement ps;

		try {

			//check for existing tuple first before continuing with insertion
			ps = connection.prepareStatement("SELECT * FROM Purchase WHERE receciptid = ?");
			ps.setInt(1, receiptid );
			int rowCount = ps.executeUpdate();

			if (rowCount != 0)
			{
				System.out.println("\n ERROR! Purchase where receipt id :" + receiptid + " already exists!");
				return;
			}

			ps = connection.prepareStatement("INSERT INTO Purchase VALUES (?,?,?,?,?,?,?,?)");
			ps.setInt(1, receiptid);
			ps.setDate(2, jdate);
			ps.setInt(3, cid);
			ps.setString(4, sname);
			ps.setInt(5, card_num);
			ps.setDate(6, jexpire);
			ps.setDate(7, jexpecteddate);
			ps.setDate(8, jdelivereddate);

			ps.executeUpdate();			
			// commit work 
			connection.commit();
			ps.close();

		} catch (SQLException e) {}

	}

	public void insert_purchaseitem(int receiptid, int upc, int quantity){
		PreparedStatement ps;

		try {

			//check for existing tuple first before continuing with insertion
			ps = connection.prepareStatement("SELECT * FROM PurchaseItem WHERE receiptid = ? AND upc = ?");
			ps.setInt(1, receiptid );
			ps.setInt(2, upc );

			int rowCount = ps.executeUpdate();

			if (rowCount != 0)
			{
				System.out.println("\n ERROR! PurchaseItem with receiptid:" + receiptid +"and UPC:"+ upc + " already exists!");
				return;
			}

			ps = connection.prepareStatement("INSERT INTO PurchaseItem VALUES (?,?,?)");
			ps.setInt(1, receiptid);
			ps.setInt(2, upc);
			ps.setInt(3, quantity);

			ps.executeUpdate();			
			// commit work 
			connection.commit();
			ps.close();

		} catch (SQLException e) {}
	}

	public void insert_customer(String cid, String password, String name, String address, int phone) throws SQLException{
		PreparedStatement ps;
		Connection connection = connect();
		try {

			//check for existing tuple first before continuing with insertion
			ps = connection.prepareStatement("SELECT * FROM Customer WHERE cid = ?");
			ps.setString(1, cid );
			int rowCount = ps.executeUpdate();

			if (rowCount != 0)
			{
				System.out.println("\n ERROR! Customer where cid:" + cid + " already exists!");
				return;
			}

			ps = connection.prepareStatement("INSERT INTO Customer VALUES (?,?,?,?,?)");
			ps.setString(1, cid);
			ps.setString(2, password);
			ps.setString(3, name);
			ps.setString(4, address);
			ps.setInt(5, phone);

			ps.executeUpdate();			
			// commit work 
			connection.commit();
			ps.close();

		} catch (SQLException e) {}

	}

	public void insert_return(int retid, String date, int receiptid, String name){

		PreparedStatement ps;
		Date jdate = Date.valueOf(date);

		try {

			//check for existing tuple first before continuing with insertion
			ps = connection.prepareStatement("SELECT * FROM Return WHERE retid = ? AND receiptid = ?");
			ps.setInt(1, retid );
			ps.setInt(3, receiptid );


			int rowCount = ps.executeUpdate();

			if (rowCount != 0)
			{
				System.out.println("\n ERROR! Return with Retid:" + retid +" and receiptid:"+ receiptid + " already exists!");
				return;
			}

			ps = connection.prepareStatement("INSERT INTO Return VALUES (?,?,?)");
			ps.setInt(1, retid);
			ps.setDate(2, jdate);
			ps.setInt(3, receiptid);
			ps.setString(4, name);

			ps.executeUpdate();			
			// commit work 
			connection.commit();
			ps.close();


		} catch (SQLException e) {}
	}

	public void insert_returnitem(int retid, int upc, int quantity){
		PreparedStatement ps;

		try {

			//check for existing tuple first before continuing with insertion
			ps = connection.prepareStatement("SELECT * FROM ReturnItem WHERE retid = ? AND upc = ?");
			ps.setInt(1, retid );
			ps.setInt(2, upc );

			int rowCount = ps.executeUpdate();

			if (rowCount != 0)
			{
				System.out.println("\n ERROR! ReturnItem with retid:" + retid +" and UPC:" + upc+ " already exists!");
				return;
			}

			ps = connection.prepareStatement("INSERT INTO ReturnItem VALUES (?,?,?)");
			ps.setInt(1, retid);
			ps.setInt(2, upc);
			ps.setInt(3, quantity);

			ps.executeUpdate();			
			// commit work 
			connection.commit();
			ps.close();

		} catch (SQLException e) {}
	}

	/////////////////////////////////////////////////////////////

	public void delete_item(int upc){
		PreparedStatement ps;

		try 
		{
			ps = connection.prepareStatement("DELETE from Item WHERE upc = ?");
			ps.setInt(1, upc);
			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\n Item where UPC:" + upc + " does not exist!");
			}
			connection.commit();	
			ps.close();		

		}
		catch(SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				connection.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void delete_leadsinger(int upc, String name) {

		PreparedStatement  ps;


		try
		{
			ps = connection.prepareStatement("DELETE FROM LeadSinger WHERE upc = ? AND name = ?");
			ps.setInt(1, upc);
			ps.setString(2, name);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\n Lead Singer with name:" + name +"and UPC:"+ upc + " does not exist!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				connection.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void delete_hassong(int upc, String title)
	{

		PreparedStatement  ps;

		try
		{
			ps = connection.prepareStatement("DELETE FROM HasSong WHERE upc = ? AND title = ?");
			ps.setInt(1, upc);
			ps.setString(2, title);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\n Has Song with title:" + title +"and UPC:" + upc + " does not exist!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				connection.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void delete_supplier( String supname )
	{

		PreparedStatement  ps;

		try
		{
			ps = connection.prepareStatement("DELETE FROM Supplier WHERE supname = ?");
			ps.setString(1, supname);


			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\n Supplier with Supplier name:" + supname + " does not exist!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				connection.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void delete_shipment( int sid, String supname, String sname )
	{

		PreparedStatement  ps;

		try
		{
			ps = connection.prepareStatement("DELETE FROM Shipment WHERE sid = ? AND supname = ? AND sname = ?");
			ps.setInt(1, sid);
			ps.setString(2, supname);
			ps.setString(3, sname);

			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\n Shipment with ID:"+ sid + "and Supplier name:"+ supname +" with name:"+ sname + " does not exist!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				connection.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void delete_shipitem( int sid, int upc )
	{

		PreparedStatement  ps;

		try
		{
			ps = connection.prepareStatement("DELETE FROM ShipItem WHERE sid = ? AND upc = ?");
			ps.setInt(1, sid);
			ps.setInt(2, upc);


			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\n Shipment item with sid " + sid + " and UPC:"+ upc + " does not exist!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				connection.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void delete_store( String sname )
	{

		PreparedStatement  ps;

		try
		{
			ps = connection.prepareStatement("DELETE FROM Store WHERE sname = ?");
			ps.setString(1, sname);


			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\n Store: " + sname + " does not exist!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				connection.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void delete_stored( String sname, int upc )
	{

		PreparedStatement  ps;

		try
		{
			ps = connection.prepareStatement("DELETE FROM Stored WHERE sname = ? AND upc = ?");
			ps.setString(1, sname);
			ps.setInt(2, upc);


			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\n Stored with sname:" + sname +"and UPC:"+ upc + " does not exist!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				connection.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void delete_purchase( int receiptid )
	{

		PreparedStatement  ps;

		try
		{
			ps = connection.prepareStatement("DELETE FROM Purchase WHERE receiptid= ?");
			ps.setInt(1, receiptid);


			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\n Purchase with receiptid:" + receiptid + " does not exist!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				connection.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void delete_purchaseitem( int receiptid, int upc )
	{

		PreparedStatement  ps;

		try
		{
			ps = connection.prepareStatement("DELETE FROM PurchaseItem WHERE receiptid = ? AND upc = ?");
			ps.setInt(1, receiptid);
			ps.setInt(2, upc);


			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{
				System.out.println("\n PurchaseItem with receiptid:" + receiptid +"and UPC:"+ upc + " does not exist!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				connection.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void delete_customer( int cid ) {
		PreparedStatement  ps;

		try
		{
			ps = connection.prepareStatement("DELETE FROM Customer WHERE cid = ?");
			ps.setInt(1, cid);


			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{ 
				System.out.println("\n Customer with cid:" + cid + " does not exist!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				connection.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void delete_return( int retid, int receiptid ) {

		PreparedStatement  ps;

		try
		{
			ps = connection.prepareStatement("DELETE FROM Return WHERE retid = ? AND receiptid = ?");
			ps.setInt(1, retid);
			ps.setInt(3, receiptid);


			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{ 
				System.out.println("\n Return with Retid:" + retid +" and receiptid:"+ receiptid + " does not exist!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				connection.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	public void delete_returnitem( int retid, int upc ) {
		PreparedStatement  ps;

		try
		{
			ps = connection.prepareStatement("DELETE FROM ReturnItem WHERE retid = ? AND upc = ?");
			ps.setInt(1, retid);
			ps.setInt(2, upc);


			int rowCount = ps.executeUpdate();

			if (rowCount == 0)
			{ 
				System.out.println("\n ReturnItem with retid:" + retid +" and UPC:" + upc+ " does not exist!");
			}

			connection.commit();

			ps.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());

			try 
			{
				connection.rollback();	
			}
			catch (SQLException ex2)
			{
				System.out.println("Message: " + ex2.getMessage());
				System.exit(-1);
			}
		}
	}

	/////////////////////////////////////////////////////////////

	public void show_item() throws SQLException {
		Statement stmt;
		ResultSet rs;

		Integer jupc ,jyear;
		String jtitle;
		String jtype;
		String jcategory;
		String jcompany;
		BigDecimal jdec;
		
		Connection connection = connect();

		try
		{
			stmt = connection.createStatement();

			rs = stmt.executeQuery("SELECT * FROM Item");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i+1));    
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string simplified output formatting; truncation may occur

				jupc = rs.getInt(1);
				System.out.printf("%-10.10s", jupc.toString());		  

				jtitle = rs.getString(2);
				System.out.printf("%-10.10s", jtitle);

				jtype = rs.getString(3);
				System.out.printf("%-10.10s", jtype);

				jcategory = rs.getString(4);
				System.out.printf("%-10.10s", jcategory);

				jcompany = rs.getString(5);
				System.out.printf("%-10.10s", jcompany);

				jyear = rs.getInt(6);
				System.out.printf("%-10.10s", jyear.toString());

				jdec = rs.getBigDecimal(7);
				System.out.printf("%-10.10s", jdec.toString());

			}

			// close the statement; 
			// the ResultSet will also be closed
			stmt.close();
			connection.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
	}

	private void show_leadsinger()
	{
		Statement stmt;
		ResultSet rs;

		int upc;
		String name;

		try
		{
			stmt = connection.createStatement();

			rs = stmt.executeQuery("SELECT * FROM LeadSinger");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i+1));    
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string simplified output formatting; truncation may occur

				upc = rs.getInt(1);
				System.out.printf("%-10.10i", upc);

				name = rs.getString(2);
				System.out.printf("%-10.10s", name);

			}

			// close the statement; 
			// the ResultSet will also be closed
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
	}

	private void show_hassong()
	{
		Statement stmt;
		ResultSet rs;

		int upc;
		String title;

		try
		{
			stmt = connection.createStatement();

			rs = stmt.executeQuery("SELECT * FROM HasSong");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i+1));    
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string simplified output formatting; truncation may occur
				upc = rs.getInt(1);
				System.out.printf("%-10.10i", upc);

				title = rs.getString(2);
				System.out.printf("%-10.10s", title);		  

			}

			// close the statement; 
			// the ResultSet will also be closed
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
	}

	private void show_supplier()
	{
		Statement stmt;
		ResultSet rs;

		String supname;
		String address;
		String city;
		int status;

		try
		{
			stmt = connection.createStatement();

			rs = stmt.executeQuery("SELECT * FROM Supplier");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i+1));    
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string simplified output formatting; truncation may occur

				supname = rs.getString(1);
				System.out.printf("%-10.10s", supname);

				address = rs.getString(2);
				System.out.printf("%-10.10s", address);

				city = rs.getString(3);
				System.out.printf("%-10.10s", city);

				status = rs.getInt(4);
				System.out.printf("%-10.10i", status);


			}

			// close the statement; 
			// the ResultSet will also be closed
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
	}

	private void show_shipment()
	{
		Statement stmt;
		ResultSet rs;

		int sid;
		String supname;
		String sname;
		Date date;

		try
		{
			stmt = connection.createStatement();

			rs = stmt.executeQuery("SELECT * FROM Shipment");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i+1));    
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string simplified output formatting; truncation may occur

				sid = rs.getInt(1);
				System.out.printf("%-10.10i", sid);

				supname = rs.getString(2);
				System.out.printf("%-10.10s", supname);

				sname = rs.getString(3);
				System.out.printf("%-10.10s", sname);

				date = rs.getDate(3);
				System.out.printf("%-10.10s", date.toString());


			}

			// close the statement; 
			// the ResultSet will also be closed
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
	}

	private void show_shipitem()
	{
		Statement stmt;
		ResultSet rs;

		int sid;
		int upc;
		BigDecimal supprice;
		int quantity;

		try
		{
			stmt = connection.createStatement();

			rs = stmt.executeQuery("SELECT * FROM ShipItem");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i+1));    
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string simplified output formatting; truncation may occur

				sid = rs.getInt(1);
				System.out.printf("%-10.10i", sid);

				upc = rs.getInt(2);
				System.out.printf("%-10.10i", upc);

				supprice = rs.getBigDecimal(3);
				System.out.printf("%-10.10s", supprice.toString());

				quantity = rs.getInt(4);
				System.out.printf("%-10.10i", quantity);

			}

			// close the statement; 
			// the ResultSet will also be closed
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
	}

	private void show_store()
	{
		Statement stmt;
		ResultSet rs;

		String sname;
		String address;
		String type;

		try
		{
			stmt = connection.createStatement();

			rs = stmt.executeQuery("SELECT * FROM Store");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i+1));    
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string simplified output formatting; truncation may occur

				sname = rs.getString(1);
				System.out.printf("%-10.10s", sname);

				address = rs.getString(2);
				System.out.printf("%-10.10s", address);

				type = rs.getString(3);
				System.out.printf("%-10.10s", type);


			}

			// close the statement; 
			// the ResultSet will also be closed
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
	}

	private void show_stored()
	{
		Statement stmt;
		ResultSet rs;

		String sname;
		int upc;
		int stock;

		try
		{
			stmt = connection.createStatement();

			rs = stmt.executeQuery("SELECT * FROM Stored");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i+1));    
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string simplified output formatting; truncation may occur

				sname = rs.getString(1);
				System.out.printf("%-10.10s", sname);

				upc = rs.getInt(2);
				System.out.printf("%-10.10i", upc);

				stock = rs.getInt(3);
				System.out.printf("%-10.10i", stock);

			}

			// close the statement; 
			// the ResultSet will also be closed
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
	}

	private void show_purchase()
	{
		Statement stmt;
		ResultSet rs;

		int receiptid;
		Date date;
		int cid;
		String sname;
		int card_num;
		Date expire;
		Date expecteddate;
		Date delivereddate;


		try
		{
			stmt = connection.createStatement();

			rs = stmt.executeQuery("SELECT * FROM Purchase");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i+1));    
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string simplified output formatting; truncation may occur

				receiptid = rs.getInt(1);
				System.out.printf("%-10.10i", receiptid);

				date = rs.getDate(2);
				System.out.printf("%-10.10s", date.toString());

				cid = rs.getInt(3);
				System.out.printf("%-10.10i", cid);

				sname = rs.getString(4);
				System.out.printf("%-10.10s", sname);

				card_num = rs.getInt(5);
				System.out.printf("%-10.10i", card_num);

				expire = rs.getDate(6);
				System.out.printf("%-10.10s", expire.toString());

				expecteddate = rs.getDate(7);
				System.out.printf("%-10.10s", expecteddate.toString());

				delivereddate = rs.getDate(8);
				System.out.printf("%-10.10s", delivereddate.toString());



			}

			// close the statement; 
			// the ResultSet will also be closed
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
	}

	private void show_purchaseitem()
	{
		Statement stmt;
		ResultSet rs;

		int receiptid;
		int upc;
		int quantity;


		try
		{
			stmt = connection.createStatement();

			rs = stmt.executeQuery("SELECT * FROM PurchaseItem");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i+1));    
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string simplified output formatting; truncation may occur

				receiptid = rs.getInt(1);
				System.out.printf("%-10.10i", receiptid);

				upc = rs.getInt(2);
				System.out.printf("%-10.10i", upc);

				quantity = rs.getInt(3);
				System.out.printf("%-10.10i", quantity);


			}

			// close the statement; 
			// the ResultSet will also be closed
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
	}

	private void show_customer()
	{
		Statement stmt;
		ResultSet rs;

		String cid;
		String password;
		String name;
		String address;
		int phone;

		try
		{
			stmt = connection.createStatement();

			rs = stmt.executeQuery("SELECT * FROM Customer");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i+1));    
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string simplified output formatting; truncation may occur

				cid = rs.getString(1);
				System.out.printf("%-10.10s", cid);

				password = rs.getString(2);
				System.out.printf("%-10.10s", password);	

				name = rs.getString(3);
				System.out.printf("%-10.10s", name);

				address = rs.getString(4);
				System.out.printf("%-10.10s", address);


				phone = rs.getInt(5);
				System.out.printf("%-10.10i", phone);		  

			}

			// close the statement; 
			// the ResultSet will also be closed
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
	}

	private void show_return()
	{
		Statement stmt;
		ResultSet rs;

		int retid;
		Date date;
		int receiptid;
		String name;

		try
		{
			stmt = connection.createStatement();

			rs = stmt.executeQuery("SELECT * FROM Return");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i+1));    
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string simplified output formatting; truncation may occur

				retid = rs.getInt(1);
				System.out.printf("%-10.10i", retid);		

				date = rs.getDate(2);
				System.out.printf("%-10.10s", date.toString());		

				receiptid = rs.getInt(3);
				System.out.printf("%-10.10i", receiptid);		

				name = rs.getString(4);
				System.out.printf("%-10.10s", name);		

			}

			// close the statement; 
			// the ResultSet will also be closed
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
	}

	private void show_returnitem()
	{
		Statement stmt;
		ResultSet rs;

		int retid;
		int upc;
		int quantity;

		try
		{
			stmt = connection.createStatement();

			rs = stmt.executeQuery("SELECT * FROM ReturnItem");

			// get info on ResultSet
			ResultSetMetaData rsmd = rs.getMetaData();

			// get number of columns
			int numCols = rsmd.getColumnCount();

			System.out.println(" ");

			// display column names;
			for (int i = 0; i < numCols; i++)
			{
				// get column name and print it
				System.out.printf("%-15s", rsmd.getColumnName(i+1));    
			}

			System.out.println(" ");

			while(rs.next())
			{
				// for display purposes get everything from Oracle 
				// as a string simplified output formatting; truncation may occur

				retid = rs.getInt(1);
				System.out.printf("%-10.10i", retid);

				upc = rs.getInt(2);
				System.out.printf("%-10.10i", upc);		

				quantity = rs.getInt(3);
				System.out.printf("%-10.10i", quantity);		

			}

			// close the statement; 
			// the ResultSet will also be closed
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Message: " + ex.getMessage());
		}	
	}

}
