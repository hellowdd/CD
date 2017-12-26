
 /**  
 * Project Name:wrm-web  
 * File Name:LoginController.java  
 * Package Name:com.bocom.controller.rest  
 * Date:2017年3月8日下午5:08:07  
 * Copyright (c) 2017, Mr-Wei@bocom.com All Rights Reserved.  
 *  
*/  
  
package com.bocom.web.controller.rest;  

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**  
 * ClassName:LoginController <br/>  
 * Function: TODO ADD FUNCTION. <br/>  
 * Reason:   TODO ADD REASON. <br/>  
 * Date:     2017年3月8日 下午5:08:07 <br/>  
 * @author   Mr-Wei  
 * @version    
 * @since    JDK 1.7  
 * @see        
 */
@Controller
@RequestMapping("/")
public class LoginController {
	@RequestMapping(method=RequestMethod.GET)
	public String homePage() {
		return "redirect:/manager/loginAction/loginCas";
	}
	/**
	 * 结合sso自动登录访问地址
	 */
	@RequestMapping(value = "/manager/loginAction/loginCas")
	public String loginCas(ModelMap model, HttpSession session) {
		return "/index";
	}

}
  
