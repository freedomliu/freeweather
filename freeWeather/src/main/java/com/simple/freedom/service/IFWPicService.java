package com.simple.freedom.service;

import java.util.List;
import java.util.Map;

public interface IFWPicService  {
	public String getIDWinfor(String area,String value,String colorString,String title,String path) throws Exception ;
	public  List<Map<String,String>>  getGaode(String area,String value,String colorString,String title,String path) throws Exception ;

}
