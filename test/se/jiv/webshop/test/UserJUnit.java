package se.jiv.webshop.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.After;
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
		if (getUserTest(user1) == null)
		{
			addUserTest(user1);
		}
	}
	
	@After
	public void after()
	{
		try
		{
			deleteUserTest(user1);
		} catch (WebshopAppException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void addUserTest(UserModel user) throws WebshopAppException
	{
		try (Connection conn = DevDBConfig.getConnection())
		{
			String sql = "INSERT INTO users (password, firstname, lastname, dob, telephone, address1, address2, town, postcode, email)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			try (PreparedStatement pstmt = conn.prepareStatement(sql))
			{
				prepareStatementFromModel(pstmt, user);
				pstmt.executeUpdate();
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private UserModel getUserTest(UserModel user) throws WebshopAppException
	{
		try (Connection conn = DevDBConfig.getConnection())
		{
			String sql = "SELECT * FROM users WHERE email = ?";
			
			try (PreparedStatement pstmt = conn.prepareStatement(sql))
			{
				pstmt.setString(1, user.getEmail());
				try (ResultSet rs = pstmt.executeQuery())
				{
					if (rs.next())
					{
						return parseModel(rs);
					}
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private UserModel getUserTest(String email) throws WebshopAppException
	{
		try (Connection conn = DevDBConfig.getConnection())
		{
			String sql = "SELECT * FROM users WHERE email = ?";
			
			try (PreparedStatement pstmt = conn.prepareStatement(sql))
			{
				pstmt.setString(1, email);
				try (ResultSet rs = pstmt.executeQuery())
				{
					if (rs.next())
					{
						return parseModel(rs);
					}
				}
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private void deleteUserTest(UserModel user) throws WebshopAppException
	{
		try (Connection conn = DevDBConfig.getConnection())
		{
			String sql = "DELETE FROM users WHERE email = ?";
			
			try (PreparedStatement pstmt = conn.prepareStatement(sql))
			{
				pstmt.setString(1, user.getEmail());
				
				pstmt.executeUpdate();
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private void prepareStatementFromModel(PreparedStatement pstmt, UserModel user)
			throws SQLException
	{
		pstmt.setString(1, user.getPassword());
		pstmt.setString(2, user.getFirstname());
		pstmt.setString(3, user.getLastname());
		pstmt.setString(4, user.getDob());
		pstmt.setString(5, user.getTelephone());
		pstmt.setString(6, user.getAddress1());
		pstmt.setString(7, user.getAddress2());
		pstmt.setString(8, user.getTown());
		pstmt.setString(9, user.getPostcode());
		pstmt.setString(10, user.getEmail());
	}
	
	private UserModel parseModel(ResultSet rs) throws SQLException
	{
		String db_email = rs.getString("email");
		String db_password = rs.getString("password");
		String db_firstname = rs.getString("firstname");
		String db_lastname = rs.getString("lastname");
		String db_dob = "" + rs.getDate("dob");
		String db_telephone = rs.getString("telephone");
		String db_address1 = rs.getString("address1");
		String db_address2 = rs.getString("address2");
		String db_town = rs.getString("town");
		String db_postcode = rs.getString("postcode");
		
		return new UserModel.Builder(db_email, db_password, db_firstname, db_lastname, db_address1,
				db_town, db_postcode).address2(db_address2).dob(db_dob).telephone(db_telephone)
				.build();
	}
	
	@Test
	public void canAddUser()
	{
		UserModel getUser = null;
		try
		{
			if (getUserTest(user1) != null)
			{
				deleteUserTest(user1);
			}
			users.addUser(user1);
			getUser = getUserTest(user1);
		} catch (WebshopAppException e)
		{
			e.printStackTrace();
		}
		assertTrue(user1.equals(getUser));
	}
	
	@Test
	public void canAddUserThatExists()
	{
		boolean wasExcpetion = false;
		try
		{
			users.addUser(user1);
		} catch (WebshopAppException e)
		{
			wasExcpetion = true;
		}
		assertTrue(wasExcpetion);
	}
	
	@Test
	public void canAddNullUser()
	{
		boolean wasException = false;
		try
		{
			users.addUser(null);
		} catch (WebshopAppException e)
		{
			wasException = true;
		}
		assertTrue(wasException);
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
			e.printStackTrace();
		}
		assertTrue(getUser != null);
	}
	
	@Test
	public void canGetUserThatDoesNotExist()
	{
		UserModel getUser = null;
		try
		{
			getUser = users
					.getUser("This is a string that will never be inside the email collumn!");
		} catch (WebshopAppException e)
		{
			e.printStackTrace();
			fail("Exception!");
		}
		assertNull(getUser);
	}
	
	@Test
	public void canGetNullUser()
	{
		boolean wasException = false;
		UserModel getUser = null;
		try
		{
			getUser = users.getUser(null);
		} catch (WebshopAppException e)
		{
			wasException = true;
		}
		assertTrue(wasException);
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
			updatedUser = getUserTest("bbq@test.se");
		} catch (WebshopAppException e)
		{
			e.printStackTrace();
		}
		assertTrue(updatedUser.getPassword().equals(newUser.getPassword()));
	}
	
	@Test
	public void canUpdateNullUser()
	{
		boolean wasException = false;
		try
		{
			users.updateUser(null);
		} catch (WebshopAppException e)
		{
			wasException = true;
		}
		assertTrue(wasException);
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
			getUser = getUserTest("bbq@test.se");
		} catch (WebshopAppException e)
		{
			e.printStackTrace();
		}
		assertTrue(getUser == null);
	}
	
	@Test
	public void canDeleteNullUser()
	{
		boolean wasException = false;
		try
		{
			users.deleteUser(null);
		} catch (WebshopAppException e)
		{
			wasException = true;
		}
		assertTrue(wasException);
		
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
			if (getUserTest(user2) == null)
			{
				addUserTest(user2);
			}
			loggedIn = users.validateLogin(user2.getEmail(), user2.getPassword());
			loggedInFailed = users.validateLogin(user2.getEmail(), "tabasco");
		} catch (WebshopAppException e)
		{
			e.printStackTrace();
		}
		assertTrue(loggedIn && !loggedInFailed);
	}
	
	@Test
	public void canLoginWithNull()
	{
		boolean login = false;
		try
		{
			login = users.validateLogin(null, null);
		} catch (WebshopAppException e)
		{
			e.printStackTrace();
		}
		assertFalse(login);
	}
}
