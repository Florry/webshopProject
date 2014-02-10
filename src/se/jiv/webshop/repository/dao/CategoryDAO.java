package se.jiv.webshop.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.repository.CategoryRepository;

public final class CategoryDAO extends GeneralDAO implements CategoryRepository
{
	private static final Logger LOGGER = Logger.getLogger(GeneralDAO.class);
	
	private void prepareStatementFromModel(PreparedStatement pstmt, CategoryModel category)
			throws SQLException
	{
		setString(pstmt, 1, category.getName());
		setInteger(pstmt, 2, category.getStaff_responsible());
	}
	
	private CategoryModel parseResultSetToModel(ResultSet rs) throws SQLException
	{
		int id = getInt(rs, "id");
		String name = rs.getString("name");
		int staff_responsible = getInt(rs, "staff_responsible");
		
		return new CategoryModel(id, name, staff_responsible);
		
	}
	
	@Override
	public CategoryModel addCategory(CategoryModel category) throws WebshopAppException
	{
		
		if (category != null)
		{
			try (Connection conn = getConnection())
			{
				
				String sql = "INSERT INTO categories (name, staff_responsible)" + "VALUES (?, ?)";
				
				try (PreparedStatement pstmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS))
				{
					
					prepareStatementFromModel(pstmt, category);
					
					pstmt.executeUpdate();
					
					int generatedId = CategoryModel.DEFAULT_ID;
					try (ResultSet rs = pstmt.getGeneratedKeys())
					{
						if (rs.next())
						{
							generatedId = rs.getInt(1);
						}
					}
					
					CategoryModel newModel = new CategoryModel(generatedId, category);
					
					LOGGER.trace("Category inserted: " + newModel);
					
					return newModel;
					
				}
				
			} catch (SQLException e)
			{
				WebshopAppException excep = new WebshopAppException(e, this.getClass()
						.getSimpleName(), "ADD_CATEGORY");
				LOGGER.error(excep);
				throw excep;
			}
		} else
		{
			WebshopAppException excep = new WebshopAppException("Category can't be null", this
					.getClass().getSimpleName(), "ADD_CATEGORY");
			LOGGER.error(excep);
			throw excep;
		}
	}
	
	@Override
	public boolean updateCategory(CategoryModel category) throws WebshopAppException
	{
		
		if (category != null)
		{
			
			try (Connection conn = getConnection())
			{
				
				String sql = "UPDATE categories SET name = ?, "
						+ "staff_responsible= ? WHERE id = ?";
				
				try (PreparedStatement pstmt = conn.prepareStatement(sql))
				{
					prepareStatementFromModel(pstmt, category);
					
					setInteger(pstmt, 3, category.getId());
					
					pstmt.executeUpdate();
					
					return true;
				}
				
			} catch (SQLException e)
			{
				throw new WebshopAppException(e, this.getClass().getSimpleName(),
						"UPDATED_CATEGORY");
			}
		} else
		{
			throw new WebshopAppException("Category can't be null",
					this.getClass().getSimpleName(), "UPDATED_CATEGORY");
		}
	}
	
	@Override
	public CategoryModel getCategory(int id) throws WebshopAppException
	{
		try (Connection conn = getConnection())
		{
			
			String sql = "SELECT id, name, staff_responsible " + "FROM categories WHERE id = ?";
			
			try (PreparedStatement pstmt = conn.prepareStatement(sql))
			{
				
				setInteger(pstmt, 1, id);
				
				try (ResultSet rs = pstmt.executeQuery())
				{
					
					if (rs.next())
					{
						return parseResultSetToModel(rs);
					}
				}
			}
			
		} catch (SQLException e)
		{
			throw new WebshopAppException(e, this.getClass().getSimpleName(), "GET_CATEGORY");
		}
		
		return null;
	}
	
	@Override
	public CategoryModel searchCategoryByName(String name) throws WebshopAppException
	{
		if (name != null)
		{
			try (Connection conn = getConnection())
			{
				
				String sql = "SELECT id, name, staff_responsible "
						+ "FROM categories WHERE name = ?";
				
				try (PreparedStatement pstmt = conn.prepareStatement(sql))
				{
					setString(pstmt, 1, name);
					
					try (ResultSet rs = pstmt.executeQuery())
					{
						
						if (rs.next())
						{
							return parseResultSetToModel(rs);
						}
					}
				}
				
			} catch (SQLException e)
			{
				throw new WebshopAppException(e, this.getClass().getSimpleName(),
						"SEARCH_CATEGORY_BY_NAME");
			}
		}
		return null;
	}
	
	@Override
	public List<CategoryModel> getAllCategories() throws WebshopAppException
	{
		
		List<CategoryModel> categories = new ArrayList<CategoryModel>();
		
		try (Connection conn = getConnection())
		{
			
			String sql = "SELECT id, name, staff_responsible FROM categories";
			
			try (Statement stmt = conn.createStatement())
			{
				
				try (ResultSet rs = stmt.executeQuery(sql))
				{
					
					while (rs.next())
					{
						categories.add(parseResultSetToModel(rs));
					}
				}
			}
		} catch (SQLException e)
		{
			throw new WebshopAppException(e, this.getClass().getSimpleName(), "GET_ALL_CATEGORIES");
		}
		
		return categories;
	}
	
	@Override
	public boolean deleteCategory(int id) throws WebshopAppException
	{
		
		try (Connection conn = getConnection())
		{
			
			String sql = "DELETE FROM categories " + "WHERE id = ?";
			
			try (PreparedStatement pstmt = conn.prepareStatement(sql))
			{
				setInteger(pstmt, 1, id);
				
				int result = pstmt.executeUpdate();
				
				return result > 0;
			}
			
		} catch (SQLException e)
		{
			throw new WebshopAppException(e, this.getClass().getSimpleName(), "DELETE_CATEGORY");
		}
	}
	
}
