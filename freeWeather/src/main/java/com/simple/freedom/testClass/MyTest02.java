package com.simple.freedom.testClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;

public class MyTest02 {
	public static void main(String[] args) throws Exception {
		Double xMax=0d;
		Double xMin=100d;
		Double yMax=0d;
		Double yMin=100d;
		
		
		StringBuffer strSvg=null;
		strSvg=new StringBuffer("<svg width=\""+(135.09567-73.502355)+"\" height=\""+(53.563269-7.320122)+"\" xmlns=\"http://www.w3.org/2000/svg\"><g>");
		File aFile = new File("C:/fsConfig/areaGaode/中国_all.csv");
		BufferedReader br = new BufferedReader(new FileReader(aFile));
		String shapeType; shapeType = br.readLine().trim();
		int shapeNum = Integer.parseInt(br.readLine());
		int i, pNum;
		String aLine;
		String[] dataArray;
		if (shapeType.equals("Polygon")) {
			for (int s = 0; s < shapeNum; s++) {
				String temp = br.readLine();
				if (temp == null) {
					continue;
				}
				pNum = Integer.parseInt(temp);
				String tempStr="";
				for (i = 0; i < pNum; i++) {
					aLine = br.readLine();
					dataArray = aLine.split(",");
					Double X = Double.parseDouble(dataArray[0])-73.502355;
					Double Y = Double.parseDouble(dataArray[1])-7.320122;
					tempStr+=(X+","+Y +",");
				}
				strSvg.append("<polygon stroke=\"#1C86EE\" stroke-width=\"1\" stroke-linejoin=\"round\" points=\""+tempStr.substring(0, tempStr.length()-1)+"\" fill=\"none\" />" );
			}
		}
		strSvg.append("</g></svg>");
		br.close();
		File file = new File("D:/test.svg");
        PrintStream ps = new PrintStream(new FileOutputStream(file));
        ps.println(strSvg.toString());
        ps.close();
	}
}
