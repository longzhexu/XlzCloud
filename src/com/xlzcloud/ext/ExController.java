package com.xlzcloud.ext;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;

public class ExController extends Controller {
	protected  Logger log = Logger.getLogger(getClass());

	
	protected Map getPageMap(Page page) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("totalPage", page.getTotalPage());
		map.put("pageNum", page.getPageNumber());
		map.put("next", page.getPageNumber() + 1 > page.getTotalPage() ? 0
				: page.getPageNumber() + 1);
		map.put("pre", page.getPageNumber() - 1 < 1 ? 0
				: page.getPageNumber() - 1);
		map.put("hasNext", page.getPageNumber() + 1 <= page.getTotalPage());
		map.put("hasPre", page.getPageNumber() - 1 >= 1);
		return map;
	}
	

}
