package kr.co.blog.interceptor;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.blog.beans.BoardInfoBean;
import kr.co.blog.beans.UserBean;
import kr.co.blog.service.TopMenuService;

public class TopMenuInterceptor implements HandlerInterceptor {

	// 자바와 다르게 XML 에서는 자동 주입이 된다.
	@Autowired
	private TopMenuService topMenuService;
	
	@Resource(name = "loginUserBean")
	@Lazy
	private UserBean loginUserBean;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		List<BoardInfoBean> topMenuList = topMenuService.getTopMenuList();
		
		request.setAttribute("topMenuList", topMenuList);
		request.setAttribute("loginUserBean", loginUserBean);
		
		return true;
	}
}
