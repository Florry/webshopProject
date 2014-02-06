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

public final class ProductDAO extends GeneralDAO implements ProductRepository {
	@Override
	public ProductModel createProduct(ProductModel product)
			throws WebshopAppException {
		if (product != null) {
			int generatedId = 0;
			try (Connection conn = getConnection()) {

				conn.setAutoCommit(false);

				String sql = "INSERT INTO products(name,description,cost,rrp) VALUES(?,?,?,?)";

				try (PreparedStatement pstmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS)) {
					setString(pstmt, 1, product.getName());
					setString(pstmt, 2, product.getDescription());
					setDouble(pstmt, 3, product.getCost());
					setDouble(pstmt, 4, product.getRrp());

					pstmt.executeUpdate();

					try (ResultSet rs = pstmt.getGeneratedKeys()) {

						if (rs.next()) {
							generatedId = rs.getInt(1);
						}

					}

					insertProductCategories(conn, generatedId,
							product.getCategories());

					conn.commit();

					return new ProductModel.Builder(product.getName())
							.id(generatedId)
							.description(product.getDescription())
							.cost(product.getCost()).rrp(product.getRrp())
							.categories(product.getCategories()).build();

				} catch (SQLException e) {
					rollback(conn);
					throw e;
				} catch (Exception e1) {
					rollback(conn);
					throw e1;
				}

			} catch (SQLException e2) {
				throw new WebshopAppException(e2, this.getClass()
						.getSimpleName(), "CREATE_PRODUCT");
			}
		} else {
			throw new WebshopAppException("Product can't be null", this
					.getClass().getSimpleName(), "CREATE_PRODUCT");
		}
	}

	private void insertProductCategories(Connection conn, int productId,
			List<Integer> categories) throws SQLException {

		String sql = "INSERT INTO product_categories VALUES( ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (int categoryId : categories) {

				setInteger(pstmt, 1, productId);
				setInteger(pstmt, 2, categoryId);

				pstmt.addBatch();
			}
			pstmt.executeBatch();
		}

	}

	@Override
	public boolean updateProduct(ProductModel product)
			throws WebshopAppException {

		if (product != null) {

			try (Connection conn = getConnection()) {
				conn.setAutoCommit(false);

				try {
					int nUpdates = updateProduct(conn, product);

					if (nUpdates > 0) {
						deleteProductCategories(conn, product.getId());

						insertProductCategories(conn, product.getId(),
								product.getCategories());
					}

					commit(conn);

					return true;
				} catch (SQLException e) {
					rollback(conn);
					throw e;
				} catch (Exception e1) {
					rollback(conn);
					throw e1;
				}

			} catch (SQLException e2) {
				throw new WebshopAppException(e2, this.getClass()
						.getSimpleName(), "UPDATE_PRODUCT");
			}
		} else {
			throw new WebshopAppException("Product can't be null", this
					.getClass().getSimpleName(), "UPDATE_PRODUCT");
		}
	}

	private int updateProduct(Connection conn, ProductModel product)
			throws SQLException {

		String sql = "UPDATE products SET name = ?, description = ?, cost = ?, rrp = ? WHERE id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			setString(pstmt, 1, product.getName());
			setString(pstmt, 2, product.getDescription());
			setDouble(pstmt, 3, product.getCost());
			setDouble(pstmt, 4, product.getRrp());
			setInteger(pstmt, 5, product.getId());

			return pstmt.executeUpdate();
		}
	}

	private void deleteProductCategories(Connection conn, int productId)
			throws SQLException {

		String sql = "DELETE FROM product_categories WHERE product_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			setInteger(pstmt, 1, productId);

			pstmt.executeUpdate();
		}
	}

	@Override
	public boolean deleteProduct(int productId) throws WebshopAppException {

		try (Connection conn = getConnection()) {

			String sql = "DELETE FROM products WHERE id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				setInteger(pstmt, 1, productId);

				pstmt.executeUpdate();

				// There are delete_on_cascade in BBDD but we delete anyway
				deleteProductCategories(conn, productId);

				return true;
			} catch (SQLException e) {
				throw e;
			}
		} catch (SQLException e1) {
			throw new WebshopAppException(e1, this.getClass().getSimpleName(),
					"DELETE_PRODUCT");
		}
	}

	@Override
	public List<ProductModel> getProductByName(String productName)
			throws WebshopAppException {

		if (productName != null) {

			try (Connection conn = getConnection()) {

				String sql = "SELECT * FROM products WHERE name = ?";
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					setString(pstmt, 1, productName);

					try (ResultSet rs = pstmt.executeQuery()) {

						List<ProductModel> foundProducts = new ArrayList<>();
						while (rs.next()) {
							foundProducts.add(parseModel(conn, rs));
						}

						return foundProducts;
					}
				}

			} catch (SQLException e) {
				throw new WebshopAppException(e, this.getClass()
						.getSimpleName(), "GET_PRODUCT_BY_NAME");
			}
		}
		return new ArrayList<ProductModel>();
	}

	private ProductModel parseModel(Connection conn, ResultSet rs)
			throws SQLException {

		int id = rs.getInt("id");
		String name = rs.getString("name");
		String description = rs.getString("description");
		double price = rs.getDouble("cost");
		double rrp = rs.getDouble("rrp");

		List<Integer> categories = getCategories(conn, id);

		return new ProductModel.Builder(name).id(id).description(description)
				.cost(price).rrp(rrp).categories(categories).build();
	}

	@Override
	public List<ProductModel> getProductsByCost(double cost)
			throws WebshopAppException {

		try (Connection conn = getConnection()) {

			String sql = "SELECT * FROM products WHERE cost = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				setDouble(pstmt, 1, cost);

				try (ResultSet rs = pstmt.executeQuery()) {

					List<ProductModel> foundProducts = new ArrayList<>();
					while (rs.next()) {
						foundProducts.add(parseModel(conn, rs));
					}

					return foundProducts;
				}
			}
		} catch (SQLException e) {
			throw new WebshopAppException(e, this.getClass().getSimpleName(),
					"GET_PRODUCT_BY_COST");
		}
	}

	@Override
	public ProductModel getProductById(int productId)
			throws WebshopAppException {

		try (Connection conn = getConnection()) {

			String sql = "SELECT * FROM products WHERE id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				setInteger(pstmt, 1, productId);

				try (ResultSet rs = pstmt.executeQuery()) {
					if (rs.next()) {
						return parseModel(conn, rs);
					}
				}
			}
			return null;
		} catch (SQLException e) {
			throw new WebshopAppException(e, this.getClass().getSimpleName(),
					"GET_PRODUCT_BY_ID");
		}

	}

	@Override
	public List<ProductModel> getProductsByCategory(int categoryId)
			throws WebshopAppException {

		try (Connection conn = getConnection()) {

			String sql = "SELECT id, name, description, cost, rrp FROM product_categories "
					+ "INNER JOIN products ON product_id = products.id"
					+ " WHERE category_id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				setInteger(pstmt, 1, categoryId);

				try (ResultSet rs = pstmt.executeQuery()) {

					List<ProductModel> foundProducts = new ArrayList<>();
					while (rs.next()) {
						foundProducts.add(parseModel(conn, rs));
					}

					return foundProducts;
				}
			}
		} catch (SQLException e) {
			throw new WebshopAppException(e, this.getClass().getSimpleName(),
					"GET_PRODUCT_BY_CATEGORY");
		}
	}

	@Override
	public List<ProductModel> getAllProducts() throws WebshopAppException {

		try (Connection conn = getConnection()) {

			String sql = "SELECT * FROM products";
			try (Statement stmt = conn.createStatement()) {

				try (ResultSet rs = stmt.executeQuery(sql)) {

					List<ProductModel> foundProducts = new ArrayList<>();
					while (rs.next()) {
						foundProducts.add(parseModel(conn, rs));
					}

					return foundProducts;
				}
			}
		} catch (SQLException e) {
			throw new WebshopAppException(e, this.getClass().getSimpleName(),
					"GET_ALL_PRODUCTS");
		}
	}

	private List<Integer> getCategories(Connection conn, int id)
			throws SQLException {

		String sql = "SELECT category_id FROM product_categories where product_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			setInteger(pstmt, 1, id);

			try (ResultSet rs = pstmt.executeQuery()) {

				List<Integer> categoryIds = new ArrayList<Integer>();

				while (rs.next()) {
					int categoryId = rs.getInt("category_id");
					categoryIds.add(categoryId);
				}
				return categoryIds;
			}
		}
	}

	@Override
	public List<Integer> getCategoriesOfProduct(int productId)
			throws WebshopAppException {

		try (Connection conn = getConnection()) {

			return getCategories(conn, productId);

		} catch (SQLException e) {
			throw new WebshopAppException(e, this.getClass().getSimpleName(),
					"GET_CATEGORIES_OF_PRODUCT");
		}
	}

}
