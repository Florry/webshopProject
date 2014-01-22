package se.jiv.webshop.service;

import java.util.List;
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
	
	public UserModel updateUser(UserModel user)
	{
		return userRepository.updateUser(user);
		
	}
	
	public void deleteUser(UserModel user)
	{
		userRepository.deleteUser(user);
	}
	
	public UserModel getUser(String email)
	{
		return userRepository.getUser(email);
		
	}
	
	public int getUserId(UserModel user)
	{
		return userRepository.getUserId(user);
		
	}
	
	public List<UserModel> getAllUsers()
	{
		return userRepository.getAllUsers();
		
	}
	
	public void addProductToCart(UserModel user, Integer id)
	{
		userRepository.addProductToCart(user, id);
	}
	
	public void removeFromCart(UserModel user, Integer id)
	{
		userRepository.removeFromCart(user, id);
	}
}
