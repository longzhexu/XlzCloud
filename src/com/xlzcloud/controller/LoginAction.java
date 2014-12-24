package com.xlzcloud.controller;

import com.jfinal.aop.Before;
import com.jfinal.aop.ClearInterceptor;
import com.jfinal.validate.Validator;
import com.xlzcloud.ext.ExController;
import com.xlzcloud.ext.UrlBind;
import com.xlzcloud.validator.LoginValidator;
@Before(LoginValidator.class)
public class LoginAction extends ExController{
	
	public void index() {
		int i = 1/0;
	}
}
