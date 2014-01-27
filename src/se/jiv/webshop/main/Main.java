package se.jiv.webshop.main;

import se.jiv.webshop.model.*;
import se.jiv.webshop.repository.dao.*;
import java.util.LinkedHashMap;
import java.util.Map;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.dao.UserDAO;
import se.jiv.webshop.service.UserService;


public class Main
{
<<<<<<< HEAD

	public static void main(String args[])
	{

		ProductModel newProduct = new ProductModel("Parachutes",149, "So good", 3000);
		ProductDAO productHandler = new ProductDAO();
//		System.out.println(productHandler.updateProduct(newProduct, 11));
		
		System.out.println(productHandler.getProductById(10));

=======
	public static void main(String args[]) throws WebshopAppException
	{
		// TEST FILES
		UserService userService = new UserService(new UserDAO());
		
		UserModel user1 = userService.getUser("sed2.facilisis@Proinnon.edu");
		Map<Integer, Integer> contents = new LinkedHashMap<Integer, Integer>();
		userService.addProductToCart(user1, 1, 20);
		userService.addProductToCart(user1, 1, 10);
		userService.addProductToCart(user1, 1, 1);
		userService.addProductToCart(user1, 2, 1);
		userService.addProductToCart(user1, 2, 2);
		userService.addProductToCart(user1, 2, 30);
>>>>>>> f223c62636dfc054a803ed9cca2b66e04e8ecbd8
	}
}
