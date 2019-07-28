package com.fang.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
//	public static final String driver = "com.mysql.cj.jdbc.Driver";// 驱动
//	public static final String url = "jdbc:mysql://localhost:3306/test1?characterEncoding=utf8";// mysql固定的URL:jdbc:mysql://localhost:3306/数据库名（我这里是mydb）
//	public static final String user = "root";// 我的数据库的用户名
//	public static final String pwd = "fq6969382";// 我的数据库密码

	public static final String driver = "net.sourceforge.jtds.jdbc.Driver";// 驱动
	public static final String url = "jdbc:jtds:sqlserver://192.168.0.103:1433/BwinSingle8_2_EDC";
	public static final String user = "sa";// 我的数据库的用户名
	public static final String pwd = "bizwin";// 我的数据库密码

	public static Connection dbConnection() {
		Connection con = null;
		try {
			// 加载mysql驱动器
			Class.forName(driver);
			// 建立数据库连接
			con = DriverManager.getConnection(url, user, pwd);
		} catch (ClassNotFoundException e) {
			System.out.println("加载驱动器失败");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("注册驱动器失败");
			e.printStackTrace();
		}
		return con;
	}
}
