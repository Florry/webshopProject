package se.jiv.webshop.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.ShoppingCartRepository;

public final class ShoppingCartDAO extends GeneralDAO implements
		ShoppingCartRepository {

	@Override
	public void addProductToCart(UserModel user, int productId, int quantity)
			throws WebshopAppException {

		if (isValidUser(user, "ADD_PRODUCT_TO_SHOPPING_CART")
				&& isPositiveQuantity(quantity, "ADD_PRODUCT_TO_SHOPPING_CART")) {

			try (Connection conn = getConnection()) {

				int db_quantity = getProductQuantity(conn, user, productId);

				deleteProductFromShoppingCart(conn, user, productId);

				int newQuantity = db_quantity + quantity;

				insertProductQuantity(conn, user, productId, newQuantity);

			} catch (SQLException e) {
				throw new WebshopAppException(e, this.getClass()
						.getSimpleName(), "ADD_PRODUCT_TO_SHOPPING_CART");
			}
		}

	}

	@Override
	public void removeProductFromCart(UserModel user, int productId)
			throws WebshopAppException {

		if (isValidUser(user, "REMOVE_PRODUCT_FROM_SHOPPING_CART")) {

			try (Connection conn = getConnection()) {

				deleteProductFromShoppingCart(conn, user, productId);

			} catch (SQLException e) {
				throw new WebshopAppException(e, this.getClass()
						.getSimpleName(), "REMOVE_PRODUCT_FROM_SHOPPING_CART");
			}
		}
	}

	@Override
	public void updateCart(UserModel user, int productId, int quantity)
			throws WebshopAppException {

		if (isValidUser(user, "UPDATE_CART")
				&& isPositiveQuantity(quantity, "UPDATE_CART")) {

			try (Connection conn = getConnection()) {

				deleteProductFromShoppingCart(conn, user, productId);

				insertProductQuantity(conn, user, productId, quantity);

			} catch (SQLException e) {
				throw new WebshopAppException(e.getMessage(), this.getClass()
						.getSimpleName(), "UPDATE_CART");
			}
		}

	}

	@Override
	public void resetShoppingCart(UserModel user) throws WebshopAppException {

		if (isValidUser(user, "RESET_SHOPPING_CART")) {

			try (Connection conn = getConnection()) {

				String sql = "DELETE FROM shopping_cart WHERE user_email = ?";
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

					setString(pstmt, 1, user.getEmail());

					pstmt.executeUpdate();
				}
			} catch (SQLException e) {
				throw new WebshopAppException(e.getMessage(), this.getClass()
						.getSimpleName(), "RESET_SHOPPING_CART");
			}
		}

	}

	@Override
	public Map<Integer, Integer> getShoppingCart(UserModel user)
			throws WebshopAppException {

		if (isValidUser(user, "GET_SHOPPING_SHOPPING_CART_CONTENTS")) {

			try (Connection conn = getConnection()) {

				String sql = "SELECT product_id, quantity FROM shopping_cart WHERE user_email = ?";
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					setString(pstmt, 1, user.getEmail());

					try (ResultSet rs = pstmt.executeQuery()) {

						Map<Integer, Integer> contents = new LinkedHashMap<>();
						while (rs.next()) {
							contents.put(rs.getInt("product_id"),
									rs.getInt("quantity"));
						}

						return contents;
					}
				}
			} catch (SQLException e) {
				throw new WebshopAppException(e.getMessage(), this.getClass()
						.getSimpleName(), "GET_SHOPPING_SHOPPING_CART_CONTENTS");
			}
		}

		return null;

	}

	private int getProductQuantity(Connection conn, UserModel user,
			int productId) throws SQLException {

		String sql = "SELECT quantity FROM shopping_cart WHERE user_email = ? and product_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			setString(pstmt, 1, user.getEmail());
			setInteger(pstmt, 2, productId);

			try (ResultSet rs = pstmt.executeQuery()) {

				if (rs.next()) {
					return rs.getInt(1);
				}
				return 0;
			}
		}
	}

	private void deleteProductFromShoppingCart(Connection conn, UserModel user,
			int productId) throws SQLException {
		String sql = "DELETE FROM shopping_cart WHERE user_email = ? and product_id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			setString(pstmt, 1, user.getEmail());
			setInteger(pstmt, 2, productId);

			pstmt.executeUpdate();
		}

	}

	private void insertProductQuantity(Connection conn, UserModel user,
			int productId, int newQuantity) throws SQLException {
		String sql = "INSERT INTO shopping_cart (user_email, product_id, quantity) VALUES (?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			setString(pstmt, 1, user.getEmail());
			setInteger(pstmt, 2, productId);
			setInteger(pstmt, 3, newQuantity);

			pstmt.executeUpdate();
		}

	}

	private boolean isPositiveQuantity(int quantity, String functionName)
			throws WebshopAppException {
		if (quantity <= 0) {
			throw new WebshopAppException("quantity can not be negative", this
					.getClass().getSimpleName(), functionName);
		}

		return true;
	}

	private boolean isValidUser(UserModel user, String functionName)
			throws WebshopAppException {
		if (user == null) {
			throw new WebshopAppException("user can not be null", this
					.getClass().getSimpleName(), functionName);
		}

		return true;

	}

}
