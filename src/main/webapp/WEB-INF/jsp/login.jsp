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
								alert(data.value);
							}
						}, "json");
	}
</script>
</head>
<body>
	<div>
		<span style="color: red;">${message }</span>
		<div>
			<form onsubmit="event.preventDefault();" id="loginForm">
				<div>
					<input type="text" name="txt_LoginID" placeholder="学号"
						maxlength="12" required="required">
				</div>
				<div>
					<input type="password" name="txt_Password" placeholder="默认密码为学号"
						maxlength="12" required="required">
				</div>
				<div>
					<input type="submit" value="登录" onclick="loginSubmit(this)">
				</div>
			</form>
		</div>
	</div>
</body>
</html>