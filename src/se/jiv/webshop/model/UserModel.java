package se.jiv.webshop.model;

public final class UserModel
{
	private final String email;
	private final String password;
	private final String firstname;
	private final String lastname;
	private final String dob;
	private final String telephone;
	private final String address1;
	private final String address2;
	private final String town;
	private final String postcode;
	
	public UserModel(String email, String password, String firstname, String lastname,
			String address1, String town, String postcode)
	{
		this(email, password, firstname, lastname, "", "", address1, "", town, postcode);
		
	}
	
	public UserModel(String email, String password, String firstname, String lastname,
			String address1, String address2, String town, String postcode)
	{
		this(email, password, firstname, lastname, "", "", address1, address2, town, postcode);
		
	}
	
	public UserModel(String email, String password, String firstname, String lastname,
			String telephone, String address1, String address2, String town, String postcode)
	{
		this(email, password, firstname, lastname, "", telephone, address1, address2, town,
				postcode);
		
	}
	
	public UserModel(String email, String password, String firstname, String lastname, String dob,
			String telephone, String address1, String address2, String town, String postcode)
	{
		this.email = email;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dob = dob;
		this.telephone = telephone;
		this.address1 = address1;
		this.address2 = address2;
		this.town = town;
		this.postcode = postcode;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getFirstname()
	{
		return firstname;
	}
	
	public String getLastname()
	{
		return lastname;
	}
	
	public String getDob()
	{
		return dob;
	}
	
	public String getTelephone()
	{
		return telephone;
	}
	
	public String getAddress1()
	{
		return address1;
	}
	
	public String getAddress2()
	{
		return address2;
	}
	
	public String getTown()
	{
		return town;
	}
	
	public String getPostcode()
	{
		return postcode;
	}
	
	public String toString()
	{
		return String.format(
				"User: %s Firstname: %s Lastname: %s Dob: %s Telephone: %s Address: %s %s %s %s",
				getEmail(), getFirstname(), getLastname(), getDob(), getTelephone(), getAddress1(),
				getAddress2(), getTown(), getPostcode());
	}
	
	// public static void main(String args[])
	// {
	// UserModel user = new UserModel("test@test.se", "lol", "bob", "bobson",
	// "2010-02-23",
	// "0703298678", "address field 1", "address field 2", "sthlm", "14462");
	// System.out.println(user.toString());
	// }
}
