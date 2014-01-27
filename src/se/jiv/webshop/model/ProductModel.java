package se.jiv.webshop.model;

import java.util.ArrayList;
import java.util.List;

public final class ProductModel
{
	String name;
	String description;
	double cost;
	double rrp;
	int id;
	List<String> categories;

	public ProductModel(String name, double cost)
	{
		this.name = name;
		this.cost = cost;
		categories = new ArrayList<>();
	}

	public ProductModel(String name, double cost, String description)
	{
		this(name, cost);
		this.description = description;
	}

	public ProductModel(String name, double cost, double rrp)
	{
		this(name, cost);
		this.rrp = rrp;
	}

	public ProductModel(String name, double cost, String description, double rrp)
	{
		this(name, cost, description);
		this.rrp = rrp;
	}
	public ProductModel(int id,String name, double cost, String description, double rrp)
	{
		this(name, cost, description, rrp);
		this.id = id;
	}

	public ProductModel(ProductModel other)
	{
		this.name = other.name;
		this.description = other.description;
		this.cost = other.cost;
		this.rrp = other.rrp;
	}
	public ProductModel(int id, ProductModel other)
	{
		this.id = id;
		this.name = other.name;
		this.description = other.description;
		this.cost = other.cost;
		this.rrp = other.rrp;
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
	public String toString()
	{
		return String.format("Id: " + id + "%nName: " + getName() + "%nCost: " + getCost() +
				"%nDescription: " + getDescription() + "%nRRP: " + getRrp());
	}

}
