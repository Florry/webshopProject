package se.jiv.webshop.repository;

import java.util.List;

import se.jiv.webshop.model.*;

public interface ProductRepository
{
	public ProductModel createProduct(ProductModel product);

	public ProductModel updateProduct(String productName, int updateProperty, String newProperty);

	public void deleteProduct(String name);

	public List<ProductModel> getProduct(String name);

	public List<ProductModel> getProduct(double price);

	public List<ProductModel> getAllProducts();

}
