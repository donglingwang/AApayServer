package com.bishe.aapay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bishe.aapay.dto.User;
import com.bishe.aapay.util.BaseDao;

public class UserDao extends BaseDao {
	private static UserDao userDao;
	private UserDao() {
		
	}
	public static UserDao getInstance() {
		if(userDao == null)
			userDao = new UserDao();
		return userDao;
	}
	
	public User find(String email) {
		String sql = "SELECT * FROM tb_user t where t.user_mail = ?";
		Connection connection = super.buildConnection();
		PreparedStatement stmt;
		ResultSet results;
		User user = null;
		try {
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, email);
			results = stmt.executeQuery();
			if(results.next()){
				user = new User();
				user.setUserId(results.getInt("_id"));
				user.setUserName(results.getString("user_name"));
				user.setUserMail(results.getString("user_mail"));
				user.setPassword(results.getString("passowrd"));
				user.setRegisterDate(results.getString("register_date"));
			}
			
			super.closeResultSet(results);
			super.closeStatement(stmt);
			super.closeConnection(connection);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return user;
	}
	
	public boolean insert(User user) {
		String sql = "INSERT INTO tb_user (user_name,user_mail,passowrd,register_date)VALUES(?,?,?,?)";
		String [] args = {user.getUserName(),user.getUserMail(),user.getPassword(),user.getRegisterDate()};
		int i = super.update(sql, args);
		return i > 0 ? true : false;
	}
	
}
