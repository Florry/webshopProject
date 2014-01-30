package se.jiv.webshop.ui;

public class MainMenuUI extends GeneralUI
{
	
	public int firstMenu()
	{
		System.out.println("\n-------------------------------------------------");
		System.out.println("Welcome!");
		System.out.println();
		System.out.println("These are your options:");
		System.out.println("1. Create Info");
		System.out.println("2. Edit Info");
		System.out.println("3. Log in");
		System.out.println("4. Search products by category");
		System.out.println("5. Search products by name");
		System.out.println("0. Exit");
		System.out.print("\nPlease introduce an option: ");
		
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
		System.out.println("1. Edit Products");
		System.out.println("2. Edit Categories");
		System.out.println("3. Edit Users");
		
		return readInt();
	}
	
	public boolean returnToMainMenu()
	{
		String answer = null;
		do
		{
			System.out.println();
			System.out.println("Return to main menu? y/n");
			answer = readString();
			if (answer.equals("y"))
			{
				return true;
			} else if (answer.equals("n"))
			{
				return false;
			}
		} while (!answer.equals("y") || !answer.equals("n"));
		
		return false;
		
	}

	public void showNotValid() {
		System.out.println("\nThe introduced option is not valid.");
	}
	
}
