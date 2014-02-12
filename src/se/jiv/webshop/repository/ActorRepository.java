package se.jiv.webshop.repository;

import java.util.List;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.ActorModel;

public interface ActorRepository
{
	public ActorModel addActor(int id, String firstname, String lastname) throws WebshopAppException;

	public boolean updateActor(int id, String firstname, String lastname) throws WebshopAppException;

	public ActorModel getActor(int id) throws WebshopAppException;
	
	public List<ActorModel> search(String name);

	public List<ActorModel> getAllActors() throws WebshopAppException;
	
	public boolean deleteActor(int id) throws WebshopAppException;
}
