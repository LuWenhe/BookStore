<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<center>
		<c:if test="${requestScope.user != null }">
			<br/>
			<h3>User: ${user.username }</h3>
			
			<br/>

			<c:forEach items="${user.trades }" var="trade">
				
				<h4>TradeTime: ${trade.tradeTime }</h4>
				
				<table cellpadding="10" cellspacing="0">
					
					<tr>
						<th>Title</th>
						<th>Price</th>
						<th>Quantity</th>
					</tr>
					
					<c:forEach items="${trade.items }" var="item">
						<tr>
							<td>${item.book.title }</td>
							<td>Price: ${item.book.price }</td>
							<td>Quantity: ${item.quantity }</td>
						</tr>
					</c:forEach>
					
				</table>
				<br/>
			</c:forEach>		
			
		</c:if>
	</center>
	
</body>
</html>