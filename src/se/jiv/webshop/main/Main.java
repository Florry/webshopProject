package se.jiv.webshop.main;

import se.jiv.webshop.model.*;
import se.jiv.webshop.repository.dao.*;

public class Main
{

	public static void main(String args[])
	{
		ProductModel newProduct = new ProductModel("Mylo Xyloto", 149);
		ProductDAO productHandler = new ProductDAO();
		System.out.println(productHandler.updateProduct("VIVA LA VIDA", productHandler.PRODUCT_COST, "200"));
		
	}
}
