package se.jiv.webshop.repository;

import java.util.List;

import se.jiv.webshop.model.UserModel;

public interface UserRepository
{
	public UserModel addUser(UserModel user);
	
	public UserModel updateUser(UserModel user);
	
	public void deleteUser(UserModel user);
	
	public UserModel getUser(Integer id);
	
	public List<UserModel> getAllUsers();
}
