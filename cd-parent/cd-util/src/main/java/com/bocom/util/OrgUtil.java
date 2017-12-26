package com.bocom.util;

import org.apache.commons.lang.StringUtils;


public class OrgUtil {
	  /*** 
     * 根据当前组织机构编码计算父组织机构编码
     * @param String orgCode    12位
     * @return String fartherOrgCode
     */
    public static String getFartherOrgCode(String orgCode){
    	if(StringUtils.isNotBlank(orgCode)){
	    	int orgLength = orgCode.length();
	    	if(orgCode.endsWith("00")&orgLength>=2){
	    		return getFartherOrgCode(orgCode.substring(0, orgLength-2));
	    	}else{
	    		if(orgLength>=2){
	        		StringBuffer sb = new StringBuffer();
	        		for(int i =0; i<14-orgLength;i++){
	        			sb.append("0");
	        		}
	    			return orgCode.substring(0, orgLength-2).concat(sb.toString());
	    		}else{
	    			return "000000000000";
	    		}
	    		
	    	}
		}
		return null;
    }
}
