package com.xlzcloud.config;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PathKit;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.xlzcloud.ext.ExActiveRecordPlugin;
import com.xlzcloud.ext.ExRoutes;
import com.xlzcloud.ext.ExController;
import com.xlzcloud.ext.UrlBind;
import com.xlzcloud.ext.interceptor.AutoRenderIntercetor;
import com.xlzcloud.ext.interceptor.ExceptionInterceptor;
import com.xlzcloud.ext.model.ExModel;
import com.xlzcloud.ext.model.TableBind;
import com.xlzcloud.util.ClassScanner;
import com.xlzcloud.util.NameConverter;

public class AppConfig extends JFinalConfig {
	private Logger log = Logger.getLogger(getClass());
	private static String dbUrl;
	private static String dbUser;
	private static String dbPassword;
	private static String dbPerfix;
	private static Boolean devMode;
	private static String basePackage;
	private static String controllerPackage;
	private static String modelPackage;
	private static String errorPage;
	private static String tplSufix;
	private static String tplPath;

	

	public AppConfig() {
		loadConfig();
	}
	
	private void loadConfig(){
		Properties prop = loadPropertyFile("./config.properties");
		Field[] fields = getClass().getDeclaredFields();
		for (Field field : Arrays.asList(fields)) {
			field.setAccessible(true);
			try {
				Object value = prop.get(field.getName());
				if (value != null) {
					if (field.getType() == Boolean.class) {
						field.set(this, new Boolean(value.toString()));
					} else {
						field.set(this, value.toString());
					}
					log.debug(field.getName() + "=" + value);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			} finally {
				field.setAccessible(false);
			}
		}
	}

	public void configConstant(Constants me) {
		me.setDevMode(devMode);
		me.setViewType(ViewType.FREE_MARKER);
	}

	public void configRoute(Routes me) {
		me.add(new ExRoutes());

	}

	public void configPlugin(Plugins me) {
		C3p0Plugin cp = new C3p0Plugin(dbUrl, dbUser, dbPassword);
		ActiveRecordPlugin arp = new ExActiveRecordPlugin(cp);
		arp.setShowSql(true);
		me.add(cp);
		me.add(arp);
		me.add(new EhCachePlugin());

	}

	public void configInterceptor(Interceptors me) {
		me.add(new ExceptionInterceptor());
		me.add(new AutoRenderIntercetor());
	}

	public void configHandler(Handlers me) {

	}
	
	
	public static String getDbUrl() {
		return dbUrl;
	}

	public static String getDbUser() {
		return dbUser;
	}

	public static String getDbPassword() {
		return dbPassword;
	}

	public static String getDbPerfix() {
		return dbPerfix;
	}

	public static Boolean getDevMode() {
		return devMode;
	}

	public static String getBasePackage() {
		return basePackage;
	}

	public static String getControllerPackage() {
		return controllerPackage;
	}

	public static String getModelPackage() {
		return modelPackage;
	}

	public static String getErrorPage() {
		return errorPage;
	}

	public static String getTpl_sufix() {
		return tplSufix;
	}

	public static String getTpl_path() {
		return tplPath;
	}

}