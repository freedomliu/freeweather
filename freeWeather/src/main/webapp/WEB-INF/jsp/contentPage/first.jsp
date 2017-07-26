<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<body>

	<div class="jumbotron">
		<div class="container">
			<h1>欢迎登陆页面！</h1>
			<p>这是一个超大屏幕（Jumbotron）的实例。</p>
			<p>
				<a class="btn btn-primary btn-lg" role="button"> 按钮</a>
			</p>
		</div>
	</div>
	
	<textarea class="form-control" rows="3"></textarea>
	<div>
		<select class="form-control" style="width: auto;float: left;">
			<option>中国</option>
		</select> <select class="form-control" style="width: auto;float: left;">
			<option>中国</option>
		</select> <select class="form-control" style="width: auto;float: left;">
			<option>中国</option>
		</select>
	</div>
	<div class="btn-group" style="float: right;">
		<button type="button" class="btn btn-default">校验</button>
		<button type="button" class="btn btn-default">生成</button>
		<button type="button" class="btn btn-default">？</button>
	</div>
	<div id="Stats-output">
</div>
<div id="WebGL-output">
</div>
</body>
</html>
<script>
function helper(scene)
{
	var axes = new THREE.AxisHelper(200);
    scene.add(axes);
    
    var grid=new THREE.GridHelper(100,100);
    scene.add(grid);
    
   /*  var directionV3 = new THREE.Vector3(1, 0, 1);
    var originV3 = new THREE.Vector3(0, 0, 0);
    var arrowHelper = new THREE.ArrowHelper(directionV3, originV3, 100, 0xff0000, 20, 10); // 100 is length, 20 and 10 are head length and width
    scene.add(arrowHelper); */
}

function init() {
    var stats = initStats();
    var scene = new THREE.Scene();
    var camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight,45, 1000);
    var webGLRenderer = new THREE.WebGLRenderer();
    webGLRenderer.setSize(window.innerWidth, window.innerHeight);
    var sphereGeometry=new THREE.SphereGeometry(20, 40, 40);
    var sphere = createMesh(sphereGeometry);
    scene.add(sphere);
    camera.position.x = -20;
    camera.position.y = 30;
    camera.position.z = 40;
    camera.lookAt(new THREE.Vector3(00, 0, 0));
    var orbitControls = new THREE.OrbitControls(camera);
    var clock = new THREE.Clock();
    var ambiLight = new THREE.AmbientLight(0xffffff);
    scene.add(ambiLight);
    helper(scene);
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
        stats.update();
        var delta = clock.getDelta();
        orbitControls.update(delta);
        requestAnimationFrame(render);
        webGLRenderer.render(scene, camera);
        
    }
    function initStats() {
        var stats = new Stats();
        stats.setMode(0); 
        stats.domElement.style.position = 'absolute';
        stats.domElement.style.left = '0px';
        stats.domElement.style.top = '0px';
        document.getElementById("Stats-output").appendChild(stats.domElement);
        return stats;
    }
}
window.onload = init;
</script>