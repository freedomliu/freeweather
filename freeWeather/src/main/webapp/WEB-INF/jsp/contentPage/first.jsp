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
</body>
</html>
<script>
	$(document).ready(function() {
		//$('#myCarousel').carousel({interval:5000});//每隔5秒自动轮播 
	});
</script>