package com.xlzcloud.ext.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.log.Logger;

public abstract class ExInterceptor implements Interceptor{
	protected Logger log = Logger.getLogger(getClass());
}
