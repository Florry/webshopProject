package se.jiv.webshop.ui;

import java.util.Map;

import se.jiv.webshop.model.UserModel;

public class ShoppingCartUI extends GeneralUI
{
	public int ShoppingCartMenu()
	{
		System.out.println("Shopping Cart Menu");
		System.out.println("Select an Action");
		System.out.println("1. Get Contents of User");
		System.out.println("2. Add product to cart of User");
		System.out.println("3. Remove product from cart of User");
		System.out.println("4. Reset cart of User");
		
		return readInt();
	}
	
	protected String askForEmail(String action)
	{
		String email;
		System.out.println(action + ": \n Enter the email address of the User");
		email = readString();
		return email;
	}
	
	protected void getContents(Map<Integer, Integer> cart)
	{
		System.out.println("Cart:");
		for (Integer item : cart.keySet())
		{
			System.out.println(item + " " + cart.get(item));
		}
	}
	
	protected int addProductToCart(UserModel user)
	{
		
		System.out.println("Add product to cart of user " + user.getFirstname() + " "
				+ user.getLastname() + " (" + user.getEmail()
				+ ")\nEnter product id to add to the cart");
		return readInt();
	}
	
	// Remove product from shopping cart
	protected int askForProductId()
	{
		System.out.println("Enter produt id of the product you want to remove from the cart:");
		return readInt();
	}
	
	protected int askForQuantity()
	{
		System.out.println("Enter quantity to remove from cart");
		return readInt();
	}
	
	// public static void main(String args[]) throws WebshopAppException
	// {
	// UserService uS = new UserService(new UserDAO(), new ShoppingCartDAO());
	// ShoppingCartUI sC = new ShoppingCartUI();
	// UserUI uU = new UserUI();
	// sC.getContents(uS.getShoppingCartContents(uS.getUser(sC
	// .askForEmail("Get Shopping Cart Contents"))));
	//
	// }
}
