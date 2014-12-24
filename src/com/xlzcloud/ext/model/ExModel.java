package com.xlzcloud.ext.model;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.TableMapping;
import com.xlzcloud.config.AppConfig;

/**
 * @author xlz 给Model提供更多数据访问方式
 */
public abstract class ExModel<M extends ExModel> extends Model<M> {

	private Map<String, Object> param;
	private Map<String, Object> order;
	protected Logger log  = Logger.getLogger(getClass());

	/**
	 * 获取当前模型对应的表名. 映射方式为XxxXxx--->表前缀+xxx_xxx
	 * 
	 * @return 当前模型所对应的表名
	 */
	public String getCurTableName() {
		String className = this.getClass().getSimpleName();
		StringBuffer tableName = new StringBuffer();
		for (Character c : className.toCharArray()) {
			if (Character.isUpperCase(c)) {
				tableName.append("_" + Character.toLowerCase(c));
			} else {
				tableName.append(c);
			}
		}
		if (AppConfig.getDbPerfix() == null) {
			return tableName.substring(1).toString();
		} else {
			return AppConfig.getDbPerfix() + tableName;
		}

	}

	private List<M> findbyCondition(Map<String, Object> params, String relation) {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select * from " + getCurTableName() + " where ");
		List<Object> values = new ArrayList<Object>();
		for (String key : params.keySet()) {
			sqlBuffer.append(params.get(key) + "=? "+relation);
			values.add(params.get(key));
		}
		sqlBuffer.setLength(sqlBuffer.length() - 3);// 裁掉多余的AND
		return find(sqlBuffer.toString(), values);
	}

	public List<M> findbyAndCondition(Map<String, Object> params) {
		return findbyCondition(params, "and");
	}

	public List<M> findbyOrCondition(Map<String, Object> params) {
		return findbyCondition(params, "or");
	}
	
	
	public Model<M> getRelation(String fk){
		Annotation[] anos = getClass().getAnnotations();
		for(int i = 0 ; i < anos.length ;i++){
			if(anos[i] instanceof ModelRelation){
				ModelRelation an = ((ModelRelation)anos[i]);
				if(an.fk().equals(fk)){
					Model m = null;
					try {
						m = an.model().newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					return m.findFirst("select * from "+TableMapping.me().getTable(getClass()));
				}
			}
		}
		log.warn("未找到关系定义");
		return null;
	}
	
}
