package se.jiv.webshop.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import se.jiv.webshop.model.CategoryModel;
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
			
			String sql = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
		return null;
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
	public UserModel getUser(Integer id)
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
			
			String sql = "SELECT * FROM users WHERE id = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
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
				db_postcode = rs.getString("db_postcode");
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
	public List<UserModel> getAllUsers()
	{
		
		return null;
	}
	
}
