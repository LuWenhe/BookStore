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
<script type="text/javascript">
	
	$(function(){
		$(".delete").click(function(){
			var $tr = $(this).parent().parent();	//tr
			var title = $.trim($tr.find("td:first").text());
			var flag = confirm("确定要删除" + title + "的信息吗?");
			
			if(flag){
				return true;
			}
			
			return false;
		});
		
		//ajax 修改单个商品的数量
		//1. 获取页面中所有的 text, 并为其添加 onchange 响应函数, 弹出对话框: 确定要修改吗?
		$("input:text").change(function(){
			var quantityVal = $.trim($(this).val());
			var quantity = parseInt(quantityVal);
			
			var flagR = true;
			var reg = /^\d+$/g;
			if(!reg.test(quantityVal)){
				flagR = false;
			}
			
			if(!flagR){
				alert("输入的数量不合法");
				$(this).val($(this).attr("class"));
				return;
			}
			
			var $tr = $(this).parent().parent();
			var title = $.trim($tr.find("td:first").text());
			
			if(quantity == 0){
				var flagR1 = confirm("确定要删除" + title + "的信息吗?");
				if(flagR1){
					//得到了 a 节点
					var $a = $tr.find("td:last").find("a");
					//执行 a 节点的 onclick 响应函数
					$a[0].onclick()
					return;
				}
			}
			
			var flag = confirm("确定要修改" + title + "的数量吗?");
			
			//如果是取消, 则跳出
			if(!flag){
				$(this).val($(this).attr("class"));
				return;
			}

			//2. 请求地址为: bookServlet
			var url = "bookServlet?method=updateItemQuantity";
			
			//3. 请求参数为: method: updateItemQuantity, id:name 的属性值, quantity:val, time:new Date()
			var bookId = $.trim(this.name);
			var args = {"id": bookId, "quantity": quantityVal, "time": new Date()};
			
			//4. 在 updateItemQuantity 方法中, 获取 quantity, id, 在获取购物车对象调用 service 的方法作修改
			//5. 传回 JSON 数据, bookNumber: xx, totalMoney
			
			//6. 更新当前页面的 
			//	 bookNumber: 购物车中的图书总量 和 totalMoney: 购物车中的图书金额
			$.getJSON(url, args, function(data){
				var bookNumber = data.bookNumber;
				var totalMoney = data.totalMoney;
	
				$("#totalMoney").text("总金额:￥" + totalMoney);
				$("#bookNumber").text("您的购物车中共有" + bookNumber + "本书");
			});
		});
		
	})

</script>
<%@ include file="/commons/queryCondition.jsp" %>
</head>
<body>
	
	<div class="container">
		<div class="row">
			<div class="col-md-10 col-md-offset-3">
				<h3>购物车列表</h3>
			</div>
		</div><br>
		
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<table class="table">
					<tr>
						<th>书名</th>
						<th>数量</th>
						<th>价格</th>
						<th>操作</th>
					</tr>
					
					<c:forEach items="${sessionScope.ShoppingCart.items }" var="item">
						<tr>
							<td>${item.book.title }</td>
							<td>
								<input class="${item.quantity }" type="text" size="2" name="${item.book.id }" value="${item.quantity }"/>
							</td>
							<td>${item.book.price }</td>
							<td>
								<a class="btn btn-danger btn-sm delete" href="bookServlet?method=remove&pageNo=${param.pageNo }&id=${item.book.id }">删除</a>
							</td>
						</tr>
					</c:forEach>
			
					<tr>
						<td colspan="2" id="totalMoney">
							<strong>总金额: ${sessionScope.ShoppingCart.totalMoney }</strong>
						</td>
						
						<td colspan="2" id="bookNumber">
							<strong>您的购物车中共有: ${sessionScope.ShoppingCart.bookNumber } 本书</strong>
						</td>
					</tr>
					
				</table>
			</div>
		</div>
		
		<div class="row"> 
			<div class="col-md-6 col-md-offset-4">
				<a class="btn btn-primary" href="bookServlet?method=getBooks&pageNo=${param.pageNo }">继续购物</a>
				&nbsp;&nbsp;
				
				<a class="btn btn-info" href="bookServlet?method=clear">清空购物车</a>
				&nbsp;&nbsp;
				
				<a class="btn btn-success" href="bookServlet?method=forwardPage&page=cash">结账</a>
				&nbsp;&nbsp;
			</div>
		</div>
	</div>
		
	
</body>
</html>