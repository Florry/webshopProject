package se.jiv.webshop.ui;

import java.util.ArrayList;
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

	public int productMenu()
	{
		System.out.println("User Menu");
		System.out.println("1. Add Product");
		System.out.println("2. Update Product");
		System.out.println("3. Delete Product");
		System.out.println("4. Get Product");
		System.out.println("5. Get all Product");

		return readInt();
	}

	public ProductModel createProduct()
	{
		boolean manyCategories = true;
		List<Integer> categories = new ArrayList<>();

		System.out.println("Please enter the name of the product you want to create: ");
		String name = readString();
		System.out.println("Please enter the description of the product: ");
		String description = readString();
		System.out.println("Please enter the cost of the product: ");
		double cost = readInt();
		System.out.println("Please enter the rrp of the product: ");
		double rrp = readInt();
		System.out.println("Please enter the id of the category of this product: ");
		int categoryId = readInt();
		categories.add(categoryId);

		while (manyCategories)
		{
			System.out.println("Do you want to add another category to this product? yes/no");
			String anwser = readString();
			if (anwser.equals("yes"))
			{
				System.out.println("Please enter the id of the other category of this product: ");
				categoryId = readInt();
				categories.add(categoryId);
			}
			else
			{
				manyCategories = false;
			}

		}

		ProductModel newProduct = new ProductModel(name, description, cost, rrp, categories);

		return newProduct;
	}

	public void productCreated(ProductModel product)
	{
		System.out.println("This is the product you created: ");
		System.out.println("Id: " + product.getId());
		System.out.println("Name: " + product.getName());
		System.out.println("Description: " + product.getDescription());
		System.out.println("Cost: " + product.getCost());
		System.out.println("Rrp: " + product.getRrp());
		System.out.println("Category: " + product.getCategories());

	}

	public ProductModel updateProduct()
	{
		List<Integer> categories = new ArrayList<>();
		boolean manyCategories = true;

		System.out.println("Please enter the new name of the product: ");
		String name = readString();
		System.out.println("Please enter the new description of the product: ");
		String description = readString();
		System.out.println("Please enter the new cost of the product: ");
		double cost = readInt();
		System.out.println("Please enter the new rrp of the product: ");
		double rrp = readInt();
		System.out.println("Please enter the new category of this product: ");
		int categoryId = readInt();

		categories.add(categoryId);

		while (manyCategories)
		{
			System.out.println("Do you want to add another new category to this product? yes/no");
			String anwser = readString();
			if (anwser.equals("yes"))
			{
				System.out.println("Please enter the id of the other new category of this product: ");
				categoryId = readInt();
				categories.add(categoryId);
			}
			else
			{
				manyCategories = false;
			}

		}

		ProductModel newProduct = new ProductModel(name, description, cost, rrp);

		return newProduct;
	}

	public int deleteProduct()
	{
		System.out.println("Please enter the id of the product you want to delete");

		return readInt();
	}

	public int getProduct()
	{
		System.out.println("Please enter the id of the product: ");
		int productId = readInt();

		return productId;
	}

	public void getAllProducts(List<ProductModel> products)
	{
		for (ProductModel product : products)
		{
			System.out.println();
			System.out.println("Id: " + product.getId());
			System.out.println("Name: " + product.getName());
			System.out.println("Description: " + product.getDescription());
			System.out.println("Cost: " + product.getCost());
			System.out.println("Rrp: " + product.getRrp());
			System.out.println("Category: " + product.getCategories());
		}
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
	
	public String toStringListArray(List<ProductModel> objects)
	{
		String stringVersionOfList = "";
		
		for (Object object : objects)
		{
			
				ProductModel currentProduct = (ProductModel) object;
				int id = currentProduct.getId();
				String name = currentProduct.getName();
				String description = currentProduct.getDescription();
				double cost = currentProduct.getCost();
				double rrp = currentProduct.getRrp();
				
				stringVersionOfList += "\nId: " + id + "\nName: " + name + 
						"\nDescription: " + description + "\nCost: " + cost + 
						"\nRrp: " + rrp + "\n";
		}

		return stringVersionOfList;
	}

}
