package com.bocom.util;

/**
 * 
 * @author Administrator
 *授权类型：0不限制，1只支持用户，2只支持组织
 */
public class EmpowerTypeUtil {
	
    public static Boolean haveOrgPower(Integer enpowerType){
    	if(null!=enpowerType){
    		int temValue = enpowerType.intValue();
    		if(temValue==0 || temValue==2){
    			return true;
    		}
		}
		return false;
    }
}
