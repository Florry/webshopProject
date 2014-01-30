package se.jiv.webshop.main;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.dao.ShoppingCartDAO;
import se.jiv.webshop.repository.dao.UserDAO;
import se.jiv.webshop.service.UserService;
import se.jiv.webshop.ui.UserUI;

public class Login
{
	
	// Denna klass Tas bort nar detta blir inkluderat i webshopmain!
	
	private static void doLogin()
	{
		UserUI userMenu = new UserUI();
		UserService userService = new UserService(new UserDAO(), new ShoppingCartDAO());
		UserModel loginUser = userMenu.loginUser();
		UserModel dataBaseUser = null;
		try
		{
			dataBaseUser = userService.getUser(loginUser.getEmail());
		} catch (WebshopAppException e)
		{
			System.out.println("Login failed!");
		}
		if (dataBaseUser == null)
		{
			dataBaseUser = new UserModel("", "", "", "", "", "", "");
		}
		if (loginUser.getPassword().equals(dataBaseUser.getPassword()))
		{
			System.out.println("Login successful! Logged in as: " + dataBaseUser.getFirstname()
					+ " " + dataBaseUser.getLastname());
		} else
		{
			System.out.println("Login failed!");
		}
		
	}
	
	static public void main(String args[])
	{
		doLogin();
	}
}
