var sub = function(data) {
	debugger;
	$("input[name='username']").val("lxt");
	$("input[name='password']").val("lxt");
	$("input[name='imgCode']").val("${code}");
	$("#formUser").submit();
}

function myAjax(url,data,isAsync,sub,error)
{
	debugger;
	var bean=new Object();
	bean.url=url;
	bean.data=data;
	bean.type="post";
	bean.async=isAsync;
	bean.success= sub;
	bean.error= error;
	debugger;
	$.ajax(bean);
}
