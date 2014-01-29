package se.jiv.webshop.ui;

import java.util.Map;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.dao.ShoppingCartDAO;
import se.jiv.webshop.repository.dao.UserDAO;
import se.jiv.webshop.service.UserService;

public class UserUI extends GeneralUI
{
	// Create User
	// Update User
	// Delete User
	// Get user
	// Get all users
	
	public UserModel createUser()
	{
		String email = null;
		String password = null;
		String firstname = null;
		String lastname = null;
		String dob = null;
		String telephone = null;
		String address1 = null;
		String address2 = null;
		String town = null;
		String postcode = null;
		boolean correctDate = false;
		UserModel user = null;
		
		System.out.println("Enter the firstname of the new user: ");
		firstname = readString();
		System.out.println("Enter the lastname of the new user: ");
		lastname = readString();
		System.out.println("Enter the email of the new user: ");
		email = readString();
		System.out.println("Enter the password of the new user: ");
		password = readString();
		System.out.println("Enter the date of birth of the new user: (yyyy-mm-dd) ");
		do
		{
			dob = readString();
			if (dob.matches("\\d{4}-\\d{2}-\\d{2}"))
			{
				correctDate = true;
			} else
			{
				System.out.println("Please use the format: yyyy-mm-dd - Your input was: " + dob);
			}
		} while (correctDate == false);
		
		System.out.println("Enter a telephone number of the new user: ");
		telephone = readString();
		System.out.println("Enter an address of the new user:");
		address1 = readString();
		System.out.println("Enter a C/O of the new user:");
		address2 = readString();
		System.out.println("Enter a town of the new user:");
		town = readString();
		System.out.println("Enter a postcode of the new user:");
		postcode = readString();
		
		user = new UserModel(email, password, firstname, lastname, dob, telephone, address1,
				address2, town, postcode);
		
		System.out.println(user.toString());
		
		return user;
	}
	
	protected String askForEmail()
	{
		String email;
		System.out.println("Update user \n Enter the email address of the user to update");
		email = readString();
		return email;
	}
	
	protected UserModel updateUser(UserModel user)
	{
		String email = user.getEmail();
		String password = user.getPassword();
		String firstname = user.getFirstname();
		String lastname = user.getLastname();
		String dob = user.getDob();
		String telephone = user.getTelephone();
		String address1 = user.getAddress1();
		String address2 = user.getAddress2();
		String town = user.getTown();
		String postcode = user.getPostcode();
		int selectedUpdate = 0;
		boolean correctDate = false;
		boolean updateDone = false;
		UserModel newUser;
		
		System.out.println("Updating: " + user.toString());
		do
		{
			System.out.println("Fields to update: \n" + "1. Firstname \n" + "2. Lastname \n"
					+ "3. password \n" + "4. date of birth \n" + "5. telephone number \n"
					+ "6. address \n" + "7. town \n" + "8. postcode \n");
			do
			{
				selectedUpdate = readInt();
			} while (selectedUpdate <= 0);
			
			switch (selectedUpdate)
			{
				case 1:
					System.out.println("Enter the new firstname of the user");
					firstname = readString();
					break;
				case 2:
					System.out.println("Enter the new lastname of the user");
					lastname = readString();
					break;
				case 3:
					System.out.println("Enter the new password of the user");
					password = readString();
					break;
				case 4:
					System.out.println("Enter the new date of birth of the user (yyyy-mm-dd)");
					do
					{
						dob = readString();
						if (dob.matches("\\d{4}-\\d{2}-\\d{2}"))
						{
							correctDate = true;
						} else
						{
							System.out
									.println("Please use the format: yyyy-mm-dd - Your input was: "
											+ dob);
						}
					} while (correctDate == false);
					break;
				case 5:
					System.out.println("Enter the new telephone number of the user");
					telephone = readString();
					break;
				case 6:
					System.out.println("Enter the new address of the user");
					address1 = readString();
					
					System.out.println("Enter the new c/o of the user");
					address2 = readString();
					break;
				case 7:
					System.out.println("Enter the new town of the user");
					town = readString();
					break;
				case 8:
					System.out.println("Enter the new postcode of the user");
					postcode = readString();
					break;
			}
			
			String yes = null;
			do
			{
				System.out.println("Do you want to update further? y/n");
				yes = readString();
			} while (!yes.equals("y") && !yes.equals("n"));
			if (yes.equals("n"))
			{
				updateDone = true;
			}
		} while (updateDone == false);
		
		newUser = new UserModel(email, password, firstname, lastname, dob, telephone, address1,
				address2, town, postcode);
		
		System.out.println("updated user: " + user.toString());
		
		return newUser;
	}
	
	protected UserModel deleteUser()
	{
		String email = null;
		String password = null;
		String firstname = null;
		String lastname = null;
		String dob = null;
		String telephone = null;
		String address1 = null;
		String address2 = null;
		String town = null;
		String postcode = null;
		UserModel user;
		
		System.out.println("Delete user \n Enter the email address of the user to delete");
		email = readString();
		
		System.out.println("Deleting user: " + email);
		
		user = new UserModel(email, password, firstname, lastname, dob, telephone, address1,
				address2, town, postcode);
		return user;
	}
	
	protected String getUser()
	{
		String email = null;
		System.out.println("Get user \n Enter the email adress of the user you want to get");
		email = readString();
		System.out.println("Getting user: " + email);
		return email;
	}
	
	protected void getUsers()
	{	
		
	}
	
	public static void main(String args[]) throws WebshopAppException
	{
		UserService uS = new UserService(new UserDAO(), new ShoppingCartDAO());
		UserUI uU = new UserUI();
		
		uS.updateUser(uU.updateUser(uS.getUser(uU.askForEmail())));
	}
	
}
