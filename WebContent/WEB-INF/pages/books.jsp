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
<style type="text/css">
	.info_right {
	    margin-left: 35px;
	}
</style>
<script type="text/javascript">
	
	$(function(){
		
		$("#pageNo").change(function(){
			var val = $(this).val();
			val = $.trim(val);
			
			//1. 校验 val 是否是数字 1, 2, 而不是 a12, b
			var flag = false;
			var reg = /^\d+$/g;
			
			if(reg.test(val)){
				//2. 校验 val 在一个合法的范围内, 1 - totalPageNumber
				pageNo = parseInt(val);
				if(pageNo >= 1 && pageNo <= parseInt("${bookPage.totalPageNumber }")){
					flag = true;
				}
			}
			
			if(!flag){
				alert('输入的不是合法的页码');
				$(this).val('');
				return;
			}
			
			//3. 页面跳转
			var href = "bookServlet?method=getBooks&pageNo=" + pageNo + "&" + $(":hidden").serialize();
			window.location.href = href;
		});
	})
	
</script>
<%@ include file="/commons/queryCondition.jsp" %>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-md-10 col-md-offset-2">
				<h1>简易商城系统</h1>
			</div>
		</div>

		<div class="row">
			<div class="col-md-10 col-md-offset-7">
				<form action="bookServlet?method=getBooks" method="post">
					<div class="form-group">
						Price: 
						<input class="input-mini" size="2" type="text" name="minPrice"/> -
						<input class="input-mini" size="2" type="text" name="maxPrice"/>
						
						<input type="submit" value="Submit" class="btn btn-success btn-sm"/>
					</div>
	 			</form>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-10 col-md-offset-3">
				<c:if test="${param.title != null }">
				您已经将 ${param.title } 放入到购物车中.
				</c:if>
				
				<c:if test="${!empty sessionScope.ShoppingCart.books }">
				您的购物车中有 ${sessionScope.ShoppingCart.bookNumber }本书, <a href="bookServlet?method=forwardPage&page=cart&pageNo=${bookPage.pageNo }">查看购物车</a>
				</c:if>
			</div>
		</div><br>
		
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<table class="table">
					<tr>
						<th>编号</th>
						<th>书名</th>
						<th>作者</th>
						<th>发行日期</th>
						<th>价格</th>
						<th>评价</th>
						<th>操作</th>
					</tr>
		 			<!-- list 表示 getList() 方法 -->
		 			<c:forEach items="${bookPage.list }" var="book">
		 				<tr>
		 					<td>${book.id }</td>
		 					<td><a href="bookServlet?method=getBook&pageNo=${bookPage.pageNo }&id=${book.id }">${book.title }</a></td>
		 					<td>${book.author }</td>
		 					<td>${book.publishingDate }</td>
		 					<td>${book.price }</td>
		 					<td>${book.remark }</td>
		 					<td><a href="bookServlet?method=addTocart&pageNo=${bookPage.pageNo }&id=${book.id }&title=${book.title }">加入购物车</a></td>
		 				</tr>
		 			</c:forEach>
	 			</table>
			</div>
		</div>
			
		<br>
		
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="info_left">
					共 <span class="label label-default">${bookPage.totalPageNumber }</span> 页
					&nbsp;&nbsp;
					当前第 <span class="label label-default">${bookPage.pageNo }</span> 页
					&nbsp;&nbsp;
					转到 <input type="text" size="1" id="pageNo"/> 页
				</div>
				
				<div class="info_right">
					<nav aria-label="Page navigation">
						<ul class="pagination">
							<li><a href="bookServlet?method=getBooks&pageNo=1">首页</a></li>
							
							<li><a href="bookServlet?method=getBooks&pageNo=${bookPage.prevPage }">
								<span aria-hidden="true">&laquo;</span></a></li>
	
							<li><a href="bookServlet?method=getBooks&pageNo=${bookPage.nextPage }">
								<span aria-hidden="true">&raquo;</span></a></li>
	
							<li><a href="bookServlet?method=getBooks&pageNo=${bookPage.totalPageNumber }">末页</a></li>
						</ul>
					</nav>
				</div>
			</div>
			
		</div>
	</div>
</body>
</html>