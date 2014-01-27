package se.jiv.webshop.repository;

import java.util.List;
import java.util.Map;

import se.jiv.webshop.model.ProductModel;
import se.jiv.webshop.model.UserModel;

public interface UserRepository
{
	public UserModel addUser(UserModel user);
	
	public UserModel updateUser(UserModel user);
	
	public void deleteUser(UserModel user);
	
	public UserModel getUser(String email);
	
	public int getUserId(UserModel user);
	
	public List<UserModel> getAllUsers();
	
	public void addProductToCart(UserModel user, int id);
	
	public void removeFromCart(UserModel user, int id);
	
	public Map<Integer, Integer> getShoppingCartContents(UserModel user);
	
}
