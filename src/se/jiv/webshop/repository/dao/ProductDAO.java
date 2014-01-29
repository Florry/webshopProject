package se.jiv.webshop.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import se.jiv.webshop.model.ProductModel;
import se.jiv.webshop.repository.ProductRepository;

public class ProductDAO extends GeneralDAO implements ProductRepository
{
	public final int PRODUCT_NAME = 1;
	public final int PRODUCT_DESCRIPTION = 2;
	public final int PRODUCT_COST = 3;
	public final int PRODUCT_RRP = 4;

	@Override
	public ProductModel createProduct(ProductModel product)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			conn = getConnection();
			conn.setAutoCommit(false);

			String sql = "INSERT INTO products(name,description,cost,rrp) VALUES(?,?,?,?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, product.getName());
			pstmt.setString(2, product.getDescription());
			pstmt.setDouble(3, product.getCost());
			pstmt.setDouble(4, product.getRrp());

			pstmt.executeUpdate();

			int generatedId = 0;
			rs = pstmt.getGeneratedKeys();

			if (rs.next())
			{
				generatedId = rs.getInt(1);
			}

			for (int categoryId : product.getCategories())
			{
				sql = "INSERT INTO product_categories VALUES( ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, generatedId);
				pstmt.setInt(2, categoryId);

				pstmt.executeUpdate();
			}

			conn.commit();

			return new ProductModel(generatedId, product);
		}
		catch (SQLException e)
		{
			rollback(conn);
			e.printStackTrace();
		}
		finally
		{
			close(rs, pstmt, conn);
		}
		return null;
	}

	@Override
	public boolean updateProduct(ProductModel product, int productId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			conn = getConnection();
			conn.setAutoCommit(false);

			String sql = "SELECT id, name, description,cost, rrp FROM products WHERE id = ?";
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			pstmt.setInt(1, productId);

			rs = pstmt.executeQuery();

			rs.absolute(1);
			rs.updateString("name", product.getName());
			rs.updateString("description", product.getDescription());
			rs.updateDouble("cost", product.getCost());
			rs.updateDouble("rrp", product.getRrp());
			rs.updateRow();

			pstmt.close();
			rs.close();

			sql = "SELECT category_id FROM product_categories "
					+ "WHERE product_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);

			rs = pstmt.executeQuery();

			List<Integer> allreadyAddedCategories = new ArrayList<>();

			while (rs.next())
			{
				allreadyAddedCategories.add(rs.getInt("category_id"));
			}

			for (int categoryId : product.getCategories())
			{

				if (!allreadyAddedCategories.contains(categoryId))
				{
					sql = "INSERT INTO product_categories VALUES( ?, ?)";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, productId);
					pstmt.setInt(2, categoryId);
					pstmt.executeUpdate();
				}

			}
			conn.commit();
			return true;

		}
		catch (SQLException e)
		{
			rollback(conn);
			e.printStackTrace();
		}
		finally
		{
			close(rs, pstmt, conn);
		}

		return false;
	}

	@Override
	public boolean deleteProduct(int productId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			conn = getConnection();

			String sql = "DELETE FROM products WHERE id = ?";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, productId);

			pstmt.executeUpdate();

			return true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(rs, pstmt, conn);
		}

		return false;
	}

	@Override
	public List<ProductModel> getProductByName(String productName)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			conn = getConnection();

			String sql = "SELECT * FROM products WHERE name = ?";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, productName);
			rs = pstmt.executeQuery();

			String name;
			String description;
			double price;
			double rrp;
			int id;
			List<Integer> categories = new ArrayList<>();
			List<ProductModel> foundProducts = new ArrayList<>();

			while (rs.next())
			{
				id = rs.getInt("id");
				name = rs.getString("name");
				description = rs.getString("description");
				price = rs.getInt("cost");
				rrp = rs.getInt("rrp");
				categories = getCategoriesOfProduct(id);

				ProductModel currentProduct = new ProductModel(id, name, price, description, rrp, categories);

				foundProducts.add(currentProduct);
			}
			return foundProducts;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(rs, pstmt, conn);
		}
		return null;
	}

	@Override
	public List<ProductModel> getProductsByCost(double cost)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			conn = getConnection();

			String sql = "SELECT* FROM products WHERE cost = ?";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setDouble(1, cost);

			rs = pstmt.executeQuery();

			String name;
			String description;
			double price;
			double rrp;
			int id;
			List<Integer> categories = new ArrayList<>();
			List<ProductModel> foundProducts = new ArrayList<>();

			while (rs.next())
			{
				id = rs.getInt("id");
				name = rs.getString("name");
				description = rs.getString("description");
				price = rs.getInt("cost");
				rrp = rs.getInt("rrp");
				categories = getCategoriesOfProduct(id);

				ProductModel currentProduct = new ProductModel(id, name, price, description, rrp, categories);

				foundProducts.add(currentProduct);
			}

			return foundProducts;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(rs, pstmt, conn);
		}
		return null;
	}

	@Override
	public ProductModel getProductById(int productId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			conn = getConnection();

			String sql = "SELECT* FROM products WHERE id = ?";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setDouble(1, productId);

			rs = pstmt.executeQuery();

			int id = 0;
			String name = "";
			String description = "";
			double price = 0;
			double rrp = 0;
			List<Integer> categories = new ArrayList<>();

			while (rs.next())
			{
				id = rs.getInt("id");
				name = rs.getString("name");
				description = rs.getString("description");
				price = rs.getDouble("cost");
				rrp = rs.getDouble("rrp");
				categories = getCategoriesOfProduct(id);
			}

			ProductModel currentProduct = new ProductModel(id, name, price, description, rrp,
					categories);

			return currentProduct;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(rs, pstmt, conn);
		}
		return null;

	}

	@Override
	public List<ProductModel> getProductsByCategory(int categoryId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			conn = getConnection();
			conn.setAutoCommit(false);

			String sql = "SELECT id, name, description, cost, rrp FROM product_categories "
					+ "INNER JOIN products ON product_id = products.id"
					+ " WHERE category_id = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryId);

			rs = pstmt.executeQuery();

			List<Integer> categories = new ArrayList<>();
			List<ProductModel> foundProducts = new ArrayList<>();

			while (rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				double price = rs.getInt("cost");
				double rrp = rs.getInt("rrp");
				categories = getCategoriesOfProduct(id);

				ProductModel currentProduct = new ProductModel(id, name, price, description, rrp, categories);

				foundProducts.add(currentProduct);
			}

			return foundProducts;
		}

		catch (SQLException e)
		{
			rollback(conn);
			e.printStackTrace();
		}
		finally
		{
			close(rs, pstmt, conn);
		}

		return null;
	}

	@Override
	public List<ProductModel> getAllProducts()
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try
		{
			conn = getConnection();

			String sql = "SELECT* FROM products";
			stmt = conn.createStatement();

			rs = stmt.executeQuery(sql);
			List<Integer> categories = new ArrayList<>();
			List<ProductModel> foundProducts = new ArrayList<>();

			while (rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				double price = rs.getInt("cost");
				double rrp = rs.getInt("rrp");
				categories = getCategoriesOfProduct(id);

				ProductModel currentProduct = new ProductModel(id, name, price, description, rrp, categories);

				foundProducts.add(currentProduct);
			}

			return foundProducts;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(rs, stmt, conn);
		}
		return null;
	}

	@Override
	public List<Integer> getCategoriesOfProduct(int productId)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			conn = getConnection();

			String sql = "SELECT category_id FROM product_category WHERE product_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);
			rs = pstmt.executeQuery(sql);

			List<Integer> categoryIds = new ArrayList<>();

			while (rs.next())
			{
				int currentCategoryId = rs.getInt("category_id");
				categoryIds.add(currentCategoryId);
			}
			return categoryIds;

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			close(rs, pstmt, conn);
		}
		return null;
	}

}
