package se.jiv.webshop.test;

import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.dao.ShoppingCartDAO;

public class ShoppingCartJUnit
{
	ShoppingCartDAO shoppingCart = new ShoppingCartDAO();
	UserModel user1 = null;
	
	@Before
	public void onlyOnce() throws WebshopAppException
	{
		user1 = new UserModel.Builder("bbq@test.se", "123456", "Tom", "Whitemore",
				"Telegrafvagen 32", "Stockholm", "postcode").address2("C/O Olsen")
				.dob("1949-09-09").telephone("0807384756").build();
	}
	
	@Test
	public void canAddProductToCart()
	{
		boolean isEmpty = false;
		try
		{
			shoppingCart.addProductToCart(user1, 2, 20);
			isEmpty = shoppingCart.getShoppingCart(user1).isEmpty();
		} catch (WebshopAppException e)
		{
			e.printStackTrace();
		}
		
		assertFalse(isEmpty);
	}
	
	@Test
	public void canRemoveProductFromCart()
	{
		boolean isEmpty = false;
		try
		{
			shoppingCart.removeProductFromCart(user1, 2);
			isEmpty = shoppingCart.getShoppingCart(user1).isEmpty();
		} catch (WebshopAppException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(isEmpty);
	}
	
	@Test
	public void canUpdateCart()
	{
		Map<Integer, Integer> shoppingCartContents = new LinkedHashMap<>();
		try
		{
			shoppingCart.updateCart(user1, 2, 8);
			shoppingCartContents = shoppingCart.getShoppingCart(user1);
		} catch (WebshopAppException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertSame(shoppingCartContents.get(2), 8);
	}
	
	@Test
	public void canResetShoppingCart()
	{
		Map<Integer, Integer> shoppingCartContents = new LinkedHashMap<>();
		boolean isEmpty = false;
		try
		{
			shoppingCart.addProductToCart(user1, 2, 20);
			shoppingCart.resetShoppingCart(user1);
			shoppingCartContents = shoppingCart.getShoppingCart(user1);
			
		} catch (WebshopAppException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		isEmpty = shoppingCartContents.isEmpty();
		assertTrue(isEmpty);
	}
	
	@Test
	public void canGetShoppingCart()
	{
		boolean isEmpty = false;
		try
		{
			shoppingCart.addProductToCart(user1, 2, 30);
			isEmpty = shoppingCart.getShoppingCart(user1).isEmpty();
			shoppingCart.resetShoppingCart(user1);
		} catch (WebshopAppException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertFalse(isEmpty);
	}
}
