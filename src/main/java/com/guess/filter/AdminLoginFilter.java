package com.guess.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.guess.controller.Result;

public class AdminLoginFilter implements Filter {
	private static Logger logger = LogManager.getLogger(AdminLoginFilter.class);

	private String[] excluded;
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String path = httpServletRequest.getServletPath();
		if(!ArrayUtils.contains(excluded, path)){
			if (httpServletRequest.getSession().getAttribute("admin") == null) {
				HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				httpServletResponse
						.setContentType("application/json;charset=utf-8");
				Result result = new Result();
				result.setError("请先登录");
				response.getWriter().append(result.toJson());
				logger.info("adminLoginFilter: " + path);
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		String excludedString = filterConfig.getInitParameter("excluded");
		if(StringUtils.isNotBlank(excludedString)){
			excluded = excludedString.split(",");
		}
	}

	public void destroy() {

	}
}
