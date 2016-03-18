package utrack;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UTrack {
	
	public static void displayMenu()
	{
		 System.out.println("        Welcome to the UTrack System     ");
    	 System.out.println("1. Sign in:");
    	 System.out.println("2. Register:");
    	 System.out.println("3. Exit:");
    	 System.out.println("Please enter your choice:");
	}
	
	public static void loggedinMenu()
	{
		System.out.println("1. Record POI Visit:");
		System.out.println("2. Log Out:");
		System.out.println("Please enter your choice:");
	}
	
	public static void adminMenu()
	{
		System.out.println("1. Enter POI:");
		System.out.println("2. Update POI:");
		System.out.println("3. Log Out:");
		System.out.println("Please enter your choice:");
	}

	@SuppressWarnings("null")
	public static void main(String[] args) 
	{
		System.out.println("Welcome to UTrack!");

		User user;
		String choice;
		int c;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		String login;
		String name;
		String address;
		String phone;
		String password;
		
		while (true)
		{       
			displayMenu();
			
			try
			{
				while ((choice = in.readLine()) == null && choice.length() == 0);
				c = Integer.parseInt(choice);
				
				if (c == 1)
				{	
					System.out.println("Enter your login:");
					while ((login = in.readLine()) == null && login.length() == 0);
					
					System.out.println("Enter your password:");
					while ((password = in.readLine()) == null && password.length() == 0);
					
					user = new User(login, password);
					
					if (user.loggedin)
					{
						while (true)
						{
							
							// The user is a regular user (not an admin).
							if (user.getUserType() == 0)
							{
								loggedinMenu();
								
								while ((choice = in.readLine()) == null && choice.length() == 0);
								c = Integer.parseInt(choice);
								
								if (c == 1)
								{
									POI poi = new POI();
									
									poi.getAllPOI();
									
									System.out.println("Enter the POI ID of your visit:");
									while ((choice = in.readLine()) == null && choice.length() == 0);
									int idPOI = Integer.parseInt(choice);
									
									System.out.println("Enter the cost of your visit:");
									while ((choice = in.readLine()) == null && choice.length() == 0);
									int costPOI = Integer.parseInt(choice);
									
									System.out.println("Enter the size of your party:");
									while ((choice = in.readLine()) == null && choice.length() == 0);
									int partySizePOI = Integer.parseInt(choice);
									
									user.addVisit(idPOI, costPOI, partySizePOI, "null");
								}
								
								// Log user out.
								else if (c == 2)
								{
									user.loggedin = false;
									System.out.println("You've been logged out");
									break;
								}
							}
							
							// The user is an Admin
							else if (user.getUserType() == 1)
							{
								adminMenu();
								
								while ((choice = in.readLine()) == null && choice.length() == 0);
								c = Integer.parseInt(choice);
								
								// Create POI
								if (c == 1)
								{
									String namePOI = "";
									System.out.println("Enter the POI name:");
									while ((namePOI = in.readLine()) == null && namePOI.length() == 0);
									
									String addressPOI = "";
									System.out.println("Enter the POI address:");
									while ((addressPOI = in.readLine()) == null && addressPOI.length() == 0);
									
									String urlPOI = "";
									System.out.println("Enter the POI URL:");
									while ((urlPOI = in.readLine()) == null && urlPOI.length() == 0);
									
									String phonePOI = "";
									System.out.println("Enter the POI phone:");
									while ((phonePOI = in.readLine()) == null && phonePOI.length() == 0);
									
									System.out.println("Enter the POI year of establishment:");
									while ((choice = in.readLine()) == null && choice.length() == 0);
									int yearPOI = Integer.parseInt(choice);
									
									String hoursPOI = "";
									System.out.println("Enter the POI hours:");
									while ((hoursPOI = in.readLine()) == null && hoursPOI.length() == 0);
									
									System.out.println("Enter the POI price:");
									while ((choice = in.readLine()) == null && choice.length() == 0);
									int pricePOI = Integer.parseInt(choice);
									
									String categoryPOI = "";
									System.out.println("Enter the POI category:");
									while ((categoryPOI = in.readLine()) == null && categoryPOI.length() == 0);
									
									POI poi = new POI(namePOI, addressPOI, urlPOI, phonePOI, yearPOI, hoursPOI, pricePOI, categoryPOI);
									poi.addPOI(user);
								}
								
								// Update POI
								else if (c == 2)
								{
									System.out.println("Enter the POI ID to update:");
									while ((choice = in.readLine()) == null && choice.length() == 0);
									c = Integer.parseInt(choice);
									
									// Get the POI from the DB.
									POI poi = new POI(c);
									poi.updatePOI();
								}
							}
						}
					}
					else
						System.out.println("Please login");
				}
				
				else if (c == 2)
				{
					int userType = 0;
					
					System.out.println("Register New User");
					
					System.out.println("Please enter a new login:");
           		 	while ((login = in.readLine()) == null && login.length() == 0);
           		 	
           		 	System.out.println("Please enter your name:");	
        		 	while ((name = in.readLine()) == null && name.length() == 0);
        		 	
        		 	System.out.println("Please enter your address:");
           		 	while ((address = in.readLine()) == null && address.length() == 0);
           		 	
           		 	System.out.println("Please enter your phone:");
        		 	while ((phone = in.readLine()) == null && phone.length() == 0);
        		 	
        		 	System.out.println("Please enter your new password:");
           		 	while ((password = in.readLine()) == null && password.length() == 0);
           		 	
           		 	// Create the new User.
           		 	user = new User(login, name, address, phone, password, userType);
				}
				
				else if (c == 3)
				{
					System.out.println("Goodbye");
					System.exit(0);
				}
       	 	}
			catch (Exception e)
			{
				System.out.println("Could not read option");
			}
		}
	}
	
}
