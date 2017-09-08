<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>抢座位</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript">
	    
	  
	function bookSeat(){
		$.post("${pageContext.request.contextPath }/seat/setTimeBook",$("#formId").serialize(),function(data){
			if(data.status=="2")
				{
				alert("未登录");
				location.href = "${pageContext.request.contextPath }/login/login";
				}
			else if(data.status=="0")
				{
				 alert("自习室不能不选");
				}
			else if(data.status=="1")
				{
				 alert("座位至少要填一个");
				}
			else if(data.status=="3")
				{
				  getLog();//获得日志
				}
			else if(data.status=="ok")
				{
				  var rooId=data.value.roomId;
				  var content;
				  var bookSuccessMessage=data.value.bookSuccess.message;
				  var successSeats=data.value.bookSuccess.seats;
				  content=(bookSuccessMessage+":");
				  content+=("<br/>");
				  for(var i=0;i<successSeats.length;i++)
					  {
					  content+=(successSeats[i]);
					  content+=("   ");
					  }
				  content+=("<br/>");
				  var bookErrorMessage=data.value.bookError.message;
				  var errorSeats=data.value.bookError.seats;
				  content+=(bookErrorMessage+":");
				  content+=("<br/>");
				  for(var i=0;i<errorSeats.length;i++)
				  {
				  content+=(errorSeats[i]);
				  content+=("   ");
				  }
				  $("#result").append(content);
				}
		},"json");
	}
	
	function getLog()
	{
		$.post("${pageContext.request.contextPath }/seat/query",null,function(data){
			  if(data.status=="2")
				{
				alert("未登录");
				location.href = "${pageContext.request.contextPath }/login/login";
				}
			  else if(data.status=="3")
				  {
				     $("#result").append("<div style='color:red'>您已经有位置啦</div>");
				     $("#result").append(data.value);
				  }
		  },"json");
	}
	
	function subCancel(str)
	{
		$.post("${pageContext.request.contextPath }/seat/cancel",{id:str},function(data){
			  if(data.status=="2")
				{
				alert("未登录");
				location.href = "${pageContext.request.contextPath }/login/login";
				}
			  else if(data.status=="3")
				  {
				    alert("取消成功");
				    location.href="${pageContext.request.contextPath }/seat/list";
				  }
			  else if(data.status=="4"){
				  alert("取消失败");
			  }
		  },"json");
	}
	</script>
</head>
<body>
	<div>
		<a href="${pageContext.request.contextPath }/menu/list">功能</a>
		<a href="${pageContext.request.contextPath }/login/login">注销</a>
	</div>
	<div>
		<form action="#" id="formId" onsubmit="event.preventDefault();">
		    <div><span>开始抢座位时间：</span>
		    <input type="number" name="year" value="2017" min="2017" max="2019" >
		    <input type="number" name="month" min="1" max="12"/>
		    <input type="number" name="day" min="1" max="31">
		    <input type="number" name="hour" min="0" max="24"/>
		    <input type="number" name="minute" min="0" max="60"/>
		    <input type="number" name="second" min="0" max="60"/>
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
				<span>座位:</span> <input type="text" name="seatName"> <input
					type="text" name="seatName"> <input type="text"
					name="seatName"> <input type="text" name="seatName">
				<input type="text" name="seatName"> <br><span style="color: red">（格式是A00,提供5个期望座位,暂时手动填,可以不填但至少要填一个）</span>
			</div>
			<div>
				<input type="submit" value="确定" onclick="bookSeat()">
			</div>
		</form>
	</div>

	<div id="result">
	   
	</div>
</body>
</html>