package com.watermelon.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.watermelon.pojo.User;
import com.watermelon.utils.JsonObject;

@Aspect
public class LogAspect {

	
	//创建日志对象
		private static final Logger logger = LogManager.getLogger("用户操作记录");

		//创建连接点方法,代表有requestMapping注解的方法需要增强
		@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
		public void controller() {

		}

		//具体的增强方法,执行时间为切入点方法执行完之后执行
		@After("controller()")
		public void log(JoinPoint joinPoint) {
			//如果info级别的日志不需要记录,直接返回
			if (!logger.isInfoEnabled()) {
				return;
			}
			//获取用户id,记录需要
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			HttpServletRequest request = requestAttributes.getRequest();
			User user = (User) request.getSession().getAttribute("user");
			String txt_LoginID = null;
			if (user != null) {
				txt_LoginID = user.getTxt_LoginID();
			}
			//获取传入参数,记录需要
			Object[] args = joinPoint.getArgs();
			if (args != null && args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					if (args[i] instanceof HttpServletRequest) {
						args[i] = "request";
					} else if (args[i] instanceof HttpServletResponse) {
						args[i] = "response";
					} else if (args[i] instanceof MultipartFile) {
						args[i] = "multipartFile";
					} else if (args[i] instanceof BindingResult) {
						args[i] = "bingdingResult";
					}
				}
				//将信息记录到日志中
				logger.info("用户id:{},方法签名:{},方法的参数列表:{}", txt_LoginID, joinPoint.getSignature(), JsonObject.toJson(args));
			}
		}
}
