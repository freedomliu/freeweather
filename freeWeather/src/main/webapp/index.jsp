<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="WEB-INF/jsp/common.jsp"%>
<html>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top"  role="navigation">
  <div class="container-fluid" >
    <div class="navbar-header">
      <a class="navbar-brand" href="javascript:void(0)" onclick="forwardPage('contentPage/first')" style="color: white;font-size: 35">freeWeather</a>
    </div>
    <div>
        <ul class="nav navbar-nav navbar-left">
            <li><a href="javascript:void(0)" onclick="forwardPage('contentPage/show')" style="color: white;font-size: 18">样例</a></li>
        </ul>
    </div>
     <div>
        <ul class="nav navbar-nav navbar-right">
        	<li><a data-toggle="collapse" style="color: white;font-size: 18" data-parent="#accordion" 
				   href="#collapseOne">制作</a></li>
        </ul>
    </div>
    </div>
</nav>

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
<div id="contentMain"></div>