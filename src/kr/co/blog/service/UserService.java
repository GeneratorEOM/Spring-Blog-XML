﻿package kr.co.blog.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import kr.co.blog.beans.UserBean;
import kr.co.blog.dao.UserDao;

@Service
public class UserService {

	@Autowired
	UserDao userDao;
	
	@Resource(name = "loginUserBean")
	@Lazy
	UserBean loginUserBean;
	
	public boolean userCheckIdExist(String user_id) {
		
		String userCheck = userDao.checkUserIdExist(user_id);
			
		if(userCheck == null) {
			
			return true;
			
		}else {
			
			return false;
		}
		
		 
	}
	
	public void addUserInfo(UserBean joinUserBean) {
		userDao.addUserInfo(joinUserBean);
	}
	
	public void getLoginUserInfo(UserBean tempLoginUserBean) {
		
		UserBean tempLoginUserBean2 = userDao.getLoginUserInfo(tempLoginUserBean);
		
		if(tempLoginUserBean2 != null) {
			loginUserBean.setUser_idx(tempLoginUserBean2.getUser_idx());
			loginUserBean.setUser_name(tempLoginUserBean2.getUser_name());
			loginUserBean.setUserLogin(true);
		}
	}
	
	public void getModifyUserBean(UserBean modifyUserBean) {
		
		UserBean tempModifyUserBean = userDao.getModifyUserInfo(loginUserBean.getUser_idx()); 
		
		modifyUserBean.setUser_idx(tempModifyUserBean.getUser_idx());
		modifyUserBean.setUser_id(tempModifyUserBean.getUser_id());
		modifyUserBean.setUser_name(tempModifyUserBean.getUser_name());

	}
	
	public void modifyUserInfo(UserBean modifyUserBean) {
		modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
		userDao.modifyUserInfo(modifyUserBean);
	}
}
