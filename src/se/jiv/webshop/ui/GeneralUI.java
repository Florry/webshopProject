package se.jiv.webshop.ui;

import java.util.List;
import java.util.Scanner;

import se.jiv.webshop.model.ProductModel;

public abstract class GeneralUI
{
	protected String readString()
	{
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}

	protected int readInt()
	{
		while (true)
		{
			Scanner scanner = new Scanner(System.in);
			int value = -1;
			try
			{
				value = scanner.nextInt();
			}
			catch (Exception e)
			{
				System.out.println("It have to be a number. Write it again:");
				continue;
			}

			return value;
		}
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
