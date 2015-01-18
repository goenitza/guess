package com.guess.controller;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class BaseController {
	Result result = new Result();
}

class Result{
	Map<Object, Object> result = new HashMap<Object, Object>();
	
	void setError(String error){
		result.put("error", error);
	}
	
	void set(String key, Object value){
		result.put(key, value);
	}
	
	String toJson(){
		return JSON.toJSONString(result);
	}
}
