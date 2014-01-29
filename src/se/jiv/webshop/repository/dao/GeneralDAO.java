package se.jiv.webshop.repository.dao;

import java.sql.*;

public abstract class GeneralDAO {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/webshop";
	static final String DB_USER = "javamaster";
	static final String DB_PASSWORD = "java";

	protected Connection getConnection() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	}
	
	protected void rollback(Connection conn){
		try
		{
			conn.rollback();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException sqlEx) {
			} // ignore

			rs = null;
		}
	}

	protected void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException sqlEx) {
			} // ignore

			stmt = null;
		}
	}

	protected void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException sqlEx) {
			}
		}
	}

	protected void close(ResultSet rs, Statement stmt, Connection conn) {
		close(rs);
		close(stmt);
		close(conn);

	}

	protected void close(Statement stmt, Connection conn) {
		close(null, stmt, conn);
	}

	protected void close(ResultSet rs, Statement stmt) {
		close(rs, stmt, null);
	}

	public void setString(PreparedStatement pstmt, int pos, String value)
			throws SQLException {
		if (value == null) {
			pstmt.setNull(pos, Types.VARCHAR);
		} else {
			pstmt.setString(pos, value);
		}
	}

	public void setInteger(PreparedStatement pstmt, int pos, Integer value)
			throws SQLException {
		if (value == null) {
			pstmt.setNull(pos, Types.INTEGER);
		} else {
			pstmt.setInt(pos, value);
		}
	}

	public int getInt(ResultSet rs, String column) throws SQLException {
		Integer integer = rs.getInt(column);
		return (integer == null) ? -1 : integer;
	}
}
