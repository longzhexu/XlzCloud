package com.xlzcloud.ext;

import java.util.List;

import com.jfinal.config.Routes;
import com.jfinal.log.Logger;
import com.xlzcloud.util.ClassScanner;

public class ExRoutes extends Routes {
	
	private Logger log = Logger.getLogger(getClass());

	@Override
	public void config() {
		loadController();
	}
	
	private void loadController() {
		List<Class<? extends ExController>> controllers = new ClassScanner<ExController>(
				"com.xlzcloud.controller", ".*Action$").getClassList();
		for (Class<? extends ExController> ctrl : controllers) {
			UrlBind url = ctrl.getAnnotation(UrlBind.class);
			String ctrlKey = null;
			if (null == url) {
				String ctrlName = ctrl.getSimpleName();
				ctrlKey = ctrlName.substring(0, ctrlName.length() - 6);
			} else {
				ctrlKey = url.value();
			}
			add(ctrlKey, ctrl);
			log.debug("Controller " + ctrlKey + "->" + ctrl.getName());

		}
	}

}
