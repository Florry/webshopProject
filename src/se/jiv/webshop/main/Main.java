package se.jiv.webshop.main;

import se.jiv.webshop.model.*;

public class Main
{
	public static void main(String args[])
	{
		// TEST FILES
		ProductModel product1 = new ProductModel("apple", 20, "a fruit", 300);
		UserModel user1 = new UserModel("apple@apple.com", "Password001", "Bob", "Johnson",
				"1940-03-04", "0704438573", "Roadrd. 2", "C/O bobson", "New York", "19587");
		
		System.out.println(String.format("%s %n%n%s", product1.toString(), user1.toString()));
	}
}
