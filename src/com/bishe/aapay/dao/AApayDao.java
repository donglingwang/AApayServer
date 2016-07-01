package com.bishe.aapay.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.bishe.aapay.util.BaseDao;

import java.sql.Statement;

public class AApayDao extends BaseDao {

	private static AApayDao aaPayDao = new AApayDao();
	private AApayDao() {
		
	}
	public static AApayDao getInstance() {
		return aaPayDao;
	}
	public void batchDelete(int userId) {
		Connection connection = super.buildConnection();
		Statement statement = null;
		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.addBatch("delete from tb_budget where user_id="+userId);
			statement.addBatch("delete from tb_category where user_id="+userId);
			statement.addBatch("delete from tb_consumtion where user_id="+userId);
			statement.addBatch("delete from tb_participant where user_id="+userId);
			statement.addBatch("delete from tb_payment where user_id="+userId);
			statement.addBatch("delete from tb_team where user_id="+userId);
			int result[] = statement.executeBatch();
			connection.commit();
			for(int i : result) 
				System.out.println(i+" ");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
