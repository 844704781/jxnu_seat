package com.watermelon.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.watermelon.pojo.User;

@Controller
@RequestMapping("/menu")
public class MenuController {

	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		User user=(User) request.getSession().getAttribute("user");
		if(user==null)
		{
			ModelAndView modelAndView = new ModelAndView("login");
			modelAndView.addObject("message", "No login");
			return modelAndView;
		}
		ModelAndView modelAndView = new ModelAndView("menu");
		return modelAndView;
	}
}
