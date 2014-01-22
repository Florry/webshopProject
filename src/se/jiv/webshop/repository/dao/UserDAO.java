package se.jiv.webshop.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.model.ProductModel;
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
			pstmt = conn.prepareStatement(sql);
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
			
			return user;
			
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
	public UserModel updateUser(UserModel user)
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
		return user;
	}
	
	@Override
	public void deleteUser(UserModel user)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try
		{
			conn = getConnection();
			
			String sql = "DELETE FROM users " + "WHERE email = ?";
			
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
			return new UserModel(db_email, db_password, db_firstname, db_lastname, db_dob,
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
	public int getUserId(UserModel user)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int id = 0;
		
		try
		{
			conn = getConnection();
			
			String sql = "SELECT id FROM users WHERE email = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getEmail());
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				id = rs.getInt("id");
			}
			return id;
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			close(rs, pstmt, conn);
		}
		
		return 0;
	}
	
	@Override
	public List<UserModel> getAllUsers()
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<UserModel> userList = new ArrayList<UserModel>();
		
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
			
			String sql = "SELECT * FROM users";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next())
			{
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
				
				userList.add(new UserModel(db_email, db_password, db_firstname, db_lastname,
						db_dob, db_telephone, db_address1, db_address2, db_town, db_postcode));
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
	// addProductToCart KANNS VALDIGT FULT!!!!!!!
	public void addProductToCart(UserModel user, Integer id)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int quantity = 1;
		
		try
		{
			conn = getConnection();
			
			String sql = "SELECT quantity FROM shopping_cart WHERE user_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getUserId(user));
			
			int db_quantity;
			
			rs = pstmt.executeQuery();
			
			if (rs.next())
			{
				db_quantity = rs.getInt(quantity);
				
				if (db_quantity > 0)
				{
					quantity += db_quantity;
				}
			}
			close(rs, pstmt, conn);
			if (quantity > 1)
			{
				try
				{
					conn = getConnection();
					
					sql = "UPDATE shopping_cart SET quantity = ? WHERE user_id = ? and product_id = ?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, quantity);
					pstmt.setInt(2, getUserId(user));
					pstmt.setInt(3, id);
					
					pstmt.executeUpdate();
					
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
					
					sql = "INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES (?, ?, ?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, getUserId(user));
					pstmt.setInt(2, id);
					pstmt.setInt(3, quantity);
					
					pstmt.executeUpdate();
					
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					close(rs, pstmt, conn);
				}
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			close(rs, pstmt, conn);
		}
		
	}
	
	@Override
	public void removeFromCart(UserModel user, Integer id)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			conn = getConnection();
			
			String sql = "DELETE FROM shopping_cart WHERE user_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getUserId(user));
			
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
