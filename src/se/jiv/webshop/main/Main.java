package se.jiv.webshop.main;

import java.util.LinkedHashMap;
import java.util.Map;

import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.dao.UserDAO;
import se.jiv.webshop.service.UserService;

public class Main
{
	public static void main(String args[])
	{
		// TEST FILES
		UserService userService = new UserService(new UserDAO());
		
		UserModel user1 = userService.getUser("dic2tum@neque.edu");
		Map<Integer, Integer> contents = new LinkedHashMap<Integer, Integer>();
		
		contents = userService.getShoppingCartContents(user1);
		
		System.out.println(contents.toString());
	}
}
