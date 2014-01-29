package se.jiv.webshop.ui;

public class MainMenuUI extends GeneralUI
{

	public int firstMenu()
	{
		System.out.println("Welcome Admin");
		System.out.println("These are your options:");
		System.out.println("1. Log in");
		System.out.println("2. Retrieve information");
		System.out.println("3. Retrieve shopping cart");
		System.out.println("4. Search for product by category");
		
		return readInt();
		
	}
	
	
}
