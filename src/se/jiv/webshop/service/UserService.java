package se.jiv.webshop.service;

import java.util.List;
import java.util.Map;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.UserRepository;

public final class UserService
{
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	
	public UserModel addUser(UserModel user)
	{
		return userRepository.addUser(user);
		
	}
	
	public void updateUser(UserModel user)
	{
		userRepository.updateUser(user);
		
	}
	
	public void deleteUser(UserModel user)
	{
		userRepository.deleteUser(user);
	}
	
	public UserModel getUser(String email)
	{
		return userRepository.getUser(email);
		
	}
	
	public List<UserModel> getAllUsers()
	{
		return userRepository.getAllUsers();
		
	}
	
	public void addProductToCart(UserModel user, int id, int quantity) throws WebshopAppException
	{
		userRepository.addProductToCart(user, id, quantity);
	}
	
	public void removeFromCart(UserModel user, Integer id, int quantity)
	{
		userRepository.removeFromCart(user, id, quantity);
	}
	
	public void updateCart(UserModel user, int productId, int quantity)
	{
		userRepository.updateCart(user, productId, quantity);
	}
	
	public Map<Integer, Integer> getShoppingCartContents(UserModel user)
	{
		return userRepository.getShoppingCartContents(user);
	}
}
