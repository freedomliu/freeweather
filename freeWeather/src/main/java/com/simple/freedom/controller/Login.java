package com.simple.freedom.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.simple.freedom.beans.UserBean;
import com.simple.freedom.common.aop.BaseController;
import com.simple.freedom.common.aop.SingleSignOn;
import com.simple.freedom.common.aop.SysVariable;
import com.simple.freedom.common.util.CreateImageCode;

/**
 * 登陆相关
 * @author liuxiangtao90
 *
 */
@Controller
/*@RequestMapping("/login")*/
public class Login extends BaseController
{
	/**
	 * 获取验证码
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/getImageCode.do")
	public void getImageCode(HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		  response.setContentType("image/jpeg"); //禁止图像缓存。
		  response.setHeader("Pragma", "no-cache");
		  response.setHeader("Cache-Control", "no-cache");
		  response.setDateHeader("Expires", 0);
		  CreateImageCode vCode = new CreateImageCode(100,34,5,10);
		  request.getSession().setAttribute("code", vCode.getCode());
		  vCode.write(response.getOutputStream()); 
	}
	
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request)
	{
		Object msg= request.getSession().getAttribute(SysVariable.MSG);
		if(msg!=null)
		{
			request.setAttribute(SysVariable.MSG, msg);
			request.getSession().removeAttribute(SysVariable.MSG);
		}
		return "index";
	}
	
	@RequestMapping("/main.do")
	public String main(HttpServletRequest request)
	{
		return "main";
	}
	
	/**
	 * 登录验证
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/check.do")
	public ModelAndView caseUser(HttpServletRequest request,HttpServletResponse response,UserBean user) throws IOException
	{
		ModelAndView mv= getMV();
		mv.setViewName("index");
		Object msg= request.getSession().getAttribute(SysVariable.MSG);
		if(msg!=null)
		{
			mv.addObject(SysVariable.MSG, msg);
			request.getSession().removeAttribute(SysVariable.MSG);
			return mv;
		}
		// 跳过登陆直接访问 则跳转到首页
		if(request.getParameter("imgCode")==null)
		{
			return mv;
		}
		if(user.getPassword()==null || user.getPassword().equals(""))
		{
			mv.addObject(SysVariable.MSG, "请输入密码");
			return mv;
		}
		if(!request.getParameter("imgCode").toLowerCase().equals((
				request.getSession().getAttribute("code")+"").toLowerCase()))
		{
			mv.addObject(SysVariable.MSG, "验证码错误");
			return mv;
		}
		if(user.getUsername().equals("lxt"))
		{
			request.getSession().setAttribute(SysVariable.USERSESSION, user);
			SingleSignOn.sessionUserCreated(user.getUsername(), request.getSession());
			
			response.sendRedirect(request.getContextPath()+"/main.do");
			return null;
		}
		mv.addObject(SysVariable.MSG, "用户名或者密码错误");
		return mv;
	}
}
