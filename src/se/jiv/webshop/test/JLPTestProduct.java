package se.jiv.webshop.test;

import java.util.ArrayList;
import java.util.List;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.ProductModel;
import se.jiv.webshop.repository.dao.ProductDAO;
import se.jiv.webshop.service.ProductService;
import se.jiv.webshop.ui.ExceptionUI;

public final class JLPTestProduct {

	public static void main(String[] args) {
		ProductService ps = new ProductService(new ProductDAO());

		// addProduct
		test1(ps);
		test2(ps);
		test3(ps);
		test4(ps);

		// deleteProduct
		// a product was deleted on test 1 and 2
		test5(ps);

		// updateProduct
		test6(ps);
		test7(ps);
		test8(ps);
		test9(ps);
		test10(ps);
		
		//getAllProducts
		test11(ps);
		
		//getProductByName
		test12(ps);
		test13(ps);
		test14(ps);
		
		//getProductByCost
		test15(ps);
		test16(ps);
		
		//getProductById
		test17(ps);
		test18(ps);
		
		//getProductsByCategory
		test19(ps);
		test20(ps);
		
	}

	private static void test20(ProductService ps) {
		try {
			List<Integer> categories = new ArrayList<Integer>();
			categories.add(1);
			categories.add(3);
			categories.add(5);
			ProductModel product = new ProductModel("test16", "desc", 1.1, 2.2,
					categories);
			product = ps.createProduct(product);
			
			List<ProductModel> products = ps.getProductsByCategory(3);
			for(ProductModel producto : products){
				System.out.println(producto);
			}
			System.out.println("TEST 20 OK(1 result)");
			
			ps.deleteProduct(product.getId());
			} catch (WebshopAppException e) {
				System.out.println("TEST 20 NOK");
				ExceptionUI.printException(e);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("TEST 20 NOK");
			}
	}

	private static void test19(ProductService ps) {
		try {
			List<ProductModel> products = ps.getProductsByCategory(-1);
			for(ProductModel producto : products){
				System.out.println(producto);
			}
			System.out.println("TEST 19 OK(not results)");
		} catch (WebshopAppException e) {
			System.out.println("TEST 19 NOK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("TEST 19 NOK");
		}
	}

	private static void test18(ProductService ps) {
		try {
			ProductModel product = new ProductModel("test18", "desc", 1.1, 2.2,
					null);
			product = ps.createProduct(product);
			
			product = ps.getProductById(product.getId());
			
			System.out.println(product);
			
			System.out.println("TEST 18 OK(1 result)");
			
			ps.deleteProduct(product.getId());
			} catch (WebshopAppException e) {
				System.out.println("TEST 18 NOK");
				ExceptionUI.printException(e);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("TEST 18 NOK");
			}
		
	}

	private static void test17(ProductService ps) {
		try {
			ProductModel product = ps.getProductById(-1);
			System.out.println(product);
				
			System.out.println("TEST 17 OK(not results)");
		} catch (WebshopAppException e) {
			System.out.println("TEST 17 NOK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("TEST 17 NOK");
		}
		
	}

	private static void test16(ProductService ps) {
		try {
		ProductModel product = new ProductModel("test16", "desc", 1.1, 2.2,
				null);
		product = ps.createProduct(product);
		
		List<ProductModel> products = ps.getProductsByCost(1.1);
		for(ProductModel producto : products){
			System.out.println(producto);
		}
		System.out.println("TEST 16 OK(1 result)");
		
		ps.deleteProduct(product.getId());
		} catch (WebshopAppException e) {
			System.out.println("TEST 16 NOK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("TEST 16 NOK");
		}
		
		
	}

	private static void test15(ProductService ps) {
		try {
			List<ProductModel> products = ps.getProductsByCost(-1);
			for(ProductModel producto : products){
				System.out.println(producto);
			}
			System.out.println("TEST 15 OK(not results)");
		} catch (WebshopAppException e) {
			System.out.println("TEST 15 NOK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("TEST 15 NOK");
		}
		
	}

	private static void test14(ProductService ps) {
		try {
			List<ProductModel> products = ps.getProductsByName(null);
			for(ProductModel producto : products){
				System.out.println(producto);
			}
			System.out.println("TEST 14 OK(not results)");
		} catch (WebshopAppException e) {
			System.out.println("TEST 14 NOK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("TEST 14 NOK");
		}
	}

	private static void test13(ProductService ps) {
		try {
			List<ProductModel> products = ps.getProductsByName("aaaaaa");
			for(ProductModel producto : products){
				System.out.println(producto);
			}
			System.out.println("TEST 13 OK(not results)");
		} catch (WebshopAppException e) {
			System.out.println("TEST 13 NOK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			System.out.println("TEST 13 NOK");
		}
		
	}

	private static void test12(ProductService ps) {
		try {
			List<Integer> categories = new ArrayList<Integer>();
			categories.add(1);
			categories.add(3);
			categories.add(5);

			ProductModel product = new ProductModel("test12", "desc", 1.1, 2.2,
					categories);

			product = ps.createProduct(product);

			List<ProductModel> products = ps.getProductsByName("test12");
			for(ProductModel producto : products){
				System.out.println(producto);
			}
			
			ps.deleteProduct(product.getId());
			System.out.println("TEST 12 OK");
		} catch (WebshopAppException e) {
			System.out.println("TEST 12 NOK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			System.out.println("TEST 12 NOK");
		}
		
	}

	private static void test11(ProductService ps) {
		try {
			List<Integer> categories = new ArrayList<Integer>();
			categories.add(1);
			categories.add(3);
			categories.add(5);

			ProductModel product = new ProductModel("test11", "desc", 1.1, 2.2,
					categories);

			product = ps.createProduct(product);

			List<ProductModel> products = ps.getAllProducts();
			for(ProductModel producto : products){
				System.out.println(producto);
			}
			
			ps.deleteProduct(product.getId());
			System.out.println("TEST 11 OK");
		} catch (WebshopAppException e) {
			System.out.println("TEST 11 NOK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			System.out.println("TEST 11 NOK");
		}
		
	}

	private static void test10(ProductService ps) {
		try {
			List<Integer> categories = new ArrayList<Integer>();
			categories.add(1);
			categories.add(3);
			categories.add(5);
			ProductModel product = new ProductModel(null, null, categories);
			ps.updateProduct(product);
			System.out.println("TEST 10 OK");
		} catch (WebshopAppException e) {
			System.out.println("TEST 10 NOK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			System.out.println("TEST 10 NOK");
		}
	}

	private static void test9(ProductService ps) {
		try {
			ProductModel product = new ProductModel(null, null, null);
			ps.updateProduct(product);
			System.out.println("TEST 9 OK");
		} catch (WebshopAppException e) {
			System.out.println("TEST 9 NOK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			System.out.println("TEST 9 NOK");
		}
		
	}

	private static void test8(ProductService ps) {
		try {
			ps.updateProduct(null);
			System.out.println("TEST 8 NOK-");
		} catch (WebshopAppException e) {
			System.out.println("TEST 8 OK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			System.out.println("TEST 8 NOK");
		}
		
	}

	private static void test7(ProductService ps) {
		try {
			// insert a product
			List<Integer> categories = new ArrayList<Integer>();
			categories.add(1);
			categories.add(3);
			categories.add(5);
			ProductModel product = new ProductModel("test7",categories);
			product = ps.createProduct(product);

			// update a product
			categories = new ArrayList<Integer>();
			categories.add(2);
			categories.add(4);
			ProductModel productNew = new ProductModel("test7 2", "desc", 1.1,
					2.2, categories);
			productNew = new ProductModel(product.getId(), productNew);
			boolean success = ps.updateProduct(productNew);

			// query product from BBDD
			productNew = ps.getProductById(product.getId());

			System.out.println("TEST 7 OK --> " + success + " \nproductOld: "
					+ product + "\nproductNew: " + productNew);

			// remove product
			ps.deleteProduct(product.getId());
		} catch (WebshopAppException e) {
			System.out.println("TEST 7 NOK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			System.out.println("TEST 7 NOK");
		}
	}

	private static void test6(ProductService ps) {
		try {
			// insert a product
			ProductModel product = new ProductModel("test6");
			product = ps.createProduct(product);

			// update a product
			List<Integer> categories = new ArrayList<Integer>();
			categories.add(1);
			categories.add(3);
			categories.add(5);
			ProductModel productNew = new ProductModel("test6 2", "desc", 1.1,
					2.2, categories);
			productNew = new ProductModel(product.getId(), productNew);
			boolean success = ps.updateProduct(productNew);

			// query product from BBDD
			productNew = ps.getProductById(product.getId());

			System.out.println("TEST 6 OK --> " + success + " \nproductOld: "
					+ product + "\nproductNew: " + productNew);

			// remove product
			ps.deleteProduct(product.getId());
		} catch (WebshopAppException e) {
			System.out.println("TEST 6 NOK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			System.out.println("TEST 6 NOK");
		}
	}

	private static void test5(ProductService ps) {
		try {
			ps.deleteProduct(-1);
			System.out.println("TEST 5 OK");
		} catch (WebshopAppException e) {
			System.out.println("TEST 5 NOK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			System.out.println("TEST 5 NOK");
		}
	}

	private static void test4(ProductService ps) {
		try {
			ProductModel product = new ProductModel(null, null, null);
			ps.createProduct(product);
			System.out.println("TEST 4 NOK");
		} catch (WebshopAppException e) {
			System.out.println("TEST 4 OK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			System.out.println("TEST 4 NOK");
		}
	}

	private static void test3(ProductService ps) {
		try {
			ps.createProduct(null);
			System.out.println("TEST 3 NOK");
		} catch (WebshopAppException e) {
			System.out.println("TEST 3 OK");
			ExceptionUI.printException(e);
		} catch (Exception e) {
			System.out.println("TEST 3 NOK");
		}

	}

	private static void test2(ProductService ps) {
		try {
			List<Integer> categories = new ArrayList<Integer>();
			categories.add(1);
			categories.add(3);
			categories.add(5);

			ProductModel product = new ProductModel("test2", "desc", 1.1, 2.2,
					categories);

			product = ps.createProduct(product);
			System.out.println("TEST 2 OK --> product: " + product);

			ps.deleteProduct(product.getId());

		} catch (WebshopAppException e) {
			System.out.println("TEST 2 NOK");
		} catch (Exception e) {
			System.out.println("TEST 2 NOK");
		}
	}

	private static void test1(ProductService ps) {
		try {
			ProductModel product = new ProductModel("test1");
			product = ps.createProduct(product);
			System.out.println("TEST 1 OK --> product: " + product);
			ps.deleteProduct(product.getId());
		} catch (WebshopAppException e) {
			System.out.println("TEST 1 NOK");
		} catch (Exception e) {
			System.out.println("TEST 1 NOK");
		}

	}

}
