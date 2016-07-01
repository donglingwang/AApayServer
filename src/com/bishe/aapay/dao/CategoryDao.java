package com.bishe.aapay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bishe.aapay.dto.Category;
import com.bishe.aapay.util.BaseDao;

public class CategoryDao extends BaseDao {
	private static CategoryDao categoryDao = new CategoryDao();
	private CategoryDao() {
		
	}
	public static CategoryDao getInstance() {
		return categoryDao;
	}

	public boolean deleteAll(int userId) {
		String sql = "delete from tb_category where user_id = ?";
		String [] args = {String.valueOf(userId)};
		int i = super.update(sql, args);
		if(i > 0) {
			return true;
		}
		return false;
	}
	/**
	 * 根据用户Id查找全部信息
	 * @param userId
	 * @return
	 */
	public List<Category> findAll(int userId) {
		List<Category> categories = new ArrayList<Category>();
		Connection connection = super.buildConnection();
		String sql = "select * from tb_category where user_id = ?";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Category category = new Category();
				category.setId(resultSet.getInt("category_id"));
				category.setUserId(resultSet.getInt("user_id"));
				category.setCategoryName(resultSet.getString("category_name"));
				category.setPartentId(resultSet.getInt("parent_id"));
				category.setType(resultSet.getInt("type"));
				categories.add(category);
			}
			resultSet.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			try {
				super.closePreparedStatement(preparedStatement);
				super.closeConnection(connection);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return categories;
	}
	

	/**
	 * 批量插入
	 * @return
	 */
	public boolean batchInsert(List<Category> categories,int userId) {
		String sql = "INSERT INTO tb_category(category_id,user_id,category_name,parent_id,type)VALUES(?,?,?,?,?)";
		Connection connection = super.buildConnection();
		PreparedStatement preparedStatement = null;
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			for(Category category : categories) {
				preparedStatement.setInt(1, category.getId());
				preparedStatement.setInt(2, userId);
				preparedStatement.setString(3, category.getCategoryName());
				preparedStatement.setInt(4, category.getPartentId());
				preparedStatement.setInt(5, category.getType());
				preparedStatement.addBatch();
			}
			int results [] = preparedStatement.executeBatch();
			connection.commit();
			for(int i : results) {
				System.out.print(" "+i);
			}
			
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				super.closePreparedStatement(preparedStatement);
				super.closeConnection(connection);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	public static void main(String[] args) {
		System.out.println(new CategoryDao().findAll(0).size());
	}
}
