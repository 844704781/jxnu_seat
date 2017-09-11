<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript">
	$(function() {
	});
	function loginSubmit(obj) {
		$
				.post(
						"${pageContext.request.contextPath }/login/loginSubmit",
						$("#loginForm").serialize(),
						function(data) {
							event.preventDefault();
							if (data.status == "true") {
								//alert(data.value);
								location.href = "${pageContext.request.contextPath }/menu/list";
							} else {
								alert("账号或密码错误.\n账号格式为:20**********,默认密码格式为:20**********");
							}
						}, "json");
	}
</script>
<style type="text/css">
</style>
</head>
<body>
	<div id="content">
		<div>
			<span style="color: red;">${message }</span>
			<div>
				<form onsubmit="event.preventDefault();" id="loginForm">
					<div>
						<input type="text" name="txt_LoginID" placeholder="学号"
							maxlength="12" required="required" value="${user.txt_LoginID }">
					</div>
					<div>
						<input type="password" name="txt_Password" placeholder="默认密码为学号"
							maxlength="12" required="required" value="${user.txt_Password }">
					</div>
					<div>
						<input type="submit" value="登录" onclick="loginSubmit(this)">
					</div>
				</form>
			</div>
		</div>
		<div id="bkimg">
			<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;亲爱的慧慧,我来啦~~~</div>
			<img alt="亲爱的慧慧,么么哒"
				src="${pageContext.request.contextPath }/images/mylove.gif">
		</div>
		<div>
			<span>(没点关注的同学点波关注,联系方式:844704781)</span><br> <a
				href="http://weibo.cn/watermelon0223">http://weibo.cn/watermelon0223</a>
		</div>
		<div>更新日志V0910:</div>
		<ul>
			<li>修改了开始时间显示的bug</li>
		</ul>
		<div>更新日志V0909:</div>
		<ul>
			<li>修改了账号密码提示</li>
			<li>修复选中的座位被人抢走的判断bug</li>
		</ul>
		<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;V0.0991</div>
		
	</div>
</body>
</html>