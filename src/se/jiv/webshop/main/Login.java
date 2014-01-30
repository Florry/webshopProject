package se.jiv.webshop.main;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.dao.ShoppingCartDAO;
import se.jiv.webshop.repository.dao.UserDAO;
import se.jiv.webshop.service.UserService;
import se.jiv.webshop.ui.ExceptionUI;
import se.jiv.webshop.ui.UserUI;

public class Login
{
	
	// Denna klass Tas bort nar detta blir inkluderat i webshopmain!
	
	private static void doLogin()
	{
		UserUI userMenu = new UserUI();
		UserService userService = new UserService(new UserDAO(), new ShoppingCartDAO());
		UserModel loginUser = userMenu.loginUser();
		
		try
		{
		if(userService.validateLogin(loginUser)){
			System.out.println("Login successfull");
		}else{
			System.out.println("Login failed!");
		}
		} catch (WebshopAppException e)
		{
			ExceptionUI.printException(e);
		}
	}
	
	static public void main(String args[])
	{
		doLogin();
	}
}
