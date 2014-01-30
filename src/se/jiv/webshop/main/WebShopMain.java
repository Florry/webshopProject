package se.jiv.webshop.main;

import java.util.List;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.model.ProductModel;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.dao.CategoryDAO;
import se.jiv.webshop.repository.dao.ProductDAO;
import se.jiv.webshop.repository.dao.ShoppingCartDAO;
import se.jiv.webshop.repository.dao.UserDAO;
import se.jiv.webshop.service.CategoryService;
import se.jiv.webshop.service.ProductService;
import se.jiv.webshop.service.UserService;
import se.jiv.webshop.ui.CategoryUI;
import se.jiv.webshop.ui.ExceptionUI;
import se.jiv.webshop.ui.MainMenuUI;
import se.jiv.webshop.ui.ProductUI;
import se.jiv.webshop.ui.ShoppingCartUI;
import se.jiv.webshop.ui.UserUI;

public final class WebShopMain {

	public static void main(String[] args) {
		MainMenuUI mainMenu = new MainMenuUI();
		boolean exit = false;

		while (!exit) {
			int option = mainMenu.firstMenu();

			switch (option) {
			case 1:
				createInfo();
				break;
			case 2:
				editInfo();
				break;
			case 3:
				doLogin();
				break;
			case 4:
				searchProductsByCategory();
				break;
			case 5:
				searchProductByName();
				break;
			case 0:
				exit = true;
				break;
			}

		}
	}

	private static void createInfo() {
		// TODO Auto-generated method stub

	}

	private static void editInfo() {
		MainMenuUI mainMenu = new MainMenuUI();
		int option = mainMenu.editInformation();

		switch (option) {
		case 1:
			editProduct();
			break;
		case 2:
			editCategory();
			break;
		case 3:
			editUser();
			break;
		default:
			mainMenu.showNotValid();
		}
	}

	private static void editProduct() {
		ProductUI productMenu = new ProductUI();
		ProductService productService = new ProductService(new ProductDAO());

		try {
			ProductModel productToUpdate = productMenu.updateProduct();
			if (productService.updateProduct(productToUpdate)) {
				productMenu.showUpdatedExit();
			} else {
				productMenu.showUpdatedNotExit();
			}
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}
	}

	private static void editCategory() {
		CategoryUI categoryMenu = new CategoryUI();
		CategoryService categoryService = new CategoryService(new CategoryDAO());

		try {
			CategoryModel categoryToUpdate = categoryMenu.updateCategory();
			if (categoryService.updateCategory(categoryToUpdate)) {
				categoryMenu.showUpdatedExit();
			} else {
				categoryMenu.showUpdatedNotExit();
			}
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}
	}

	private static void editUser() {
		UserUI userMenu = new UserUI();
		UserService userService = new UserService(new UserDAO(),
				new ShoppingCartDAO());
		try {
			String userEmail = userMenu.askForEmail();
			UserModel oldUser = userService.getUser(userEmail);
			if(oldUser != null){
				UserModel newUser = userMenu.updateUser(oldUser);
				userService.updateUser(newUser);
			}else{
				userMenu.showUserNotFound();
			}
			
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}
	}

	private static void doLogin() {
		UserUI userMenu = new UserUI();
		UserService userService = new UserService(new UserDAO(),
				new ShoppingCartDAO());

		UserModel loginUser = userMenu.getLoginInfo();

		try {
			boolean isLoginValid = userService.validateLogin(loginUser);
			if (isLoginValid) {
				UserModel user = userService.getUser(loginUser.getEmail());
				userMenu.showLoginExit(user);
			} else {
				userMenu.showNotLoginExit();
			}
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}

	}

	private static void searchProductsByCategory() {
		ProductUI productMenu = new ProductUI();
		CategoryService categoryService = new CategoryService(new CategoryDAO());
		ProductService productService = new ProductService(new ProductDAO());

		try {
			int categoryId = productMenu.askCategory(categoryService
					.getAllCategories());
			List<ProductModel> products = productService
					.getProductsByCategory(categoryId);
			productMenu.showProducts(products);
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}

	}

	private static void searchProductByName() {
		ProductUI productMenu = new ProductUI();
		ProductService productService = new ProductService(new ProductDAO());

		try {
			String productName = productMenu.askProductName();
			List<ProductModel> products = productService
					.getProductsByName(productName);
			productMenu.showProducts(products);
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}

	}

	private static void editInformation() {
		MainMenuUI mainMenu = new MainMenuUI();
		ProductUI productMenu = new ProductUI();
		CategoryUI categoryMenu = new CategoryUI();
		UserUI userMenu = new UserUI();

		ProductService productService = new ProductService(new ProductDAO());
		CategoryService categoryService = new CategoryService(new CategoryDAO());
		UserService userService = new UserService(new UserDAO(),
				new ShoppingCartDAO());

		int option = mainMenu.editInformation();
		switch (option) {
		case 1:
			option = productMenu.productMenu();
			switch (option) {
			case 1:
				try {
					productMenu.productCreated(productService
							.createProduct(productMenu.createProduct()));
				} catch (WebshopAppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					if (productService.updateProduct(productMenu
							.updateProduct())) {
						System.out.println("You have updated your product");
					} else {
						System.out
								.println("Your product was not updated, try again.");
					}
				} catch (WebshopAppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					if (productService.deleteProduct(productMenu
							.deleteProduct())) {
						System.out.println("You have deleted your product");
					} else {
						System.out.println("Your product was not deleted");
					}
				} catch (WebshopAppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					System.out.println(productService
							.getProductById(productMenu.getProduct()));
				} catch (WebshopAppException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case 5:
				try {
					productMenu.getAllProducts(productService.getAllProducts());
				} catch (WebshopAppException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
			break;
		case 2:
			option = categoryMenu.categoryMenu();
			switch (option) {
			case 1:
				try {
					categoryMenu.addedCategory(categoryService
							.addCategory(categoryMenu.addCategory()));
				} catch (WebshopAppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					if (categoryService.updateCategory(categoryMenu
							.updateCategory())) {
						System.out.println("You have updated your category.");
					} else {
						System.out
								.println("You have not updated your category");
					}
				} catch (WebshopAppException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					if (categoryService.deleteCategory(categoryMenu
							.deleteCategory())) {
						System.out.println("You have deleted this category");
					} else {
						System.out
								.println("You have not deleted this category");
					}
				} catch (WebshopAppException e) {
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					categoryMenu.foundCategory(categoryService
							.searchCategoryByName(categoryMenu.getCategory()));
				} catch (WebshopAppException e1) {
					e1.printStackTrace();
				}
				break;
			case 5:
				try {
					categoryMenu.getAllCategories(categoryService
							.getAllCategories());
				} catch (WebshopAppException e1) {
					e1.printStackTrace();
				}
				break;
			}
			break;
		case 3:
			option = userMenu.UserMenu();
			switch (option) {
			case 1:
				try {
					System.out.println(userService.addUser(userMenu
							.createUser()));
				} catch (WebshopAppException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					System.out.println(userMenu.updateUser(userService
							.getUser(userMenu.askForEmail())));
				} catch (WebshopAppException e1) {
					e1.printStackTrace();
				}
				break;
			case 3:
				try {
					userService.deleteUser(userMenu.deleteUser());
				} catch (WebshopAppException e) {
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					System.out.println(userService.getUser(userMenu.getUser()));
				} catch (WebshopAppException e) {
					e.printStackTrace();
				}
				break;
			case 5:
				try {
					userMenu.getAllUsers(userService.getAllUsers());
				} catch (WebshopAppException e) {
					e.printStackTrace();
				}
				break;
			}
			break;
		}

	}

	private static void retrieveInformation() {
		MainMenuUI mainMenu = new MainMenuUI();

		int option = mainMenu.retrieveInformation();
		switch (option) {
		case 1:
			retrieveProductInfo();
			break;
		case 2:
			retrieveCategoryInfo();
			break;
		case 3:
			retrieveUserInfo();
			break;

		}

	}

	private static void retrieveUserInfo() {
		UserUI userMenu = new UserUI();
		UserService userService = new UserService(new UserDAO(),
				new ShoppingCartDAO());

		try {
			System.out.println(userService.getUser(userMenu.getUser()));
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}
	}

	private static void retrieveCategoryInfo() {
		CategoryUI categoryMenu = new CategoryUI();
		CategoryService categoryService = new CategoryService(new CategoryDAO());

		try {
			List<CategoryModel> categories = categoryService.getAllCategories();
			categoryMenu.showCategories(categories);
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}
	}

	private static void retrieveProductInfo() {
		ProductUI productMenu = new ProductUI();
		ProductService productService = new ProductService(new ProductDAO());

		try {
			List<ProductModel> products = productService.getAllProducts();
			productMenu.showProducts(products);
		} catch (WebshopAppException e) {
			ExceptionUI.printException(e);
		}
	}

	private static void retrieveShoppingCart() {
		ShoppingCartUI shoppingMenu = new ShoppingCartUI();
		UserService userService = new UserService(new UserDAO(),
				new ShoppingCartDAO());

		try {
			System.out.println(userService.getShoppingCartContents(userService
					.getUser(shoppingMenu
							.askForEmail("Get Shopping Cart Contents"))));
		} catch (WebshopAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
