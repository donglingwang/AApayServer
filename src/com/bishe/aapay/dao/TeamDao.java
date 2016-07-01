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
import com.bishe.aapay.dto.PersonalBudget;
import com.bishe.aapay.dto.Team;
import com.bishe.aapay.util.BaseDao;

public class TeamDao extends BaseDao {
	private static TeamDao teamDao = new TeamDao();
	private TeamDao() {
		
	}
	public static TeamDao getInstance() {
		return teamDao;
	}

	/**
	 * 根据用户Id查找全部信息
	 * @param userId
	 * @return
	 */
	public List<Team> findAll(int userId) {
		List<Team> teams = new ArrayList<Team>();
		Connection connection = super.buildConnection();
		String sql = "select * from tb_team where user_id = ?";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Team team = new Team();
				team.setTeamId(resultSet.getInt("team_id"));
				team.setUserId(resultSet.getInt("user_id"));
				team.setTeamName(resultSet.getString("team_name"));
				team.setTeamNum(resultSet.getInt("team_num"));
				team.setFoundDate(resultSet.getString("found_date"));
				team.setConsumeTotalAmount(resultSet.getDouble("consume_amount"));
				team.setHasPart(resultSet.getString("has_part"));
				teams.add(team);
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
		return teams;
	}
	
	public boolean deleteAll(int userId) {
		String sql = "delete from tb_team where user_id = ?";
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
	public boolean batchInsert(List<Team> teams,int userId) {
		String sql = "INSERT INTO tb_team(team_id,user_id,team_name,team_num,found_date,consume_amount,has_part)"
				+ "VALUES(?,?,?,?,?,?,?)";
		Connection connection = super.buildConnection();
		PreparedStatement preparedStatement = null;
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			for(Team team : teams) {
				preparedStatement.setInt(1, team.getTeamId());
				preparedStatement.setInt(2, userId);
				preparedStatement.setString(3, team.getTeamName());
				preparedStatement.setInt(4, team.getTeamNum());
				preparedStatement.setString(5, team.getFoundDate());
				preparedStatement.setDouble(6, team.getConsumeTotalAmount());
				preparedStatement.setString(7, team.getHasPart());
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
		System.out.println(new TeamDao().findAll(0).size());
	}
}
