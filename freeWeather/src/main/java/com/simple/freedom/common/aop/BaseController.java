package com.simple.freedom.common.aop;

import org.springframework.web.servlet.ModelAndView;

public class BaseController extends BaseClass{

	protected ModelAndView getMV()
	{
		return new ModelAndView();
	}
}
