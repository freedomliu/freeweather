package com.simple.freedom.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.simple.freedom.common.aop.BaseController;
import com.simple.freedom.service.IFWPicService;

@Controller
public class FWPic extends BaseController{
	@Autowired
	IFWPicService fwPic;
	
	@RequestMapping("getFWPic")
	@ResponseBody
	public String getFWPic(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		String url=null;
		try
		{			
			String value= request.getParameter("zb");
			String color= request.getParameter("legend");
			String title= request.getParameter("title");
			String sessionId= request.getParameter("sessionId");
			String path= request.getRealPath("/");
			String strUrl=fwPic.getIDWinfor(value,color,title,path,sessionId);
			if(strUrl.equals(""))
			{
				url="暂无生成的图片";
			}
			url="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+strUrl;
		}
		catch(Exception e)
		{
			logger.error(this.getClass() + ":" + e);
			url="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+"error.png";
		}
		System.out.println(url);
		return url;
	}
}
