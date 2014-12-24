package com.xlzcloud.validator;

import java.util.Enumeration;

import com.jfinal.core.Controller;
import com.xlzcloud.ext.ExValidator;
import com.xlzcloud.ext.JsonResult;

public class LoginValidator extends ExValidator{

	@Override
	protected void validate(Controller c) {
		validateString("user.account", true, 2, 10, "accountError", "用户名格式不合法");
		validateString("user.password", true, 8, 16, "passwordError", "密码格式不合法");
	}

}
