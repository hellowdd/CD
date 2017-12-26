package com.bocom.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmdUtil {
	private static Logger logger = LoggerFactory.getLogger(CmdUtil.class);
	public static String runCmd(String cmd) throws Exception {
		logger.info("进入该方法命令为："+cmd);
		InputStream in = null;
		Process process = Runtime.getRuntime().exec(cmd);
		InputStreamReader ir = new InputStreamReader(process.getInputStream());
        LineNumberReader input = new LineNumberReader(ir);
        String result="";
        String line="";
        while ((line = input.readLine()) != null) {//输出结果
        	logger.info("命令输出"+line);
        	result+=line;
        }
		return result;
	}
	
	public static String runCmd(String[] cmd) throws Exception {
		logger.info("进入该方法命令为："+cmd);
		InputStream in = null;
		Process process = Runtime.getRuntime().exec(cmd);
		InputStreamReader ir = new InputStreamReader(process.getInputStream());
        LineNumberReader input = new LineNumberReader(ir);
        String result="";
        String line="";
        while ((line = input.readLine()) != null) {//输出结果
        	logger.info("命令输出"+line);
        	result+=line;
        }
		return result;
	}
}
