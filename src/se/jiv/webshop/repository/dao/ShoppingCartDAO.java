package se.jiv.webshop.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.ShoppingCartRepository;

public class ShoppingCartDAO extends GeneralDAO implements ShoppingCartRepository
{
	
	@Override
	public void addProductToCart(UserModel user, int productId, int quantity)
			throws WebshopAppException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if (user != null)
		{
			if (quantity > 0)
			{
				try
				{
					conn = getConnection();
					
					String sql = "SELECT quantity FROM shopping_cart WHERE user_id = ? and product_id = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, user.getId());
					pstmt.setInt(2, productId);
					
					rs = pstmt.executeQuery();
					
					int db_quantity = 0;
					
					if (rs.next())
					{
						db_quantity = rs.getInt(quantity);
						
						if (db_quantity > 0)
						{
							quantity += db_quantity;
						}
					}
					close(rs, pstmt);
					if (db_quantity > 1)
					{
						sql = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? and product_id = ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, quantity);
						pstmt.setInt(2, user.getId());
						pstmt.setInt(3, productId);
						
						pstmt.executeUpdate();
					} else
					{
						sql = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, user.getId());
						pstmt.setInt(2, productId);
						pstmt.setInt(3, quantity);
						
						pstmt.executeUpdate();
					}
					close(rs, pstmt, conn);
					
				} catch (SQLException e)
				{
					throw new WebshopAppException(e, this.getClass().getSimpleName(),
							"ADD_PRODUCT_TO_SHOPPING_CART");
				} finally
				{
					close(rs, pstmt, conn);
				}
				
			} else
			{
				throw new WebshopAppException("quantity is negative", this.getClass()
						.getSimpleName(), "ADD_PRODUCT_TO_SHOPPING_CART");
			}
		}
		
	}
	
	@Override
	public void removeFromCart(UserModel user, int productId, int quantity)
			throws WebshopAppException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		if (user != null)
		{
			if (quantity > 0)
			{
				try
				{
					conn = getConnection();
					
					String sql = "SELECT quantity FROM shopping_cart WHERE user_id = ? and product_id = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, user.getId());
					pstmt.setInt(2, productId);
					
					rs = pstmt.executeQuery();
					
					int db_quantity = 0;
					
					if (rs.next())
					{
						db_quantity = rs.getInt("quantity");
						db_quantity -= quantity;
					}
					close(rs, pstmt);
					
					if (db_quantity <= 0)
					{
						sql = "DELETE FROM shopping_cart WHERE user_id = ? and product_id = ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, user.getId());
						pstmt.setInt(2, productId);
						
						pstmt.executeUpdate();
					} else if (db_quantity > 0)
					{
						sql = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? and product_id = ?";
						pstmt = conn.prepareStatement(sql);
						pstmt.setInt(1, db_quantity);
						pstmt.setInt(2, user.getId());
						pstmt.setInt(3, productId);
						
						pstmt.executeUpdate();
					}
					
				} catch (SQLException e)
				{
					throw new WebshopAppException(e, this.getClass().getSimpleName(),
							"REMOVE_FROM_SHOPPING_CART");
				} finally
				{
					close(rs, pstmt, conn);
				}
			} else
			{
				
				try
				{
					conn = getConnection();
					
					String sql = "DELETE FROM shopping_cart WHERE user_id = ? and product_id = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, user.getId());
					pstmt.setInt(2, productId);
					
					pstmt.executeUpdate();
					
				} catch (SQLException e)
				{
					throw new WebshopAppException(e.getMessage(), this.getClass().getSimpleName(),
							"REMOVE_FROM_SHOPPING_CART");
				} finally
				{
					close(rs, pstmt, conn);
				}
			}
		}
		
	}
	
	public Map<Integer, Integer> getShoppingCartContents(UserModel user) throws WebshopAppException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<Integer, Integer> contents = new LinkedHashMap<Integer, Integer>();
		if (user != null)
		{
			try
			{
				conn = getConnection();
				
				String sql = "SELECT product_id, quantity FROM shopping_cart WHERE user_id = ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, user.getId());
				
				rs = pstmt.executeQuery();
				
				while (rs.next())
				{
					contents.put(rs.getInt("product_id"), rs.getInt("quantity"));
				}
				
			} catch (SQLException e)
			{
				throw new WebshopAppException(e.getMessage(), this.getClass().getSimpleName(),
						"GET_SHOPPING_SHOPPING_CART_CONTENTS");
			} finally
			{
				close(rs, pstmt, conn);
			}
		}
		return contents;
	}
	
	@Override
	public void updateCart(UserModel user, int productId, int quantity) throws WebshopAppException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		if (user != null)
		{
			try
			{
				conn = getConnection();
				if (quantity > 0)
				{
					sql = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? and product_id = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, quantity);
					pstmt.setInt(2, user.getId());
					pstmt.setInt(3, productId);
				} else
				{
					sql = "DELETE FROM shopping_cart WHERE user_id = ? and product_id = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, user.getId());
					pstmt.setInt(2, productId);
				}
				pstmt.executeUpdate();
			} catch (SQLException e)
			{
				throw new WebshopAppException(e.getMessage(), this.getClass().getSimpleName(),
						"UPDATE_SHOPPING_CART");
			} finally
			{
				close(pstmt, conn);
			}
			
		}
	}
	
}
