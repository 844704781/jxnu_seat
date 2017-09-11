<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
$(function(){
	getLog();
});
	function getLog() {
		$
				.post(
						"${pageContext.request.contextPath }/seat/query",
						null,
						function(data) {
							if (data.status == "2") {
								alert("未登录");
								location.href = "${pageContext.request.contextPath }/login/login";
							} else if (data.status == "3") {
								$("#result").append(
										"<div style='color:red'>记录:</div>");
								$("#result").append(data.value);
							}
						}, "json");
	}
	
	
	function subCancel(str) {
		$
				.post(
						"${pageContext.request.contextPath }/seat/cancel",
						{
							id : str
						},
						function(data) {
							if (data.status == "2") {
								alert("未登录");
								location.href = "${pageContext.request.contextPath }/login/login";
							} else if (data.status == "3") {
								alert("取消成功");
								location.href = "${pageContext.request.contextPath }/seat/list";
							} else if (data.status == "4") {
								alert("取消失败");
							}
						}, "json");
	}
</script>
<style type="text/css">

</style>
</head>
<body>
<div id="menuImg"></div>
	<div>
		<a href="${pageContext.request.contextPath }/menu/list">首页</a> <a
			href="${pageContext.request.contextPath }/login/login">注销</a>
	</div>
	<ul>
	<li><a href="${pageContext.request.contextPath }/seat/list">抢位置</a></li>
	<li><a href="#" onclick="alert('是不是有点不太好');">一键取消学校所有人的抢的位置</a></li>
	</ul>
	
	<div id="result"></div>
	<img alt="" src="${pageContext.request.contextPath }/images/menu.gif">
	<div>
		<span>(没点关注的同学点波关注)</span><br> <a href="http://weibo.cn/watermelon0223">http://weibo.cn/watermelon0223</a>
	</div>
	<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;V0.0990</div>

</body>
</html>