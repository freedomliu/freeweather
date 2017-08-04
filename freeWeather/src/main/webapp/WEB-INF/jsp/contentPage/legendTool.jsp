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
	<div>颜色<input type="text" id="color" class="form-control" placeholder="d1439d,b043d1,5b30c9,3061c9,30c9c4,30c968,9ec930"></div>
	<div id="colorpickerHolder" style="margin-top: 10px">
	</div>
	<input type="text" id="legend" class="form-control" disabled="disabled" placeholder="图例字符串" style="margin-top: 10px">
	<div class="btn-group" data-toggle="buttons">
	<button class="btn btn-primary" onclick="legendOver()">生成
	</button>
	<button class="btn btn-primary" onclick="useColorValue()">
		应用
	</button>
</div>
</body>
</html>
<script>
	var c=document.getElementById("myCanvas");
	var ctx=c.getContext("2d");
	$(function(){
		$('#colorpickerHolder').ColorPicker({flat: true});
		
		
		ctx.font="15px Arial";
		
		ctx.moveTo(10,70);
		ctx.lineTo(790,70);
		
		var itemWidth=780/7;
		ctx.fillText("1",itemWidth*1+5,30);
		ctx.fillText("2",itemWidth*2+6,30);
		ctx.fillText("3",itemWidth*3+6,30);
		ctx.fillText("4",itemWidth*4+6,30);
		ctx.fillText("5",itemWidth*5+6,30);
		ctx.fillText("6",itemWidth*6+6,30);
		
		ctx.moveTo(itemWidth*1+10,70);
		ctx.lineTo(itemWidth*1+10,40);
		ctx.moveTo(itemWidth*2+10,70);
		ctx.lineTo(itemWidth*2+10,40);
		ctx.moveTo(itemWidth*3+10,70);
		ctx.lineTo(itemWidth*3+10,40);
		ctx.moveTo(itemWidth*4+10,70);
		ctx.lineTo(itemWidth*4+10,40);
		ctx.moveTo(itemWidth*5+10,70);
		ctx.lineTo(itemWidth*5+10,40);
		ctx.moveTo(itemWidth*6+10,70);
		ctx.lineTo(itemWidth*6+10,40);
		
		ctx.fillStyle='#d1439d';
		ctx.fillRect(10,50,itemWidth,20);
		
		ctx.fillStyle='#b043d1';
		ctx.fillRect(itemWidth*1+10,50,itemWidth,20);
		
		ctx.fillStyle='#5b30c9';
		ctx.fillRect(itemWidth*2+10,50,itemWidth,20);
		
		ctx.fillStyle='#3061c9';
		ctx.fillRect(itemWidth*3+10,50,itemWidth,20);
		
		ctx.fillStyle='#30c9c4';
		ctx.fillRect(itemWidth*4+10,50,itemWidth,20);
		
		ctx.fillStyle='#30c968';
		ctx.fillRect(itemWidth*5+10,50,itemWidth,20);
		
		ctx.fillStyle='#9ec930';
		ctx.fillRect(itemWidth*6+10,50,itemWidth,20);
		
		ctx.stroke();
	});
	
	
	function getColorValue()
	{
		var colorValue=$("#color").val();
		if(colorValue=="")
		{
			$("#color").val($("#colorValue").val());
		}
		else
		{
			$("#color").val(colorValue+","+$("#colorValue").val());
		}
	}
	
	function useColorValue()
	{
		if(legendOver())
		{
			var legend=$("#legend").val();
			window.parent.document.getElementById("legend").value=legend;
		}	
	}
	
	function legendOver()
	{
		debugger;
		var sectionValue=$("#section").val();
		var colorValue=$("#color").val();
		
		var reg=/，/g;

		sectionValue=sectionValue.replace(reg,",");
		colorValue=colorValue.replace(reg,",");
		$("#section").val(sectionValue);
		$("#color").val(colorValue);
		
		if(colorValue=="")
		{
			alert("请填写颜色区间");
			return false;
		}
		if(sectionValue=="")
		{
			alert("请填写图例区间");
			return false;
		}
		var sectionValueItem=sectionValue.split(",");
		var colorValueItem=colorValue.split(",");
		
		if((colorValueItem.length-sectionValueItem.length)!=1)
		{
			alert("颜色个数应等于区间个数加1");
			return false;
		}
		
		var isPass=true;
		
		for(var i=0;i<sectionValueItem.length;i++)
		{
			if(isNaN(sectionValueItem[i]))
			{
				alert(sectionValueItem[i]+"并不是有效数字");
				isPass=false;
				return false;
			}
		}
		
		for(var i=1;i<sectionValueItem.length;i++)
		{
			if(sectionValueItem[i]<=sectionValueItem[i-1])
			{
				alert(sectionValueItem[i]+"应大于"+sectionValueItem[i-1]);
				return false;
			}
		}
		
		if(isPass==false)
		{
			return false;
		}
		for(var i=0;i<colorValueItem.length;i++)
		{
			if(!/^#[0-9a-fA-F]{3,6}$/.test("#"+colorValueItem[i]))
			{
				alert(colorValueItem[i]+"并不是有效16进制颜色");
				isPass=false;
				return false;
			}
		}
		if(isPass==false)
		{
			return false;
		}
		
		var legend="~,"+sectionValueItem[0]+","+colorValueItem[0];
		for(var i=0;i<sectionValueItem.length-1;i++)
		{
			legend=";"+sectionValueItem[i]+","+sectionValueItem[i+1]+","+colorValueItem[i+1];
		}
		legend=legend+";"+sectionValueItem[sectionValueItem.length-1]+",~"+colorValueItem[colorValueItem.length-1];
		
		$("#legend").val(legend);
		c.width="801";
		c.width="800";
		var ctx=c.getContext("2d");
		ctx.clearRect(0,0,800,100);
		ctx.font="15px Arial";
		ctx.moveTo(10,70);
		ctx.lineTo(790,70);
		var itemWidth=780/colorValueItem.length;
		
		// 写字
		for(var i=0;i<colorValueItem.length-1;i++)
		{
			ctx.fillText(sectionValueItem[i],itemWidth*(i+1)+5,30);
		}
		
		// 画竖线
		for(var i=0;i<colorValueItem.length-1;i++)
		{
			ctx.moveTo(itemWidth*(i+1)+10,70);
			ctx.lineTo(itemWidth*(i+1)+10,40);
		}
		
		// 画图例
		for(var i=0;i<colorValueItem.length;i++)
		{
			ctx.fillStyle='#'+colorValueItem[i];
			ctx.fillRect(itemWidth*i+10,50,itemWidth,20);
		}
		ctx.stroke();
		return true;
	}
</script>