package com.guess.controller;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class Result {
	Map<Object, Object> result = new HashMap<Object, Object>();

	public void setError(String error) {
		result.put("error", error);
	}

	public void set(String key, Object value) {
		result.put(key, value);
	}

	public String toJson() {
		return JSON.toJSONString(result);
	}
}