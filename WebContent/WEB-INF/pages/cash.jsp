<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<div class="container">
		<div class="row">
			<div class="col-md-10 col-md-offset-3">
				<h3>结账系统</h3>
			</div>
		</div><br>
		
		<div class="row">
			<div class="col-md-6 col-md-offset-4">
				您一共买了  <span class="label label-primary">${sessionScope.ShoppingCart.bookNumber }</span> 本书
				应付:   <span class="label label-primary">￥ ${sessionScope.ShoppingCart.totalMoney }</span>
			</div>
		</div><br>
		
		<div class="row">
			<div class="col-md-6 col-md-offset-4">
				<c:if test="${requestScope.errors != null }">
					<font color="red">${requestScope.errors }</font>
				</c:if>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-7 col-md-offset-3">
				<form class="form-horizontal" action="bookServlet?method=cash" method="post">
					<div class="form-group">
				    	<label class="col-sm-2 control-label">信用卡姓名</label>
					    <div class="col-sm-5">
					    	<input type="text" name="username" class="form-control"
					    		value="${requestScope.username }" placeholder="信用卡姓名">
					    </div>
				  	</div>
				  	
					<div class="form-group">
				    	<label class="col-sm-2 control-label">信用卡账户</label>
					    <div class="col-sm-5">
					    	<input type="text" name="accountId" class="form-control" placeholder="信用卡账户">
					    </div>
				  	</div>

				    <div class="form-group">
					    <label class="col-sm-2 control-label">信用卡密码</label>
					    <div class="col-sm-5">
					    	<input type="password" class="form-control" placeholder="Password">
					    </div>
				  	</div>
				  
					<div class="form-group">
					    <div class="col-sm-offset-2 col-sm-10">
					      <button type="submit" class="btn btn-default">提交</button>
					    </div>
					</div>
				</form>
			</div>
		</div>
	</div>

</body>
</html>