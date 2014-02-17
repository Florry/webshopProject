package se.jiv.webshop.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.dao.UserDAO;

public class UserJUnit
{
	UserDAO users = new UserDAO();
	String randomString = "";
	UserModel user1 = null;
	static final String JDBC_DRIVER = DevDBConfig.JDBC_DRIVER;
	static final String DB_URL = DevDBConfig.DB_URL;
	static final String DB_USER = DevDBConfig.DB_USER;
	static final String DB_PASSWORD = DevDBConfig.DB_PASSWORD;
	
	@Before
	public void onlyOnce() throws WebshopAppException
	{
		Random random = new Random();
		randomString = random.nextInt() + "";
		user1 = new UserModel.Builder("bbq@test.se", "123456", "Tom", "Whitemore",
				"Telegrafvagen 32", "Stockholm", "postcode").address2("C/O Olsen")
				.dob("1949-09-09").telephone("0807384756").build();
		UserModel user2 = users.getUser(user1.getEmail());
		if (user2 == null)
		{
			users.addUser(user1);
		}
	}
	
	@Test
	public void canAddUser()
	{
		UserModel getUser = null;
		try
		{
			getUser = users.getUser(user1.getEmail());
		} catch (WebshopAppException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(user1.equals(getUser));
		
	}
	
	@Test
	public void canGetUser()
	{
		UserModel getUser = null;
		try
		{
			getUser = users.getUser("bbq@test.se");
		} catch (WebshopAppException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue(getUser != null);
	}
	
	@Test
	public void canGetAllUsers()
	{
		List<UserModel> getUsers = new ArrayList<UserModel>();
		
		try
		{
			getUsers = users.getAllUsers();
		} catch (WebshopAppException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertFalse(getUsers.isEmpty());
	}
	
	@Test
	public void canUpdateUser()
	{
		
		UserModel newUser = new UserModel.Builder("bbq@test.se", "00700700700", "Bob", "Whitemore",
				"Telegrafvagen 32", "Stockholm", "postcode").address2("C/O Olsen")
				.dob("1949-09-09").telephone("0807384756").build();
		UserModel updatedUser = null;
		
		try
		{
			users.updateUser(newUser);
			updatedUser = users.getUser("bbq@test.se");
		} catch (WebshopAppException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(updatedUser.getPassword().equals(newUser.getPassword()));
		
	}
	
	@Test
	public void canDeleteUser()
	{
		UserModel getUser = null;
		UserModel user1 = new UserModel.Builder("bbq@test.se", randomString, randomString,
				randomString, randomString, randomString, randomString).build();
		try
		{
			users.deleteUser(user1);
			getUser = users.getUser("bbq@test.se");
		} catch (WebshopAppException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(getUser == null);
	}
	
	@Test
	public void canLogin()
	{
		UserModel user2 = new UserModel.Builder("Doctor@Frankenstein.se", "trololol", "Dr.Tom",
				"Whitemore", "Telegrafvagen 32", "Stockholm", "postcode").build();
		boolean loggedIn = false;
		boolean loggedInFailed = false;
		try
		{
			users.addUser(user2);
			loggedIn = users.validateLogin(user2.getEmail(), user2.getPassword());
			loggedInFailed = users.validateLogin(user2.getEmail(), "tabasco");
			users.deleteUser(user2);
		} catch (WebshopAppException e)
		{
			e.printStackTrace();
		}
		assertTrue(loggedIn && !loggedInFailed);
	}
}
