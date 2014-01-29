package se.jiv.webshop.ui;

import java.util.List;

import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.model.ProductModel;

public class ProductUI extends GeneralUI
{
	public String productInformation()
	{
		System.out.println("Please enter the name of the product you information about: ");

		return readString();
	}

	public int editProduct()
	{
		System.out.println("What do you want to do with products?");
		System.out.println("1. Edit");
		System.out.println("2. Add");
		System.out.println("3. Delete");

		return readInt();
	}

	public ProductModel createProduct()
	{
		System.out.println("Please enter the name of the product you want to create: ");
		String name = readString();
		System.out.println("Please enter the description of the product: ");
		String description = readString();
		System.out.println("Please enter the cost of the product: ");
		double cost = readInt();
		System.out.println("Please enter the rrp of the product: ");
		double rrp = readInt();

		ProductModel newProduct = new ProductModel(name, description, cost, rrp);

		return newProduct;
	}

	public int updateProductId()
	{
		System.out.println("Please enter the id of the product you want to update: ");

		return readInt();
	}

	public ProductModel updateProduct()
	{
		System.out.println("Please enter the new name of the product: ");
		String name = readString();
		System.out.println("Please enter the new description of the product: ");
		String description = readString();
		System.out.println("Please enter the new cost of the product: ");
		double cost = readInt();
		System.out.println("Please enter the new rrp of the product: ");
		double rrp = readInt();

		ProductModel newProduct = new ProductModel(name, description, cost, rrp);

		return newProduct;
	}

	public int deleteProduct()
	{
		System.out.println("Please enter the id of the product you want to delete");

		return readInt();
	}

	public int productsByCategory(List<CategoryModel> categories)
	{

		System.out.println("What category do you want to retrieve information from: ");

		for (CategoryModel category : categories)
		{
			System.out.println(category.getId() + ". " + category.getName());
		}

		return readInt();
	}

}
