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
		System.out.println("5. Edit information");

		return readInt();

	}

	public int retrieveInformation()
	{
		System.out.println("About what do you want to retrieve information?");
		System.out.println("1.Products");
		System.out.println("2.Categories");
		System.out.println("3.Users");

		return readInt();
	}

	public int editInformation()
	{
		System.out.println("What do you want to edit?");
		System.out.println("1. Products");
		System.out.println("2. Categories");
		System.out.println("3. Users");

		return readInt();
	}

}
