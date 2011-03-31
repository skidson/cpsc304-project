<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<body>
	<div id="wrap">
		<c:set var="directory" value="purchase"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<div id="main">
				<h2>Purchase</h2>
				<table width="100%">
					<tr><th>UPC</th><th>Item</th><th>Quantity</th><th>Price</th></tr>
					<c:set var="totalPrice" value="0" />
					<c:forEach var="item" items="${profile.shoppingCart}">
						<tr>
							<td>${item.upc}</td>
							<td>${item.title}</td>
							<td>${item.quantity}</td>
							<fmt:formatNumber var="subPrice" value="${item.sellPrice * item.quantity}" pattern="0.00"/>
							<td>$${subPrice}</td>
							<c:set var="totalPrice" value="${totalPrice + subPrice}" />
						</tr>
					</c:forEach>
					<tr>
						<td colSpan="3" />
						<td><b>Total: $${totalPrice}</b></td>
					</tr>
					<tr><form method="post" action="/allegro/clerk/addPurchase">
						<td />
						<td><input type="text" size="30" name="in_upc" /></td>
						<td><input type="text" size="10" name="in_qty" value="1" /></td>
						<td align="center"><input class="button" value="    Add Item   " type="submit"/></td>
					</form></tr>
				</table>
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div> <!-- wrap -->

</body>
</html>