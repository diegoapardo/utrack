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
							loggedinMenu();
							
							while ((choice = in.readLine()) == null && choice.length() == 0);
							c = Integer.parseInt(choice);
							
							if (c == 1)
							{
								user.addVisit();
							}
							
							// Log user out.
							else if (c == 2)
							{
								user.loggedin = false;
								System.out.println("You've been logged out");
								break;
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
