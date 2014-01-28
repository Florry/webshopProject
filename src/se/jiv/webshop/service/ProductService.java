package se.jiv.webshop.service;

import java.util.List;

import se.jiv.webshop.model.ProductModel;
import se.jiv.webshop.repository.ProductRepository;

public class ProductService
{
	private ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository)
	{
		this.productRepository = productRepository;
	}
	
	public ProductModel createProduct(ProductModel product)
	{
		return productRepository.createProduct(product);
	}
	
	public ProductModel updateProduct(String productName, int updateProperty, String newProperty)
	{
		return productRepository.updateProduct(productName, updateProperty, newProperty);
	}
	
	public boolean updateProduct(ProductModel product, int productId)
	{
		return productRepository.updateProduct(product, productId);
	}
	
	public boolean deleteProduct(String name)
	{
		return productRepository.deleteProduct(name);
	}
	
	public boolean deleteProduct(int productId)
	{
		return productRepository.deleteProduct(productId);
	}
	
	public List<ProductModel> getProductByName(String name)
	{
		return productRepository.getProductByName(name);
	}
	
	public List<ProductModel> getProductByCost(double cost)
	{
		return productRepository.getProductByCost(cost);
	}
	
	public ProductModel getProductById(int id)
	{
		return productRepository.getProductById(id);
	}
	
	public List<ProductModel> getAllProducts()
	{
		return productRepository.getAllProducts();
	}
}
