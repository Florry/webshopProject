package se.jiv.webshop.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.ProductModel;
import se.jiv.webshop.repository.dao.ProductDAO;

public class ProductJUnit {
	static final String JDBC_DRIVER = DevDBConfig.JDBC_DRIVER;
	static final String DB_URL = DevDBConfig.DB_URL;
	static final String DB_USER = DevDBConfig.DB_USER;
	static final String DB_PASSWORD = DevDBConfig.DB_PASSWORD;

	@Before
	public void setUp() throws Exception {
		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				String sql = "INSERT INTO products(name,description,cost,rrp) VALUES('A rush of blood to the head',"
						+ "'Best Coldplay Album', 149, 500)";

				stmt.executeUpdate(sql);

			}
		}

	}

	@After
	public void tearDown() throws Exception {
		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				String sql = "DELETE FROM products WHERE name = 'A rush of blood to the head'";

				stmt.executeUpdate(sql);
			}
		}
	}

	@Test
	public void canCreateProduct() {
		ProductDAO productDao = new ProductDAO();
		ProductModel expectedProduct = null;
		ProductModel actualProduct = null;

		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				String sql = "SELECT * FROM products";
				try (ResultSet rs = stmt.executeQuery(sql)) {

					int id = 0;

					if (rs.last()) {
						id = rs.getInt("id");
					}

					expectedProduct = ProductModel.builder("Night Visions")
							.id(id + 1).description("Imagine Dragons")
							.cost(149).rrp(400).build();

					try {
						actualProduct = productDao
								.createProduct(expectedProduct);

					} catch (WebshopAppException e) {
						e.printStackTrace();
					}

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertTrue("they are not equal", expectedProduct.equals(actualProduct));

	}

	@Test
	public void canGetProductsByName() {
		ProductDAO productDao = new ProductDAO();
		List<ProductModel> expectedProducts = new ArrayList<>();
		List<ProductModel> actualProducts = null;

		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				String sql = "SELECT * FROM products WHERE name = 'A rush of blood to the head'";
				try (ResultSet rs = stmt.executeQuery(sql)) {

					while (rs.next()) {

						int id = rs.getInt("id");
						String name = rs.getString("name");
						String description = rs.getString("description");
						double cost = rs.getDouble("cost");
						double rrp = rs.getDouble("rrp");

						expectedProducts.add(ProductModel.builder(name).id(id)
								.description(description).cost(cost).rrp(rrp)
								.build());
					}

					try {
						actualProducts = productDao
								.getProductByName("A rush of blood to the head");
					} catch (WebshopAppException e) {
						e.printStackTrace();
					}

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertTrue("Not the same", expectedProducts.equals(actualProducts));
	}

	@Test
	public void canGetProductByNonExcistingName() {
		ProductDAO productDao = new ProductDAO();
		List<ProductModel> expected = new ArrayList<>();
		List<ProductModel> actual = null;
		try {
			actual = productDao.getProductByName("alskdaskldj");
		} catch (WebshopAppException e) {
			e.printStackTrace();
			fail("Exception");
		}

		assertTrue(expected.equals(actual));
	}

	@Test
	public void canGetProductById() {
		ProductDAO productDao = new ProductDAO();
		ProductModel expectedProduct = null;
		ProductModel actualProduct = null;

		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				String sql = "SELECT * FROM products WHERE id = 2";
				try (ResultSet rs = stmt.executeQuery(sql)) {

					while (rs.next()) {

						int id = rs.getInt("id");
						String name = rs.getString("name");
						String description = rs.getString("description");
						double cost = rs.getDouble("cost");
						double rrp = rs.getDouble("rrp");

						expectedProduct = ProductModel.builder(name).id(id)
								.description(description).cost(cost).rrp(rrp)
								.build();
					}

					try {
						actualProduct = productDao.getProductById(2);
					} catch (WebshopAppException e) {
						e.printStackTrace();
					}

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertTrue("Not able to get the right product",
				expectedProduct.equals(actualProduct));
	}

	@Test
	public void canGetProductByNonExcistingId() {
		ProductDAO productDao = new ProductDAO();
		ProductModel retrieved = null;
		try {
			retrieved = productDao.getProductById(1234);
		} catch (WebshopAppException e) {
			e.printStackTrace();
			fail("Exception");
		}

		assertNull(retrieved);
	}

	@Test
	public void canGetAllProducts() {
		ProductDAO productDao = new ProductDAO();
		List<ProductModel> expectedProducts = new ArrayList<>();
		List<ProductModel> actualProducts = null;

		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				String sql = "SELECT * FROM products";
				try (ResultSet rs = stmt.executeQuery(sql)) {

					while (rs.next()) {
						int id = rs.getInt("id");
						String name = rs.getString("name");
						String description = rs.getString("description");
						double cost = rs.getDouble("cost");
						double rrp = rs.getDouble("rrp");

						expectedProducts.add(ProductModel.builder(name).id(id)
								.description(description).cost(cost).rrp(rrp)
								.build());
					}

					try {
						actualProducts = productDao.getAllProducts();
					} catch (WebshopAppException e) {
						e.printStackTrace();
					}

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		assertTrue("The product list are not the same",
				expectedProducts.equals(actualProducts));

	}

	@Test
	public void canUpdateProduct() {
		ProductDAO productDao = new ProductDAO();
		ProductModel newProduct = ProductModel.builder("Megalithic Melody")
				.id(3).description("AWOLNATION").cost(145).rrp(199).build();
		boolean expected = true;
		boolean actual = false;

		try {
			actual = productDao.updateProduct(newProduct);
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}

		assertEquals("Was not able to update", expected, actual);

	}

	@Test
	public void canDeleteProduct() {
		ProductDAO productDao = new ProductDAO();
		boolean expected = true;
		boolean actual = false;

		try {
			actual = productDao.deleteProduct(5);
		} catch (WebshopAppException e) {
			e.printStackTrace();
		}

		assertEquals("Was not able to delete", expected, actual);
	}

	@Test
	public void canGetProductsByCost() {
		ProductDAO productDao = new ProductDAO();
		List<ProductModel> expectedProducts = new ArrayList<>();
		List<ProductModel> actualProducts = null;

		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				String sql = "SELECT * FROM products WHERE cost = 149";
				try (ResultSet rs = stmt.executeQuery(sql)) {

					while (rs.next()) {
						int id = rs.getInt("id");
						String name = rs.getString("name");
						String description = rs.getString("description");
						double cost = rs.getDouble("cost");
						double rrp = rs.getDouble("rrp");

						expectedProducts.add(ProductModel.builder(name).id(id)
								.description(description).cost(cost).rrp(rrp)
								.build());
					}

					try {
						actualProducts = productDao.getProductsByCost(149);
					} catch (WebshopAppException e) {
						e.printStackTrace();
					}

				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		assertTrue("The foundlist are not the same",
				expectedProducts.equals(actualProducts));

	}

	@Test
	public void canGetProductByNonExcistingCost() {
		ProductDAO productDao = new ProductDAO();
		List<ProductModel> expected = new ArrayList<>();
		List<ProductModel> actual = null;
		try {
			actual = productDao.getProductsByCost(123456);
		} catch (WebshopAppException e) {
			e.printStackTrace();
			fail("Exception");
		}

		assertTrue(expected.equals(actual));
	}

	@Test
	public void canGetProductsByCategory() {
		ProductDAO productDao = new ProductDAO();
		List<ProductModel> expectedProducts = new ArrayList<>();
		List<ProductModel> actualProducts = null;

		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				String sql = "SELECT id, name, description, cost, rrp FROM product_categories "
						+ "INNER JOIN products ON product_id = products.id"
						+ " WHERE category_id = 1";
				try (ResultSet rs = stmt.executeQuery(sql)) {

					while (rs.next()) {
						int id = rs.getInt("id");
						String name = rs.getString("name");
						String description = rs.getString("description");
						double cost = rs.getDouble("cost");
						double rrp = rs.getDouble("rrp");

						expectedProducts.add(ProductModel.builder(name).id(id)
								.description(description).cost(cost).rrp(rrp)
								.build());
					}

					try {
						actualProducts = productDao.getProductsByCategory(1);
					} catch (WebshopAppException e) {
						e.printStackTrace();
					}

				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		assertTrue("Unable to get products by category",
				expectedProducts.equals(actualProducts));

	}

	@Test
	public void canGetProductByNonExcistingCategory() {
		ProductDAO productDao = new ProductDAO();
		List<ProductModel> expected = new ArrayList<>();
		List<ProductModel> actual = null;
		try {
			actual = productDao.getProductsByCategory(1234);
		} catch (WebshopAppException e) {
			e.printStackTrace();
			fail("Exception");
		}

		assertTrue(expected.equals(actual));
	}

	@Test
	public void canGetCategoriesOfProduct() {
		ProductDAO productDao = new ProductDAO();
		List<Integer> expectedCategories = new ArrayList<>();
		List<Integer> actualCategories = null;

		try (Connection conn = DevDBConfig.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				String sql = "SELECT category_id FROM product_categories where product_id = 2";

				try (ResultSet rs = stmt.executeQuery(sql)) {

					while (rs.next()) {
						int categoryId = rs.getInt("category_id");

						expectedCategories.add(categoryId);
					}

					try {
						actualCategories = productDao.getCategoriesOfProduct(2);
					} catch (WebshopAppException e) {
						e.printStackTrace();
					}

				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		assertTrue("Unable to get categories of product",
				expectedCategories.equals(actualCategories));
	}

	@Test
	public void canGetCategoriesOfNonExcistingProduct() {
		ProductDAO productDao = new ProductDAO();
		List<Integer> expected = new ArrayList<>();
		List<Integer> actual = null;
		try {
			actual = productDao.getCategoriesOfProduct(1234);
		} catch (WebshopAppException e) {
			e.printStackTrace();
			fail("Exception");
		}

		assertTrue(expected.equals(actual));
	}

}
