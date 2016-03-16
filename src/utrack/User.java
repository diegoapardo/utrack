package utrack;

import java.sql.Date;
import java.sql.ResultSet;

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
	
	public void addVisit(String POI, int cost, int partySize, Date date)
	{
		Connector con = null;
		
		String query = "";
	}
	
}
