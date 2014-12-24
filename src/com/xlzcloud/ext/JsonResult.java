package com.xlzcloud.ext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinal.kit.JsonKit;

public class JsonResult {
	public static final String STATUS_OK = "ok";
	public static final String STATUS_ERROR = "error";
	private String status = STATUS_OK;
	private String title = "";
	private String textData = "";
	private Map<String, Object> mapData = new HashMap<String, Object>();
	private List<Object> listData = new ArrayList<Object>();
	
	public JsonResult() {

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTextData() {
		return textData;
	}

	public void setTextData(String textData) {
		this.textData = textData;
	}

	public Map<String, Object> getMapData() {
		return mapData;
	}

	public void setMapData(Map<String, Object> mapData) {
		this.mapData = mapData;
	}

	public List<Object> getListData() {
		return listData;
	}

	public void setListData(List<Object> listData) {
		this.listData = listData;
	}

	public JsonResult(String status) {
		this.status = status;
	}

	public JsonResult(String status, String title, String textData) {
		this(status);
		this.title = title;
		this.textData = textData;
	}


	public void putMapItem(String key, Object value) {
		mapData.put(key, value);
	}

	public void putListItem(Object item) {
		listData.add(item);
	}

}
