package se.jiv.webshop.service;

import java.util.List;
import java.util.Map;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.ShoppingCartRepository;
import se.jiv.webshop.repository.UserRepository;

public final class UserService
{
	private UserRepository userRepository;
	private ShoppingCartRepository shoppingCartRepository;
	
	public UserService(UserRepository userRepository, ShoppingCartRepository shoppingCartRepository)
	{
		this.userRepository = userRepository;
		this.shoppingCartRepository = shoppingCartRepository;
	}
	
	public UserModel addUser(UserModel user) throws WebshopAppException
	{
		return userRepository.addUser(user);
		
	}
	
	public void updateUser(UserModel user) throws WebshopAppException
	{
		userRepository.updateUser(user);
		
	}
	
	public void deleteUser(UserModel user) throws WebshopAppException
	{
		userRepository.deleteUser(user);
	}
	
	public UserModel getUser(String email) throws WebshopAppException
	{
		return userRepository.getUser(email);
		
	}
	
	public List<UserModel> getAllUsers() throws WebshopAppException
	{
		return userRepository.getAllUsers();
		
	}
	
	public void addProductToCart(UserModel user, int id, int quantity) throws WebshopAppException
	{
		shoppingCartRepository.addProductToCart(user, id, quantity);
	}
	
	public void removeFromCart(UserModel user, Integer id, int quantity) throws WebshopAppException
	{
		shoppingCartRepository.removeFromCart(user, id, quantity);
	}
	
	public void updateCart(UserModel user, int productId, int quantity) throws WebshopAppException
	{
		shoppingCartRepository.updateCart(user, productId, quantity);
	}
	
	public Map<Integer, Integer> getShoppingCartContents(UserModel user) throws WebshopAppException
	{
		return shoppingCartRepository.getShoppingCartContents(user);
	}
	
	public void resetShoppingCart(UserModel user) throws WebshopAppException
	{
		shoppingCartRepository.resetShoppingCart(user);
	}
}
