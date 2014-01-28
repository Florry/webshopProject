package se.jiv.webshop.repository;

import java.util.List;
import java.util.Map;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;

public interface UserRepository
{
	public UserModel addUser(UserModel user);
	
	public void updateUser(UserModel user);
	
	public void deleteUser(UserModel user);
	
	public UserModel getUser(String email);
	
	public List<UserModel> getAllUsers();
	
	public void addProductToCart(UserModel user, int productId, int quantity)
			throws WebshopAppException;
	
	public void removeFromCart(UserModel user, int productId, int quantity);
	
	public void updateCart(UserModel user, int productId, int quantity);
	
	public Map<Integer, Integer> getShoppingCartContents(UserModel user);
	
}
