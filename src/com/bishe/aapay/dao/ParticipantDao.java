package com.bishe.aapay.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bishe.aapay.dto.Category;
import com.bishe.aapay.dto.Participant;
import com.bishe.aapay.util.BaseDao;

public class ParticipantDao extends BaseDao {
	private static ParticipantDao participantDao = new ParticipantDao();
	private ParticipantDao() {
		
	}
	public static ParticipantDao getInstance() {
		return participantDao;
	}

	/**
	 * 根据用户Id查找全部信息
	 * @param userId
	 * @return
	 */
	public List<Participant> findAll(int userId) {
		List<Participant> participants = new ArrayList<Participant>();
		Connection connection = super.buildConnection();
		String sql = "select * from tb_participant where user_id = ?";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Participant participant = new Participant();
				participant.setId(resultSet.getInt("participant_id"));
				participant.setUserId(resultSet.getInt("user_id"));
				participant.setName(resultSet.getString("part_name"));
				participant.setTeamId(resultSet.getLong("team_id"));
				participant.setPhone(resultSet.getString("phone"));
				participant.setBalance(resultSet.getDouble("balance"));
				participant.setTotalAmount(resultSet.getDouble("total_amount"));
				participants.add(participant);
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
		return participants;
	}
	
	public boolean deleteAll(int userId) {
		String sql = "delete from tb_participant where user_id = ?";
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
	public boolean batchInsert(List<Participant> participants,int userId) {
		String sql = "INSERT INTO tb_participant(participant_id,user_id,part_name,team_id,phone,balance,total_amount)"
				+ "VALUES(?,?,?,?,?,?,?)";
		Connection connection = super.buildConnection();
		PreparedStatement preparedStatement = null;
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			for(Participant participant : participants) {
				preparedStatement.setLong(1, participant.getId());
				preparedStatement.setInt(2, userId);
				preparedStatement.setString(3, participant.getName());
				preparedStatement.setLong(4, participant.getTeamId());
				preparedStatement.setString(5, participant.getPhone());
				preparedStatement.setDouble(6, participant.getBalance());
				preparedStatement.setDouble(7, participant.getTotalAmount());
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
		System.out.println(new ParticipantDao().findAll(0).size());
	}
}
