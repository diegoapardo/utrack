package utrack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public void addVisit(int POI, int cost, int partySize, String date)
	{
		Connector con = null;
		int vid = 0;
		
		PreparedStatement preparedInsert = null;
		
		try 
		{
			con = new Connector();
			
			con.con.setAutoCommit(false);
			
			String insert = "insert into VisEvent (cost, numberofheads) values (?, ?)";
			preparedInsert = con.con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			preparedInsert.setInt(1, cost);
			preparedInsert.setInt(2, partySize);
			preparedInsert.executeUpdate();
			
			ResultSet generatedKeys = preparedInsert.getGeneratedKeys();
			
			if (generatedKeys.next())
				vid = generatedKeys.getInt(1);

			insert = "insert into Visit (login, pid, vid) values (?, ?, ?)";
			preparedInsert = con.con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			preparedInsert.setString(1, this.login);
			preparedInsert.setInt(2, POI);
			preparedInsert.setInt(3, vid);
			preparedInsert.executeUpdate();
			
			con.con.commit();
		} 
		catch (Exception e) 
		{
			System.out.println("An errored occured while trying to add visit.");
		}
	}
	
	public void addFavoritePOI(int poi)
	{
	    Connector con = null;
        PreparedStatement preparedInsert = null;
        
        try 
        {
            con = new Connector();
            
            con.con.setAutoCommit(false);
            
            String insert = "insert into Favorites (pid, login) values (?, ?)";
            preparedInsert = con.con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            preparedInsert.setInt(1, poi);
            preparedInsert.setString(2, this.login);
            preparedInsert.executeUpdate();
            con.con.commit();
            
            System.out.println("The POI has been added to your favorites!");
        } 
        catch (Exception e) 
        {
            System.out.println("An errored occured while trying to add visit.");
        }
	}
	
	public void addPOIFeedback(int poi, String text, int rating)
	{
	    Connector con = null;
        PreparedStatement preparedInsert = null;
        
        try 
        {
            con = new Connector();
            
            con.con.setAutoCommit(false);
            
            String insert = "insert into Feedback (pid, login, text) values (?, ?, ?)";
            preparedInsert = con.con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            preparedInsert.setInt(1, poi);
            preparedInsert.setString(2, this.login);
            preparedInsert.setString(3, text);
            preparedInsert.executeUpdate();
            
            ResultSet generatedKeys = preparedInsert.getGeneratedKeys();
            
            int fid =0;
            if (generatedKeys.next())
                fid = generatedKeys.getInt(1);
           
            insert = "insert into Rates (login, fid, rating) values (?, ?, ?)";
            preparedInsert = con.con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            preparedInsert.setString(1, this.login);
            preparedInsert.setInt(2, fid);
            preparedInsert.setInt(3, rating);
            preparedInsert.executeUpdate();
            
            con.con.commit();
            
            System.out.println("You've left feedback on the POI!");
        } 
        catch (Exception e) 
        {
            System.out.println("An errored occured while trying to feedback.");
        }
	}
	
	public void trust()
	
	public int getUserType()
	{
		return this.userType;
	}
	
	public String getUserLogin()
	{
	    return this.login;
	}
	
}
