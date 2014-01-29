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
	
	public boolean updateProduct(ProductModel product, int productId)
	{
		return productRepository.updateProduct(product, productId);
	}
	
	public boolean deleteProduct(int productId)
	{
		return productRepository.deleteProduct(productId);
	}
	
	public List<ProductModel> getProductByName(String name)
	{
		return productRepository.getProductByName(name);
	}
	
	public List<ProductModel> getProductsByCost(double cost)
	{
		return productRepository.getProductsByCost(cost);
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
