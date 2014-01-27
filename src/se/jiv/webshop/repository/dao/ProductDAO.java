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

			String sql = "INSERT INTO products(name,description,cost,rrp) VALUES(?,?,?,?)";

			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, product.getName());
			pstmt.setString(2, product.getDescription());
			pstmt.setDouble(3, product.getCost());
			pstmt.setDouble(4, product.getRrp());
			pstmt.executeUpdate();

			int generatedId = 0;
			rs = pstmt.getGeneratedKeys();
			System.out.println(rs.next());
			if (rs.next())
			{
				generatedId = rs.getInt(1);
			}

			return new ProductModel(generatedId, product);
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
	public ProductModel updateProduct(String productName, int updateProperty,
			String newProperty)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			conn = getConnection();
			String sql = "";

			if (updateProperty == 1)
			{
				sql = "UPDATE products SET name = ? WHERE name = ?";
			}
			else if (updateProperty == 2)
			{
				sql = "UPDATE products SET description = ? WHERE name = ?";
			}
			else if (updateProperty == 3)
			{
				sql = "UPDATE products SET cost = ? WHERE name = ?";
			}
			else if (updateProperty == 4)
			{
				sql = "UPDATE products SET rrp = ? WHERE name = ?";
			}

			if (updateProperty > 2)
			{
				double currentNewProperty = Double.parseDouble(newProperty);
				pstmt = conn.prepareStatement(sql);
				pstmt.setDouble(1, currentNewProperty);
				pstmt.setString(2, productName);
				pstmt.executeUpdate();
			}
			else
			{
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newProperty);
				pstmt.setString(2, productName);
				pstmt.executeUpdate();
			}

			pstmt.close();
			sql = "SELECT id, name, description,cost, rrp FROM products WHERE name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productName);

			rs = pstmt.executeQuery();

			String name;
			String description;
			double cost;
			double rrp;
			int id;

			if (rs.next())
			{
				id = rs.getInt("id");
				name = rs.getString("name");
				description = rs.getString("description");
				cost = rs.getDouble("cost");
				rrp = rs.getDouble("rrp");
				return new ProductModel(id, name, cost, description, rrp);

			}

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
	public boolean updateProduct(ProductModel product, int productId){
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			conn = getConnection();
			
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
	public boolean deleteProduct(String name)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try
		{
			conn = getConnection();

			String sql = "DELETE FROM products WHERE name = ?";

			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, name);
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
	public boolean deleteProduct(int productId){
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

			String sql = "SELECT* FROM products WHERE name = ?";

			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, productName);
			rs = pstmt.executeQuery();

			String name;
			String description;
			double price;
			double rrp;
			List<ProductModel> foundProducts = new ArrayList<>();

			while (rs.next())
			{
				name = rs.getString("name");
				description = rs.getString("description");
				price = rs.getInt("cost");
				rrp = rs.getInt("rrp");
				ProductModel currentProduct = new ProductModel(name, price, description, rrp);
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
	public List<ProductModel> getProductByCost(double cost)
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
			System.out.println(pstmt);
			rs = pstmt.executeQuery();

			String name;
			String description;
			double price;
			double rrp;
			List<ProductModel> foundProducts = new ArrayList<>();

			while (rs.next())
			{
				name = rs.getString("name");
				description = rs.getString("description");
				price = rs.getInt("cost");
				rrp = rs.getInt("rrp");
				ProductModel currentProduct = new ProductModel(name, price, description, rrp);
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
	public ProductModel getProductById(int productId){
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

			while (rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				double price = rs.getDouble("cost");
				double rrp = rs.getDouble("rrp");
				ProductModel currentProduct = new ProductModel(id,name, price, description, rrp);
				return currentProduct;
			}

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

			String name;
			String description;
			double price;
			double rrp;
			List<ProductModel> foundProducts = new ArrayList<>();

			while (rs.next())
			{
				name = rs.getString("name");
				description = rs.getString("description");
				price = rs.getInt("cost");
				rrp = rs.getInt("rrp");
				ProductModel currentProduct = new ProductModel(name, price, description, rrp);
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

}
