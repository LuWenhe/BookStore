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
		<c:if test="${requestScope.user != null }">
	
		<div class="row">
			<div class="col-md-7 col-md-offset-3">
				<h4>姓名: ${user.username }</h4>
			</div>
		</div>
		<br>
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
			<c:forEach items="${user.trades }" var="trade">
				
				<span class="label label-primary">${trade.tradeTime }</span>
				<br><br>
				<table class="table">
					<tr>
						<th>书名</th>
						<th>作者</th>
						<th>价格</th>
						<th>数量</th>
						<th>交易时间</th>
					</tr>
					
					<c:forEach items="${trade.items }" var="item">
						<tr>
							<td>${item.book.title }</td>
							<td>${item.book.author }</td>
							<td>${item.book.price }</td>
							<td>${item.quantity }</td>
							<td>${item.book.publishingDate }</td>
						</tr>
					</c:forEach>
				</table>
				
			</c:forEach>		
			</div>
		</div>
			
		</c:if>
		
	</div>
	
</body>
</html>