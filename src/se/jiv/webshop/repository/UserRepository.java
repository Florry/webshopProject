package se.jiv.webshop.repository;

import java.util.List;

import se.jiv.webshop.model.UserModel;

public interface UserRepository
{
	void createUser(UserModel user);
	
	void updateUser(UserModel user);
	
	void removeUser(UserModel user);
	
	UserModel getUser(String id);
	
	List<UserModel> getAllUsers();
}
