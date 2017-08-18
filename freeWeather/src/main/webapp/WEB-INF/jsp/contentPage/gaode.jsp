<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="chrome=1">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <style type="text/css">
      body,html,#container{
        height: 100%;
        margin: 0px;
      }
    .amap-logo {
            display: none;
     } 
   .amap-copyright {
          bottom:-100px;
           display: none;
    }  
    </style>
    <title>快速入门</title>
  </head>
  <body>
    <div id="container" tabindex="0"></div>
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=49aa102a40ea3676f608dc7f96511298"></script>
    <script src="${pageContext.request.contextPath}/js/main.js?v=1.0.10"></script>
    <script type="text/javascript">
    var map = new AMap.Map('container', {
        resizeEnable: true,
        center: [116.33719, 39.942384],
        zoom: 5
    });
    
    function gd(param)
    {
    	debugger;
    	$.post("${pageContext.request.contextPath}/getGaode?",param,function(data)
    			{
    		debugger;
    				var data= eval("("+data+")");
    				$.each(data,function(index)
						{
    				    var polygonOptions = {
    				            map: map,
    				            strokeColor: '#97EC71',
    				            strokeWeight: 2,
    				            fillColor: data[index]["color"],
    				            fillOpacity: 0.7
    				        };
    				    
    				    	var o= data[index]["outer"].split(";");
    				    	var outer=new Array();
    				    	for(var i=0;i<o.length-1;i++)
    				    	{
    				    		outer.push(o[i].split(","));
    				    	}
    				    	var inner=new Array();
    				    	if(data[index]["inner"]==null)
    				    	{
    				    		//inner.push(null);
    				    	}
    				    	else
    				    	{
	    				    	var inr= data[index]["inner"].split(";");
	    				    	for(var i=0;i<inr.length-1;i++)
	    				    	{
	    				    		inner.push(inr[i].split(","));
	    				    	}
    				    	}
    				        // 外多边形坐标数组和内多边形坐标数组
    				        var pointers = {
    				            outer: outer,
    				            inner: inner
    				        };
    				        var pathArray = [
    				            pointers.outer,
    				            pointers.inner
    				        ];
    				        var polygon = new AMap.Polygon(polygonOptions);
    				        polygon.setPath(pathArray);
    				        map.setFitView();
						});
    			});
    }
    </script>
  </body>
</html>