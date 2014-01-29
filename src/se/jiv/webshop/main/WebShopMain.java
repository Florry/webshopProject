package se.jiv.webshop.main;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.repository.CategoryRepository;
import se.jiv.webshop.repository.ProductRepository;
import se.jiv.webshop.repository.dao.CategoryDAO;
import se.jiv.webshop.repository.dao.ProductDAO;
import se.jiv.webshop.service.CategoryService;
import se.jiv.webshop.service.ProductService;
import se.jiv.webshop.ui.CategoryUI;
import se.jiv.webshop.ui.MainMenuUI;
import se.jiv.webshop.ui.ProductUI;

public final class WebShopMain
{

	public static void main(String[] args)
	{
//		MainMenuUI mm = new MainMenuUI();
//		System.out.println("text:");
//		String text = mm.readString();
//		System.out.println(text);
//		
//		System.out.println("number:");
//		int number = mm.readInt();
//		System.out.println(number);
		
		boolean in = true;

		while (in)
		{
			MainMenuUI mainMenu = new MainMenuUI();
			ProductUI productMenu = new ProductUI();
			CategoryUI categoryMenu = new CategoryUI();

			CategoryRepository categoryRepository = new CategoryDAO();
			CategoryService categoryService = new CategoryService(categoryRepository);

			ProductRepository productRepository = new ProductDAO();
			ProductService productService = new ProductService(productRepository);

			int option = mainMenu.firstMenu();

			switch (option)
			{
			case 1:
				break;
			case 2:
				option = mainMenu.retrieveInformation();
				switch (option)
				{
				case 1:
					try
					{
						System.out.println(productMenu.toStringListArray(productService.getProductsByName(productMenu.productInformation())));
					}
					catch (WebshopAppException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case 2:
					try
					{
						System.out.println(categoryService.getCategory(categoryMenu.categoryInformation(categoryService.getAllCategories())));
					}
					catch (WebshopAppException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case 3:
					break;
				}
				break;
			case 3:
				break;
			case 4:
				try
				{
					option = productMenu.productsByCategory(categoryService.getAllCategories());
					System.out.println(productMenu.toStringListArray(productService.getProductsByCategory(option)));
				}
				catch (WebshopAppException e)
				{
					e.printStackTrace();
				}
				break;
			case 5:
				option = mainMenu.editInformation();
				switch (option)
				{
				case 1:
					option = productMenu.editProduct();
	
					break;
				case 2:
					break;
				case 3: 
					break;
				}

				break;
			}

		}
		
	}

}
