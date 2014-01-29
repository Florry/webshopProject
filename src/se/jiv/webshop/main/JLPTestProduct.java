package se.jiv.webshop.main;

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
		
		//addProduct
		test1(ps);
		test2(ps);
		test3(ps);
		test4(ps);
		
		
	}

	private static void test4(ProductService ps) {
		try {
			ProductModel product = new ProductModel(null, null, null);
			ps.createProduct(product);
			System.out.println("TEST 4 NOK");
		} catch (WebshopAppException e) {
			System.out.println("TEST 4 OK");
			ExceptionUI.printException(e);
		} catch (Exception e){
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
		} catch (Exception e){
			System.out.println("TEST 3 NOK");
		}
		
	}

	private static void test2(ProductService ps) {
		try {
			List<Integer> categories = new ArrayList<Integer>();
			categories.add(1);
			categories.add(3);
			categories.add(5);
			
			ProductModel product = new ProductModel("name","desc", 1.1, 2.2, categories);
			
			product = ps.createProduct(product);
			System.out.println("TEST 2 OK --> product: " + product);
			
			ps.deleteProduct(product.getId());
			
		} catch (WebshopAppException e) {
			System.out.println("TEST 2 NOK");
		} catch (Exception e){
			System.out.println("TEST 2 NOK");
		}
	}

	private static void test1(ProductService ps) {
		try {
			ProductModel product = new ProductModel("name");
			product = ps.createProduct(product);
			System.out.println("TEST 1 OK --> product: " + product);
			ps.deleteProduct(product.getId());
		} catch (WebshopAppException e) {
			System.out.println("TEST 1 NOK");
		} catch (Exception e){
			System.out.println("TEST 1 NOK");
		}
	
	}

}
