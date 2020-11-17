package kr.co.blog.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.blog.beans.UserBean;

public class CheckLoginInterceptor implements HandlerInterceptor{

	@Resource(name = "loginUserBean")
	@Lazy
	private UserBean loginUserBean;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	
		if(loginUserBean.isUserLogin() == false) {
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/user/not_login");
			return false;
		}
		
		return true;
	}
	
	
}
