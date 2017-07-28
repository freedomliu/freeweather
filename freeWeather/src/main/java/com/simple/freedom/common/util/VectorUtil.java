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
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.json.JSONException;
import org.json.JSONObject;

import com.simple.freedom.common.aop.SysVariable;

import wContour.Contour;
import wContour.Interpolate;
import wContour.Legend;
import wContour.Global.Border;
import wContour.Global.LPolygon;
import wContour.Global.LegendPara;
import wContour.Global.PointD;
import wContour.Global.PolyLine;

public class VectorUtil {
	private  final VectorUtil vu = new VectorUtil();
	
	private  Map<String,double[]> areaInfoMap;
	private  Color[] color;
	private  Double _maxValue = 500.0;
	private  Double _minValue = 80.0;
	String rootPath = SysVariable.areaAbout;
	private String path = rootPath+"/areaInfo/";
	// 内部区域划线路径
	private String pathAreaLine=rootPath+"/areaLine/";

	private double[][] _discreteData;
	private List<List<PointD>> _clipLines;
	private List<List<PointD>> _provLines;
	private List<List<PointD>> _mapLines;
	private double[][] _gridData;
	private double[] _CValues;
	private List<Border> _borders;
	private List<PolyLine> _contourLines;
	private List<PolyLine> _clipContourLines;
	private List<wContour.Global.Polygon> _contourPolygons;
	private List<wContour.Global.Polygon> _clipContourPolygons;
	private List<LPolygon> _legendPolygons;
	// 用于存放配置文件的jason
	private JSONObject configFile;
	
	private BufferedImage iamge;
	
	private double[] _X;
    private double[] _Y;
    
	private final int rows = 600;
	private final int cols = 600;
	private final double _undefData = -9999.0;
	
	private double longitude_min = 70D;
	private double longitude_max = 140D;
	
	private double latitude_min = 15D;
	private double latitude_max = 55D;
	
	private double _minX = 0;
    private double _minY = 0;
    private double _maxX = 0;
    private double _maxY = 0;
    private double _scaleX = 1.0;
    private double _scaleY = 1.0;
	
    private int pointNum = 8;
    
    private int width = 1080;
	private int height = 810;
    
	private Color _startColor = Color.yellow;
    private Color _endColor = Color.blue;
    private Color fontColor=Color.green;
	


	private Color[] _colors;
    
	private boolean showTitle=false;
	private boolean showLegend=false;
	private String title=null;
	
	private VectorUtil() {
		File file = new File(path);
		if(file.exists()) {
			file.mkdirs();
		}
		
		if(color == null) {
			color = new Color[]{Color.yellow, Color.blue,Color.green};
		}
		
		if(areaInfoMap == null) {
			try {
				areaInfoMap = new HashMap<String,double[]> ();
	    		String filePath = path + "AllAreaInfo.csv";
	    		File infoFile = new File(filePath);
    		
				BufferedReader br = new BufferedReader(new FileReader(infoFile));
				
				String line = "";
				
				while((line = br.readLine()) != null) {
					//System.out.println(line);
					String[] result = line.split(",");
					
					if(result.length < 5) {
						continue;
					}
					
					double[] d = new double[]{Double.parseDouble(result[1]),Double.parseDouble(result[2]),Double.parseDouble(result[3]),Double.parseDouble(result[4])};
					
					areaInfoMap.put(result[0], d);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
    		
    	}
	}

	public String getBufferedImageByDataList(List<double[]> list,JSONObject configFile,String title,String mypath,List<String> colorList,List<String> valueList) {
			this.title=title;
			Format format = new SimpleDateFormat("yyyyMMddhhmmss");
			String picName=format.format(new Date());
			this.configFile=configFile;
		    _provLines=new ArrayList<List<PointD>>() ;
		    _clipLines= new ArrayList<List<PointD>>();
			String[] names=new String[]{"滨州"};
			for(String name :names)
			{
			    // 行政边界内部区域
				File aFile = new File(pathAreaLine +name+"_new.csv");

		        try {
		        	if(aFile.exists())
		        	{
		        		ReadMapFile_WMP1(aFile,"Polygon");
		        	}
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            clearInfor();
		        }
			}
		//色差区间的数组
		double[] values = this.loadParameter();
		
		try {
			//根据区域名称设置最大和最小经纬度
			this.setMaxAndMin_AreaInfo(names);
			for(String name :names)
			{
				//获取底图信息
				File file = new File(path + name + ".csv");
				//读取底图
				this.ReadMapFile_WMP(file);
			}
			_mapLines = new ArrayList<List<PointD>>(_clipLines);
			
			//解析数据
			this.parseListData(list);
			
			_X = new double[rows];
			_Y = new double[cols];
			
	        Interpolate.CreateGridXY_Num(70, 15, 140, 55, _X, _Y);
			_gridData = new double[rows][cols];
			_gridData = Interpolate.Interpolation_IDW_Neighbor(_discreteData, _X, _Y, 8, _undefData);
			//差值分析
			this.getInterpolation(values);
			//绘制差值分析图
			this.paintComponent(names,colorList,valueList);
			// 保存
			ImageIO.write(iamge,"PNG", new File(mypath+"/"+picName+".png"));
			System.out.println(mypath+"/"+picName+".png");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("getBufferedImageByDataList类内部");
			return "error.png";
		}
		return picName+".png";
	}
	
	private void paintComponent(String[] name,List<String> colorList,List<String> valueList) 
	{
		iamge = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 =iamge.createGraphics();
        iamge=g2.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);  
        g2.dispose();
        g2=iamge.createGraphics();
        //绘制底图
        g2.setColor(new Color(255,0,0));
        // 边框宽度
        g2.setStroke(new BasicStroke(1));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        this.drawContourPolygons(g2);
        this.drawLegend(g2,colorList,valueList);
        //this.drawLegend(g2);
        // 绘制内部线条
        this.drawProvLines(g2);
        this.darwCityName(g2);
	}

	// 清空上次查询全局变量
	private void clearInfor()
	{
		_provLines=null;
	}
	
	private double[] loadParameter() {
		
		_startColor = color[0];
		_endColor = color[1];
		fontColor=color[2];
		
		double[] doubleArr = new double[10];
		
		DecimalFormat df = new DecimalFormat("#.00");
		
		for(int i = 0; i < 10; i ++) {
			double d = (_maxValue - _minValue) / 10;
			doubleArr[i] = Double.parseDouble(df.format(_minValue + d * i));
		}
		
		doubleArr[doubleArr.length - 1] = _maxValue;
		if(configFile!=null)
		{
			String[] values;
			try {
				values = configFile.getString("value").toString().split(";");
			
    		double [] dArr=new double[values.length];
    		for(int i=0;i<values.length;i++)
    		{
    			dArr[i]=Double.valueOf(values[i]);
    		}
    		return dArr;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return doubleArr;
	}

	public static VectorUtil getInstance() {
		return new VectorUtil();
	}
	
	/**
	 * @Title: setMaxAndMin_AreaInfo 
	 * @Description: 根据站点名称,设置最大最小的经度及纬度
	 * @param @param name    参数
	 * @return void    返回类型 
	 * @throws 
	 * @author asus
	 */
	private void setMaxAndMin_AreaInfo(String[] names) {
		 //  针对滨州接口
		longitude_min=116.7;
		longitude_max=118.74;
		latitude_min=36.6289800002269;
		latitude_max=38.419;
	}

	private void parseListData(List<double[]> list) {
		
		int length = list.size();
		
		this.setPointNum(length);
		
		_discreteData = new double[3][list.size()];
		for(int i = 0; i < length; i ++) {
			double[] d = list.get(i);
			/*if(d[3]==999.9)
				continue;*/
        	_discreteData[0][i] = d[0];
        	_discreteData[1][i] = d[1];
        	_discreteData[2][i] = d[2];//(d[3]+"").contains("999")?0:d[2];
        	System.out.println(d[2]);
        }
	}
	
	private void setPointNum(int length) {
		if(length > 0 && length < 500) {
			this.pointNum = 8;
		}
		else if(length < 1000) {
			this.pointNum = 6;
		}
		else if(length < 2000) {
			this.pointNum = 5;
		}
		else if(length < 4000) {
			this.pointNum = 4;
		}
		else if(length >= 4000) {
			this.pointNum = 3;
		}
	}

	//读取指定地图
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
	
	        //Read shape type
	        shapeType = br.readLine().trim();
	        //Read shape number
	        shapeNum = Integer.parseInt(br.readLine());
	        //_clipLines = new ArrayList<List<PointD>>();
	        if (shapeType.equals("Polygon")) {
	            for (int s = 0; s < shapeNum; s++) {
	            	String fNum=br.readLine();
	            	if(fNum==null)
	            		continue;
	                pNum = Integer.parseInt(fNum);
	                List<PointD> cLine = new ArrayList<PointD>();
	                for (i = 0; i < pNum; i++) {
	                    aLine = br.readLine();
	                    if(aLine==null)
	                    	continue;
	                    try{
	                    dataArray = aLine.split(",");
	                    aPoint = new PointD();
	                    aPoint.X = Double.parseDouble(dataArray[0]);
	                    aPoint.Y = Double.parseDouble(dataArray[1]);
	                    cLine.add(aPoint);
	                    }
	                    catch(Exception e)
	                    {
	                    	System.out.println(aLine+";"+pNum+";"+shapeNum);
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
	public void ReadMapFile_WMP1(File aFile,String type) throws FileNotFoundException, IOException {
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
            	if(temp==null){
            		continue;
            	}
            	//System.out.println(temp);
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
	/**
	 * @Title: getInterpolation 
	 * @Description: 计算差值
	 * @param @param values    参数
	 * @return void    返回类型 
	 * @throws 
	 * @author asus
	 */
	private void getInterpolation(double[] values) {
		
		this.SetContourValues(values);
        //描绘多边形底线
        this.TracingContourLines();
        //平滑线
        this.SmoothLines();
        //剪切线,多边形的边界线
        this.ClipLines();
        //描绘多边形
        this.TracingPolygons();
        //剪切多边形每一块区域
        this.ClipPolygons();
        //设置比例
        this.SetCoordinate(longitude_min, longitude_max, latitude_min, latitude_max);
        this.CreateLegend();
     
	}
	
	/**
	 * @Title: SetContourValues 
	 * @Description: 给颜色区间数组赋值
	 * @param @param values    参数
	 * @return void    返回类型 
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
        _contourLines = Contour.tracingContourLines(_gridData, _X, _Y, nc, _CValues, _undefData, _borders, S1);
    }

    public void SmoothLines() {
        _contourLines = Contour.smoothLines(_contourLines);
    }
	
    public void ClipLines() {
        _clipContourLines = new ArrayList<PolyLine>();
        for (List< PointD> cLine : _clipLines) {
            _clipContourLines.addAll(Contour.clipPolylines(_contourLines, cLine));
        }
    }
	
    public void TracingPolygons() {
        int nc = _CValues.length;
        //---- Colors
        CreateColors(_startColor, _endColor, nc + 1);

        _contourPolygons = Contour.tracingPolygons(_gridData, _contourLines, _borders, _CValues);
    }
	
	public void ClipPolygons() {
        _clipContourPolygons = new ArrayList<wContour.Global.Polygon>();

        int i = 2;
        
        for (List<PointD> cLine : _clipLines) {
        	i = cLine.size() + ++i;
        	
        	List <wContour.Global.Polygon> list = Contour.clipPolygons(_contourPolygons, cLine);
            _clipContourPolygons.addAll(list);
        }
    }
	
	public void createColors(){
			CreateColors(_startColor, _endColor, _CValues.length + 1);
    }
	
	public void CreateColors(Color sColor, Color eColor, int cNum) {
		if(configFile!=null)
		{
			String[] values;
			try {
					values = configFile.getString("colorString").toString().replace("#","").trim().split(";");
				_colors=new Color[values.length];
				for(int i=0;i<values.length;i++)
				{
					_colors[i]=new Color(Integer.parseInt(values[i],16));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
	        _colors = new Color[cNum];
	        int sR = 0;
	        int sG = 0;
	        int sB = 0;
	        int eR = 0;
	        int eG = 0;
	        int eB = 0;
	        int rStep = 0;
	        int gStep = 0;
	        int bStep = 0;
	        int i = 0;
	
	        sR = sColor.getRed();
	        sG = sColor.getGreen();
	        sB = sColor.getBlue();
	        eR = eColor.getRed();
	        eG = eColor.getGreen();
	        eB = eColor.getBlue();
	        rStep = (int) ((eR - sR) / cNum);
	        gStep = (int) ((eG - sG) / cNum);
	        bStep = (int) ((eB - sB) / cNum);
	        for (i = 0; i < _colors.length; i++) {
	            int r = sR + i * rStep;
	            int g = sG + i * gStep;
	            int b = sB + i * bStep;
	            _colors[i] = new Color(r, g, b);
	        }
	        // 上边其实结束颜色并不存在，替换最后一个颜色为结束颜色
	        // 起始颜色和结束颜色 在图像中其实并不存在  因为图像中的点并不在这个颜色区间中 故改为起始第二个区间 倒数第二个区间 与起始区间和结束区间颜色一致
	        _colors[1]=sColor;
	        _colors[cNum-1]=eColor;
	        _colors[cNum-2]=eColor;
		}
    }
	
	public void SetCoordinate() {
        this.SetCoordinate(-10, this.width, 0, this.height);
    }
	
	public void CreateLegend() {
        PointD aPoint = new PointD();

        double width = _maxX - _minX;
        aPoint.X = _minX + width / 4;
        aPoint.Y = _minY + width / 100;
        LegendPara lPara = new LegendPara();
        lPara.startPoint = aPoint;
        lPara.isTriangle = true;	//lPara.isTriangle = true;
        lPara.isVertical = false;
        lPara.length = width / 2;
        lPara.width = width / 100;
        lPara.contourValues = _CValues;
        
        _legendPolygons = Legend.CreateLegend(lPara);
    }
	
	private void SetCoordinate(double minX, double maxX, double minY, double maxY) {
        _minX = minX;
        _maxX = maxX;
        _minY = minY;
        _maxY = maxY;
        _scaleX = (this.width - 10) / (_maxX - _minX);
        _scaleY = (this.height - 10 ) / (_maxY - _minY);
    }
	
	private void drawContourPolygons(Graphics2D g) {
        List<wContour.Global.Polygon> drawPolygons = _clipContourPolygons;
        //颜色区间范围
        List<String> values = new ArrayList<String>();
        for (double v : _CValues) {
            values.add(String.valueOf(v));
        }
        for (int i = 0; i < drawPolygons.size(); i++) {
            wContour.Global.Polygon aPolygon = drawPolygons.get(i);
            drawPolygon(g, aPolygon, values, false);
        }
        
    }
	// 就是这里
	private void drawPolygon(Graphics2D g, wContour.Global.Polygon aPolygon, List<String> values, boolean isHighlight) {
        PointD aPoint;
        String aValue = String.valueOf(aPolygon.LowValue);
        int idx = values.indexOf(aValue) + 1;
        Color aColor = Color.black;
        if (isHighlight) {
            aColor = Color.green;
        } else {
            if (aPolygon.IsHighCenter) {
            	aColor = _colors[idx];
                for (int j = 1; j < _colors.length; j++) {
                    if (aColor.getRGB() == _colors[j].getRGB()) {
                        aColor = _colors[j - 1];
                    }
                }
            }
        }

        int len = aPolygon.OutLine.PointList.size();
        GeneralPath drawPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, len);
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
        //g.setColor(bColor);
        g.draw(drawPolygon);
    }
	
	private int[] ToScreen(double pX, double pY) {
        int sX = (int) ((pX - _minX) * _scaleX);
        int sY = (int) ((_maxY - pY) * _scaleY);

        int[] sxy = {sX, sY};
        return sxy;
    }
	
	private void drawLegend(Graphics2D g) {
        if (_legendPolygons.size() > 0) {
            LPolygon aLPolygon;
            int i, j;
            List<Double> values = new ArrayList<Double>();
            for (double v : _CValues) {
                values.add(v);
            }
            PointD aPoint;
            for (i = 0; i < _legendPolygons.size(); i++) {
                aLPolygon = _legendPolygons.get(i);
                double aValue = aLPolygon.value;
                int idx = values.indexOf(aValue) + 1;
                Color aColor;
                if (aLPolygon.isFirst) {
                    aColor = _colors[0];
                } else {
                    aColor = _colors[idx];
                }
                List<PointD> newPList = aLPolygon.pointList;

                int len = newPList.size();
                GeneralPath drawPolygon = new GeneralPath(GeneralPath.WIND_EVEN_ODD, len);
                int dx = 0, dy = 0;
                for (j = 0; j < len; j++) {
                    aPoint = newPList.get(j);
                    int[] sxy = ToScreen(aPoint.X, aPoint.Y);
                    if (j == 0) {
                        drawPolygon.moveTo(sxy[0], sxy[1]);
                    } else {
                        drawPolygon.lineTo(sxy[0], sxy[1]);
                        if (j == 2) {
                            dx = sxy[0];
                            dy = sxy[1];
                        }
                    }
                }
                drawPolygon.closePath();

                g.setColor(aColor);
                g.fill(drawPolygon);
                
                g.setColor(Color.black);
                g.draw(drawPolygon);

                
                if (i < _legendPolygons.size() - 1) {
                    g.drawString(String.valueOf(_CValues[i]), dx - 10, dy - 10);
                }
            }
            
        }
    }
	
	private void drawLegend(Graphics2D g,List<String> listColor,List<String> listValue) 
	{
		int j=0;
		for(int i=listColor.size()-1;i>-1;i--)
		{
			 j++;
			 Rectangle2D rectangle = new Rectangle2D.Float(100, height-30-30*j, 10, 30);
		     Color color =  new Color(Integer.parseInt(listColor.get(i), 16)) ; 
			 g.setColor(color);
		     g.fill(rectangle); 
		     
		     // 第一次循环
		     if(i==listColor.size()-1)
		     {
		    	 Font font=new Font("宋体",Font.BOLD,15);
			     g.setColor(Color.BLACK);
			     g.setFont(font);
			     if(!listValue.get(i+1).equals("~"))
			     {
			    	 g.drawString(listValue.get(i+1),110,height-30*j);
			     }
			     g.drawString(listValue.get(i),110,height-30-30*j);
		     }
		     else if(i==0)// 最后一次循环
		     {
		    	 if(!listValue.get(i).equals("~"))
		    	 {
			    	 Font font=new Font("宋体",Font.BOLD,15);
				     g.setColor(Color.BLACK);
				     g.setFont(font);
			    	 g.drawString(listValue.get(i),110,height-30-30*j);
		    	 }
		     }
		     else
		     {
		    	 Font font=new Font("宋体",Font.BOLD,15);
			     g.setColor(Color.BLACK);
			     g.setFont(font);
		    	 g.drawString(listValue.get(i),110,height-30-30*j);
		     }
		}
	}
	
	// 添加区域内部城市名称
	private void darwCityName(Graphics2D g)
	{
		Font font=new Font("宋体",Font.BOLD,30);
        g.setColor(Color.BLACK);
        g.setFont(font);
        int wd= (width-title.length())/2;
        g.drawString(title,wd,height/16);
        int[]point= ToScreen(Double.valueOf(118.12),Double.valueOf(37.12));
        g.drawString("博兴县",point[0],point[1]);
        point= ToScreen(Double.valueOf(117.48),Double.valueOf(37.37));
        g.drawString("惠民县",point[0],point[1]);
        point= ToScreen(Double.valueOf(117.68),Double.valueOf(37.93));
        g.drawString("无棣县",point[0],point[1]);
        point= ToScreen(Double.valueOf(118.04),Double.valueOf(37.70));
        g.drawString("沾化县",point[0],point[1]);
        point= ToScreen(Double.valueOf(117.90),Double.valueOf(37.38));
        g.drawString("滨州市",point[0],point[1]);
        point= ToScreen(Double.valueOf(117.65),Double.valueOf(36.89));
        g.drawString("邹平县",point[0],point[1]);
        point= ToScreen(Double.valueOf(117.55),Double.valueOf(37.61));
        g.drawString("阳信县",point[0],point[1]);
	}
	
	//绘制内部区域
	private void drawProvLines(Graphics2D g) {
        PointD aPoint;
        if(_provLines!=null)
        {
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
