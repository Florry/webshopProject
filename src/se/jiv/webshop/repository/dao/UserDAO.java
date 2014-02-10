package se.jiv.webshop.repository.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import se.jiv.webshop.exception.WebshopAppException;
import se.jiv.webshop.model.UserModel;
import se.jiv.webshop.repository.UserRepository;

public final class UserDAO extends GeneralDAO implements UserRepository {

	@Override
	public void addUser(UserModel user) throws WebshopAppException {
		if (isValidUser(user, "ADD_USER")) {

			try (Connection conn = getConnection()) {

				String sql = "INSERT INTO users (password, firstname, lastname, dob, telephone, address1, address2, town, postcode, email)"
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					prepareStatementFromModel(pstmt, user);

					pstmt.executeUpdate();

				}

			} catch (SQLException e) {
				throw new WebshopAppException(e.getMessage(), this.getClass()
						.getSimpleName(), "ADD_USER");
			}
		}

	}

	@Override
	public void updateUser(UserModel user) throws WebshopAppException {

		if (isValidUser(user, "UPDATE_USER")) {

			try (Connection conn = getConnection()) {

				String sql = "UPDATE users SET password = ?, firstname = ?, lastname = ?, dob = ?, telephone = ?, address1 = ?, "
						+ "address2 = ?, town = ?, postcode = ? WHERE email = ?";

				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					prepareStatementFromModel(pstmt, user);

					pstmt.executeUpdate();
				}
			} catch (SQLException e) {
				throw new WebshopAppException(e.getMessage(), this.getClass()
						.getSimpleName(), "UPDATE_USER");
			}
		}

	}

	@Override
	public void deleteUser(UserModel user) throws WebshopAppException {

		if (isValidUser(user, "DELETE_USER")) {

			try (Connection conn = getConnection()) {

				String sql = "DELETE FROM users WHERE email = ?";

				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					setString(pstmt, 1, user.getEmail());

					pstmt.executeUpdate();
				}

			} catch (SQLException e) {
				throw new WebshopAppException(e.getMessage(), this.getClass()
						.getSimpleName(), "DELETE_USER");
			}
		}
	}

	@Override
	public UserModel getUser(String email) throws WebshopAppException {
		if (isValidEmail(email, "GET_USER")) {

			try (Connection conn = getConnection()) {

				String sql = "SELECT * FROM users WHERE email = ?";

				try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
					setString(pstmt, 1, email);

					try (ResultSet rs = pstmt.executeQuery()) {

						if (rs.next()) {
							return parseModel(rs);
						}
					}
				}

			} catch (SQLException e) {
				throw new WebshopAppException(e.getMessage(), this.getClass()
						.getSimpleName(), "GET_USER");
			}
		}
		return null;
	}

	@Override
	public List<UserModel> getAllUsers() throws WebshopAppException {

		try (Connection conn = getConnection()) {

			String sql = "SELECT * FROM users";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

				try (ResultSet rs = pstmt.executeQuery()) {

					List<UserModel> userList = new ArrayList<UserModel>();
					while (rs.next()) {
						userList.add(parseModel(rs));

					}
					return userList;
				}
			}
		} catch (SQLException e) {
			throw new WebshopAppException(e.getMessage(), this.getClass()
					.getSimpleName(), "GET_ALL_USERS");
		}

	}

	@Override
	public boolean validateLogin(String email, String password)
			throws WebshopAppException {
		try (Connection conn = getConnection()) {

			String sql = "SELECT * FROM users WHERE email = ? and password = ?";

			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				setString(pstmt, 1, email);
				setString(pstmt, 2, password);

				try (ResultSet rs = pstmt.executeQuery()) {
					return rs.next();
				}
			}

		} catch (SQLException e) {
			throw new WebshopAppException(e.getMessage(), this.getClass()
					.getSimpleName(), "VALIDATE_LOGIN");
		}
	}

	private UserModel parseModel(ResultSet rs) throws SQLException {
		String db_email = rs.getString("email");
		String db_password = rs.getString("password");
		String db_firstname = rs.getString("firstname");
		String db_lastname = rs.getString("lastname");
		String db_dob = "" + rs.getDate("dob");
		String db_telephone = rs.getString("telephone");
		String db_address1 = rs.getString("address1");
		String db_address2 = rs.getString("address2");
		String db_town = rs.getString("town");
		String db_postcode = rs.getString("postcode");

		return new UserModel.Builder(db_email, db_password, db_firstname,
				db_lastname, db_address1, db_town, db_postcode)
				.address2(db_address2).dob(db_dob).telephone(db_telephone)
				.build();
	}

	private void prepareStatementFromModel(PreparedStatement pstmt,
			UserModel user) throws SQLException {
		pstmt.setString(1, user.getPassword());
		pstmt.setString(2, user.getFirstname());
		pstmt.setString(3, user.getLastname());
		pstmt.setString(4, user.getDob());
		pstmt.setString(5, user.getTelephone());
		pstmt.setString(6, user.getAddress1());
		pstmt.setString(7, user.getAddress2());
		pstmt.setString(8, user.getTown());
		pstmt.setString(9, user.getPostcode());
		pstmt.setString(10, user.getEmail());

	}

	private boolean isValidUser(UserModel user, String functionName)
			throws WebshopAppException {
		if (user == null) {
			throw new WebshopAppException("user can not be null", this
					.getClass().getSimpleName(), functionName);
		}

		return true;
	}

	private boolean isValidEmail(String email, String functionName)
			throws WebshopAppException {
		if (email == null) {
			throw new WebshopAppException("Email can't be null", this
					.getClass().getSimpleName(), functionName);
		}
		return true;
	}

}
