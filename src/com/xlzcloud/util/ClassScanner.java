package com.xlzcloud.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.kit.PathKit;

public class ClassScanner<M> {

	private String classPath = "";
	private String packageName = "";
	private ClassLoader loader;
	private String regx;
	private List<Class<? extends M>> clazz = new ArrayList<Class<? extends M>>();

	public ClassScanner(String packageName) {
		this.packageName = packageName;
		this.classPath = packageName.replace('.', '/');
		loader = getClass().getClassLoader();
	}
	
	/**
	 * @param packageName 包名
	 * @param reg 类名正则式
	 */
	public ClassScanner(String packageName,String regx){
		this(packageName);
		this.regx = regx;
		
	}

	public List<Class<? extends M>> getClassList() {
		String path = PathKit.getRootClassPath();
		File classPath = new File(path + "/" + this.classPath);
		scanClass(classPath);
		return clazz;
	}

	private void scanClass(File root) {
		if (root.isDirectory()) {
			for (File child : root.listFiles()) {
				scanClass(child);
			}
		} else {
			addClass(root);
		}
	}

	private void addClass(File child) {
		Class<? extends M> clas = null;
		String className = null;
		className = getPackageName(child);
//		如果不符合正则表达式则直接返回
		if(regx != null){
			Pattern pattern = Pattern.compile(regx);
			Matcher matcher = pattern.matcher(className);
			if(!matcher.matches()){
				return;
			}
		}
		try {
			clas = (Class<? extends M>) loader.loadClass(className);
//			System.out.println("auto load class :"+className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		clazz.add(clas);
	}

	private String getPackageName(File clazz) {
		String path = clazz.getAbsolutePath();
		path = path.replace(".class", "").replace('\\', '.').split(packageName)[1];
		return packageName + path;
	}

	public static void main(String[] args) {
		new ClassScanner("com").getClassList();
	}

}
