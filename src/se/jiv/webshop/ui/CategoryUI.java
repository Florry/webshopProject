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

	public int categoryMenu()
	{
		System.out.println("Category Menu");
		System.out.println("1. Add Category");
		System.out.println("2. Update Category");
		System.out.println("3. Delete Category");
		System.out.println("4. Get Category");
		System.out.println("5. Get all Category");

		return readInt();
	}

	public CategoryModel addCategory()
	{
		System.out.println("Please enter the name of the category you want to create: ");
		String categoryName = readString();
		System.out.println("Please enter the id nr of the of the staff member responsible for \nthe new category: ");
		int categoryId = readInt();

		CategoryModel newCategory = new CategoryModel(categoryName, categoryId);

		return newCategory;
	}

	public void addedCategory(CategoryModel category)
	{
		System.out.println("This is the category you created: ");
		System.out.println("Id: " + category.getId());
		System.out.println("Name: " + category.getName());
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

	public String getCategory()
	{
		System.out.println("Please enter the name of the category: ");
		String categoryName = readString();

		return categoryName;
	}

	public void foundCategory(CategoryModel category)
	{
		System.out.println("This is the category your searched for: ");
		System.out.println("Id: " + category.getId());
		System.out.println("Name: " + category.getName());
		System.out.println("Id # of staff member resposible: " + category.getStaff_responsible());
	}

	public void getAllCategories(List<CategoryModel> categories)
	{
		for (CategoryModel category : categories)
		{
			System.out.println();
			System.out.println("Id: " + category.getId());
			System.out.println("Name: " + category.getName());
			System.out.println("Id # of staff member responsible: " + category.getStaff_responsible());
		}
	}

}
