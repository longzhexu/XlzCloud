package com.xlzcloud.ext.interceptor;

import java.util.Map;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.xlzcloud.config.AppConfig;


public class ExceptionInterceptor extends ExInterceptor{
	@Override
	public void intercept(ActionInvocation ai) {
		try{
			ai.invoke();
		}catch(Exception e){
			Controller ctrl = ai.getController();
			if(AppConfig.getDevMode()){
				ctrl.renderHtml(getExceptionHtml(e, ai));
			}else{
				ctrl.setAttr("title", "系统异常");
				ctrl.setAttr("content", "系统出现异常，请联系管理员！");
				ctrl.renderFreeMarker(AppConfig.getErrorPage());
			}
			log.error(getExceptionText(e, ai));
		}
	}
	
	private String getExceptionHtml(Exception e,ActionInvocation ai){
		Map<String, String[]> params = ai.getController().getParaMap();
		StackTraceElement[] stack = e.getStackTrace();
		String title = e.toString();
		StringBuffer html = new StringBuffer();
		html.append("<html>");
		html.append("<head>");
		html.append("<title>"+title+"</title>");
		html.append("</head>");
		html.append("<body>");
		html.append("<h2>"+title+"</h2>");
		html.append("<h2>Controller:"+ai.getController().getClass().getName()+"</h2>");
		html.append("<h2>Action:"+ai.getMethodName()+"()</h2>");
		html.append("<h3>Paramaters</h3>");
		for(String key :params.keySet()){
			html.append("<div>"+key+"=");
			for(String value:params.get(key)){
				html.append(value+",");
			}
			html.append("</div>");
		}
		html.append("<h3>StackTrace</h3>");
		for(StackTraceElement s : stack){
			if(!s.isNativeMethod()){
				html.append("<div>"+s.getClassName()+"->"+s.getMethodName()+"():"+s.getLineNumber()+"</div>");
			}
		}
		html.append("</body>");
		html.append("</html>");
		return html.toString();
	}
	
	private String getExceptionText(Exception e,ActionInvocation ai){
		StackTraceElement[] stack = e.getStackTrace();
		StringBuffer text = new StringBuffer();
		text.append(e.toString());
		for(StackTraceElement s : stack){
			if(!s.isNativeMethod()){
				text.append(s.getClassName()+"->"+s.getMethodName()+"():"+s.getLineNumber()+"\n");
			}
		}
		return text.toString();
	}

}
