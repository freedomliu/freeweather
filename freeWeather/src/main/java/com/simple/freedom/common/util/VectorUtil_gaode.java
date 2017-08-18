package com.simple.freedom.common.util;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wContour.Contour;
import wContour.Interpolate;
import wContour.Global.Border;
import wContour.Global.PointD;
import wContour.Global.PolyLine;

import com.simple.freedom.common.aop.SysVariable;
import com.simple.freedom.dao.IAreaSizeBeansMapper;

@Service
public class VectorUtil_gaode {
	String rootPath = SysVariable.areaAbout;
	private double[][] _discreteData;
	private List<List<PointD>> _clipLines;
	private double[][] _gridData;
	private double[] _CValues;
	private List<Border> _borders;
	private List<PolyLine> _contourLines;
	private List<PolyLine> _clipContourLines;
	private List<wContour.Global.Polygon> _contourPolygons;
	private List<wContour.Global.Polygon> _clipContourPolygons;
	// 用于存放配置文件的jason
	private JSONObject configFile;

	private List<Map<String,String>> result;
	
	private double[] _X;
	private double[] _Y;

	private final int rows = 800;
	private final int cols = 800;
	private final double _undefData = -9999.0;

	private Color[] _colors;

	@Autowired
	IAreaSizeBeansMapper areaSizeBeansMapper;
	
	public  List<Map<String,String>> getBufferedImageByDataList(String area,List<double[]> list,
			JSONObject configFile,
			List<String> colorList, List<String> valueList) {
		result=new ArrayList<Map<String,String>>();
		this.configFile = configFile;
		_clipLines = new ArrayList<List<PointD>>();
		String[] names = new String[] {area};
		// 色差区间的数组
		double[] values = this.loadParameter();
		// 获取底图信息
		File file = new File("C:/fsConfig/areaGaode/" + area + "_lunkuo.csv");
		// 读取底图
		this.ReadMapFile_WMP(file);
		// 解析数据
		this.parseListData(list);

		_X = new double[rows];
		_Y = new double[cols];

		Interpolate.CreateGridXY_Num(70, 15, 140, 55, _X, _Y);
		_gridData = new double[rows][cols];
		_gridData = Interpolate.Interpolation_IDW_Neighbor(_discreteData,
				_X, _Y, 8, _undefData);
		// 差值分析
		this.getInterpolation(values);
		// 绘制差值分析图
		this.paintComponent(names, colorList, valueList);
		return result;
	}

	private void paintComponent(String[] name, List<String> colorList,
			List<String> valueList) {
		this.drawContourPolygons();
	}

	private double[] loadParameter() {
		String[] values;
		try {
			values = configFile.getString("value").toString().split(";");
			double[] dArr = new double[values.length+1];
			for (int i = 0; i < values.length; i++) {
				dArr[i] = Double.valueOf(values[i]);
			}
			dArr[values.length]=dArr[values.length-1]+1;
			return dArr;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	public static VectorUtil_gaode getInstance() {
		return new VectorUtil_gaode();
	}

	private void parseListData(List<double[]> list) {

		int length = list.size();

		_discreteData = new double[3][list.size()];
		
		for (int i = 0; i < length; i++) {
			double[] d = list.get(i);
			_discreteData[0][i] = d[0];
			_discreteData[1][i] = d[1];
			_discreteData[2][i] = d[2];
		}
	}

	// 读取指定地图
	private void ReadMapFile_WMP(File file) {

		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(file));

			String aLine;
			String shapeType;
			String[] dataArray;
			int shapeNum;
			int i, pNum;
			PointD aPoint;

			// Read shape type
			shapeType = br.readLine().trim();
			// Read shape number
			shapeNum = Integer.parseInt(br.readLine());
			if (shapeType.equals("Polygon")) {
				for (int s = 0; s < shapeNum; s++) {
					String fNum = br.readLine();
					if (fNum == null)
						continue;
					pNum = Integer.parseInt(fNum);
					List<PointD> cLine = new ArrayList<PointD>();
					for (i = 0; i < pNum; i++) {
						aLine = br.readLine();
						if (aLine == null)
							continue;
						try {
							dataArray = aLine.split(",");
							aPoint = new PointD();
							aPoint.X = Double.parseDouble(dataArray[0]);
							aPoint.Y = Double.parseDouble(dataArray[1]);
							cLine.add(aPoint);
						} catch (Exception e) {
							System.out.println(aLine + ";" + pNum + ";"
									+ shapeNum);
							System.out.println(pNum);
							System.out.println(i);
						}

					}
					_clipLines.add(cLine);
				}
			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: getInterpolation
	 * @Description: 计算差值
	 * @param @param values 参数
	 * @return void 返回类型
	 * @throws
	 * @author asus
	 */
	private void getInterpolation(double[] values) {
		this.SetContourValues(values);
		// 描绘多边形底线
		this.TracingContourLines();
		// 平滑线
		this.SmoothLines();
		// 剪切线,多边形的边界线
		this.ClipLines();
		// 描绘多边形
		this.TracingPolygons();
		// 剪切多边形每一块区域
		this.ClipPolygons();
	}

	/**
	 * @Title: SetContourValues
	 * @Description: 给颜色区间数组赋值
	 * @param @param values 参数
	 * @return void 返回类型
	 * @throws
	 * @author asus
	 */
	public void SetContourValues(double[] values) {
		_CValues = values;
	}

	public void TracingContourLines() {
		int nc = _CValues.length;
		int[][] S1 = new int[_gridData.length][_gridData[0].length];
		_borders = Contour.tracingBorders(_gridData, _X, _Y, S1, _undefData);
		_contourLines = Contour.tracingContourLines(_gridData, _X, _Y, nc,
				_CValues, _undefData, _borders, S1);
	}

	public void SmoothLines() {
		_contourLines = Contour.smoothLines(_contourLines);
	}

	public void ClipLines() {
		_clipContourLines = new ArrayList<PolyLine>();
		for (List<PointD> cLine : _clipLines) {
			_clipContourLines.addAll(Contour
					.clipPolylines(_contourLines, cLine));
		}
	}

	public void TracingPolygons() {
		CreateColors();
		_contourPolygons = Contour.tracingPolygons(_gridData, _contourLines,
				_borders, _CValues);
	}

	public void ClipPolygons() {
		_clipContourPolygons = new ArrayList<wContour.Global.Polygon>();
		for (List<PointD> cLine : _clipLines) {
			List<wContour.Global.Polygon> list = Contour.clipPolygons(
					_contourPolygons, cLine);
			_clipContourPolygons.addAll(list);
		}
	}

	public void CreateColors() {
		String[] values;
		try {
			values = configFile.getString("colorString").toString()
					.replace("#", "").trim().split(";");
			_colors = new Color[values.length];
			for (int i = 0; i < values.length; i++) {
				_colors[i] = new Color(Integer.parseInt(values[i], 16));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void drawContourPolygons() {
		List<wContour.Global.Polygon> drawPolygons = _clipContourPolygons;
		// 颜色区间范围
		List<String> values = new ArrayList<String>();
		for (double v : _CValues) {
			values.add(String.valueOf(v));
		}
		for (int i = 0; i < drawPolygons.size(); i++) {
			wContour.Global.Polygon aPolygon = drawPolygons.get(i);
			drawPolygon(aPolygon, values, false);
		}

	}

	private void drawPolygon(wContour.Global.Polygon aPolygon,
			List<String> values, boolean isHighlight) {
		PointD aPoint;
		String aValue = String.valueOf(aPolygon.HighValue);
		
		int idx = values.indexOf(aValue);
		Color aColor = _colors[idx];
		int len = aPolygon.OutLine.PointList.size();

		StringBuilder wai=new StringBuilder();
		for (int j = 0; j < len; j++) {
			aPoint = aPolygon.OutLine.PointList.get(j);
			wai.append(aPoint.X+", "+aPoint.Y+";");
			//System.out.println("polygonArr.push(["+aPoint.X+", "+aPoint.Y+"]);");
		}
		Map<String,String> waiMap=new HashMap<String, String>();
		waiMap.put("outer", wai.toString());
		StringBuilder nei=new StringBuilder();
		if (aPolygon.HasHoles()) {
			for (int h = 0; h < aPolygon.HoleLines.size(); h++) {
				List<PointD> newPList = aPolygon.HoleLines.get(h).PointList;
				for (int j = 0; j < newPList.size(); j++) {
					aPoint = newPList.get(j);
					nei.append(aPoint.X+", "+aPoint.Y+";");
					//System.out.println("polygonArr.push(["+aPoint.X+", "+aPoint.Y+"]);");
				}
			}
		}
		waiMap.put("inner", nei==null?null:nei.toString());
		waiMap.put("color", toHexFromColor(aColor));
		result.add(waiMap);
	}
	
	 private String toHexFromColor(Color color){  
	        String r,g,b;  
	        StringBuilder su = new StringBuilder();  
	        r = Integer.toHexString(color.getRed());  
	        g = Integer.toHexString(color.getGreen());  
	        b = Integer.toHexString(color.getBlue());  
	        r = r.length() == 1 ? "0" + r : r;  
	        g = g.length() ==1 ? "0" +g : g;  
	        b = b.length() == 1 ? "0" + b : b;  
	        r = r.toUpperCase();  
	        g = g.toUpperCase();  
	        b = b.toUpperCase();  
	        su.append("#");  
	        su.append(r);  
	        su.append(g);  
	        su.append(b);  
	        //0xFF0000FF  
	        return su.toString();  
	    } 
}
