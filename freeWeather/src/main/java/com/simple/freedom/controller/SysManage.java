package com.simple.freedom.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.simple.freedom.common.aop.BaseController;
import com.simple.freedom.common.aop.SysVariable;
import com.simple.freedom.dao.ISysManageMapper;

@Controller
@RequestMapping("/sys")
public class SysManage extends BaseController{
	
	@Autowired
	ISysManageMapper smm;
	
	/**
	 * 页面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping("/forwardPage.do")
	public ModelAndView forwardPage(HttpServletRequest request)
	{
		String page= request.getParameter("pageName")+"";
		ModelAndView mv=getMV();
		mv.setViewName(page);

		return mv;
	}
	
	@RequestMapping("/logout.do")
	public void logout(HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		//request.getSession().removeAttribute(SysVariable.USERSESSION);
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath()+"/login.do");
	}
	
	@RequestMapping("/getCity")
	@ResponseBody
	public List<String> getCity(HttpServletRequest request,HttpServletResponse response,String proName)
	{
		List<String> list=smm.selectCity(proName);
		return list;
	}
}
