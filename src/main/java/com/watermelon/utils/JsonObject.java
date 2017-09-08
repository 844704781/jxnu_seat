package com.watermelon.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonObject {
	private String status;
	private Object value;
	public JsonObject(String status, Object value) {
		super();
		this.status = status;
		this.value = value;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 将对象转换成json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		
		return gson.toJson(obj);
	}

}
