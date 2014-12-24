package com.xlzcloud.util;

public class NameConverter {

	public String underlineToHungarian(String name) {

		StringBuffer resultName = new StringBuffer();
		boolean upper = false;
		for (Character c : name.toCharArray()) {
			if (c == '_') {
				upper = true;
				continue;
			} else {
				if (upper) {
					resultName.append(Character.toUpperCase(c));
					upper = false;
				} else {
					resultName.append(Character.toLowerCase(c));
				}
			}
		}
		return resultName.toString();

	}

	public String hungarianToUnderline(String name) {
		StringBuffer resultName = new StringBuffer();
		for (Character c : name.toCharArray()) {
			if (Character.isUpperCase(c)) {
				resultName.append("_" + Character.toLowerCase(c));
			} else {
				resultName.append(c);
			}
		}
		if (resultName.charAt(0) == '_') {
			return resultName.substring(1);
		}
		return resultName.toString();
	}

	public String initialToLowercase (String name) {
		return name.replace(name.charAt(0), Character.toLowerCase(name.charAt(0)));
	}

}
