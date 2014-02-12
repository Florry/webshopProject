package se.jiv.webshop.test;

import se.jiv.webshop.model.AuthorModel;

public class AuthorModelTest
{
	public static void main(String args[])
	{
		AuthorModel author = new AuthorModel.Builder("Harry", "Otter").dob("1962-03-19")
				.country("Finland").build();
		AuthorModel author2 = new AuthorModel.Builder("Perry", "Otter").dob("1962-03-19")
				.country("Finland").build();
		AuthorModel author3 = new AuthorModel.Builder("Harry", "Otter").dob("1962-03-19")
				.country("Finland").build();
		AuthorModel author4 = new AuthorModel.Builder("Harry", "Otter").build();
		
		System.out.println(author);
		
		System.out.println(author + " = " + author2 + " = " + author.equals(author2));
		System.out.println(author + " = " + author3 + " = " + author.equals(author3));
		System.out.println(author + " = " + author4 + " = " + author.equals(author4));
		
		System.out.println();
		
		System.out.println(String.format("author: %s %s's hashcode = %s", author.getFirstname(),
				author.getLastname(), author.hashCode()));
		System.out.println(String.format("author3: %s %s's hashcode = %s", author2.getFirstname(),
				author2.getLastname(), author2.hashCode()));
		System.out.println(String.format("author3: %s %s's hashcode = %s", author3.getFirstname(),
				author3.getLastname(), author3.hashCode()));
		System.out.println(String.format("author4: %s %s's hashcode = %s", author4.getFirstname(),
				author4.getLastname(), author4.hashCode()));
	}
}
