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
	public UserModel addUser(UserModel user) throws WebshopAppException {
		if (user != null) {
			Connection conn = null;
			PreparedStatement pstmt = null;

			try {
				conn = getConnection();

				String sql = "INSERT INTO users (email, password, firstname, lastname, dob, telephone, address1, address2, town, postcode)"
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user.getEmail());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getFirstname());
				pstmt.setString(4, user.getLastname());
				pstmt.setString(5, user.getDob());
				pstmt.setString(6, user.getTelephone());
				pstmt.setString(7, user.getAddress1());
				pstmt.setString(8, user.getAddress2());
				pstmt.setString(9, user.getTown());
				pstmt.setString(10, user.getPostcode());
				pstmt.executeUpdate();

				return new UserModel(user);

			} catch (SQLException e) {
				throw new WebshopAppException(e.getMessage(), this.getClass()
						.getSimpleName(), "ADD_USER");
			} finally {
				close(pstmt, conn);
			}
		} else {
			throw new WebshopAppException("User can't be null", this.getClass()
					.getSimpleName(), "ADD_USER");
		}
	}

	@Override
	public void updateUser(UserModel user) throws WebshopAppException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		if (user != null) {
			try {
				conn = getConnection();

				String sql = "UPDATE users SET password = ?, firstname = ?, lastname = ?, dob = ?, telephone = ?, address1 = ?, "
						+ "address2 = ?, town = ?, postcode = ? WHERE email = ?";

				pstmt = conn.prepareStatement(sql);
				setString(pstmt, 1, user.getPassword());
				setString(pstmt, 2, user.getFirstname());
				setString(pstmt, 3, user.getLastname());
				setString(pstmt, 4, user.getDob());
				setString(pstmt, 5, user.getTelephone());
				setString(pstmt, 6, user.getAddress1());
				setString(pstmt, 7, user.getAddress2());
				setString(pstmt, 8, user.getTown());
				setString(pstmt, 9, user.getPostcode());
				setString(pstmt, 10, user.getEmail());

				pstmt.executeUpdate();

			} catch (SQLException e) {
				throw new WebshopAppException(e.getMessage(), this.getClass()
						.getSimpleName(), "UPDATE_USER");
			} finally {
				close(pstmt, conn);
			}
		} else {
			throw new WebshopAppException("User can't be null", this.getClass()
					.getSimpleName(), "UPDATE_USER");
		}
	}

	@Override
	public void deleteUser(UserModel user) throws WebshopAppException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		if (user != null) {
			try {
				conn = getConnection();

				String sql = "DELETE FROM users WHERE email = ?";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, user.getEmail());

				pstmt.executeUpdate();

			} catch (SQLException e) {
				throw new WebshopAppException(e.getMessage(), this.getClass()
						.getSimpleName(), "DELETE_USER");
			} finally {
				close(pstmt, conn);
			}
		} else {
			throw new WebshopAppException("User can't be null", this.getClass()
					.getSimpleName(), "DELETE_USER");
		}
	}

	@Override
	public UserModel getUser(String email) throws WebshopAppException {
		if (email != null) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			try {
				conn = getConnection();

				String sql = "SELECT * FROM users WHERE email = ?";

				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, email);

				rs = pstmt.executeQuery();

				if (rs.next()) {
					return parseModel(rs);
				}

			} catch (SQLException e) {
				throw new WebshopAppException(e.getMessage(), this.getClass()
						.getSimpleName(), "GET_USER");
			} finally {
				close(rs, pstmt, conn);
			}
		} else {
			throw new WebshopAppException("Email can't be null", this
					.getClass().getSimpleName(), "GET_USER");
		}
		return null;
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

		return new UserModel(db_email, db_password, db_firstname, db_lastname,
				db_dob, db_telephone, db_address1, db_address2, db_town,
				db_postcode);
	}

	@Override
	public List<UserModel> getAllUsers() throws WebshopAppException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<UserModel> userList = new ArrayList<UserModel>();

		try {
			conn = getConnection();

			String sql = "SELECT * FROM users";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				userList.add(parseModel(rs));

			}
			return userList;
		} catch (SQLException e) {
			throw new WebshopAppException(e.getMessage(), this.getClass()
					.getSimpleName(), "GET_ALL_USERS");
		} finally {
			close(rs, pstmt, conn);
		}

	}

	@Override
	public boolean validateLogin(UserModel user) throws WebshopAppException {
		if (user != null) {
			String email = user.getEmail();
			String password = user.getPassword();
			if ((email != null) && (password != null)) {
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;

				try {
					conn = getConnection();

					String sql = "SELECT * FROM users WHERE email = ? and password = ?";

					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, email);
					pstmt.setString(2, password);

					rs = pstmt.executeQuery();

					return rs.next();

				} catch (SQLException e) {
					throw new WebshopAppException(e.getMessage(), this
							.getClass().getSimpleName(), "GET_USER");
				} finally {
					close(rs, pstmt, conn);
				}
			} else {
				return false;
			}
		} else {
			throw new WebshopAppException("User can't be null", this.getClass()
					.getSimpleName(), "VALIDATE_LOGIN");
		}
	}

}
