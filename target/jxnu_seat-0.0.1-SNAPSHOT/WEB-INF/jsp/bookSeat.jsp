<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>抢座位</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		
		
		//用于ajax等待
		$("body").bind("ajaxSend", function() {
			$("#dialog").show();
		});
		$("body").bind("ajaxComplete", function() {
			$("#dialog").hide();
		});
	});
	function bookSeat() {
		$("#result").append("吃好睡好才能考的好,加油哦");
		alert("开始抢座位的时间为:"+$("#beginDateTime").val()+",你可以先去睡觉");
		$("#book").css("display", "none");
		$.post("${pageContext.request.contextPath }/seat/setTimeBook", $(
				"#formId").serialize(), function(data) {

			var status = data.status;
			var value = data.value;
			if (status = "0")//教室为null
			{
				alert(value);
			} else if (status == "1")// 座位为null
			{
				alert(value);
			} else if (status == "2")// 未登陆
			{
				alert(value);
			} else if (status == "3")// 已经有座位了
			{
				alert(value);
			} else if (status == "4")// 位置已经被人抢了
			{
				alert(value);
			} else if (status == "5")// 抢位置成功
			{
				alert(value);
				getLog();
			} else if (status == "6")//未知情况
			{
				alert("出现未知情况,网页源代码为:\n" + value);
			}
		}, "json");
	}

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
.seat {
	width: 40px;
}

.Wdate {
	width: 200px;
}

#dialog { //
	border: solid 1px black;
	position: absolute;
	top: 1140px;
	left: 1200px;
	right: 0;
	bottom: 0; //
	width: 100%; //
	height: 100%;
	background-color: #8FB0D1;
	-moz-opacity: 0.8;
	opacity: 0.8;
	z-index: 1001;
	filter: alpha(opacity = 40);
}
</style>
</head>
<body>
	<div>
		<a href="${pageContext.request.contextPath }/menu/list">首页</a> <a
			href="${pageContext.request.contextPath }/login/login">注销</a>
	</div>
	<div>
		<form action="#" id="formId" onsubmit="event.preventDefault();">
			<div>
				<span>开始抢座位时间：</span> <input type="text" id="beginDateTime" name="beginDateTime"
					class="Wdate"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					required="required">
			</div>
			<div>
				<span>自习室:</span> <select name="roomId">
					<option value="101001">自习室101(南)</option>
					<option value="101002">自习室201(南)</option>
					<option value="101003">自习室202(北)</option>
					<option value="101004">自习室301(南)</option>
					<option value="101005">自习室302(北)</option>
				</select>
			</div>
			<div>
				<span>座位:</span> <input type="text" name="seatName" class="seat">
				<input type="text" name="seatName" class="seat"> <input
					type="text" name="seatName" class="seat"> <input
					type="text" name="seatName" class="seat"> <input
					type="text" name="seatName" class="seat">
				<ul>
					<li><span style="color: red">（格式是A00,提供5个期望座位,可以不填但至<br>少要填一个）
					</span></li>
					<li><span style="color: red">（剩余空闲座位的显示后期开放,暂时手动填）</span></li>
					<li><span style="color: red">（定时功能:在开始抢座位时间设置让系统开始抢座位的时间,如果设置的时间比现在的时间小,则会立即抢座位,并且响应结果,否则要到你设置的时间才会开始抢,并且响应结果,也就是说如果你设置的时间还没到,系统是不会有任何结果响应的）</span></li>
				</ul>


			</div>
			<div>
				<input type="submit" value="确定" onclick="bookSeat()">
			</div>
		</form>
	</div>

	<div id="result">
		<div id="book">
			<img src="${pageContext.request.contextPath }/images/book.gif">
		</div>
		<div id="dialog" style="display: none">
			<img src="${pageContext.request.contextPath }/images/loading.gif" />
		</div>

	</div>
	<div>
		<span>(没点关注的同学点波关注)</span><br> <a
			href="http://weibo.cn/watermelon0223">http://weibo.cn/watermelon0223</a>
	</div>
	<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;V0.0990</div>
</body>
</html>