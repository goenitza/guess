package com.guess.controller;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.guess.enums.UserRole;
import com.guess.model.Circle;
import com.guess.model.Individual;
import com.guess.model.Organization;
import com.guess.model.User;
import com.guess.service.IndividualService;
import com.guess.service.OrganizationService;
import com.guess.vo.Result;
import com.guess.vo.UserInSession;

@Controller
@RequestMapping(value = "/user/circle", produces = "application/json;charset=utf-8")
public class CircleContrller {
	private static Logger logger = LogManager.getLogger(CircleContrller.class);
	@Autowired
	private IndividualService individualService;
	@Autowired
	private OrganizationService organizationService;
	
	@RequestMapping("/get_all")
	@ResponseBody
	public String getAll(HttpServletRequest request){
		Result result = new Result();
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		User user = null;
		if(userInSession.role == UserRole.INDIVIDUAL){
			user = individualService.get(userInSession.id);
		}else {
			user = organizationService.get(userInSession.id);
		}
		Set<Circle> circles = user.getCircles();
		result.set("circles", JSON.toJSON(circles));
		logger.info("get all circle: " + userInSession.id);
		return result.toJson();
	}
	
	@RequestMapping("/add")
	@ResponseBody
	public String add(@RequestParam("name") String name, HttpServletRequest request){
		Result result = new Result();
		Circle circle = new Circle();
		circle.setName(name);
		String id = null;
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		if(userInSession.role == UserRole.INDIVIDUAL){
			Individual individual = individualService.get(userInSession.id);
			Set<Circle> circles = individual.getCircles();
			circles.add(circle);
			individual.setCircles(circles);
			individualService.update(individual);
			for(Circle c : circles){
				if(c.equals(circle)){
					id = c.getId();
					break;
				}
			}
		}else {
			Organization organization = organizationService.get(userInSession.id);
			Set<Circle> circles = organization.getCircles();
			circles.add(circle);
			organization.setCircles(circles);
			organizationService.update(organization);
			for(Circle c : circles){
				if(c.equals(circle)){
					id = c.getId();
					break;
				}
			}
		}
		result.set("id", id);
		logger.info("add new circle: " + name);
		return result.toJson();
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(@RequestParam("id") String id,
			@RequestParam("name") String name, 
			HttpServletRequest request, HttpServletResponse response){
		Result result = new Result();
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		if(userInSession.role == UserRole.INDIVIDUAL){
			Individual individual = individualService.get(userInSession.id);
			Set<Circle> circles = individual.getCircles();
			for(Circle circle : circles){
				if(circle.getId().equals(id)){
					circle.setName(name);
					individual.setCircles(circles);
					individualService.update(individual);
					logger.info("update circle: " + id);
					return result.toJson();
				}
			}
		}else {
			Organization organization = organizationService.get(userInSession.id);
			Set<Circle> circles = organization.getCircles();
			for(Circle circle : circles){
				if(circle.getId().equals(id)){
					circle.setName(name);
					organization.setCircles(circles);
					organizationService.update(organization);
					logger.info("update circle: " + id);
					return result.toJson();
				}
			}
		}
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		result.setError("找不到具有此ID的朋友圈");
		logger.info("The circle was not found: " + id);
		return result.toJson();
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(@RequestParam("id") String id, 
			HttpServletRequest request, HttpServletResponse response){
		Result result = new Result();
		UserInSession userInSession = (UserInSession) request.getSession().getAttribute("user");
		Circle circle = null;
		if(userInSession.role == UserRole.INDIVIDUAL){
			Individual individual = individualService.get(userInSession.id);
			Set<Circle> circles = individual.getCircles();
			Iterator<Circle> iterator = circles.iterator();
			while(iterator.hasNext()){
				circle = iterator.next();
				if(circle.getId().equals(id)){
					iterator.remove();
					individual.setCircles(circles);
					individualService.update(individual);
					logger.info("delete circle: " + id);
					return result.toJson();
				}
			}
		}else {
			Organization organization = organizationService.get(userInSession.id);
			Set<Circle> circles = organization.getCircles();
			Iterator<Circle> iterator = circles.iterator();
			while(iterator.hasNext()){
				circle = iterator.next();
				if(circle.getId().equals(id)){
					iterator.remove();
					organization.setCircles(circles);
					organizationService.update(organization);
					logger.info("delete circle: " + id);
					return result.toJson();
				}
			}
		}
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		result.setError("找不到具有此ID的朋友圈");
		logger.info("The circle was not found: " + id);
		return result.toJson();
	}
}
