package com.bocom.util;

public class ResponseBaseUtil {
	
	  public static String success(Object object){
	        ResponseVo responseVo = new ResponseVo();
	        responseVo.setSuccess(true);
	        responseVo.setData(object);
	        return getBASE64(JsonUtil.toJSon(responseVo));
	    }

	    public static String fail(String msg, Object object){
	        ResponseVo responseVo = new ResponseVo();
	        responseVo.setSuccess(false);
	        responseVo.setData(object);
	        responseVo.setMessage(msg);
	        return getBASE64(JsonUtil.toJSon(responseVo));
	    }
	    
	    public static String getBASE64(String s) { 
	    	if (s == null) return null; 
	    	return (new sun.misc.BASE64Encoder()).encode(s.getBytes()); 
	    } 
}
