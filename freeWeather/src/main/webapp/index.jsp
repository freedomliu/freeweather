<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="WEB-INF/jsp/common.jsp"%>
<html>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
  <div class="container-fluid">
    <div class="navbar-header">
      <a class="navbar-brand" href="javascript:void(0)" onclick="forwardPage('contentPage/first')" style="color: white;">freeWeather</a>
    </div>
    <div>
        <ul class="nav navbar-nav navbar-left">
            <!-- <li class="dropdown">
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
            </li> -->
            <li><a href="javascript:void(0)" onclick="forwardPage('contentPage/show')">样例</a></li>
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

<div class="projects-header page-header" style="margin-top: 60px">
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
	
	<div id="contentMain"></div>
	
	
    <script src="js/three/three.min.js"></script>
   
    <script type="text/javascript">
    
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
    
   
</script>
</div>
</body>
</html>
<script>
	var urlFirst="${pageContext.request.contextPath}/sys/forwardPage.do?pageName=";
	$(function(){
		$("#contentMain").load(urlFirst+"contentPage/first");
	})  

	function forwardPage(pageName)
	{
		debugger;
		$("#contentMain").load(urlFirst+pageName);
	}
</script>