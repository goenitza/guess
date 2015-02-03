package com.guess.vo;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class Result {
	Map<Object, Object> result = new HashMap<Object, Object>();
	public void setError(String error) {
		result.put("error", error);
	}

	public void set(String key, Object value) {
		result.put(key, value);
	}

	public String toJson() {
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		return JSON.toJSONString(result, SerializerFeature.WriteDateUseDateFormat);
	}
}