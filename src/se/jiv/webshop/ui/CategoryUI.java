package se.jiv.webshop.ui;

import java.util.List;

import se.jiv.webshop.model.CategoryModel;

public class CategoryUI extends GeneralUI
{
	public int categoryInformation(List<CategoryModel> categories)
	{
		System.out.println("Which category do you want to get information about?");

		for (CategoryModel category : categories)
		{
			System.out.println(category.getId() + ". " + category.getName());
		}

		return readInt();
	}

	public int editCategories()
	{
		System.out.println("What do you want to do with categories?");
		System.out.println("1. Add");
		System.out.println("2. Update");
		System.out.println("3. Delete");

		return readInt();
	}

	public CategoryModel updateCategory()
	{
		System.out.println("Please enter the new name of the category: ");
		String name = readString();
		System.out.println("Please enter the new id of the staff member responsible: ");
		int staffId = readInt();

		CategoryModel newCategory = new CategoryModel(name, staffId);

		return newCategory;
	}

	public int deleteCategory()
	{
		System.out.println("Enter the id of the category you want to delete: ");

		return readInt();
	}

}
