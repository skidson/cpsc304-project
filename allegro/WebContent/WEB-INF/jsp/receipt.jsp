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
				<h2>Receipt</h2>
				<table width="100%">
					<tr><th>UPC</th><th>Quantity</th><th>Price</th></tr>
					<c:set var="totalPrice" value="0" />
					<c:set var="index" value ="0"/>
					<c:forEach var="item" items="${items}">
						<tr>
							<td>${item.upc}</td>
							<td>${item.quantity}</td>
							<fmt:formatNumber var="subPrice" value="${item.sellPrice * item.quantity}" pattern="0.00"/>
							<td>$${subPrice}</td>
							<c:set var="index" value="${index+1}"/>
							<c:set var="totalPrice" value="${totalPrice + subPrice}" />
						</tr>
						<tr />
						<tr>
							<td colSpan="2" />
							<fmt:formatNumber var="totalPrice" value="${totalPrice}" pattern="0.00"/>
							<td align="right"><b>Total: </b></td>
							<td><b>$${totalPrice}</b></td>
							<td />
						</tr>
						<tr />
					</c:forEach>
					<c:if test="${not empty paid}">
						<tr>
							<td colSpan="2" />
							<fmt:formatNumber var="paid" value="${paid}" pattern="0.00"/>
							<td align="right"><b>Paid: </b></td>
							<td><b>$${paid}</b></td>
							<td />
						</tr>
						<tr>
							<td colSpan="2" />
							<fmt:formatNumber var="change" value="${paid - total}" pattern="0.00"/>
							<td align="right"><b>Change: </b></td>
							<td><b>$${change}</b></td>
							<td />
						</tr>
					</c:if>
					<c:if test="${not empty purchase.cardNum}" >
						<tr>
							<td align="right"><b>Card #: </b></td>
							<td>${purchase.cardNum}</td>
						</tr>
					</c:if>
				</table>
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div> <!-- wrap -->

</body>
</html>