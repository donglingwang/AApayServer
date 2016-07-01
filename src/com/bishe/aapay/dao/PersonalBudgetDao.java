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
import com.bishe.aapay.util.BaseDao;

public class PersonalBudgetDao extends BaseDao {
	private static PersonalBudgetDao budgetDao = new PersonalBudgetDao();
	private PersonalBudgetDao() {
		
	}
	public static PersonalBudgetDao getInstance() {
		return budgetDao;
	}

	public boolean deleteAll(int userId) {
		String sql = "delete from tb_budget where user_id = ?";
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
	public List<PersonalBudget> findAll(int userId) {
		List<PersonalBudget> budgets = new ArrayList<PersonalBudget>();
		Connection connection = super.buildConnection();
		String sql = "select * from tb_budget where user_id = ?";
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, userId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				PersonalBudget budget = new PersonalBudget();
				budget.setId(resultSet.getLong("budget_id"));
				budget.setBudgetType(resultSet.getInt("budget_type"));
				budget.setUserId(resultSet.getInt("user_id"));
				budget.setBudgetMoney(resultSet.getDouble("budget_money"));
				budget.setBudgetCategory(resultSet.getString("budget_category"));
				budget.setBudgetTime(resultSet.getString("budget_time"));
				budget.setOperateTime(resultSet.getString("operate_time"));
				budget.setRemark(resultSet.getString("remark"));
				budgets.add(budget);
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
		return budgets;
	}
	
	/**
	 * 批量插入
	 * @return
	 */
	public boolean batchInsert(List<PersonalBudget> budgets,int userId) {
		String sql = "INSERT INTO tb_budget(budget_type,user_id,budget_id,budget_money,"
				+ "budget_category,budget_time,operate_time,remark)VALUES(?,?,?,?,?,?,?,?)";
		Connection connection = super.buildConnection();
		PreparedStatement preparedStatement = null;
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(sql);
			for(PersonalBudget budget : budgets) {
				preparedStatement.setInt(1, budget.getBudgetType());
				preparedStatement.setInt(2, userId);
				preparedStatement.setLong(3, budget.getId());
				preparedStatement.setDouble(4, budget.getBudgetMoney());
				preparedStatement.setString(5, budget.getBudgetCategory());
				preparedStatement.setString(6, budget.getBudgetTime());
				preparedStatement.setString(7, budget.getOperateTime());
				preparedStatement.setString(8, budget.getRemark());
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
		System.out.println(new PersonalBudgetDao().findAll(0).size());
	}
}
