<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<body>
	<div id="wrap">
		<c:set var="directory" value="shipments"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<div id="main">
				<h2>Pending Shipments</h2>
				<c:if test="${not empty error}">
					<center><font color="red"><b>${error}</b></font></center>
				</c:if>
				<c:if test="${not empty message}">
					<center><font color="green"><b>${message}</b></font></center>
				</c:if>
				<table width="100%">
					<tr><th>Shipment ID</th><th>Destination</th><th>Order Date</th><th>Supplier</th></tr>
					<c:forEach var="shipment" items="${shipments}">
						<form method="post" action="/allegro/manager/viewShipment?sid=${shipment.sid}">
							<tr>
								<td>${shipment.sid}</td>
								<td>${shipment.sname}</td>
								<td>${shipment.orderDate}</td>
								<td>${shipment.supname}</td>
								<td><input class="button" value=" View " type="submit"/></td>
							</tr>
						</form>
					</c:forEach>
				</table>
				<br />
				<c:if test="${not empty sid}">
				<h2>Shipment #${sid}</h2>
					<table width="100%"><form method="post" action="/allegro/manager/receiveShipment?sid=${sid}">
						<tr><th>UPC</th><th>Quantity</th><th>Supply Price</th></tr>
						<c:forEach var="shipItem" items="${shipItems}">
							<tr>
								<td>${shipItem.upc}</td>
								<td>${shipItem.quantity}</td>
								<fmt:formatNumber var="supPrice" value="${shipItem.supPrice}" pattern="0.00"/>
								<td>$${supPrice}</td>
							</tr>
						</c:forEach>
						<tr />
						<tr><td align="center" colSpan="3"><input class="button" value=" Receive " type="submit"/></td></tr>
					</form></table>
				</c:if>
			</div> <!-- main -->
		</div> <!-- content-wrap -->	
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div> <!-- wrap -->

</body>
</html>
