package se.jiv.webshop.repository.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.repository.CategoryRepository;

public final class CategoryDAO extends GeneralDAO implements CategoryRepository {

	@Override
	public CategoryModel addCategory(CategoryModel category) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "INSERT INTO categories (name, staff_responsible)"
					+ "VALUES (?, ?)";

			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, category.getName());
			pstmt.setInt(2, category.getStaff_responsible());

			pstmt.executeUpdate();

			Integer generatedId = CategoryModel.DEFAULT_ID;
			rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				generatedId = rs.getInt(1);
			}

			return new CategoryModel(generatedId, category);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}

		return null;
	}

	@Override
	public CategoryModel getCategory(Integer id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "SELECT id, name, staff_responsible "
					+ "FROM categories " + "WHERE id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				Integer db_id = rs.getInt("id");
				String db_name = rs.getString("name");
				Integer db_staff_responsible = rs.getInt("staff_responsible");

				return new CategoryModel(db_id, db_name, db_staff_responsible);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, pstmt, conn);
		}

		return null;
	}

	@Override
	public List<CategoryModel> getAllCategories() {
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
				Integer db_id = rs.getInt("id");
				String db_name = rs.getString("name");
				Integer db_staff_responsible = rs.getInt("staff_responsible");

				categories.add(new CategoryModel(db_id, db_name,
						db_staff_responsible));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt, conn);
		}
		return categories;
	}

	@Override
	public boolean deleteCategory(Integer id) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "DELETE FROM categories " + "WHERE id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			int result = pstmt.executeUpdate();
			// TODO JLP --> TO TEST, we have to validate if result is 1(if
			// remove something) or 0 if not
			if (result > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt, conn);
		}

		return false;
	}

}
