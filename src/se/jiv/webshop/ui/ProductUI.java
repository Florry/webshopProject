package se.jiv.webshop.ui;

import java.util.ArrayList;
import java.util.List;

import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.model.ProductModel;

public final class ProductUI extends GeneralUI {

	public ProductModel createProduct() {
		boolean manyCategories = true;
		List<Integer> categories = new ArrayList<>();

		System.out
				.println("Please enter the name of the product you want to create: ");
		String name = readString();
		System.out.println("Please enter the description of the product: ");
		String description = readString();
		System.out.println("Please enter the cost of the product: ");
		double cost = readInt();
		System.out.println("Please enter the rrp of the product: ");
		double rrp = readInt();
		System.out
				.println("Please enter the id of the category of this product: ");
		int categoryId = readInt();
		categories.add(categoryId);

		while (manyCategories) {
			System.out
					.println("Do you want to add another category to this product? yes/no");
			String anwser = readString();
			if (anwser.equals("yes")) {
				System.out
						.println("Please enter the id of the other category of this product: ");
				categoryId = readInt();
				categories.add(categoryId);
			} else {
				manyCategories = false;
			}

		}

		ProductModel newProduct = ProductModel.builder(name)
				.description(description).cost(cost).rrp(rrp)
				.categories(categories).build();

		return newProduct;
	}

	public ProductModel updateProduct(int productId) {
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

		while (manyCategories) {
			System.out
					.println("Do you want to add another new category to this product? yes/no");
			String anwser = readString();
			if (anwser.equals("yes")) {
				System.out
						.println("Please enter the id of the other new category of this product: ");
				categoryId = readInt();
				categories.add(categoryId);
			} else {
				manyCategories = false;
			}

		}

		ProductModel newProduct = ProductModel.builder(name).id(productId)
				.description(description).cost(cost).rrp(rrp)
				.categories(categories).build();

		return newProduct;
	}

	public int askCategoryToSearch(List<CategoryModel> categories) {

		System.out.println("What category do you want to search books: ");

		for (CategoryModel category : categories) {
			System.out.println(category.getId() + ". " + category.getName());
		}

		return readInt();
	}

	public void showProductsSearch(List<ProductModel> products) {
		System.out.println();
		if (products.size() == 0) {
			System.out.println("We don't found any product in the search.");
		} else {
			System.out.println("Products finded in the search: ");
			for (ProductModel product : products) {
				System.out.println(product);
			}
		}
	}

	public int askProductId() {
		System.out.println("Please enter the id of the product: ");

		return readInt();
	}

	public String askProductName() {
		System.out
				.println("Please enter the name of the product that you want to search: ");

		return readString();
	}

	public void showCreateSuccess(ProductModel newProduct) {
		System.out.println("You have created a new product:");
		System.out.println(newProduct);
	}

	public void showUpdatedSuccess() {
		System.out.println("You have updated your product");
	}

	public void showProductNotFound() {
		System.out.println("Product not found.");
	}

}
