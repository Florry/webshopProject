package se.jiv.webshop.model;

public class AuthorModel
{
	private final String firstname;
	private final String lastname;
	private final String dob;
	private final String country;
	
	public static class Builder
	{
		// required fields
		private final String firstname;
		private final String lastname;
		
		// optional fields
		private String dob;
		private String country;
		
		public Builder(String firstname, String lastname)
		{
			this.firstname = firstname;
			this.lastname = lastname;
		}
		
		public Builder dob(String dob)
		{
			this.dob = dob;
			return this;
		}
		
		public Builder country(String country)
		{
			this.country = country;
			return this;
		}
		
		public AuthorModel build()
		{
			return new AuthorModel(this);
		}
	}
	
	private AuthorModel(Builder builder)
	{
		this.firstname = builder.firstname;
		this.lastname = builder.lastname;
		this.dob = builder.dob;
		this.country = builder.country;
		
	}
	
	public String getDob()
	{
		return dob;
	}
	
	public String getCountry()
	{
		return country;
	}
	
	public String getFirstname()
	{
		return firstname;
	}
	
	public String getLastname()
	{
		return lastname;
	}
	
	@Override
	public String toString()
	{
		return String.format("Author name: %s %s, Dob: %s, from: %s", this.getFirstname(),
				this.getLastname(), this.getDob(), this.getCountry());
	}
	
	@Override
	public int hashCode()
	{
		int result = 1;
		result += 37 * this.firstname.hashCode();
		result += 37 * this.lastname.hashCode();
		if (this.dob != null)
		{
			result += 37 * this.dob.hashCode();
		}
		if (this.country != null)
		{
			result += 37 * this.country.hashCode();
		}
		result += 37 * this.getClass().hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (other == this)
		{
			return true;
		}
		
		if (other instanceof AuthorModel)
		{
			AuthorModel otherAuthor = (AuthorModel) other;
			boolean isSameClass = this.getClass().equals(otherAuthor.getClass());
			
			return (firstname.equals(otherAuthor.firstname))
					&& (lastname.equals(otherAuthor.lastname)) && (dob.equals(otherAuthor.dob))
					&& (country.equals(otherAuthor.country)) && isSameClass;
		}
		
		return false;
	}
	
}
