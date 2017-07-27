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
	
	<textarea class="form-control" rows="3"></textarea>
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
		<button type="button" class="btn btn-default">生成</button>
		<button type="button" class="btn btn-default">？</button>
	</div>
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
        var camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight,1, 1000);
        var webGLRenderer = new THREE.WebGLRenderer();
        webGLRenderer.setSize(600, 300);
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