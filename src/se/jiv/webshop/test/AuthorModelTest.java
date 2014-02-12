package se.jiv.webshop.test;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.AuthorModel;
import se.jiv.webshop.repository.dao.AuthorDAO;
import se.jiv.webshop.service.AuthorService;

public class AuthorModelTest
{
	public static void main(String args[])
	{
		AuthorService authors = new AuthorService(new AuthorDAO());
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
		
		System.out.println("Author: " + author + " added");
		
		try
		{
			authors.addAuthor(author);
		} catch (WebshopAppException e)
		{
			e.printStackTrace();
		}
		try
		{
			authors.updateAuthor(author2, 7);
			authors.deleteAuthor(7);
		} catch (WebshopAppException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			System.out.println(authors.getAuthor(12));
		} catch (WebshopAppException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try
		{
			System.out.println(authors.getAuthorsByName("harry"));
			System.out.println(authors.getAllAuthors());
		} catch (WebshopAppException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
