package se.jiv.webshop.repository.dao;

import java.sql.*;
import java.util.List;

import se.jiv.webshop.model.CategoryModel;
import se.jiv.webshop.repository.CategoryRepository;

public final class CategoryDAO extends GeneralDAO implements CategoryRepository {

	@Override
	public CategoryModel addCategory(CategoryModel category) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try{
			conn = getConnection();
			
			String sql = "INSERT INTO categories values(name, staff_responsible)"
					+ "VALUES (?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, category.getName());
			pstmt.setInt(2, category.getStaff_responsible());
			
			pstmt.executeUpdate();
			;
			Integer generatedId = CategoryModel.DEFAULT_ID;
			rs = pstmt.getGeneratedKeys();
			if(rs.next()){
				generatedId = rs.getInt(1);
			}
			
			
		}catch(SQLException e){
			
		}finally{
			close(pstmt, conn);
		}
		
		return null;
	}

	@Override
	public CategoryModel getCategory(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategoryModel> getAllCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteCategory(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
