package se.jiv.webshop.repository.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.repository.CategoryRepository;

public final class CategoryDAO extends GeneralDAO implements CategoryRepository {

	private void prepareStatementFromModel(PreparedStatement pstmt,
			CategoryModel category) throws SQLException {
		setString(pstmt, 1, category.getName());
		setInteger(pstmt, 2, category.getStaff_responsible());
	}

	private CategoryModel parseResultSetToModel(ResultSet rs)
			throws SQLException {
		int id = getInt(rs, "id");
		String name = rs.getString("name");
		int staff_responsible = getInt(rs, "staff_responsible");

		return new CategoryModel(id, name, staff_responsible);

	}

	@Override
	public CategoryModel addCategory(CategoryModel category)
			throws WebshopAppException {

		if (category != null) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = getConnection();

				String sql = "INSERT INTO categories (name, staff_responsible)"
						+ "VALUES (?, ?)";

				pstmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				prepareStatementFromModel(pstmt, category);

				pstmt.executeUpdate();

				int generatedId = CategoryModel.DEFAULT_ID;
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					generatedId = rs.getInt(1);
				}

				return new CategoryModel(generatedId, category);

			} catch (SQLException e) {
				throw new WebshopAppException(e, this.getClass()
						.getSimpleName(), "ADD_CATEGORY");
			} finally {
				close(rs, pstmt, conn);
			}
		}else{
			throw new WebshopAppException("Category can't be null", this.getClass()
					.getSimpleName(), "ADD_CATEGORY");
		}
	}

	@Override
	public boolean updateCategory(CategoryModel category)
			throws WebshopAppException {
		if (category != null) {
			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = getConnection();

				String sql = "UPDATE categories SET name = ?, "
						+ "staff_responsible= ? WHERE id = ?";

				pstmt = conn.prepareStatement(sql);
				prepareStatementFromModel(pstmt, category);
				setInteger(pstmt, 3, category.getId());

				pstmt.executeUpdate();

				return true;

			} catch (SQLException e) {
				throw new WebshopAppException(e, this.getClass()
						.getSimpleName(), "UPDATED_CATEGORY");
			} finally {
				close(pstmt, conn);
			}
		} else {
			throw new WebshopAppException("Category can't be null", this
					.getClass().getSimpleName(), "ADD_CATEGORY");
		}
	}

	@Override
	public CategoryModel getCategory(int id) throws WebshopAppException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "SELECT id, name, staff_responsible "
					+ "FROM categories WHERE id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return parseResultSetToModel(rs);
			}

		} catch (SQLException e) {
			throw new WebshopAppException(e, this.getClass().getSimpleName(),
					"GET_CATEGORY");
		} finally {
			close(rs, pstmt, conn);
		}

		return null;
	}

	@Override
	public CategoryModel searchCategoryByName(String name)
			throws WebshopAppException {
		if (name != null) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = getConnection();

				String sql = "SELECT id, name, staff_responsible "
						+ "FROM categories WHERE name = ?";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, name);

				rs = pstmt.executeQuery();

				if (rs.next()) {
					return parseResultSetToModel(rs);
				}

			} catch (SQLException e) {
				throw new WebshopAppException(e, this.getClass()
						.getSimpleName(), "SEARCH_CATEGORY_BY_NAME");
			} finally {
				close(rs, pstmt, conn);
			}
		}
		return null;
	}

	@Override
	public List<CategoryModel> getAllCategories() throws WebshopAppException {

		List<CategoryModel> categories = new ArrayList<CategoryModel>();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "SELECT id, name, staff_responsible FROM categories";

			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				categories.add(parseResultSetToModel(rs));
			}

		} catch (SQLException e) {
			throw new WebshopAppException(e, this.getClass().getSimpleName(),
					"GET_ALL_CATEGORIES");
		} finally {
			close(rs, stmt, conn);
		}

		return categories;
	}

	@Override
	public boolean deleteCategory(int id) throws WebshopAppException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "DELETE FROM categories " + "WHERE id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			int result = pstmt.executeUpdate();

			return result > 0;

		} catch (SQLException e) {
			throw new WebshopAppException(e, this.getClass().getSimpleName(),
					"DELETE_CATEGORY");
		} finally {
			close(pstmt, conn);
		}
	}

}
