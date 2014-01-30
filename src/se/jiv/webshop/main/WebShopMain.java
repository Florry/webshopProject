package se.jiv.webshop.main;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.dao.CategoryDAO;
import se.jiv.webshop.repository.dao.ProductDAO;
import se.jiv.webshop.repository.dao.ShoppingCartDAO;
import se.jiv.webshop.repository.dao.UserDAO;
import se.jiv.webshop.service.CategoryService;
import se.jiv.webshop.service.ProductService;
import se.jiv.webshop.service.UserService;
import se.jiv.webshop.ui.CategoryUI;
import se.jiv.webshop.ui.MainMenuUI;
import se.jiv.webshop.ui.ProductUI;
import se.jiv.webshop.ui.ShoppingCartUI;
import se.jiv.webshop.ui.UserUI;

public final class WebShopMain
{
	
	public static void main(String[] args)
	{
		boolean in = true;
		
		while (in)
		{
			MainMenuUI mainMenu = new MainMenuUI();
			
			int option = mainMenu.firstMenu();
			
			switch (option)
			{
				case 1:
					doLogin();
					break;
				case 2:
					retrieveInformation();
					break;
				case 3:
					retrieveShoppingCart();
					break;
				case 4:
					searchProductsByCategory();
					break;
				case 5:
					editInformation();
					break;
			}
			
			in = mainMenu.returnToMainMenu();
			
		}
	}
	
	private static void editInformation()
	{
		MainMenuUI mainMenu = new MainMenuUI();
		ProductUI productMenu = new ProductUI();
		CategoryUI categoryMenu = new CategoryUI();
		UserUI userMenu = new UserUI();
		
		ProductService productService = new ProductService(new ProductDAO());
		CategoryService categoryService = new CategoryService(new CategoryDAO());
		UserService userService = new UserService(new UserDAO(), new ShoppingCartDAO());
		
		int option = mainMenu.editInformation();
		switch (option)
		{
			case 1:
				option = productMenu.productMenu();
				switch (option)
				{
					case 1:
						try
						{
							productMenu.productCreated(productService.createProduct(productMenu
									.createProduct()));
						} catch (WebshopAppException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case 2:
						try
						{
							if (productService.updateProduct(productMenu.updateProduct()))
							{
								System.out.println("You have updated your product");
							} else
							{
								System.out.println("Your product was not updated, try again.");
							}
						} catch (WebshopAppException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case 3:
						try
						{
							if (productService.deleteProduct(productMenu.deleteProduct()))
							{
								System.out.println("You have deleted your product");
							} else
							{
								System.out.println("Your product was not deleted");
							}
						} catch (WebshopAppException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case 4:
						try
						{
							System.out.println(productService.getProductById(productMenu
									.getProduct()));
						} catch (WebshopAppException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					case 5:
						try
						{
							productMenu.getAllProducts(productService.getAllProducts());
						} catch (WebshopAppException e1)
						{
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
				}
				break;
			case 2:
				option = categoryMenu.categoryMenu();
				switch (option)
				{
					case 1:
						try
						{
							categoryMenu.addedCategory(categoryService.addCategory(categoryMenu
									.addCategory()));
						} catch (WebshopAppException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case 2:
						try
						{
							if (categoryService.updateCategory(categoryMenu.updateCategory()))
							{
								System.out.println("You have updated your category.");
							} else
							{
								System.out.println("You have not updated your category");
							}
						} catch (WebshopAppException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					case 3:
						try
						{
							if (categoryService.deleteCategory(categoryMenu.deleteCategory()))
							{
								System.out.println("You have deleted this category");
							} else
							{
								System.out.println("You have not deleted this category");
							}
						} catch (WebshopAppException e)
						{
							e.printStackTrace();
						}
						break;
					case 4:
						try
						{
							categoryMenu.foundCategory(categoryService
									.searchCategoryByName(categoryMenu.getCategory()));
						} catch (WebshopAppException e1)
						{
							e1.printStackTrace();
						}
						break;
					case 5:
						try
						{
							categoryMenu.getAllCategories(categoryService.getAllCategories());
						} catch (WebshopAppException e1)
						{
							e1.printStackTrace();
						}
						break;
				}
				break;
			case 3:
				option = userMenu.UserMenu();
				switch (option)
				{
					case 1:
						try
						{
							System.out.println(userService.addUser(userMenu.createUser()));
						} catch (WebshopAppException e)
						{
							e.printStackTrace();
						}
						break;
					case 2:
						try
						{
							System.out.println(userMenu.updateUser(userService.getUser(userMenu
									.askForEmail())));
						} catch (WebshopAppException e1)
						{
							e1.printStackTrace();
						}
						break;
					case 3:
						try
						{
							userService.deleteUser(userMenu.deleteUser());
						} catch (WebshopAppException e)
						{
							e.printStackTrace();
						}
						break;
					case 4:
						try
						{
							System.out.println(userService.getUser(userMenu.getUser()));
						} catch (WebshopAppException e)
						{
							e.printStackTrace();
						}
						break;
					case 5:
						try
						{
							userMenu.getAllUsers(userService.getAllUsers());
						} catch (WebshopAppException e)
						{
							e.printStackTrace();
						}
						break;
				}
				break;
		}
		
	}
	
	private static void searchProductsByCategory()
	{
		ProductUI productMenu = new ProductUI();
		CategoryService categoryService = new CategoryService(new CategoryDAO());
		ProductService productService = new ProductService(new ProductDAO());
		
		try
		{
			int option = productMenu.productsByCategory(categoryService.getAllCategories());
			System.out.println(productMenu.toStringListArray(productService
					.getProductsByCategory(option)));
		} catch (WebshopAppException e)
		{
			e.printStackTrace();
		}
		
	}
	
	private static void retrieveInformation()
	{
		MainMenuUI mainMenu = new MainMenuUI();
		ProductUI productMenu = new ProductUI();
		CategoryUI categoryMenu = new CategoryUI();
		UserUI userMenu = new UserUI();
		
		ProductService productService = new ProductService(new ProductDAO());
		CategoryService categoryService = new CategoryService(new CategoryDAO());
		UserService userService = new UserService(new UserDAO(), new ShoppingCartDAO());
		
		int option = mainMenu.retrieveInformation();
		switch (option)
		{
			case 1:
				try
				{
					System.out.println(productMenu.toStringListArray(productService
							.getProductsByName(productMenu.productInformation())));
				} catch (WebshopAppException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case 2:
				try
				{
					System.out.println(categoryService.getCategory(categoryMenu
							.categoryInformation(categoryService.getAllCategories())));
				} catch (WebshopAppException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case 3:
				try
				{
					System.out.println(userService.getUser(userMenu.getUser()));
				} catch (WebshopAppException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
		
		}
		
	}
	
	private static void retrieveShoppingCart()
	{
		ShoppingCartUI shoppingMenu = new ShoppingCartUI();
		UserService userService = new UserService(new UserDAO(), new ShoppingCartDAO());
		
		try
		{
			System.out.println(userService.getShoppingCartContents(userService.getUser(shoppingMenu
					.askForEmail("Get Shopping Cart Contents"))));
		} catch (WebshopAppException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void doLogin()
	{
		UserUI userMenu = new UserUI();
		UserService userService = new UserService(new UserDAO(), new ShoppingCartDAO());
		UserModel loginUser = userMenu.loginUser();
		UserModel dataBaseUser = null;
		try
		{
			dataBaseUser = userService.getUser(loginUser.getEmail());
		} catch (WebshopAppException e)
		{
			System.out.println("Login failed!");
		}
		if (dataBaseUser != null && loginUser.getPassword().equals(dataBaseUser.getPassword()))
		{
			System.out.println("Login successful! Logged in as: " + dataBaseUser.getFirstname()
					+ " " + dataBaseUser.getLastname());
		} else
		{
			System.out.println("Login failed!");
		}
		
	}
}
