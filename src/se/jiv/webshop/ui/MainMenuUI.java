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
		System.out.println("What do you want to do?");
		System.out.println("1. Edit Products");
		System.out.println("2. Edit Categories");
		System.out.println("3. Edit Users");

		return readInt();
	}

	public boolean returnToMainMenu()
	{
		System.out.println();
		System.out.println("Return to main menu? yes/no");
		String anwser = readString();
		
		if( anwser.equals("yes")){
			return true;
		}
		return false;
		
	}

}
