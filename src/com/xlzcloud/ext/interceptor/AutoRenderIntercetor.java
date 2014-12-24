package com.xlzcloud.ext.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.kit.PathKit;
import com.jfinal.log.Logger;
import com.xlzcloud.config.AppConfig;

public class AutoRenderIntercetor extends ExInterceptor {
	
	@Override
	public void intercept(ActionInvocation ai) {
		ai.getController().renderFreeMarker(getPackageViewPath(ai));
		ai.invoke();
	}

	private String getPackageViewPath(ActionInvocation ai) {
		String pack = PathKit.getPackagePath(ai.getController());
		String relativePath = pack.substring(
				AppConfig.getControllerPackage().length()).replace(".", "/");
		return relativePath + "/" + AppConfig.getTpl_path() + "/"+ai.getController().getClass().getSimpleName()+"/"
				+ ai.getMethodName() + "." + AppConfig.getTpl_sufix();
	}

}
