package com.simple.freedom.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
			String area= request.getParameter("area");
			String path= request.getRealPath("/");
			String strUrl=fwPic.getIDWinfor(area,value,color,title,path);
			if(strUrl.equals(""))
			{
				url="暂无生成的图片";
			}
			url="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+strUrl;
			
			// 把这个图片名称保存在用户session中  当其退出把其色斑图删除
			/*Object fwImg= request.getAttribute("fwImg");
			if(fwImg!=null)
			{
				((ArrayList<String>)fwImg).add(strUrl);
				//request.setAttribute("fwImg", imgList);
			}
			else
			{
				List<String> imgList=new ArrayList<>();
				imgList.add(strUrl);
				request.setAttribute("fwImg", imgList);
			}*/
		}
		catch(Exception e)
		{
			System.err.println(e);
			logger.error(this.getClass() + ":" + e);
			url="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+"error.png";
		}
		return url;
	}
	
	@RequestMapping("getFWSvg")
	@ResponseBody
	public String getFWSvg(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		String url=null;
		try
		{			
			String value= request.getParameter("zb");
			String color= request.getParameter("legend");
			String title= request.getParameter("title");
			String area= request.getParameter("area");
			String path= request.getRealPath("/");
			String strUrl=fwPic.getSVGinfor(area,value,color,title,path);
			if(strUrl.equals(""))
			{
				url="暂无生成的图片";
			}
			url="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+strUrl;
			
		}
		catch(Exception e)
		{
			System.err.println(e);
			logger.error(this.getClass() + ":" + e);
			url="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+"error.png";
		}
		return url;
	}
	
	@RequestMapping("getGaode")
	@ResponseBody
	public List<Map<String,String>> getGaode(HttpServletRequest request,
			HttpServletResponse response) throws IOException
	{
		
		List<Map<String,String>> result=null;
		try
		{			
			String value= request.getParameter("zb");
			String color= request.getParameter("legend");
			String area= request.getParameter("area");
			result=fwPic.getGaode(area,value,color);

		}
		catch(Exception e)
		{
			System.err.println(e);
		}
		return result;
	}
}
