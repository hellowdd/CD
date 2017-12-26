package com.bocom.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.SQLExec;

public class ConnectionUtil {
	public static int exeSQL(String url, String username, String password,
			String sql) throws Exception {
		String driver = "com.mysql.jdbc.Driver";
		Connection conn = null;
		int i = 0;
		PreparedStatement pstmt;

		Class.forName(driver); // classLoader,加载对应驱动
		conn = (Connection) DriverManager
				.getConnection(url, username, password);
		pstmt = (PreparedStatement) conn.prepareStatement(sql);
		i = pstmt.executeUpdate();
		pstmt.close();
		conn.close();

		return i;
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void ExcuteSqlFile(String sqlFile,String ip,String userName,String passWord) throws Exception {
		String linkStr = ip;
		SQLExec sqlExec = new SQLExec();
		sqlExec.setEncoding("UTF-8");
		sqlExec.setDriver("com.mysql.jdbc.Driver");
		sqlExec.setUrl(linkStr);
		sqlExec.setUserid(userName);
		sqlExec.setPassword(passWord);
		sqlExec.setSrc(new File(sqlFile)); 
		sqlExec.setProject(new Project());
		sqlExec.execute();
	}
}
