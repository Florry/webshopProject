package se.jiv.webshop.main;

import java.util.List;

import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.repository.dao.CategoryDAO;
import se.jiv.webshop.service.CategoryService;

public final class TestCategoryService {
	public static void printAll(List<CategoryModel> categories) {
		for (CategoryModel category : categories) {
			System.out.println(category);
		}
	}

	public static void main(String[] args) {
		CategoryService service = new CategoryService(new CategoryDAO());

		System.out.println("---------------------------------------");
		System.out.println("GET ALL CATEGORIES");
		System.out.println("---------------------------------------");
		List<CategoryModel> categories = service.getAllCategories();
		printAll(categories);

		System.out.println("---------------------------------------");
		System.out.println("GET CATEGORY BY ID 4");
		System.out.println("---------------------------------------");
		System.out.println("A finded category");
		CategoryModel category = service.getCategory(4);
		System.out.println(category);

		System.out.println("\nA not finded category");
		category = service.getCategory(400000);
		System.out.println(category);

		System.out.println("---------------------------------------");
		System.out.println("ADD CATEGORY");
		System.out.println("---------------------------------------");
		CategoryModel categoryAdded = new CategoryModel("Cameras", 27);
		categoryAdded = service.addCategory(categoryAdded);
		System.out.println("returned from service: " + categoryAdded);

		categoryAdded = service.getCategory(categoryAdded.getId());
		System.out.println("\nreturned from Databas: " + categoryAdded);

		System.out.println("---------------------------------------");
		System.out.println("REMOVE CATEGORY");
		System.out.println("---------------------------------------");
		System.out
				.println("We remove an category that exist(the previus added)");
		boolean exit = service.deleteCategory(categoryAdded.getId());
		System.out.println("it was exit: " + exit);

		System.out.println("\nWe remove an category that NOT exist");
		exit = service.deleteCategory(4000);
		System.out.println("it was exit: " + exit);

		System.out.println("\nprint alla categories again:");
		categories = service.getAllCategories();
		printAll(categories);

	}

}
