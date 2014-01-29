package se.jiv.webshop.ui;

public class Controller
{
	
	public static void main(String args[])
	{
		MainMenuUI mainMenu = new MainMenuUI();
		UserUI userMenu = new UserUI();
		ProductUI productMenu = new ProductUI();
		ShoppingCartUI shoppingCartMenu = new ShoppingCartUI();
		
		switch (mainMenu.firstMenu())
		{
			case 1:
				
				break;
			case 2:
				mainMenu.retrieveInformation();
				break;
			case 3:
				shoppingCartMenu.ShoppingCartMenu();
				break;
			case 4:
				
				break;
			case 5:
				switch (mainMenu.editInformation())
				{
					case 1:
						productMenu.editProduct();
						break;
					case 2:
						
						break;
					case 3:
						userMenu.UserMenu();
						break;
				}
				break;
		}
	}
}
