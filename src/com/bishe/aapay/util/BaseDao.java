package com.bishe.aapay.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 执行SQL，并且返回执行结果
 * @author 
 */
public class BaseDao {
	//定义JDBC驱动的名称
	public static final String drivername="com.mysql.jdbc.Driver";
	public static final String URL="jdbc:mysql://localhost:3306/aapay";
	public static final String UID="root";
	public static final String PWD="root";
	/**
	 * 加载驱动
	 */
	static{
		try {
			Class.forName(drivername);
			System.out.println("BaseDao. 加载驱动成功");
		} catch (ClassNotFoundException e) {
			System.out.println("BaseDao. 加载驱动失败：" + e.getMessage());
		}
	}
	
	public int update( String sql ,  String[] args  )
	{
		int i = -1;
		Connection connection=null;
		try {
			connection = DriverManager.getConnection(URL,UID,PWD);
			//获得PreparedStatement ，此对此可以执行无占位符的SQL语句，也
			//可以中有占位符的SQL，如果有占位符，那么必须在执行之前为
			//占位符赋值；
			PreparedStatement preparedStatement = connection.prepareStatement(sql);			
			if (args==null || args.length==0) {				
			}
			else {
				//为PreparedStatement的每个占位符赋值
				for (int j = 0; j < args.length; j++) {
					//注意，所以JDBC中的索引从1 开始；
					preparedStatement.setString(j+1,  args[j] );					
				}
			}
			i = preparedStatement.executeUpdate();
			System.out.println("BaseDao.update() 执行成功:" +  sql);
		} 
		catch (Exception e) {
			System.out.println("BaseDao.update() 执行失败:" +  sql);
			System.out.println("BaseDao.update() 执行失败:" +  e.getMessage());
		}
		finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}
	
	public Connection buildConnection()
	{
		Connection connection =null;
		try {
			 connection = DriverManager.getConnection(URL, UID, PWD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void closeStatement(Statement statement) throws SQLException {
		statement.close();
	}
	public static void closePreparedStatement(PreparedStatement pStatement)
			throws SQLException {
		pStatement.close();
	}
	public static void closeResultSet(ResultSet resultSet) throws SQLException {
		resultSet.close();
	}
	public static void closeConnection(Connection connection) throws SQLException {
		connection.close();
	}
	public static void main(String[] args) {
		System.out.println(new BaseDao().buildConnection());
	}

	

}
