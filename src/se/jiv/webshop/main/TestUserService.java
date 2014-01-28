package se.jiv.webshop.main;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.ProductModel;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.dao.ProductDAO;
import se.jiv.webshop.repository.dao.UserDAO;
import se.jiv.webshop.service.UserService;

public class TestUserService
{
	
	public static void main(String[] args) throws WebshopAppException
	{
		UserService service = new UserService(new UserDAO());
		ProductDAO productServ = new ProductDAO();
		UserModel user2 = new UserModel("Test@test.se", "123", "Test", "Testson", "1949-09-09",
				"0807384756", "Stockholmsv. 32", "C/O Olsen", "Stockholm", "postcode");
		service.addUser(user2);
		System.out.println(service.getUser("test@test.se"));
		UserModel updatedUser2 = new UserModel("Test@test.se", "123", "Test", "Testson",
				"1949-09-09", "0807384756", "Stockholmsv. 32", "C/O Olsen", "Uppsala", "postcode");
		service.updateUser(updatedUser2);
		
		System.out.println(service.getUser("test@test.se").toString());
		
		System.out.println("______");
		for (UserModel user : service.getAllUsers())
		{
			System.out.println(user.toString());
		}
		System.out.println("______");
		
		UserModel user3 = service.getUser("test@test.se");
		service.addProductToCart(user3, 2, 20);
		service.addProductToCart(user3, 1, 10);
		
		System.out.print(user3.getFirstname() + " ");
		System.out.println(user3.getLastname() + ":");
		System.out.println(service.getShoppingCartContents(user3).toString());
		System.out.println("______");
		
		service.removeFromCart(user3, 1, 2);
		service.updateCart(user3, 2, 30);
		System.out.print(user3.getFirstname() + " ");
		System.out.println(user3.getLastname() + ":");
		System.out.println(service.getShoppingCartContents(user3).toString());
		System.out.println("______");
		
		service.deleteUser(user3);
		
		for (ProductModel prod : productServ.getAllProducts())
		{
			System.out.println(prod.getName());
		}
	}
}
