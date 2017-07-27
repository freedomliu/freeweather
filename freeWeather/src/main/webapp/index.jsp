<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="WEB-INF/jsp/common.jsp"%>
<html>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="#" style="color: white;">freeWeather</a>
    </div>
    <div>
        <ul class="nav navbar-nav navbar-left">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    	菜单
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li><a href="#">功能1</a></li>
                    <li><a href="#">功能1</a></li>
                    <li><a href="#">功能1</a></li>
                    <li class="divider"></li>
                    <li><a href="#">功能1</a></li>
                    <li class="divider"></li>
                    <li><a href="#">功能1</a></li>
                </ul>
            </li>
        </ul>
    </div>
    <ul class="nav navbar-nav navbar-right">
      <li class="dropdown"> 
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              	系统
              <b class="caret"></b>
          </a>
          <ul class="dropdown-menu">
              <li><a href="javascript:void(0)" onclick="forwardPage('sys/menuManage')">菜单管理</a></li>
          </ul>
      </li>
      <li><a href="#"><span class="glyphicon glyphicon-user"></span>登录</a></li>
      <li><a href="${pageContext.request.contextPath}/sys/logout.do"><span class="glyphicon glyphicon-log-out"></span> 退出</a></li>
    </ul>
  </div>
</nav>

<div class="projects-header page-header" id="contentMain" style="margin-top: 60px">
	<div class="jumbotron" style="width: 100%;background-color: black;">  
		<div class="container" style="width: 100%;background-color: black;">
			
			<div style="float: left;position: relative;margin-left: 50px">
				<h1 style="color: white;">欢迎登陆freeWeather</h1>
				<h2>本站所有资源全部收集于互联网，<br>
					仅供大家学习与参考，<br>
				本站不承担用户因使用这些资源<br>
				对自己和他人造成任何形式的损失。</h2>
			</div>
			<div id="WebGL-output"  style="float: right;position: relative;">
			</div>
		</div>
	</div>
	<div class="input-group input-group-lg">
            <span class="input-group-addon">标&nbsp;&nbsp;&nbsp; 题</span>
            <input type="text" class="form-control" id="title" placeholder="累计降水图">
    </div>
    <div class="input-group input-group-lg">
            <span class="input-group-addon">坐标值</span>
            <input type="text" class="form-control" id="zb" placeholder="117.62c37.75c356b117.57c37.65c238.3b117.53c37.48c251.3b118.13c37.68c312.1b118.02c37.37c298.9b118.15c37.15c139.3b117.73c36.87c139.1b117.901944444c38.166111111c220.2b117.366944443c37.583055554c306b117.486666666c37.22361111c205.5b117.579999999c37.361944444c458.3b117.733888888c37.296944444c297b118.208888888c37.343055555c99.7b118.288055555c37.2c105b118.261944444c37.03611111c147.6b117.453888888c36.978888888c70b117.691944444c37.018055554c86.7b117.879999999c36.923055554c60.2b118.071944443c37.283055554c183.4b117.848055555c37.506944444c354.2b118.081944443c37.483888888c185.2b118.014166666c37.505833333c207.2b117.88361111c37.605833333c234.3b118.2c37.9c292.4b118.05c38.04c423.8b117.679166666c37.993055555c223.5b117.635555555c37.910833333c352.7b117.826388888c37.884444444c307.3b117.916111111c38.053888888c177.8b117.98111111c38.119999999c403.4b117.426388888c37.62611111c322.4b117.666944443c37.65c435.7b117.75c37.583055554c310b117.519444443c37.589166666c402.2b117.366944443c37.516666666c339.4b117.616944443c37.6c353.4b117.500277777c37.527777777c122.8b117.636944444c37.418888888c241.8b117.47611111c37.41c301.8b117.738055555c37.328888888c555b117.423888888c37.480277777c176.1b117.68111111c37.293055555c332b117.578888888c37.553888888c424.9b117.462222222c37.347499999c457.1b117.535833333c37.183333333c214.1b117.715c37.5025c282.5b117.593888888c37.263055555c23.4b118.19361111c37.099166666c158.8b118.119999999c37.215c182.2b118.293888888c37.106111111c107.4b118.218055554c37.23611111c199.1b118.269999999c37.26c271.8b118.139999999c37.108888888c259.2b118.191944444c37.02111111c170.6b117.743888888c36.858888888c80.3b117.623888888c36.928055554c150.6b117.644722221c37.034444444c108.8b117.823888888c37c85.4b117.389999999c37.026944443c79.9b117.489999999c37.091944444c108.3b117.613055555c36.866944443c137.2b117.77111111c36.713055555c89.9b117.79611111c36.818888888c90.8b117.703888888c36.946944444c151.7b117.68611111c36.89111111c165.7b117.818888888c36.881944443c116b117.88611111c37.411944444c418.3b117.983333333c37.467777777c305b117.992777777c37.253888888c295.2b117.876944443c37.36c468.3b118.085555555c37.398333333c269.4b117.951666666c37.391944444c332.8b118.116944443c37.7c306.4b117.783055554c37.683055554c355.3b117.966944443c37.616944443c291.5b118.283055554c37.816944443c188.9b118.216944443c37.683055554c304.1b117.966944443c37.783055554c321.1b117.702777777c37.729722221c339b117.853888888c37.801111111c373.8b117.703055555c37.831388888c327.4b117.596944444c38.051944444c268.6b117.736666666c37.967222221c281.1b117.713611111c38.048888888c252.8b117.838888888c37.356944444c509.1b117.911111111c37.916388888c364">
    </div>
    <div class="input-group input-group-lg">
            <span class="input-group-addon">图&nbsp;&nbsp;&nbsp;例</span>
            <input type="text" class="form-control" id="legend" placeholder="~c240cFFB6C1b240c244cDC143Cb244c248c4B0082b248c250c7B68EEb250c254c1E90FFb254c258c00BFFFb258c265c00FFFFb265c270c7FFFAAb270c275c3CB371b275c280cADFF2Fb280c285cA9A9A9b285c~c000000">
    </div>
	<div>
		<select class="form-control" style="width: auto;float: left;">
			<option>中国</option>
		</select> 
		<select class="form-control" id="pro" onchange="setCity(this.value)" style="width: auto;float: left;">
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
		<select class="form-control" id="city" style="width: auto;float: left;">
			<option value=""></option>
		</select>
	</div>
	<div class="btn-group" style="float: right;">
		<button type="button" class="btn btn-default">校验</button>
		<button type="button" class="btn btn-default" onclick="getPic()">生成</button>
		<button type="button" class="btn btn-default">？</button>
	</div>
	<img id="sbt">
    <script src="js/three/three.min.js"></script>
   
    <script type="text/javascript">
    function setCity(value)
    {
    	debugger;
    	var sub = function(data) {
    		$("#city").empty();
    		if(data==null||data==undefined||data=="")
    		return false;
    		var data= eval("("+data+")")
    		$.each(data,function(index){
    			debugger;
    			$("#city").append("<option value='"+data[index]+"'>"+data[index]+"</option>");
    		});
    	}
    	myAjax("${pageContext.request.contextPath}/sys/getCity?",{"proName":value},true,sub);
    }
    function init() {
    	debugger;
        var scene = new THREE.Scene();
        var camera = new THREE.PerspectiveCamera(45, 1,1, 1000);
        var webGLRenderer = new THREE.WebGLRenderer();
        webGLRenderer.setSize(300, 300);
        var sphereGeometry=new THREE.SphereGeometry(60, 60, 100);
        var sphere = createMesh(sphereGeometry);
        scene.add(sphere);
        camera.position.x = 100;
        camera.position.y = 100;
        camera.position.z = 100;
        camera.lookAt(new THREE.Vector3(0, 0, 0));
        debugger;
        var orbitControls = new THREE.OrbitControls(camera,webGLRenderer.domElement);
        var clock = new THREE.Clock();
        var ambiLight = new THREE.AmbientLight(0xffffff);
        scene.add(ambiLight);
        document.getElementById("WebGL-output").appendChild(webGLRenderer.domElement);       
        render();
        function createMesh(geom) {
            var planetTexture = THREE.ImageUtils.loadTexture("image/earth.jpg");
            var planetMaterial = new THREE.MeshPhongMaterial({map: planetTexture});
            var planetMaterial1 = new THREE.MeshLambertMaterial({blending:THREE.AdditiveBlending , transparent:true, color:0x2AC7CC, opacity:.6, map: new THREE.TextureLoader().load("image/earth_political_alpha.png")});
            var mesh = THREE.SceneUtils.createMultiMaterialObject(geom, [planetMaterial,planetMaterial1]);
            return mesh;
        }

        function render() {
            var delta = clock.getDelta();
            orbitControls.update(delta);
            requestAnimationFrame(render);
            webGLRenderer.render(scene, camera);
            
        }
    }
    debugger;
    window.onload = init;
    
    function getPic()
    {
    	debugger;
    	var param={"title":$("#title").val(),"zb":$("#zb").val(),"legend":$("#legend").val()};

    	var sub = function(data) {
    		alert(data.substring(1,data.length-1));
    		$("#sbt").attr('src',data.substring(1,data.length-1)); 
    	}
    	myAjax("${pageContext.request.contextPath}/getFWPic?",param,true,sub);
    }
</script>
</div>
</body>
</html>
<script>
	var urlFirst="${pageContext.request.contextPath}/sys/forwardPage.do?pageName=";
	/* $(function(){
		$("#contentMain").load(urlFirst+"contentPage/first");
		
	})  */

	function forwardPage(pageName)
	{
		debugger;
		$("#contentMain").load(urlFirst+pageName);
	}
</script>