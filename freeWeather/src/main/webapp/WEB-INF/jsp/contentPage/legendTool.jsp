<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<html>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/colorpicker.css" type="text/css" />
    <link rel="stylesheet" media="screen" type="text/css" href="${pageContext.request.contextPath}/css/layout.css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/colorpicker/colorpicker.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/colorpicker/eye.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/colorpicker/utils.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/colorpicker/layout.js?ver=1.0.2"></script>
</head>
<body>
	<canvas id="myCanvas"  width="800px" height="100" style="border:1px solid #d3d3d3;margin-top: 5px">
	您的浏览器不支持 HTML5 canvas 标签。</canvas>
	<div>区间<input type="text" id="section" class="form-control" placeholder="0,1,2,3,5,6"></div>
	<div>颜色<input type="text" id="color" class="form-control" placeholder="8a3232,8a3232,8a3232,8a3232"></div>
	<div id="colorpickerHolder" >
	</div>
	<input type="text" id="legend" class="form-control" placeholder="图例字符串">
	<div class="btn-group" data-toggle="buttons">
	<button class="btn btn-primary">生成
	</button>
	<button class="btn btn-primary">
		应用
	</button>
</div>
</body>
</html>
<script>
	debugger;
 	var temp= window.parent.document.getElementById("legend");
	$('#colorpickerHolder').ColorPicker({flat: true});
	
	var c=document.getElementById("myCanvas");
	var ctx=c.getContext("2d");
	ctx.font="10px Arial";
	
	ctx.moveTo(10,90);
	ctx.lineTo(790,90);
	
	var itemWidth=780/7;
	ctx.fillText("1",itemWidth*1+10,50);
	ctx.fillText("2",itemWidth*2+10,50);
	ctx.fillText("3",itemWidth*3+10,50);
	ctx.fillText("4",itemWidth*4+10,50);
	ctx.fillText("5",itemWidth*5+10,50);
	ctx.fillText("6",itemWidth*6+10,50);
	
	ctx.moveTo(itemWidth*1+10,90);
	ctx.lineTo(itemWidth*1+10,50);
	ctx.moveTo(itemWidth*2+10,90);
	ctx.lineTo(itemWidth*2+10,50);
	ctx.moveTo(itemWidth*3+10,90);
	ctx.lineTo(itemWidth*3+10,50);
	ctx.moveTo(itemWidth*4+10,90);
	ctx.lineTo(itemWidth*4+10,50);
	ctx.moveTo(itemWidth*5+10,90);
	ctx.lineTo(itemWidth*5+10,50);
	ctx.moveTo(itemWidth*6+10,90);
	ctx.lineTo(itemWidth*6+10,50);
	
	
	ctx.stroke();
</script>