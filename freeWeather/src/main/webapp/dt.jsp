<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- 重点：loadAreaNode -->
<!doctype html>
<html lang="zh-CN">

<head>
    <!-- 原始地址：//webapi.amap.com/ui/1.0/ui/geo/DistrictExplorer/examples/simple-load.html -->
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
    <title>单区域加载</title>
    <style>
    html,
    body,
    #container {
        width: 100%;
        height: 100%;
        margin: 0px;
    }
    </style>
</head>

<body>
  <div id="allmap">
  </div>
    <div id="container" tabindex="0"></div>
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=49aa102a40ea3676f608dc7f96511298"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <!-- UI组件库 1.0 -->
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script type="text/javascript">
    //创建地图
    var map = new AMap.Map('container', {
        cursor: 'default',
        zoom: 4
    });

    AMapUI.loadUI(['control/BasicControl'], function(BasicControl) {

        var layerCtrl = new BasicControl.LayerSwitcher({
            position: 'tr'
        });

        map.addControl(layerCtrl);

    });
    
    AMapUI.loadUI(['geo/DistrictExplorer'], function(DistrictExplorer) {

        //创建一个实例
        var districtExplorer = new DistrictExplorer({
            map: map
        });

        
        var adcode = 210200;

        districtExplorer.loadAreaNode(adcode, function(error, areaNode) {

		debugger;
            //更新地图视野
            map.setBounds(areaNode.getBounds(), null, null, true);

            //清除已有的绘制内容
            districtExplorer.clearFeaturePolygons();

			var geometry= areaNode.getParentFeature().geometry;
			var text="Polygon";
			var coordinates = geometry.coordinates;
			text+=(";"+coordinates.length);
			for (var c = 0; c < coordinates.length;c++) 
			{
	             var coordinate = coordinates[c][0];
				 text+=(";"+coordinate.length);
				 for(var cc=0;cc<coordinate.length;cc++)
				 {
				 	text+=(";"+(coordinate[cc][0])+","+(coordinate[cc][1]));
				 }
			 }
			 $("#allmap").html(text);
			 console.log(text);
            //绘制父区域
            districtExplorer.renderParentFeature(areaNode, {
                cursor: 'default',
                bubble: true,
                strokeColor: 'black', //线颜色
                strokeOpacity: 1, //线透明度
                strokeWeight: 1, //线宽
                fillColor: null, //填充色
                fillOpacity: 0.35, //填充透明度
            });
        });
    });
    </script>
</body>

</html>