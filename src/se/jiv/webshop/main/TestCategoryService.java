package se.jiv.webshop.main;

import java.util.List;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.repository.dao.CategoryDAO;
import se.jiv.webshop.service.CategoryService;

public final class TestCategoryService {
	public static void printAll(List<CategoryModel> categories) {
		for (CategoryModel category : categories) {
			System.out.println(category);
		}
	}

	private static void testGetAllCategories(CategoryService service) {
		List<CategoryModel> categories = null;
		try {
			categories = service.getAllCategories();
			printAll(categories);
		} catch (WebshopAppException e) {
			System.err.println("Exception: " + e.getActionName() + ": " + e.getMessage());
		}
	}

	private static CategoryModel testGetCategory(CategoryService service, int id) {
		CategoryModel category = null;
		try {
			category = service.getCategory(id);
			System.out.println(category);

			return category;
		} catch (WebshopAppException e) {
			System.err.println("Exception: " + e.getActionName() + ": " + e.getMessage());
		}
		return null;
	}

	private static CategoryModel testAddCategory(CategoryService service,
			CategoryModel category) {
		try {
			System.out.println("Trying to add: \n" + category);
			category = service.addCategory(category);
			System.out.println("It worked!!");
			System.out.println("Object returned from the service: \n" + category);
			return category;
		} catch (WebshopAppException e) {
			System.out.println("It NOT worked!!");
			System.err.println("Exception: " + e.getActionName() + ": " + e.getMessage());
		}
		return null;
	}
	
	private static boolean testUpdateCategory(CategoryService service,
			CategoryModel category) {
		try {
			System.out.println("Before the update in BBDD have the value: ");
			testGetCategory(service, category.getId());
			boolean updated = service.updateCategory(category);
			System.out.println("Was it updated: " + updated);
			return updated;
		} catch (WebshopAppException e) {
			System.err.println("Exception: " + e.getActionName() + ": " + e.getMessage());
		}
		return false;
	}
	
	private static boolean testDeleteCategory(CategoryService service, int id) {
		try {
			boolean deleted = service.deleteCategory(id);
			System.out.println("Was it removed?: " + deleted);
			return deleted;
		} catch (WebshopAppException e) {
			System.err.println("Exception: " + e.getActionName() + ": " + e.getMessage());
		}
		return false;
	}

	public static void main(String[] args) {
		CategoryService service = new CategoryService(new CategoryDAO());

		System.out.println("---------------------------------------");
		System.out.println("GET ALL CATEGORIES");
		System.out.println("---------------------------------------");
		System.out.println("All categories in BBDD");
		testGetAllCategories(service);

		System.out.println("---------------------------------------");
		System.out.println("GET CATEGORY BY ID");
		System.out.println("---------------------------------------");
		System.out.println("We get a category that exist in BBDD(ID=4)");
		testGetCategory(service, 4);

		System.out.println("\nWe try to get a category that not exist in BBDD(ID=4000)");
		testGetCategory(service, 40000);

		System.out.println("---------------------------------------");
		System.out.println("ADD CATEGORY");
		System.out.println("---------------------------------------");
		System.out.println("We add a new category");
		CategoryModel categoryAdded = testAddCategory(service,
				new CategoryModel("Cameras test add", 27));
		System.out.println("We get the new categoy from BBDD(to compare): ");
		testGetCategory(service, categoryAdded.getId());

		System.out.println("\nTesting errors:");
		System.out.println("- insert a category without a name (null)");
		testAddCategory(service,
				new CategoryModel(null, 27));
		
		System.out
				.println("\n- insert a category with the same name that another existent (Cameras test add)");
		testAddCategory(service,
				new CategoryModel("Cameras test add", 27));
		
		System.out
				.println("\n- insert a category with a not existing staff responsible(staff_id=4000000)");
		testAddCategory(service,
				new CategoryModel("asdfadsfaadsfafa", 4000000));
		
		System.out.println("---------------------------------------");
		System.out.println("UPDATE CATEGORY");
		System.out.println("---------------------------------------");
		System.out.println("Update the category inserted, set desc to (Cameras test add 2) ");
		testUpdateCategory(service,
				new CategoryModel(categoryAdded.getId(),categoryAdded.getName() + " 2", categoryAdded.getStaff_responsible()));
		System.out.println("After the update retrieve from BBDD(to compare): ");
		testGetCategory(service, categoryAdded.getId());
		
		System.out.println("\nTesting errors:");
		System.out.println("- update a category without a name (null)");
		testUpdateCategory(service,
				new CategoryModel(categoryAdded.getId(),null, 27));
		
		System.out
				.println("\n- update a category with the same name that another existent (Books)");
		testUpdateCategory(service, new CategoryModel(categoryAdded.getId(),"Books", categoryAdded.getStaff_responsible()));
		
		System.out
				.println("\n- update a category with a not existing staff responsible(staff_id=4000000)");
		testUpdateCategory(service,
				new CategoryModel(categoryAdded.getId(),"asdfadsfaadsfafa", 4000000));
		
		System.out.println("---------------------------------------");
		System.out.println("REMOVE CATEGORY");
		System.out.println("---------------------------------------");
		System.out
				.println("We remove an category that exist(the previus added with id=" + categoryAdded.getId() + ")");
		testDeleteCategory(service, categoryAdded.getId());
				System.out.println("\nWe remove an category that NOT exist(id=4000000)");
		testDeleteCategory(service, 4000000);
		
		System.out.println("---------------------------------------");
		System.out.println("TEST ENDED");
		System.out.println("---------------------------------------");
		System.out
				.println("All categories in BBDD (It should be exactly the same that the start list)");
		testGetAllCategories(service);

	}
}
