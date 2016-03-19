package utrack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import cs5530.*;

public class POI {
	
	private int pid;
	private String name;
	private String address;
	private String url;
	private String phone;
	private int year;
	private String hours;
	private int price;
	private String category;

	POI()
	{
		// Empty constructor.
	}
	
	POI (int pid)
	{
		this.pid = pid;
		Connector con = null;
		
		try 
		{
			con = new Connector();
			
			String findPOI = "select * from POI where pid = " + this.pid;
			ResultSet rs = con.stmt.executeQuery(findPOI);
			
			while (rs.next())
			{
				this.name = rs.getString("name");
				this.address = rs.getString("address");
				this.url = rs.getString("url");
				this.phone = rs.getString("phone");
				this.year = rs.getInt("year");
				this.hours = rs.getString("hours");
				this.price = rs.getInt("price");
				this.category = rs.getString("category");
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Could not get all the POIs");
		}
		
	}
	
	POI (String name, String address, String url, String phone, int year, String hours, int price, String category)
	{
		this.pid = 0; // Set to 0 because the DB will generate this number.
		this.name = name;
		this.address = address;
		this.url = url;
		this.phone = phone;
		this.year = year;
		this.hours = hours;
		this.price = price;
		this.category = category;
	}
	
	public void addPOI(User user)
	{
		if (user.getUserType() == 1)
		{
			Connector con = null;
			
			PreparedStatement preparedInsert = null;
			
			try 
			{
				con = new Connector();
				
				con.con.setAutoCommit(false);
				
				String insert = "insert into POI (name, address, url, phone, year, hours, price, category) values (?, ?, ?, ?, ?, ?, ?, ?)";
				preparedInsert = con.con.prepareStatement(insert);
				preparedInsert.setString(1, this.name);
				preparedInsert.setString(2, this.address);
				preparedInsert.setString(3, this.url);
				preparedInsert.setString(4, this.phone);
				preparedInsert.setInt(5, this.year);
				preparedInsert.setString(6, this.hours);
				preparedInsert.setInt(7, this.price);
				preparedInsert.setString(8, this.category);
				preparedInsert.executeUpdate();		
				con.con.commit();
				
				System.out.println("The POI has been added.");
			} 
			catch (Exception e) 
			{
				System.out.println("An errored occured while trying to add visit.");
			}
		}
		else
			System.out.println("You do not have permission to perform this task");
	}
	
	public void updatePOI() 
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String choice;
		
		while (true)
		{
			try 
			{
				/* Get all the information needed from the User to Update the POI */
				
				String namePOI = "";
				System.out.println("Current name: " + this.name);
				System.out.println("Update the POI name? Y/N");
				while ((namePOI = in.readLine()) == null && namePOI.length() == 0);
				if (namePOI.toUpperCase().trim().equals("Y"))
				{
					System.out.println("Enter new POI name:");
					while ((namePOI = in.readLine()) == null && namePOI.length() == 0);
					this.name = namePOI;
				}
				else
					System.out.println("Name will not change");
				
				String addressPOI = "";
				System.out.println("Current address: " + this.address);
				System.out.println("Update the POI address?");
				while ((addressPOI = in.readLine()) == null && addressPOI.length() == 0);
				if (addressPOI.toUpperCase().trim().equals("Y"))
				{
					System.out.println("Enter new POI address:");
					while ((addressPOI = in.readLine()) == null && addressPOI.length() == 0);
					this.address = addressPOI;
				}
				else
					System.out.println("Address will not change");
				
				String urlPOI = "";
				System.out.println("Current url: " + this.url);
				System.out.println("Update the POI URL? Y/N");
				while ((urlPOI = in.readLine()) == null && urlPOI.length() == 0);
				if (urlPOI.toUpperCase().trim().equals("Y"))
				{
					System.out.println("Enter new POI URL:");
					while ((urlPOI = in.readLine()) == null && urlPOI.length() == 0);
					this.url = urlPOI;
				}
				else
					System.out.println("URL will not change");
				
				String phonePOI = "";
				System.out.println("Current phone: " + this.phone);
				System.out.println("Update the POI phone? Y/N");
				while ((phonePOI = in.readLine()) == null && phonePOI.length() == 0);
				if (phonePOI.toUpperCase().trim().equals("Y"))
				{
					System.out.println("Enter new POI phone:");
					while ((phonePOI = in.readLine()) == null && phonePOI.length() == 0);
					this.phone = phonePOI;
				}
				else
					System.out.println("Phone will not change");
				
				System.out.println("Current year: " + this.year);
				System.out.println("Update the POI year of establishment? Y/N");
				while ((choice = in.readLine()) == null && choice.length() == 0);
				if (choice.toUpperCase().trim().equals("Y"))
				{
					System.out.println("Enter new POI year:");
					while ((choice = in.readLine()) == null && choice.length() == 0);
					this.year = Integer.parseInt(choice);
				}
				else
					System.out.println("Year will not change");
				
				String hoursPOI = "";
				System.out.println("Current hours: " + this.hours);
				System.out.println("Update the POI hours? Y/N");
				while ((hoursPOI = in.readLine()) == null && hoursPOI.length() == 0);
				if (hoursPOI.toUpperCase().trim().equals("Y"))
				{
					System.out.println("Enter new POI hours:");
					while ((hoursPOI = in.readLine()) == null && hoursPOI.length() == 0);
					this.hours = hoursPOI;
				}
				else
					System.out.println("Hours will not change");
				
				System.out.println("Current price: " + this.price);
				System.out.println("Update the POI price? Y/N");
				while ((choice = in.readLine()) == null && choice.length() == 0);
				if (choice.toUpperCase().trim().equals("Y"))
				{
					System.out.println("Enter new POI price:");
					while ((choice = in.readLine()) == null && choice.length() == 0);
					this.price = Integer.parseInt(choice);
				}
				else
					System.out.println("Price will not change");
				
				String categoryPOI = "";
				System.out.println("Current category: " + this.category);
				System.out.println("Update the POI category? Y/N");
				while ((categoryPOI = in.readLine()) == null && categoryPOI.length() == 0);
				if (categoryPOI.toUpperCase().trim().equals("Y"))
				{
					System.out.println("Enter new POI category:");
					while ((categoryPOI = in.readLine()) == null && categoryPOI.length() == 0);
					this.category = categoryPOI;
				}
				else
					System.out.println("Category will not change");
				
				/* With all the information to Update the POI, call update on the DB */
				
				Connector con = null;
				PreparedStatement preparedUpdate = null;
				
				try 
				{
					con = new Connector();
					
					con.con.setAutoCommit(false);
					
					String update = "update POI set name = ?, address = ?, url = ?, phone = ?, year = ?, hours = ?, price = ?, category = ? where pid = ?";
					
					preparedUpdate = con.con.prepareStatement(update);
					preparedUpdate.setString(1, this.name);
					preparedUpdate.setString(2, this.address);
					preparedUpdate.setString(3, this.url);
					preparedUpdate.setString(4, this.phone);
					preparedUpdate.setInt(5, this.year);
					preparedUpdate.setString(6, this.hours);
					preparedUpdate.setInt(7, this.price);
					preparedUpdate.setString(8, this.category);
					preparedUpdate.setInt(9, this.pid);
					preparedUpdate.executeUpdate();
					con.con.commit();
					con.closeConnection();
					
					System.out.println("POI has been updated");
					return;
				} 
				catch (Exception e) 
				{
					System.out.println("An errored occured while trying to update POI.");
					e.printStackTrace();
				}
				
			}
			catch (Exception e)
			{
				System.out.println("There was a problem updating the POI");
			}
		}
	}
	
	public void getAllPOI()
	{
		Connector con = null;
		
		try 
		{
			con = new Connector();
			
			String findPOI = "select pid, name from POI";
			ResultSet rs = con.stmt.executeQuery(findPOI);
			
			System.out.println("");
			while (rs.next())
			{
				System.out.println(rs.getString("pid") + " \t\t" + rs.getString("name"));
			}
		} 
		catch (Exception e) 
		{
			System.out.println("Could not get all the POIs");
		}
	}

}
