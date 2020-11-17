package kr.co.blog.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.blog.beans.BoardInfoBean;

@Repository
public class TopMenuDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List<BoardInfoBean> getTopMenuList() {
		
		List<BoardInfoBean> topMenuList = sqlSessionTemplate.selectList("topmenu.get_topmenu_list");
		
		return topMenuList;
	}
}
