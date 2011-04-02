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
								<td><input class="button" value="  View  " type="submit"/></td>
							</tr>
						</form>
					</c:forEach>
					<tr />
					<form method="post" action="/allegro/manager/createShipment"><tr>
						<td />
						<td><select name="in_store">
							<c:forEach var="store" items="${stores}">
								<option>${store.sname}</option>
							</c:forEach>
						</select></td>
						<td>
							<select name="in_year">
								<c:forEach var="i" begin="2011" end="2020"><option>${i}</option></c:forEach>
							</select>
							<select name="in_month">
								<c:forEach var="i" begin="1" end="12"><option>${i}</option></c:forEach>
							</select>
							<select name="in_day">
								<c:forEach var="i" begin="1" end="31"><option>${i}</option></c:forEach>
							</select>
						</td>
						<td><select name="in_supplier">
							<c:forEach var="supplier" items="suppliers">
								<option>${supplier.supname}</option>
							</c:forEach>
						</select></td>
						<td><input class="button" value=" Order " type="submit"/></td>
					</tr></form>
				</table>
				<br />
				<c:choose>
					<c:when test="${not empty sid}">
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
					</c:when>
					<c:otherwise>
					
					</c:otherwise>
				</c:choose>
			</div> <!-- main -->
		</div> <!-- content-wrap -->	
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div> <!-- wrap -->

</body>
</html>
