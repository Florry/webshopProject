package se.jiv.webshop.test;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.AuthorModel;
import se.jiv.webshop.model.BookModel;
import se.jiv.webshop.model.FilmModel;
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
		
		BookModel book = new BookModel.Builder("Lord of the rings", 11,
				"Lord of the rings: the two towers", 50393248, 20000, "Book publusher company 2000")
				.build();
		BookModel book2 = new BookModel.Builder("Lord of the rings", 11,
				"Lord of the rings: the two towers", 5093248, 20000, "Book publusher company 2000")
				.build();
		BookModel book3 = new BookModel.Builder("Lord of the rings", 11,
				"Lord of the rings: the two towers", 5093248, 20000, "Book publusher company 2000")
				.build();
		
		FilmModel film = new FilmModel.Builder("Terminator 2", "Terminator 2: Judgement Day", 18,
				"Karl Thompsson").format("16:9").build();
		FilmModel film2 = new FilmModel.Builder("Terminator 2", "Terminator 2: Judgement Day", 18,
				"Bob Bobson").format("16:9").build();
		FilmModel film3 = (FilmModel) new FilmModel.Builder("Terminator 2",
				"Terminator 2: Judgement Day", 18, "Bob Bobson").format("16:9").build();
		
		System.out.println(book);
		System.out.println(book2);
		System.out.println(book.equals(book2));
		System.out.println(book2.equals(book3));
		
		System.out.println(film);
		System.out.println(film2);
		System.out.println(film.equals(film2));
		System.out.println(film2.equals(film3));
		
	}
}
