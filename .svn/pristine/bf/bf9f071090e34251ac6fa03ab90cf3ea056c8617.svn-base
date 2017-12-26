package com.bocom.web.controller.rest;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bocom.util.ResponseVo;

@Controller
@RequestMapping("/manager/loginAction")
public class LoginRestController {

	/**
	 * 结合sso自动登录访问地址
	 */
	@RequestMapping(value = "/loginCasOut", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVo loginCasOut(HttpSession session) {
		session.invalidate();
		return new ResponseVo();
	}

}
