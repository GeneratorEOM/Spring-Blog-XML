package kr.co.blog.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import kr.co.blog.beans.UserBean;

public class UserValidator implements Validator{

	// 유효성 검사
	@Override
	public boolean supports(Class<?> clazz) {
	
		return UserBean.class.isAssignableFrom(clazz);
	}
	
	// target 으로 유효성 검사가 필요한 객체가 주입됨
	@Override
	public void validate(Object target, Errors errors) {
		
		UserBean userBean = (UserBean)target;
		
		String beanName = errors.getObjectName();
		
		if(beanName.equals("joinUserBean") || beanName.equals("modifyUserBean") ) {
			
			if(userBean.getUser_pw().equals(userBean.getUser_pw2()) == false) {
				for(ObjectError error : errors.getAllErrors()) {
					System.out.println(error);
				}
				errors.rejectValue("user_pw", "NotEquals");
				errors.rejectValue("user_pw2", "NotEquals");			
			}
			
			if(beanName.equals("joinUserBean")) {
				
				if(userBean.isUserIdExist() == false) {
					errors.rejectValue("user_id", "DontCheckUserIdExist");
				}
				
			}
			
			
		}
	}
}

