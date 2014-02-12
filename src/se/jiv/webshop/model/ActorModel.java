package se.jiv.webshop.model;

public class ActorModel
{
	int id;
	String firstname;
	String lastname;
	String dob;

	ActorModel(int id, String firstname, String lastname)
	{
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;

	}

	ActorModel(int id, String firstname, String lastname, String dob)
	{
		this(id, firstname, lastname);
		this.dob = dob;

	}

	ActorModel(int id, ActorModel other)
	{
		this(id, other.firstname, other.lastname);
	}

	String getFirstname()
	{
		return firstname;
	}

	String getLastname()
	{
		return lastname;
	}

	@Override
	public int hashCode()
	{
		return 43 * id;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj instanceof ActorModel)
		{
			ActorModel currentActor = (ActorModel) obj;
			if (currentActor.id == this.id)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString()
	{
		return String.format("Id: " + id + "%nFirstname: " + firstname + "%nLastname: " + lastname + "%nDate of birth: " + dob);
	}

}
