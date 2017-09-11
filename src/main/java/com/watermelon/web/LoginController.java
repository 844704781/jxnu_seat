package com.watermelon.web;

import com.watermelon.pojo.Login;
import com.watermelon.pojo.User;
import com.watermelon.utils.JnueberryUtils;
import com.watermelon.utils.JsonObject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping({ "login" })
public class LoginController {
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		User user = new User();
		if((cookies!=null))
		{
			
			for (Cookie cookie : cookies) {
				if ("txt_LoginID".equals(cookie.getName())) {
					user.setTxt_LoginID(cookie.getValue());
				} else if ("txt_Password".equals(cookie.getName())) {
					user.setTxt_Password(cookie.getValue());
				}
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.addObject("user", user);
		return modelAndView;
	}

	@RequestMapping(value = "loginSubmit", method = RequestMethod.POST)
	public @ResponseBody String login(User user, HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie1 = new Cookie("txt_LoginID", user.getTxt_LoginID());
		Cookie cookie2 = new Cookie("txt_Password", user.getTxt_Password());
		cookie1.setPath("/");
		cookie2.setPath("/");
		response.addCookie(cookie1);
		response.addCookie(cookie2);
		Login login = new Login();
		login.set__EVENTVALIDATION("/wEWBALGu8H0CwK1lMLgCgLS9cL8AgKXzJ6eD1PrwC/+tEuQt/W6kERZa2FJGBofrpzrzMbXnOcWuVzp");
		login.set__VIEWSTATE(
				"/wEPDwUKLTI1Nzg1ODIyMGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgEFEGNoa19SZW1QYXNzcHdvcmTiU5zolo6/Gtin2EhtwQjwibMyu11t2YOmrWpFNSXQOw==");
		login.set__VIEWSTATEGENERATOR("C2EE9ABB");
		login.setSubCmd("Login");
		boolean isLogin;
		for (int i = 0; i < 10; i++) {
			isLogin = JnueberryUtils.login(user, login);
			if (isLogin) {
				request.getSession().setAttribute("user", user);
				String message = "success";
				JsonObject jsonObject = new JsonObject("true", message);
				String str = JsonObject.toJson(jsonObject);
				return str;
			}
			if (i == 999 && isLogin == false) {
				break;
			}
		}
		String message = "error";
		JsonObject jsonObject = new JsonObject("false", message);
		return JsonObject.toJson(jsonObject);
	}
}