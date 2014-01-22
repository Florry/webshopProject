package se.jiv.webshop.model;

public final class ProductModel
{
	String name;
	String description;
	double cost;
	double rrp;

	ProductModel(String name, double cost)
	{
		this.name = name;
		this.cost = cost;
	}

	ProductModel(String name, double cost, String description)
	{
		this(name, cost);
		this.description = description;
	}

	ProductModel(String name, double cost, double rrp)
	{
		this(name, cost);
		this.rrp = rrp;
	}

	ProductModel(String name, double cost, String description, double rrp)
	{
		this(name, cost, description);
		this.rrp = rrp;
	}

	public String getName()
	{
		return this.name;
	}

	public String getDescription()
	{
		return this.description;
	}

	public double getCost()
	{
		return this.cost;
	}

	public double getRrp()
	{
		return this.rrp;
	}
	
	@Override
	public String toString(){
		return String.format("Name: " + getName() + "%nCost: " + getCost() + 
				"%nDescription: " + getDescription() + "%nRRP: " + getRrp());
	}

}
