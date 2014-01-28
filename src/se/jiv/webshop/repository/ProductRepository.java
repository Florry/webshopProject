package se.jiv.webshop.repository;

import java.util.List;

import se.jiv.webshop.model.*;

public interface ProductRepository
{
	public ProductModel createProduct(ProductModel product);

	public ProductModel updateProduct(String productName, int updateProperty, String newProperty);
	
	public boolean updateProduct(ProductModel product, int productId);

	public boolean deleteProduct(String name);
	
	public boolean deleteProduct(int productId);

	public List<ProductModel> getProductByName(String name);

	public List<ProductModel> getProductByCost(double cost);
	
	public ProductModel getProductById(int id);

	public List<ProductModel> getAllProducts();

}
