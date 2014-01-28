package se.jiv.webshop.repository;

import java.util.List;
import java.util.Map;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;

public interface UserRepository
{
	public UserModel addUser(UserModel user) throws WebshopAppException;
	
	public void updateUser(UserModel user) throws WebshopAppException;
	
	public void deleteUser(UserModel user) throws WebshopAppException;
	
	public UserModel getUser(String email) throws WebshopAppException;
	
	public List<UserModel> getAllUsers() throws WebshopAppException;
	
	public void addProductToCart(UserModel user, int productId, int quantity)
			throws WebshopAppException;
	
	public void removeFromCart(UserModel user, int productId, int quantity)
			throws WebshopAppException;
	
	public void updateCart(UserModel user, int productId, int quantity) throws WebshopAppException;
	
	public Map<Integer, Integer> getShoppingCartContents(UserModel user) throws WebshopAppException;
	
}
