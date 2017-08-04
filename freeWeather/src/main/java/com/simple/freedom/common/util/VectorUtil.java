package com.simple.freedom.common.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.imageio.ImageIO;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wContour.Contour;
import wContour.Interpolate;
import wContour.Global.Border;
import wContour.Global.PointD;
import wContour.Global.PolyLine;

import com.simple.freedom.beans.AreaSizeBean;
import com.simple.freedom.common.aop.SysVariable;
import com.simple.freedom.dao.IAreaSizeBeansMapper;

@Service
public class VectorUtil {
	String rootPath = SysVariable.areaAbout;
	private String path = rootPath + "/areaInfo/";
	// 内部区域划线路径
	private String pathAreaLine = rootPath + "/areaLine/";

	private double[][] _discreteData;
	private List<List<PointD>> _clipLines;
	private List<List<PointD>> _provLines;
	//private List<List<PointD>> _mapLines;
	private double[][] _gridData;
	private double[] _CValues;
	private List<Border> _borders;
	private List<PolyLine> _contourLines;
	private List<PolyLine> _clipContourLines;
	private List<wContour.Global.Polygon> _contourPolygons;
	private List<wContour.Global.Polygon> _clipContourPolygons;
	// 用于存放配置文件的jason
	private JSONObject configFile;

	private BufferedImage iamge;

	private double[] _X;
	private double[] _Y;

	private final int rows = 800;
	private final int cols = 800;
	private final double _undefData = -9999.0;

	private double longitude_min;
	private double longitude_max;

	private double latitude_min;
	private double latitude_max;

	private double _minX = 0;
	private double _minY = 0;
	private double _maxX = 0;
	private double _maxY = 0;
	private double _scaleX = 1.0;
	private double _scaleY = 1.0;

	private int width = 1080;
	private int height = 810;

	private Color[] _colors;

	private boolean showLegend = false;
	private String title = null;

	@Autowired
	IAreaSizeBeansMapper areaSizeBeansMapper;
	
	public String getBufferedImageByDataList(String area,List<double[]> list,
			JSONObject configFile, String title, String mypath,
			List<String> colorList, List<String> valueList) {
		this.title = title;
		Format format = new SimpleDateFormat("yyyyMMddhhmmss");
		String picName = format.format(new Date());
		this.configFile = configFile;
		_provLines = new ArrayList<List<PointD>>();
		_clipLines = new ArrayList<List<PointD>>();
		String[] names = new String[] {area};
		for (String name : names) {
			// 行政边界内部区域
			File aFile = new File(pathAreaLine + name + "_new.csv");

			try {
				if (aFile.exists()) {
					ReadMapFile_WMP1(area,aFile, "Polygon");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				//clearInfor();
			}
		}
		// 色差区间的数组
		double[] values = this.loadParameter();

		try {
			for (String name : names) {
				// 获取底图信息
				File file = new File(path + name + ".csv");
				// 读取底图
				this.ReadMapFile_WMP(file);
			}
			//_mapLines = new ArrayList<List<PointD>>(_clipLines);

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
			// 保存
			ImageIO.write(iamge, "PNG", new File(mypath + "/" + picName
					+ ".png"));
			System.out.println(mypath + "/" + picName + ".png");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getBufferedImageByDataList类内部");
			return "error.png";
		}
		return picName + ".png";
	}

	private void paintComponent(String[] name, List<String> colorList,
			List<String> valueList) {
		iamge = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = iamge.createGraphics();
		iamge = g2.getDeviceConfiguration().createCompatibleImage(width,
				height, Transparency.TRANSLUCENT);
		g2.dispose();
		g2 = iamge.createGraphics();
		// 绘制底图
		g2.setColor(new Color(255, 0, 0));
		// 边框宽度
		g2.setStroke(new BasicStroke(1));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		this.drawContourPolygons(g2);
		this.drawLegend(g2, colorList, valueList);
		// 绘制内部线条
		this.drawProvLines(g2);
		this.darwCityName(g2);
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

	public static VectorUtil getInstance() {
		return new VectorUtil();
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
			// _clipLines = new ArrayList<List<PointD>>();
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

	public void ReadMapFile_WMP1(String areaName,File aFile, String type)
			throws FileNotFoundException, IOException {
		
		AreaSizeBean area= areaSizeBeansMapper.selectByPrimaryKey(areaName);
		if(area!=null)
		{
			longitude_min = area.getLongitudeMin()-0.3;
			longitude_max = area.getLongitudeMax();
			latitude_min = area.getLatitudeMin();
			latitude_max = area.getLatitudeMax()+0.2;
			
			BufferedReader br = new BufferedReader(new FileReader(aFile));
			String aLine;
			String shapeType;
			String[] dataArray;
			int shapeNum;
			int i, pNum;
			PointD aPoint;

			shapeType = br.readLine().trim();
			shapeNum = Integer.parseInt(br.readLine());
			if (shapeType.equals(type)) {
				for (int s = 0; s < shapeNum; s++) {
					String temp = br.readLine();
					if (temp == null) {
						continue;
					}
					pNum = Integer.parseInt(temp);
					List<PointD> cLine = new ArrayList<PointD>();
					for (i = 0; i < pNum; i++) {
						aLine = br.readLine();
						dataArray = aLine.split(",");
						aPoint = new PointD();
						aPoint.X = Double.parseDouble(dataArray[0]);
						aPoint.Y = Double.parseDouble(dataArray[1]);
						cLine.add(aPoint);
					}
					_provLines.add(cLine);
				}
			}

			br.close();
		}
		else
		{
			BufferedReader br = new BufferedReader(new FileReader(aFile));
			String aLine;
			String shapeType;
			String[] dataArray;
			int shapeNum;
			int i, pNum;
			PointD aPoint;

			shapeType = br.readLine().trim();
			shapeNum = Integer.parseInt(br.readLine());
			if (shapeType.equals(type)) {
				for (int s = 0; s < shapeNum; s++) {
					String temp = br.readLine();
					if (temp == null) {
						continue;
					}
					pNum = Integer.parseInt(temp);
					List<PointD> cLine = new ArrayList<PointD>();
					for (i = 0; i < pNum; i++) {
						aLine = br.readLine();
						dataArray = aLine.split(",");
						aPoint = new PointD();
						aPoint.X = Double.parseDouble(dataArray[0]);
						aPoint.Y = Double.parseDouble(dataArray[1]);
						if(s==0&&i==0)
						{
							longitude_max=longitude_min = aPoint.X;
							latitude_max=latitude_min =aPoint.Y ;
						}
						if(aPoint.X<longitude_min)
						{
							longitude_min=aPoint.X;
						}
						if(aPoint.X>longitude_max)
						{
							longitude_max=aPoint.X;
						}
						if(aPoint.Y<latitude_min)
						{
							latitude_min=aPoint.Y;
						}
						if(aPoint.Y>latitude_max)
						{
							latitude_max=aPoint.Y;
						}
						cLine.add(aPoint);
					}
					_provLines.add(cLine);
				}
			}
			AreaSizeBean areaSize=new AreaSizeBean();
			areaSize.setAreaName(areaName);
			areaSize.setLatitudeMax(latitude_max);
			areaSize.setLatitudeMin(latitude_min);
			areaSize.setLongitudeMax(longitude_max);
			areaSize.setLongitudeMin(longitude_min);
			areaSizeBeansMapper.insertSelective(areaSize);
			br.close();	
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
		// 设置比例
		this.SetCoordinate(longitude_min, longitude_max, latitude_min,
				latitude_max);
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

		int i = 2;

		for (List<PointD> cLine : _clipLines) {
			i = cLine.size() + ++i;

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

	public void SetCoordinate() {
		this.SetCoordinate(-10, this.width, 0, this.height);
	}

	private void SetCoordinate(double minX, double maxX, double minY,
			double maxY) {
		_minX = minX;
		_maxX = maxX;
		_minY = minY;
		_maxY = maxY;
		_scaleX = (this.width - 10) / (_maxX - _minX);
		_scaleY = (this.height - 10) / (_maxY - _minY);
	}

	private void drawContourPolygons(Graphics2D g) {
		List<wContour.Global.Polygon> drawPolygons = _clipContourPolygons;
		// 颜色区间范围
		List<String> values = new ArrayList<String>();
		for (double v : _CValues) {
			values.add(String.valueOf(v));
		}
		for (int i = 0; i < drawPolygons.size(); i++) {
			wContour.Global.Polygon aPolygon = drawPolygons.get(i);
			drawPolygon(g, aPolygon, values, false);
		}

	}

	private void drawPolygon(Graphics2D g, wContour.Global.Polygon aPolygon,
			List<String> values, boolean isHighlight) {
		PointD aPoint;
		String aValue = String.valueOf(aPolygon.HighValue);
		
		int idx = values.indexOf(aValue);
		Color aColor = _colors[idx];
		int len = aPolygon.OutLine.PointList.size();
		GeneralPath drawPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD,
				len);
		for (int j = 0; j < len; j++) {
			aPoint = aPolygon.OutLine.PointList.get(j);
			int[] sxy = ToScreen(aPoint.X, aPoint.Y);
			if (j == 0) {
				drawPolygon.moveTo(sxy[0], sxy[1]);
			} else {
				drawPolygon.lineTo(sxy[0], sxy[1]);
			}
		}

		if (aPolygon.HasHoles()) {
			for (int h = 0; h < aPolygon.HoleLines.size(); h++) {
				List<PointD> newPList = aPolygon.HoleLines.get(h).PointList;
				for (int j = 0; j < newPList.size(); j++) {
					aPoint = newPList.get(j);
					int[] sxy = ToScreen(aPoint.X, aPoint.Y);
					if (j == 0) {
						drawPolygon.moveTo(sxy[0], sxy[1]);
					} else {
						drawPolygon.lineTo(sxy[0], sxy[1]);
					}
				}
			}
		}
		drawPolygon.closePath();
		g.setColor(aColor);
		g.fill(drawPolygon);
		// 取消下边注释 则添加不用颜色边界线
		// g.setColor(bColor);
		g.draw(drawPolygon);
	}

	private int[] ToScreen(double pX, double pY) {
		int sX = (int) ((pX - _minX) * _scaleX);
		int sY = (int) ((_maxY - pY) * _scaleY);

		int[] sxy = { sX, sY };
		return sxy;
	}

	private void drawLegend(Graphics2D g, List<String> listColor,
			List<String> listValue) {
		int j = 0;
		for (int i = listColor.size() - 1; i > -1; i--) {
			j++;
			Rectangle2D rectangle = new Rectangle2D.Float(2, height - 30 - 30
					* j, 10, 30);
			Color color = new Color(Integer.parseInt(listColor.get(i), 16));
			g.setColor(color);
			g.fill(rectangle);

			// 第一次循环
			if (i == listColor.size() - 1) {
				Font font = new Font("宋体", Font.BOLD, 15);
				g.setColor(Color.BLACK);
				g.setFont(font);
				if (!listValue.get(i + 1).equals("~")) {
					g.drawString(listValue.get(i + 1), 110, height - 30 * j);
				}
				g.drawString(listValue.get(i), 10, height - 30 - 30 * j);
			} else if (i == 0)// 最后一次循环
			{
				if (!listValue.get(i).equals("~")) {
					Font font = new Font("宋体", Font.BOLD, 15);
					g.setColor(Color.BLACK);
					g.setFont(font);
					g.drawString(listValue.get(i), 10, height - 30 - 30 * j);
				}
			} else {
				Font font = new Font("宋体", Font.BOLD, 15);
				g.setColor(Color.BLACK);
				g.setFont(font);
				g.drawString(listValue.get(i), 10, height - 30 - 30 * j);
			}
		}
	}

	// 添加区域内部城市名称
	private void darwCityName(Graphics2D g) {
		Font font = new Font("宋体", Font.BOLD, 30);
		g.setColor(Color.BLACK);
		g.setFont(font);
		int wd = (width - title.length()) / 2;
		g.drawString(title, wd, 31);
		
		
		/* 画内部点
		font = new Font("宋体", Font.BOLD, 10);
		g.setFont(font);
		for(int i=0;i<_discreteData[0].length;i++)
		{
			int[] point = ToScreen(Double.valueOf(_discreteData[0][i]), Double.valueOf(_discreteData[1][i]));
			g.drawString(_discreteData[2][i]+"", point[0], point[1]);
		}*/
		
		
		/*int[] point = ToScreen(Double.valueOf(118.12), Double.valueOf(37.12));
		g.drawString("博兴县", point[0], point[1]);
		point = ToScreen(Double.valueOf(117.48), Double.valueOf(37.37));
		g.drawString("惠民县", point[0], point[1]);
		point = ToScreen(Double.valueOf(117.68), Double.valueOf(37.93));
		g.drawString("无棣县", point[0], point[1]);
		point = ToScreen(Double.valueOf(118.04), Double.valueOf(37.70));
		g.drawString("沾化县", point[0], point[1]);
		point = ToScreen(Double.valueOf(117.90), Double.valueOf(37.38));
		g.drawString("滨州市", point[0], point[1]);
		point = ToScreen(Double.valueOf(117.65), Double.valueOf(36.89));
		g.drawString("邹平县", point[0], point[1]);
		point = ToScreen(Double.valueOf(117.55), Double.valueOf(37.61));
		g.drawString("阳信县", point[0], point[1]);*/
	}

	// 绘制内部区域
	private void drawProvLines(Graphics2D g) {
		PointD aPoint;
		if (_provLines != null) {
			for (List<PointD> cLine : _provLines) {

				int len = cLine.size();
				int[] xPoints = new int[len];
				int[] yPoints = new int[len];
				for (int j = 0; j < len; j++) {
					aPoint = cLine.get(j);
					int[] sxy = ToScreen(aPoint.X, aPoint.Y);
					xPoints[j] = sxy[0];
					yPoints[j] = sxy[1];
				}
				g.setColor(Color.black);
				g.drawPolyline(xPoints, yPoints, len);

			}
		}
	}
}
