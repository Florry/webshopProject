package se.jiv.webshop.main;

import se.jiv.webshop.model.*;
import se.jiv.webshop.repository.UserRepository;
import se.jiv.webshop.repository.dao.UserDAO;

public class Main
{
	public static void main(String args[])
	{
		// TEST FILES
		UserRepository userdb = new UserDAO();
		
		UserModel user1 = new UserModel("goranGorsson@vitaedolor.co.uk", "Montenegro", "Orlando",
				"Richmond", "2010-02-01", "0391 352 5730", "P.O. Box 336, 2373 Eleifend, Road",
				"Maldives", "Midlands", "3332");
		
		// userdb.addUser(user1);
		
		System.out.println(userdb.getAllUsers().toString());
		System.out.println(userdb.getUserId(user1));
		
		for (int i = 0; i < 1; i++)
		{
			userdb.addProductToCart(userdb.getUser("goranGorsson@vitaedolor.co.uk"), 1);
			System.out.println("+1");
		}
		// userdb.removeFromCart(user1, 1);
		
	}
}
