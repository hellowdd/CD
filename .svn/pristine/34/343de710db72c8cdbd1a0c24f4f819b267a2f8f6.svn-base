package com.bocom.util;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SFTPv3Client;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SshUtil {

	private static Logger logger = LoggerFactory.getLogger(SshUtil.class);

	public static void scpFile(String IP, int PORT, String USERNAME,
			String PASSWORD, String fromPath, String toPath) throws IOException {
		Connection conn = new Connection(IP);
		conn.connect();
		boolean isAuthenticated = conn.authenticateWithPassword(USERNAME,
				PASSWORD);
		logger.info(USERNAME);
		logger.info(PASSWORD);
		logger.info("执行了scp");
		SCPClient client = new SCPClient(conn);
		client.put(fromPath, toPath);
		conn.close();

	}

	public static void delteFile(String IP, int PORT, String USERNAME,
			String PASSWORD, String filePath) throws IOException {
		Connection conn = new Connection(IP);
		conn.connect();
		boolean isAuthenticated = conn.authenticateWithPassword(USERNAME,
				PASSWORD);
		logger.info("链接结果==========》" + isAuthenticated);
		logger.info(USERNAME);
		logger.info(PASSWORD);
		Session session = conn.openSession();
		session.execCommand("rm -rf "+filePath);
		session.close();
		conn.close();

	}


	/**
	 * 判断布局文件是否存在 不存在则复制一个新的文件到项目中
	 *
	 * @param appId
	 */
	public static void validateConfigFile(String IP, int PORT, String USERNAME,
			String PASSWORD, String cmd) {
		logger.info("进入执行命令的方法");
		// is config file exsit???
		Connection con = null;
		Session session = null;
		try {
			con = new Connection(IP, PORT);
			// 建立连接
			con.connect();
			// 输入用户名&&口令
			boolean isAuthed = con.authenticateWithPassword(USERNAME, PASSWORD);
			// 建立SCP客户端
			// SCPClient scpClient = con.createSCPClient();
			// 建立会话，开始执行cp命令
			session = con.openSession();
			session.execCommand(cmd);

		} catch (Exception e) {
			logger.error("Connection linux error  ", e.getMessage());
		} finally {
			if (session != null) {
				session.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}
}
