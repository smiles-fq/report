package com.fang.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DataUtils {

	public static String[][] getData(String sql) throws SQLException {
		Connection conn;
		ResultSet rs;// 创建结果集
		Statement stmt;
		conn = DBConnection.dbConnection();
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		int rowNum = ResultSet.TYPE_SCROLL_SENSITIVE;
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		if (rowNum > 0 && count > 0) {
			String[][] data = new String[rowNum][count];
			int index = 0;
			while (rs.next()) {
				for (int i = 0; i < count; i++) {
					data[index][i] = rs.getString(i + 1);
				}
				index++;
			}
			return data;
		}
		stmt.close();
		rs.close();
		conn.close();
		return null;
	}

	public static String[] getDataColumn(String sql) throws SQLException {
		Connection conn;
		ResultSet rs;// 创建结果集
		Statement stmt;
		conn = DBConnection.dbConnection();
		stmt = conn.createStatement();
		rs = stmt.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		String[] data = new String[rsmd.getColumnCount()];
		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			// resultSet数据下标从1开始
			String columnName = rsmd.getColumnLabel(i + 1);
			data[i] = columnName;
		}
		stmt.close();
		rs.close();
		conn.close();
		return data;
	}
}
