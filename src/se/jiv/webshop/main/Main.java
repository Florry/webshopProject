package se.jiv.webshop.main;

import se.jiv.webshop.model.*;
import se.jiv.webshop.repository.dao.*;
import java.util.LinkedHashMap;
import java.util.Map;

import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.dao.UserDAO;
import se.jiv.webshop.service.UserService;


public class Main
{

	public static void main(String args[])
	{

		ProductModel newProduct = new ProductModel("Parachutes",149, "So good", 3000);
		ProductDAO productHandler = new ProductDAO();
//		System.out.println(productHandler.updateProduct(newProduct, 11));
		
		System.out.println(productHandler.getProductById(10));

	}
}
