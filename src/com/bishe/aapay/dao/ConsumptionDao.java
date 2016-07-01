package com.bishe.aapay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bishe.aapay.dto.Category;
import com.bishe.aapay.dto.Consumption;
import com.bishe.aapay.util.BaseDao;

public class ConsumptionDao extends BaseDao {

	private static ConsumptionDao consumptionDao = new ConsumptionDao();
	private ConsumptionDao() {
		
	}
	public static ConsumptionDao getInstance() {
		return consumptionDao;
	}
	
	/**
	 * 根据用户Id查找全部信息
	 * @param userId
	 * @return
	 */
	public List<Consumption> findAll(int userId) {
		List<Consumption> consumptions = new ArrayList<Consumption>();
		Connection connection = super.buildConnection();
		String sql = "select * from tb_consumtion where user_id = ?";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Consumption consumption = new Consumption();
				consumption.setId(resultSet.getLong("consumption_id"));
				consumption.setUserId(resultSet.getInt("user_id"));
				consumption.setName(resultSet.getString("name"));
				consumption.setTeamId(resultSet.getLong("team_id"));
				consumption.setConsumptionMode(resultSet.getString("consumption_mode"));
				consumption.setMoney(resultSet.getDouble("money"));
				consumption.setType(resultSet.getString("type"));
				consumption.setTime(resultSet.getString("consumption_time"));
				consumption.setParticipantNum(resultSet.getInt("part_num"));
				consumption.setRemark(resultSet.getString("remark"));
				consumption.setPayOFF(resultSet.getString("pay_off"));
				consumption.setOperateTime(resultSet.getString("operate_time"));
				consumptions.add(consumption);
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
		return consumptions;
	}
	
	public boolean deleteAll(int userId) {
		String sql = "delete from tb_consumtion where user_id = ?";
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
	public boolean insert(List<Consumption> consumptions,int userId) {
		String sql = "INSERT INTO tb_consumtion(consumption_id,user_id,name,team_id,consumption_mode,money,"
				+ "type,consumption_time,part_num,remark,pay_off,operate_time)"
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
		Connection connection = super.buildConnection();
		PreparedStatement preparedStatement = null;
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			for(Consumption consumption : consumptions) {
				preparedStatement.setLong(1, consumption.getId());
				preparedStatement.setInt(2, userId);
				preparedStatement.setString(3, consumption.getName());
				preparedStatement.setLong(4, consumption.getTeamId());
				preparedStatement.setString(5, consumption.getConsumptionMode());
				preparedStatement.setDouble(6, consumption.getMoney());
				preparedStatement.setString(7, consumption.getType());
				preparedStatement.setString(8, consumption.getTime());
				preparedStatement.setInt(9, consumption.getParticipantNum());
				preparedStatement.setString(10, consumption.getRemark());
				preparedStatement.setString(11, consumption.getPayOFF());
				preparedStatement.setString(12, consumption.getOperateTime());
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
		System.out.println(new ConsumptionDao().findAll(0).size());
	}
}
