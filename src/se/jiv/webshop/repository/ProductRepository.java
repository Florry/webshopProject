package se.jiv.webshop.repository;

import java.util.List;

import se.jiv.webshop.model.*;

public interface ProductRepository
{
	public ProductModel createProduct(ProductModel product);
	
	public boolean updateProduct(ProductModel product, int productId);
	
	public boolean deleteProduct(int productId);
	
	public List<Integer> getCategoriesOfProduct(int productId);

	public List<ProductModel> getProductByName(String name);

	public List<ProductModel> getProductsByCost(double cost);
	
	public List<ProductModel> getProductsByCategory(int categoryId);
	
	public ProductModel getProductById(int id);

	public List<ProductModel> getAllProducts();

}
