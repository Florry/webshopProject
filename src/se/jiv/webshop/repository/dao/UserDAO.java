package se.jiv.webshop.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.UserRepository;

public class UserDAO extends GeneralDAO implements UserRepository
{
	
	@Override
	public UserModel addUser(UserModel user)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			conn = getConnection();
			
			String sql = "INSERT INTO users (email, password, firstname, lastname, dob, telephone, address1, address2, town, postcode)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getFirstname());
			pstmt.setString(4, user.getLastname());
			pstmt.setString(5, user.getDob());
			pstmt.setString(6, user.getTelephone());
			pstmt.setString(7, user.getAddress1());
			pstmt.setString(8, user.getAddress2());
			pstmt.setString(9, user.getTown());
			pstmt.setString(10, user.getPostcode());
			pstmt.executeUpdate();
			
			int generatedId = 0;
			rs = pstmt.getGeneratedKeys();
			
			if (rs.next())
			{
				generatedId = rs.getInt(1);
			}
			
			return new UserModel(user, generatedId);
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			close(rs, pstmt, conn);
		}
		
		return null;
	}
	
	@Override
	public void updateUser(UserModel user)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = getConnection();
			
			String sql = "UPDATE users SET password = ?, firstname = ?, lastname = ?, dob = ?, telephone = ?, address1 = ?, "
					+ "address2 = ?, town = ?, postcode = ? WHERE email = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getFirstname());
			pstmt.setString(3, user.getLastname());
			pstmt.setString(4, user.getDob());
			pstmt.setString(5, user.getTelephone());
			pstmt.setString(6, user.getAddress1());
			pstmt.setString(7, user.getAddress2());
			pstmt.setString(8, user.getTown());
			pstmt.setString(9, user.getPostcode());
			pstmt.setString(10, user.getEmail());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			close(pstmt, conn);
		}
	}
	
	@Override
	public void deleteUser(UserModel user)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = getConnection();
			
			String sql = "DELETE FROM users WHERE email = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getEmail());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			close(pstmt, conn);
		}
	}
	
	@Override
	public UserModel getUser(String email)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int db_id = 0;
		String db_email = null;
		String db_password = null;
		String db_firstname = null;
		String db_lastname = null;
		String db_dob = null;
		String db_telephone = null;
		String db_address1 = null;
		String db_address2 = null;
		String db_town = null;
		String db_postcode = null;
		
		try
		{
			conn = getConnection();
			
			String sql = "SELECT * FROM users WHERE email = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				db_id = rs.getInt("id");
				db_email = rs.getString("email");
				db_password = rs.getString("password");
				db_firstname = rs.getString("firstname");
				db_lastname = rs.getString("lastname");
				db_dob = "" + rs.getDate("dob");
				db_telephone = rs.getString("telephone");
				db_address1 = rs.getString("address1");
				db_address2 = rs.getString("address2");
				db_town = rs.getString("town");
				db_postcode = rs.getString("postcode");
			}
			return new UserModel(db_id, db_email, db_password, db_firstname, db_lastname, db_dob,
					db_telephone, db_address1, db_address2, db_town, db_postcode);
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			close(rs, pstmt, conn);
		}
		
		return null;
	}
	
	@Override
	public List<UserModel> getAllUsers()
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<UserModel> userList = new ArrayList<UserModel>();
		
		try
		{
			conn = getConnection();
			
			String sql = "SELECT * FROM users";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
				int db_int = rs.getInt("id");
				String db_email = rs.getString("email");
				String db_password = rs.getString("password");
				String db_firstname = rs.getString("firstname");
				String db_lastname = rs.getString("lastname");
				String db_dob = "" + rs.getDate("dob");
				String db_telephone = rs.getString("telephone");
				String db_address1 = rs.getString("address1");
				String db_address2 = rs.getString("address2");
				String db_town = rs.getString("town");
				String db_postcode = rs.getString("postcode");
				
				userList.add(new UserModel(db_int, db_email, db_password, db_firstname,
						db_lastname, db_dob, db_telephone, db_address1, db_address2, db_town,
						db_postcode));
			}
			return userList;
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			close(rs, pstmt, conn);
		}
		
		return null;
	}
	
	@Override
	public void addProductToCart(UserModel user, int productId, int quantity)
			throws WebshopAppException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		if (quantity >= 0)
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
				e.printStackTrace();
			} finally
			{
				close(rs, pstmt, conn);
			}
			
		} else
		{
			throw new WebshopAppException("quantity is negative", this.getClass().getSimpleName(), "ADD_PRODUCT_TO_CART");
		}
		
	}
	
	@Override
	// quantity
	public void removeFromCart(UserModel user, int productId, int quantity)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
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
				
				if (db_quantity > 0)
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
				e.printStackTrace();
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
				e.printStackTrace();
			} finally
			{
				close(rs, pstmt, conn);
			}
		}
		
	}
	
	public Map<Integer, Integer> getShoppingCartContents(UserModel user)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<Integer, Integer> contents = new LinkedHashMap<Integer, Integer>();
		
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
			
			return contents;
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			close(rs, pstmt, conn);
		}
		
		return null;
		
	}
	
	@Override
	public void updateCart(UserModel user, int productId, int quantity)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
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
			e.printStackTrace();
		} finally
		{
			close(pstmt, conn);
		}
		
	}
	
}
