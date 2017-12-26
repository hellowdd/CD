package com.bocom.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ShellUtils {

	
    private static final Logger logger = LoggerFactory.getLogger(ShellUtils.class);
	
	 private static JSch jsch;  
	 private static Session session;  
	 
	  /** 
	     * 连接到指定的IP 
	     *  
	     * @throws JSchException 
	     */  
	    public static void connect(String user, String passwd, String host) throws JSchException {  
	        jsch = new JSch();  
	        session = jsch.getSession(user, host, 22);  
	        session.setPassword(passwd);  
	          
	        java.util.Properties config = new java.util.Properties();  
	        config.put("StrictHostKeyChecking", "no");  
	        session.setConfig(config);    
	        session.setTimeout(60000);
	          
	        session.connect();  
	    }  
	  
	    /** 
	     * 执行相关的命令 
	     * @throws JSchException  
	     */  
	    public static void execCmd(List<String> commands, String user, String passwd, String host){  
	        try {
				connect(user, passwd, host);
			} catch (JSchException e1) {
                logger.error(e1.getMessage());
			}  
	          
	        BufferedReader reader = null;  
	        Channel channel = null;  
	  
	        try {  
	            if (!CollectionUtils.isEmpty(commands)) {
	            	for(String command:commands){
		                channel = session.openChannel("exec");  
		                ((ChannelExec) channel).setCommand(command);  
		                  
		                channel.setInputStream(null);  
		                ((ChannelExec) channel).setErrStream(System.err);  
		  
		                channel.connect();  
		                InputStream in = channel.getInputStream();  
		                reader = new BufferedReader(new InputStreamReader(in));  
		                String buf = null;  
		                while ((buf = reader.readLine()) != null) {  
		                    //System.out.println(buf);  
		                    logger.info(buf);
		                }  
	            	}
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (JSchException e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                reader.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	            channel.disconnect();  
	            session.disconnect();  
	        }  
	    }  
	    
	    public static String execCmd(String command, String user, String passwd, String host){  
	        try {
				connect(user, passwd, host);
			} catch (JSchException e1) {
                logger.error(e1.getMessage());
			}  
	        String result="";
	          
	        BufferedReader reader = null;  
	        Channel channel = null;  
	  
	        try {  
	        	if (command != null) {  
	                channel = session.openChannel("exec");  
	                ((ChannelExec) channel).setCommand(command);  
	                  
	                channel.setInputStream(null);  
	                ((ChannelExec) channel).setErrStream(System.err);  
	  
	                channel.connect();  
	                InputStream in = channel.getInputStream();  
	                reader = new BufferedReader(new InputStreamReader(in));  
	                String buf = null;  
	                while ((buf = reader.readLine()) != null) {  
	                    result+=buf;
	                    logger.debug(buf);
	                }  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } catch (JSchException e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                reader.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	            channel.disconnect();  
	            session.disconnect();  
	        }
			return result;  
	    }  
}
