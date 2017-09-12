package com.simple.freedom.service.imp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simple.freedom.common.util.VectorUtil;
import com.simple.freedom.common.util.VectorUtil_SVG;
import com.simple.freedom.common.util.VectorUtil_gaode;
import com.simple.freedom.service.IFWPicService;
@Service
public class FWPicService implements IFWPicService{	
	@Autowired
	VectorUtil_SVG vectorUtil_svg;
	
	@Autowired
	VectorUtil_gaode vectorUtil_gaode;
	
	@Autowired
	VectorUtil vectorUtil;
	
	public String getIDWinfor(String area,String value,String colorString,String title,String path) throws Exception {
		String[] infor= value.split(";");
		List<double[]> list = new ArrayList<double[]>();
		for(int i=0;i<infor.length;i++)
		{
			String[] temp=infor[i].split(",");
			double[] temp1=new double[3];
			temp1[0]=Double.parseDouble(temp[0]);
			temp1[1]=Double.parseDouble(temp[1]);
			temp1[2]=Double.parseDouble(temp[2]);
			list.add(temp1);
		}
		String[] cs= colorString.split(";");
		String colorStringTemp="";
		List<String> colorValue=new ArrayList<String>();
		String firstCol="";
		String lastCol="";
		for(int i=0;i<cs.length;i++)
		{
			String[] temp=cs[i].split(",");
			colorValue.add(temp[0]);
			colorValue.add(temp[1]);
			colorStringTemp=colorStringTemp+"#"+temp[2]+";";
			if(i==0)
			{
				firstCol=temp[2];
			}
			if(i==cs.length-1)
			{
				lastCol=temp[2];
			}
		}
		List<String> tempList= new ArrayList<String>();  
	    for(String i:colorValue){
	    	if(i.equals("~"))
	    	{
	    		 tempList.add(i); 
	    	}
	    	else if(!tempList.contains(i)){  
	            tempList.add(i);  
	        }  
	    } 
	    String strTemp="";
	    for(String item: tempList)
	    {
	    	strTemp=strTemp+item+";";
	    }
		String configValue=strTemp.substring(0, strTemp.toString().length()-1).replace("~;","").replace(";~","");
		String jString="{";
	    jString=jString+"\"value\":\""+configValue+"\",";
		jString=jString+"\"colorString\":\""+colorStringTemp+"\"}";
		org.json.JSONObject jb=new org.json.JSONObject(jString);
		String pathStr= vectorUtil.getBufferedImageByDataList(area,list,jb,title,path,Arrays.asList(colorStringTemp.replaceAll("#", "").split(";")),Arrays.asList(strTemp.split(";")));
	    return pathStr;
	}
	
	public String getSVGinfor(String area,String value,String colorString,String title,String path) throws Exception {
		String[] infor= value.split(";");
		List<double[]> list = new ArrayList<double[]>();
		for(int i=0;i<infor.length;i++)
		{
			String[] temp=infor[i].split(",");
			double[] temp1=new double[3];
			temp1[0]=Double.parseDouble(temp[0]);
			temp1[1]=Double.parseDouble(temp[1]);
			temp1[2]=Double.parseDouble(temp[2]);
			list.add(temp1);
		}
		String[] cs= colorString.split(";");
		String colorStringTemp="";
		List<String> colorValue=new ArrayList<String>();
		String firstCol="";
		String lastCol="";
		for(int i=0;i<cs.length;i++)
		{
			String[] temp=cs[i].split(",");
			colorValue.add(temp[0]);
			colorValue.add(temp[1]);
			colorStringTemp=colorStringTemp+"#"+temp[2]+";";
			if(i==0)
			{
				firstCol=temp[2];
			}
			if(i==cs.length-1)
			{
				lastCol=temp[2];
			}
		}
		List<String> tempList= new ArrayList<String>();  
	    for(String i:colorValue){
	    	if(i.equals("~"))
	    	{
	    		 tempList.add(i); 
	    	}
	    	else if(!tempList.contains(i)){  
	            tempList.add(i);  
	        }  
	    } 
	    String strTemp="";
	    for(String item: tempList)
	    {
	    	strTemp=strTemp+item+";";
	    }
		String configValue=strTemp.substring(0, strTemp.toString().length()-1).replace("~;","").replace(";~","");
		String jString="{";
	    jString=jString+"\"value\":\""+configValue+"\",";
		jString=jString+"\"colorString\":\""+colorStringTemp+"\"}";
		org.json.JSONObject jb=new org.json.JSONObject(jString);
		String pathStr= vectorUtil_svg.getBufferedImageByDataList(area,list,jb,title,path,Arrays.asList(colorStringTemp.replaceAll("#", "").split(";")),Arrays.asList(strTemp.split(";")));
	    return pathStr;
	}
	
	public List<Map<String,String>> getGaode(String area,String value,String colorString) throws Exception {
		String[] infor= value.split(";");
		List<double[]> list = new ArrayList<double[]>();
		for(int i=0;i<infor.length;i++)
		{
			String[] temp=infor[i].split(",");
			double[] temp1=new double[3];
			temp1[0]=Double.parseDouble(temp[0]);
			temp1[1]=Double.parseDouble(temp[1]);
			temp1[2]=Double.parseDouble(temp[2]);
			list.add(temp1);
		}
		String[] cs= colorString.split(";");
		String colorStringTemp="";
		List<String> colorValue=new ArrayList<String>();
		for(int i=0;i<cs.length;i++)
		{
			String[] temp=cs[i].split(",");
			colorValue.add(temp[0]);
			colorValue.add(temp[1]);
			colorStringTemp=colorStringTemp+"#"+temp[2]+";";
		}
		List<String> tempList= new ArrayList<String>();  
	    for(String i:colorValue){
	    	if(i.equals("~"))
	    	{
	    		 tempList.add(i); 
	    	}
	    	else if(!tempList.contains(i)){  
	            tempList.add(i);  
	        }  
	    } 
	    String strTemp="";
	    for(String item: tempList)
	    {
	    	strTemp=strTemp+item+";";
	    }
		String configValue=strTemp.substring(0, strTemp.toString().length()-1).replace("~;","").replace(";~","");
		String jString="{";
	    jString=jString+"\"value\":\""+configValue+"\",";
		jString=jString+"\"colorString\":\""+colorStringTemp+"\"}";
		org.json.JSONObject jb=new org.json.JSONObject(jString);
		List<Map<String,String>> pathStr= vectorUtil_gaode.getBufferedImageByDataList(area,list,jb,Arrays.asList(colorStringTemp.replaceAll("#", "").split(";")),Arrays.asList(strTemp.split(";")));
	    return pathStr;
	}
}
