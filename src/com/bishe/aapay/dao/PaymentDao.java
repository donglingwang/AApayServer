package com.bishe.aapay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bishe.aapay.dto.Category;
import com.bishe.aapay.dto.Participant;
import com.bishe.aapay.dto.Payment;
import com.bishe.aapay.util.BaseDao;

public class PaymentDao extends BaseDao {
	private static PaymentDao paymentDao = new PaymentDao();
	private PaymentDao() {
		
	}
	public static PaymentDao getInstance() {
		return paymentDao;
	}

	/**
	 * 根据用户Id查找全部信息
	 * @param userId
	 * @return
	 */
	public List<Payment> findAll(int userId) {
		List<Payment> payments = new ArrayList<Payment>();
		Connection connection = super.buildConnection();
		String sql = "select * from tb_payment where user_id = ?";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Payment payment = new Payment();
				payment.setId(resultSet.getLong("payment_id"));
				payment.setUserId(resultSet.getInt("user_id"));
				payment.setConsumptionId(resultSet.getLong("consumption_id"));
				payment.setPartId(resultSet.getLong("part_id"));
				payment.setAdvanceMoney(resultSet.getDouble("advance_money"));
				payments.add(payment);
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
		return payments;
	}
	
	
	public boolean deleteAll(int userId) {
		String sql = "delete from tb_payment where user_id = ?";
		String [] args = {String.valueOf(userId)};
		int i = super.update(sql, args);
		if(i > 0) {
			return true;
		}
		return false;
	}
	/**
	 * 批量插入
	 * @return
	 */
	public boolean batchInsert(List<Payment> payments,int userId) {
		String sql = "INSERT INTO tb_payment(payment_id,user_id,consumption_id,part_id,advance_money)VALUES(?,?,?,?,?)";
		Connection connection = super.buildConnection();
		PreparedStatement preparedStatement = null;
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			for(Payment payment : payments) {
				preparedStatement.setLong(1, payment.getId());
				preparedStatement.setInt(2, userId);
				preparedStatement.setLong(3, payment.getConsumptionId());
				preparedStatement.setLong(4, payment.getPartId());
				preparedStatement.setDouble(5, payment.getAdvanceMoney());
				preparedStatement.addBatch();
			}
			preparedStatement.executeBatch();
			connection.commit();
			return true;
		} catch (SQLException e) {
			try {
				connection.rollback();
				return false;
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
		System.out.println(new PaymentDao().findAll(0).size());
	}
}
