package com.xlzcloud.ext;

import java.util.Enumeration;

import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.validate.Validator;

public abstract class ExValidator extends Validator{
	protected Logger log = Logger.getLogger(getClass());
	
	/*校验失败默认以json形式返回
	 * @see com.jfinal.validate.Validator#handleError(com.jfinal.core.Controller)
	 */
	protected void handleError(Controller c) {
		Enumeration<String> names = c.getRequest().getAttributeNames();
		JsonResult json = new JsonResult("error");
		while(names.hasMoreElements()){
			String key = names.nextElement();
			json.putMapItem(key, c.getAttr(key));
			log.debug(key);
		}
		c.renderJson(json);
	}

}
