package kr.co.blog.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.HandlerInterceptor;

import kr.co.blog.beans.ContentBean;
import kr.co.blog.beans.UserBean;
import kr.co.blog.service.BoardService;

public class CheckWriterInterceptor implements HandlerInterceptor{
	
	// XML 에서는 바로 주입 가능
	@Resource(name = "loginUserBean")
	@Lazy
	private UserBean loginUserBean;
	
	@Autowired
	private BoardService boardService;
	
	// 자바 설정 인터셉터는 생성자를 통해서 객체를 주입받아야한다.	
//	public CheckWriterInterceptor(UserBean loginUserBean, BoardService boardService) {
//		this.loginUserBean = loginUserBean;
//		this.boardService = boardService;
//	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String str1 = request.getParameter("content_idx");
		int content_idx = Integer.parseInt(str1);
		
		ContentBean currentContentBean = boardService.getContentInfo(content_idx);
		
		if(currentContentBean.getContent_writer_idx() != loginUserBean.getUser_idx()) {
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/board/not_writer"); 
			return false;
		}		
		return true;
	}
}
