package com.xlzcloud.ext.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jfinal.plugin.activerecord.Model;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModelRelation {
	public String fk();
	public String pk()default"id";
	public Class<? extends Model> model();
}
