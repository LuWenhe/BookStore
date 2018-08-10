<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="script/jqeury/jquery-3.3.1.js"></script>
<link type="text/css" rel="stylesheet" href="script/bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script type="text/javascript" src="script/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
	
	<center>
		<br><br>
		<form class="form-inline" action="userServlet?method=getUser" method="post">
			<div class="form-group">
		    	<label>Username</label>
		    	<input type="text" class="form-control" name="username" placeholder="姓名">
		  	</div>
			
			<button type="submit" class="btn btn-success">提交</button>
		</form>
	</center>
	
</body>
</html>