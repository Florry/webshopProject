package se.jiv.webshop.repository;

import java.util.Map;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;

public interface ShoppingCartRepository
{
	public void addProductToCart(UserModel user, int productId, int quantity)
			throws WebshopAppException;
	
	public void removeFromCart(UserModel user, int productId, int quantity)
			throws WebshopAppException;
	
	public void updateCart(UserModel user, int productId, int quantity) throws WebshopAppException;
	
	public Map<Integer, Integer> getShoppingCartContents(UserModel user) throws WebshopAppException;
}
