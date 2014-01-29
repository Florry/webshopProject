package se.jiv.webshop.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.ProductModel;
import se.jiv.webshop.repository.ProductRepository;

public class ProductDAO extends GeneralDAO implements ProductRepository {
	@Override
	public ProductModel createProduct(ProductModel product)
			throws WebshopAppException {
		if (product != null) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = getConnection();
				conn.setAutoCommit(false);

				String sql = "INSERT INTO products(name,description,cost,rrp) VALUES(?,?,?,?)";
				pstmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				setString(pstmt, 1, product.getName());
				setString(pstmt, 2, product.getDescription());
				setDouble(pstmt, 3, product.getCost());
				setDouble(pstmt, 4, product.getRrp());

				pstmt.executeUpdate();

				int generatedId = 0;
				rs = pstmt.getGeneratedKeys();

				if (rs.next()) {
					generatedId = rs.getInt(1);
				}

				close(rs, pstmt);

				insertCategories(conn, pstmt, generatedId,
						product.getCategories());

				commit(conn);

				return new ProductModel(generatedId, product);
			} catch (SQLException e) {
				throw new WebshopAppException(e, this.getClass()
						.getSimpleName(), "CREATE_PRODUCT");
			} finally {
				close(rs, pstmt, conn);
			}
		}else{
			throw new WebshopAppException("Product can't be null", this.getClass()
					.getSimpleName(), "CREATE_PRODUCT");
		}
	}

	private void insertCategories(Connection conn, PreparedStatement pstmt,
			int productId, List<Integer> categories) throws SQLException {
		String sql = "INSERT INTO product_categories VALUES( ?, ?)";
		for (int categoryId : categories) {
			pstmt = conn.prepareStatement(sql);
			setInteger(pstmt, 1, productId);
			setInteger(pstmt, 2, categoryId);

			pstmt.executeUpdate();
		}
	}

	@Override
	public boolean updateProduct(ProductModel product)
			throws WebshopAppException {
		if (product != null) {
			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = getConnection();
				conn.setAutoCommit(false);

				String sql = "UPDATE products SET name = ?, description = ?, cost = ?, rrp = ? WHERE id = ?";
				pstmt = conn.prepareStatement(sql);
				setString(pstmt, 1, product.getName());
				setString(pstmt, 2, product.getDescription());
				setDouble(pstmt, 3, product.getCost());
				setDouble(pstmt, 4, product.getRrp());
				setInteger(pstmt, 5, product.getId());

				pstmt.executeUpdate();

				close(pstmt);

				sql = "DELETE FROM product_categories WHERE product_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, product.getId());

				pstmt.executeUpdate();

				close(pstmt);

				insertCategories(conn, pstmt, product.getId(),
						product.getCategories());

				commit(conn);

				return true;

			} catch (SQLException e) {
				throw new WebshopAppException(e, this.getClass()
						.getSimpleName(), "UPDATE_PRODUCT");
			} finally {
				close(pstmt, conn);
			}
		}
		return false;
	}

	@Override
	public boolean deleteProduct(int productId) throws WebshopAppException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "DELETE FROM products WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);

			pstmt.executeUpdate();

			return true;
		} catch (SQLException e) {
			throw new WebshopAppException(e, this.getClass().getSimpleName(),
					"DELETE_PRODUCT");
		} finally {
			close(pstmt, conn);
		}
	}

	@Override
	public List<ProductModel> getProductByName(String productName)
			throws WebshopAppException {
		if (productName != null) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = getConnection();

				String sql = "SELECT * FROM products WHERE name = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, productName);
				rs = pstmt.executeQuery();

				List<ProductModel> foundProducts = new ArrayList<>();

				while (rs.next()) {
					foundProducts.add(parseModel(conn, rs));
				}

				return foundProducts;

			} catch (SQLException e) {
				throw new WebshopAppException(e, this.getClass()
						.getSimpleName(), "GET_PRODUCT_BY_NAME");
			} finally {
				close(rs, pstmt, conn);
			}
		}
		return null;
	}

	private ProductModel parseModel(Connection conn, ResultSet rs)
			throws SQLException {
		int id = rs.getInt("id");
		String name = rs.getString("name");
		String description = rs.getString("description");
		double price = rs.getInt("cost");
		double rrp = rs.getInt("rrp");
		List<Integer> categories = getCategories(conn, id);

		return new ProductModel(id, name, description, price, rrp, categories);
	}

	@Override
	public List<ProductModel> getProductsByCost(double cost)
			throws WebshopAppException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "SELECT * FROM products WHERE cost = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, cost);

			rs = pstmt.executeQuery();

			List<ProductModel> foundProducts = new ArrayList<>();

			while (rs.next()) {
				foundProducts.add(parseModel(conn, rs));
			}

			return foundProducts;
		} catch (SQLException e) {
			throw new WebshopAppException(e, this.getClass().getSimpleName(),
					"GET_PRODUCT_BY_COST");
		} finally {
			close(rs, pstmt, conn);
		}
	}

	@Override
	public ProductModel getProductById(int productId)
			throws WebshopAppException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "SELECT * FROM products WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, productId);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return parseModel(conn, rs);
			}
			return null;

		} catch (SQLException e) {
			throw new WebshopAppException(e, this.getClass().getSimpleName(),
					"GET_PRODUCT_BY_ID");
		} finally {
			close(rs, pstmt, conn);
		}

	}

	@Override
	public List<ProductModel> getProductsByCategory(int categoryId)
			throws WebshopAppException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "SELECT id, name, description, cost, rrp FROM product_categories "
					+ "INNER JOIN products ON product_id = products.id"
					+ " WHERE category_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryId);

			rs = pstmt.executeQuery();

			List<ProductModel> foundProducts = new ArrayList<>();

			while (rs.next()) {
				foundProducts.add(parseModel(conn, rs));
			}

			return foundProducts;
		}

		catch (SQLException e) {
			throw new WebshopAppException(e, this.getClass().getSimpleName(),
					"GET_PRODUCT_BY_CATEGORY");
		} finally {
			close(rs, pstmt, conn);
		}
	}

	@Override
	public List<ProductModel> getAllProducts() throws WebshopAppException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "SELECT * FROM products";
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);

			List<ProductModel> foundProducts = new ArrayList<>();

			while (rs.next()) {
				foundProducts.add(parseModel(conn, rs));
			}

			return foundProducts;
		} catch (SQLException e) {
			throw new WebshopAppException(e, this.getClass().getSimpleName(),
					"GET_ALL_PRODUCTS");
		} finally {
			close(rs, stmt, conn);
		}
	}

	private List<Integer> getCategories(Connection conn, int id)
			throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			String sql = "SELECT product_id FROM product_categories where category_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			List<Integer> categoryIds = new ArrayList<Integer>();

			while (rs.next()) {
				int currentCategoryId = rs.getInt("product_id");
				categoryIds.add(currentCategoryId);
			}
			return categoryIds;
		} finally {
			close(rs, pstmt);
		}
	}

	@Override
	public List<Integer> getCategoriesOfProduct(int productId)
			throws WebshopAppException {
		Connection conn = null;

		try {
			conn = getConnection();

			return getCategories(conn, productId);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(conn);
		}
		return null;
	}

}
