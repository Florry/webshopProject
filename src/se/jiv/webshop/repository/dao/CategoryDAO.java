package se.jiv.webshop.repository.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.repository.CategoryRepository;

public final class CategoryDAO extends GeneralDAO implements CategoryRepository {
	
	private CategoryModel parseResultSetToModel(ResultSet rs) throws SQLException{
		int id = getInt(rs, "id");
		String name = rs.getString("name");
		int staff_responsible = getInt(rs,
				"staff_responsible");
		
		return new CategoryModel(id, name, staff_responsible);
		
	}

	@Override
	public CategoryModel addCategory(CategoryModel category)
			throws WebshopAppException {
		try (Connection conn = getConnection()) {

			String sql = "INSERT INTO categories (name, staff_responsible)"
					+ "VALUES (?, ?)";

			try (PreparedStatement pstmt = conn.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS)) {
				setString(pstmt, 1, category.getName());
				setInteger(pstmt, 2, category.getStaff_responsible());

				pstmt.executeUpdate();

				int generatedId = CategoryModel.DEFAULT_ID;
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					if (rs.next()) {
						generatedId = rs.getInt(1);
					}
				}

				return new CategoryModel(generatedId, category);
			}

		} catch (SQLException e) {
			throw new WebshopAppException(e.getMessage(), "ADD_CATEGORY");
		}
	}

	@Override
	public CategoryModel getCategory(int id) throws WebshopAppException {
		try (Connection conn = getConnection()) {

			String sql = "SELECT id, name, staff_responsible "
					+ "FROM categories " + "WHERE id = ?";

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, id);

				try (ResultSet rs = pstmt.executeQuery()) {

					if (rs.next()) {
						return parseResultSetToModel(rs);
					}
				}
			}

		} catch (SQLException e) {
			throw new WebshopAppException(e.getMessage(), "GET_CATEGORY");
		}

		return null;
	}

	@Override
	public List<CategoryModel> getAllCategories() throws WebshopAppException {
		List<CategoryModel> categories = new ArrayList<CategoryModel>();

		try (Connection conn = getConnection()) {

			String sql = "SELECT id, name, staff_responsible FROM categories";

			try (Statement stmt = conn.createStatement()) {

				try (ResultSet rs = stmt.executeQuery(sql)) {

					while (rs.next()) {
						categories.add(parseResultSetToModel(rs));
					}
				}
			}

		} catch (SQLException e) {
			throw new WebshopAppException(e.getMessage(), "GET_ALL_CATEGORIES");
		}

		return categories;
	}

	@Override
	public boolean deleteCategory(int id) throws WebshopAppException {
		try (Connection conn = getConnection()) {

			String sql = "DELETE FROM categories " + "WHERE id = ?";

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, id);

				int result = pstmt.executeUpdate();
				
				if (result > 0) {
					return true;
				}
			}

		} catch (SQLException e) {
			throw new WebshopAppException(e.getMessage(), "DELETE_CATEGORY");
		}

		return false;
	}

}
