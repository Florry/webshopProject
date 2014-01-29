package se.jiv.webshop.ui;

public class MainMenuUI extends GeneralUI
{

	public int firstMenu()
	{
		System.out.println("Welcome!");
		System.out.println();
		System.out.println("These are your options:");
		System.out.println("1. Log in");
		System.out.println("2. Retrieve information");
		System.out.println("3. Retrieve shopping cart");
		System.out.println("4. Search for products by category");

		return readInt();

	}

	public String logInEmail()
	{
		System.out.println("Please enter your email: ");

		return readString();
	}

	public String logInPassword()
	{
		System.out.println("Please enter your password: ");

		return readString();
	}

	public int retrieveInformation()
	{
		System.out.println("About what do you want to retrieve information?");
		System.out.println("1.Products");
		System.out.println("2.Categories");
		System.out.println("3.Users");

		return readInt();
	}

	public String productInformation()
	{
		System.out.println("Please enter the name of the product you want to "
				+ "get information about");

		return readString();
	}

	public String categoryInformation()
	{
		System.out.println("Please enter the name of the category you want to "
				+ "get information about: ");

		return readString();
	}

	public String userInformation()
	{
		System.out.println("Please enter the name of the user you want to "
				+ "get information about: ");

		return readString();
	}
	
	public String productsByCategory(){
		System.out.println("What category do you want to search for?");
		
		return readString();
	}

}
