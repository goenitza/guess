package com.guess.controller;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.guess.vo.Result;

@ControllerAdvice
public class GlobalDefaultExceptionHandler{
	
	private static Logger logger = LogManager.getLogger(GlobalDefaultExceptionHandler.class);
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String missingServletRequestParameterExceptionHandler(Exception e) {
		Result result = new Result();
		logger.error(ExceptionUtils.getStackTrace(e));
		result.setError(e.getMessage());
		return result.toJson();
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String defaultExceptionHandler(Exception e) {
		Result result = new Result();
		logger.error(ExceptionUtils.getStackTrace(e));
		result.setError(e.getMessage() != null ? e.getMessage() : "server internal error");
		return result.toJson();
	}
}
