<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
<div class="row" style="margin-left:10px;margin-right:10px;">
	<div class="col-sm-6 col-md-8">
		<textarea  class="form-control" rows="8" id="zb" data-toggle="tooltip" data-placement="bottom" title="形如: 经度,维度,数值;经度,维度,数值">117.62,37.75,356;117.57,37.65,238.3;117.53,37.48,251.3;118.13,37.68,312.1;118.02,37.37,298.9;118.15,37.15,139.3;117.73,36.87,139.1;117.901944444,38.166111111,220.2;117.366944443,37.583055554,306;117.486666666,37.22361111,205.5;117.579999999,37.361944444,458.3;117.733888888,37.296944444,297;118.208888888,37.343055555,99.7;118.288055555,37.2,105;118.261944444,37.03611111,147.6;117.453888888,36.978888888,70;117.691944444,37.018055554,86.7;117.879999999,36.923055554,60.2;118.071944443,37.283055554,183.4;117.848055555,37.506944444,354.2;118.081944443,37.483888888,185.2;118.014166666,37.505833333,207.2;117.88361111,37.605833333,234.3;118.2,37.9,292.4;118.05,38.04,423.8;117.679166666,37.993055555,223.5;117.635555555,37.910833333,352.7;117.826388888,37.884444444,307.3;117.916111111,38.053888888,177.8;117.98111111,38.119999999,403.4;117.426388888,37.62611111,322.4;117.666944443,37.65,435.7;117.75,37.583055554,310;117.519444443,37.589166666,402.2;117.366944443,37.516666666,339.4;117.616944443,37.6,353.4;117.500277777,37.527777777,122.8;117.636944444,37.418888888,241.8;117.47611111,37.41,301.8;117.738055555,37.328888888,555;117.423888888,37.480277777,176.1;117.68111111,37.293055555,332;117.578888888,37.553888888,424.9;117.462222222,37.347499999,457.1;117.535833333,37.183333333,214.1;117.715,37.5025,282.5;117.593888888,37.263055555,23.4;118.19361111,37.099166666,158.8;118.119999999,37.215,182.2;118.293888888,37.106111111,107.4;118.218055554,37.23611111,199.1;118.269999999,37.26,271.8;118.139999999,37.108888888,259.2;118.191944444,37.02111111,170.6;117.743888888,36.858888888,80.3;117.623888888,36.928055554,150.6;117.644722221,37.034444444,108.8;117.823888888,37,85.4;117.389999999,37.026944443,79.9;117.489999999,37.091944444,108.3;117.613055555,36.866944443,137.2;117.77111111,36.713055555,89.9;117.79611111,36.818888888,90.8;117.703888888,36.946944444,151.7;117.68611111,36.89111111,165.7;117.818888888,36.881944443,116;117.88611111,37.411944444,418.3;117.983333333,37.467777777,305;117.992777777,37.253888888,295.2;117.876944443,37.36,468.3;118.085555555,37.398333333,269.4;117.951666666,37.391944444,332.8;118.116944443,37.7,306.4;117.783055554,37.683055554,355.3;117.966944443,37.616944443,291.5;118.283055554,37.816944443,188.9;118.216944443,37.683055554,304.1;117.966944443,37.783055554,321.1;117.702777777,37.729722221,339;117.853888888,37.801111111,373.8;117.703055555,37.831388888,327.4;117.596944444,38.051944444,268.6;117.736666666,37.967222221,281.1;117.713611111,38.048888888,252.8;117.838888888,37.356944444,509.1;117.911111111,37.916388888,364</textarea>
	</div>
<div class="col-sm-6 col-md-4">
		<div class="input-group input-group-lg">
		<select class="form-control" id="country" style="width: 100px;">
			<option value="中国">中国</option>
		</select> 
		<select class="form-control" id="pro" onchange="setCity(this.value)" style="width:auto;">
		    <option value=""></option>
			<option value="北京">北京</option>
			<option value="上海">上海</option>
			<option value="天津">天津</option>
			<option value="重庆">重庆</option>
			<option value="安徽">安徽</option>
			<option value="福建">福建</option>
			<option value="甘肃">甘肃</option>
			<option value="广东">广东</option>
			<option value="广西">广西</option>
			<option value="贵州">贵州</option>
			<option value="海南">海南</option>
			<option value="河北">河北</option>
			<option value="河南">河南</option>
			<option value="黑龙江">黑龙江</option>
			<option value="湖北">湖北</option>
			<option value="湖南">湖南</option>
			<option value="吉林">吉林</option>
			<option value="江苏">江苏</option>
			<option value="江西">江西</option>
			<option value="辽宁">辽宁</option>
			<option value="内蒙古">内蒙古</option>
			<option value="宁夏">宁夏</option>
			<option value="青海">青海</option>
			<option value="山东">山东</option>
			<option value="山西">山西</option>
			<option value="陕西">陕西</option>
			<option value="四川">四川</option>
			<option value="西藏">西藏</option>
			<option value="新疆">新疆</option>
			<option value="云南">云南</option>
			<option value="浙江">浙江</option>
		</select> 
		<select class="form-control" id="city" style="width:auto;">
			<option value=""></option>
		</select>
	</div>
	<div class="input-group input-group-lg">
            <span class="input-group-addon" style="width: 100px">标题</span>
            <input type="text" class="form-control" id="title" placeholder="累计降水图">
    </div>
    <!-- <div class="input-group input-group-lg">
            <span class="input-group-addon" style="width: 100px">坐标值</span>
            <input type="text" class="form-control" id="zb" >
    </div> -->
    <div class="input-group input-group-lg">
            <span class="input-group-addon" style="width: 100px">图例</span>
            <input type="text" class="form-control" id="legend" data-toggle="tooltip" data-placement="bottom" title="形如:负无穷(~),值A,颜色;值A,值B,颜色;值B,值C,颜色;值C,正无穷(~),颜色" value="~,240,FFB6C1;240,244,DC143C;244,248,4B0082;248,250,7B68EE;250,254,1E90FF;254,258,00BFFF;258,265,00FFFF;265,270,7FFFAA;270,275,3CB371;275,280,ADFF2F;280,285,A9A9A9;285,~,8B008B">
    </div>
	<div class="btn-group" style="float: left;">
		<button type="button" class="btn btn-primary" onclick="showModal(true)" data-toggle="modal">校验</button>
		<button type="button" class="btn btn-primary" onclick="getPic()">生成</button>
	</div>
	</div>
	<div class="col-sm-12 col-md-12" style="margin-top: 10px">
	<div class="progress progress-striped active">
		<div class="progress-bar progress-bar-success" role="progressbar" id="proBar"
			 aria-valuemin="0" aria-valuemax="100"
			 style="width: 0%;">
		</div>
	</div>
	<div style="width: 100%;text-align: c">
	<div>
		<button type="button" class="btn btn-primary" data-toggle="collapse" 
			data-target="#legendTool">
			图例工具
		</button>
		
		<div id="legendTool" class="collapse">
			<iframe style="width: 100%;height: 500px" seamless src="${pageContext.request.contextPath}/sys/forwardPage.do?pageName=contentPage/legendTool"></iframe>
		</div>
	</div>
	<div style="width: 100%;text-align: center;">  	
	</div>
	</div>
	</div>
</div>
<div class="modal fade" id="myModal" backdrop="false"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:700;height: 600;">
		<div class="modal-content">
			<div class="modal-body">
					<div class="table-responsive">
		<table class="table table-bordered" >
			<caption id="jxResult">解析结果如下</caption>
			<thead>
				<tr>
					<th>经度</th>
					<th>纬度</th>
					<th>数值</th>
				</tr>
			</thead>
			<tbody id="tbody">
				
			</tbody>
			</table>
		</div>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>
</body>
</html>
<script>
$(function () { $("[data-toggle='tooltip']").tooltip(); });
var barNum=0;
function setCity(value)
{
	var sub = function(data) {
		$("#city").empty();
		if(data==null||data==undefined||data=="")
		return false;
		var data= eval("("+data+")")
		$("#city").append("<option value=''></option>")
		$.each(data,function(index){
			$("#city").append("<option value='"+data[index]+"'>"+data[index]+"</option>");
		});
	}
	myAjax("${pageContext.request.contextPath}/sys/getCity?",{"proName":value},true,sub);
}
function getPic()
{
	if(showModal(false))
	{
		return false;
	}
	debugger;
	var area=$("#city").val();
	if(area=="")
	{
		area=$("#pro").val();
	}
	if(area=="")
	{
		area=$("#country").val();
	}
	barNum=0;
	var progressBar=setInterval ("progressBar()", 1000);
	var param={"area":area,"title":$("#title").val(),"zb":$("#zb").val(),"legend":$("#legend").val(),"pro":$("#pro").val(),"city":$("#city").val()};

	var sub = function(data) {
		debugger;
		$("#sbt").attr('src',data.substring(1,data.length-1)); 
		document.getElementById("proBar").style.width="100%";
		clearInterval(progressBar);
	}
	var error=function(data)
	{
		document.getElementById("proBar").style.width="0%";
		clearInterval(progressBar);
	}
	myAjax("${pageContext.request.contextPath}/getFWPic?",param,true,sub,error);
}

function progressBar()
{
	document.getElementById("proBar").style.width=barNum+"%";
	barNum++;
	if(barNum==100)
		barNum=0;
}

function showModal(show)
{
	debugger;
	$("#tbody").empty();
	// 校验图例
	var legend= $("#legend").val();
	if(legend=="")
	{
		alert("图例校验失败:请填写图例,如有不明请使用图例工具进行辅助");
		return false;
	}
	var isPass=true;
	var legendItem= legend.split(";")
	for(var i=0;i<legendItem.length;i++)
	{
		var temp= legendItem[i].split(",");
		if(i==0)
		{
			if(temp[0]!="~")
			{
				alert("图例校验失败:图例应以~开始,代表负无穷")
				return false;
			}
			if(isNaN(temp[1]))
			{
				alert("图例校验失败: "+temp[1]+" 应为有效数字")
				return false;
			}
			if(!/^#[0-9a-fA-F]{3,6}$/.test("#"+temp[2]))
			{
				alert("图例校验失败: "+temp[2]+" 应为有效16进制颜色值")
				return false;
			}
			
		}
		else if(i==legendItem.length-1)
		{
			if(isNaN(temp[0]))
			{
				alert("图例校验失败: "+temp[0]+" 应为有效数字")
				return false;
			}
			if(temp[1]!="~")
			{
				alert("图例校验失败:图例最后一个数值位应为  ~ 代表负无穷")
				return false;
			}
			if(!/^#[0-9a-fA-F]{3,6}$/.test("#"+temp[2]))
			{
				alert("图例校验失败: "+temp[2]+" 应为有效16进制颜色值")
				return false;
			}
			var frontOne=legendItem[i-1].split(",")[1];
			if(frontOne!=temp[0])
			{
				alert("图例校验失败: "+temp[0]+" 输入错误,应为"+frontOne);
				return false;
			}
		}
		else
		{
			if(isNaN(temp[0]))
			{
				alert("图例校验失败: "+temp[0]+" 应为有效数字")
				return false;
			}
			if(isNaN(temp[1]))
			{
				alert("图例校验失败: "+temp[1]+" 应为有效数字")
				return false;
			}
			if(!/^#[0-9a-fA-F]{3,6}$/.test("#"+temp[2]))
			{
				alert("图例校验失败: "+temp[2]+" 应为有效16进制颜色值")
				return false;
			}
			
			var frontOne=legendItem[i-1].split(",")[1];
			if(frontOne!=temp[0])
			{
				alert("图例校验失败: "+temp[0]+" 输入错误,应为"+frontOne);
				return false;
			}
		}
	}
	if(isPass==false)
	{
		return false;
	}
	
	// 验证数据 
	var zb= $("#zb").val();
	if(zb=="")
	{
		alert("请按格式填写坐标及数值");
		return false;
	}
	
	var reg0=/，/g;
	var reg1=/；/g;

	zb=zb.replace(reg0,",").replace(reg1,";");
	$("zb").val(zb);
	
	var zbItem= zb.split(";");
	
	var tableValue="";

	for(var i=0;i<zbItem.length;i++)
	{
		var temp= zbItem[i].split(",");
		var j=temp[0];
		var w=temp[1];
		var z=temp[2];
		if(isNaN(j))
		{
			isPass=false;
			j=j+"<font style='color: red;'>无效</font>";
		}
		if(isNaN(w))
		{
			isPass=false;
			w=w+"<font style='color: red;'>无效</font>";
		}
		if(isNaN(z))
		{
			isPass=false;
			z=z+"<font style='color: red;'>无效</font>";
		}
		
		tableValue=tableValue+"<tr><td>"+j+"</td><td>"+w+"</td><td>"+z+"</td></tr>";
	}
	$("#tbody").html(tableValue);
	if(isPass)
	{
		$("#jxResult").html("解析结果如下(格式校验通过)");
	}
	else
	{
		$("#jxResult").html("解析结果如下(<font style='color: red;'>含无效值校验不通过</font>)");
	}
	if(show)
	{
		$('#myModal').modal('show');
	}
	return isPass;
}

</script>