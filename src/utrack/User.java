package utrack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cs5530.*;

public class User {

	// Global variables
	private String login;
	private String name;
	private String address;
	private String phone;
	private String password;
	private int userType;
	
	public Boolean loggedin;
	
	// Constructor for existing user.
	User(String login, String password)
	{
		this.login = login;
		this.password = password;
		
		this.login();
	}
	
	// Constructor for creating a new user.
	User(String login, String name, String address, String phone, String password, int userType)
	{
		this.login = login;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.password = password;
		this.userType = userType;
		
		this.addUser();
	}
	
	private void addUser()
	{
		Connector con = null;
		String query = "insert into Users (login, name, address, phone, password, userType) " + "values (\"" + this.login + "\", \"" + this.name + "\", \"" + this.address + "\", \"" + this.phone + "\", \"" + this.password + "\", \"" + this.userType + "\")";
		
		try
		{
			con = new Connector();
			String output = "";
			
			// Check that the login is available.
			String loginQuery = "select * from Users where login = '" + this.login + "'";
			ResultSet rs=con.stmt.executeQuery(loginQuery);
			while (rs.next())
			{
		        output+=rs.getString("login"); 
			}
			
			if (output.length() != 0) 
			{
				System.out.println("The login already exists.");
				this.loggedin = false;
				return;
			}
			
			// The user login is unique, create a new user.
			else 
			{
				con.stmt.executeUpdate(query);
				System.out.println("Successfully added new user");
				this.loggedin = true;
			}
		}
		catch (Exception e)
		{
			this.loggedin = false;
			System.out.println("Could not add new user");
		}
		finally
		{
			try {
				con.closeConnection();
			} catch (Exception e) {
				//Do nothing
			}
		}
	}
	
	private void login()
	{
		Connector con = null;
		String query = "select * from Users where login = '" + this.login + "' and password = '" + this.password + "'";
		
		try
		{
			con = new Connector();
			
			ResultSet rs = con.stmt.executeQuery(query);
			
			while (rs.next())
			{
				// Check if user enter correct credentials.
				if (rs.getString("login").toString().equals(this.login))
				{
					this.name = rs.getString("name").toString();
					this.address = rs.getString("address").toString();
					this.phone = rs.getString("phone").toString();
					this.userType = rs.getInt("userType");
					
					this.loggedin = true;
					
					System.out.println("Welcome " + this.name);
					return;
				}
			}
			
			// The user has the wrong login and/or password
			this.loggedin = false;
			System.out.println("Wrong credentials");
		}
		catch (Exception e)
		{
			this.loggedin = false;
			System.out.println("Could not login.");
		}
		
		finally
		{
			try {
				con.closeConnection();
			} catch (Exception e) {
				//Do nothing
			}
		}
	}
	
	public void addVisit()//(String POI, int cost, int partySize, Date date)
	{
		Connector con = null;
		String choice;
		int c;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("        Look up POI     ");
   	 	System.out.println("1. Search by POI name:");
   	 	System.out.println("2. List all POIs:");
   	 	
   	 	while (true)
   	 	{
   	 		try 
   	 		{	
   	 			while ((choice = in.readLine()) == null && choice.length() == 0);
				c = Integer.parseInt(choice);
				
				if (c == 1)
				{
					System.out.println("Enter POI name:");
					
					while (true)
					{
						while ((choice = in.readLine()) == null && choice.length() == 0);
						
						String query = "select pid, name from POI where name like '%" + choice + "%'";
						ResultSet rs = con.stmt.executeQuery(query);
			
						System.out.println("POI ID: \tPOI Name:");
						
						while (rs.next())
						{
							System.out.println(rs.getString("pid") + " \t\t" + rs.getString("name"));
						}
					}
					
				}
				else if (c == 2)
				{
					try
					{
						System.out.println("POI ID: \tPOI Name:");
						
						con = new Connector();
						
						String findPOI = "select pid, name from POI";
						ResultSet rs = con.stmt.executeQuery(findPOI);
						
						while (rs.next())
						{
							System.out.println(rs.getString("pid") + " \t\t" + rs.getString("name"));
						}
						
						PreparedStatement preparedInsert = null;
						PreparedStatement preparedSelect = null;
						
						con.con.setAutoCommit(false);
						
						String insert = "insert into VisEvent (cost, numberofheads) values (?, ?)";
						
						preparedInsert = con.con.prepareStatement(insert);
						//preparedInsert.setInt(1, cost);
						//preparedInsert.setInt(2, partySize);
						preparedInsert.executeUpdate();
						
						return;
					}
					catch (Exception e)
					{
						System.out.println("An error occured.");
					}
				}
			} 
   	 		catch (IOException | SQLException e) 
   	 		{
				// TODO Auto-generated catch block
			}

   	 	}
	}
	
}
