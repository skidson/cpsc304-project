<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<body>
	<div id="wrap">
		<c:set var="directory" value="reports"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<div id="main">
				<c:if test="${not empty error}">
					<center><font color="red"><b>${error}</b></font></center>
				</c:if>
				
				<!-- Store Report -->
				<c:if test="${not empty store}">
					<h2>${store}: ${reportDate}</h2>
					<table>
						<tr><th>UPC</th><th>Category</th><th>Price</th><th>Sold</th><th>Total Value</th></tr>
						<c:forEach var="group" items="${groups}">
							<c:forEach var="item" items="${group}">
								<tr>
									<td>${item.upc}</td>
									<td>${item.category}</td>
									<td>$${item.sellPrice}</td>
									<td>${item.quantity}</td>
									<fmt:formatNumber var="subPrice" value="${item.sellPrice * item.quantity}" pattern="0.00"/>
									<td>$${subPrice}</td>
									<c:set var="catQuantity" value="${catQuantity + item.quantity}"/>
									<c:set var="catPrice" value="${catPrice + item.sellPrice}"/>
								</tr>
							</c:forEach>
							<fmt:formatNumber var="subPrice" value="${catPrice}" pattern="0.00"/>
							<tr><td colspan="3" align="right"><b>Total:</b></td><td>${catQuantity}</td><td>$${subPrice}</td></tr>
							<tr />
							<c:set var="totalQuantity" value="${totalQuantity + catQuantity}"/>
							<c:set var="totalPrice" value="${totalPrice + catPrice}" />
							<c:set var="catQuantity" value="0"/>
							<c:set var="catPrice" value="0"/>
						</c:forEach>
						<tr />
						<fmt:formatNumber var="subPrice" value="${totalPrice}" pattern="0.00"/>
						<tr><td colspan="3" align="right"><b>Total Daily Sales:</b></td><td>${totalQuantity}</td><td>$${subPrice}</td></tr>
					</table>
				</c:if>
				
				<h2>Generate Store Report</h2>
				<table><form method="post" action="/allegro/manager/storeReport">
					<tr><th>Store</th><th>Date</th></tr>
					<tr>
						<td>
							<select name="in_sname">
								<c:forEach var="store" items="${stores}"><option>${store.sname}</option></c:forEach>
							</select>
						</td>
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
						<td colSpan="2">
							<input class="button" value=" Generate Report " type="submit"/>
						</td>
					</tr>
				</form></table>
				<br />
				
				<h2>Generate Sales Report</h2>
				<table><form method="post" action="/allegro/manager/salesReport">
					<tr><th>Number of Entries</th><th>Date</th></tr>
					<tr>
						<td><input type="text" size="10" name="in_num" value="10" /></td>
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
						<td colSpan="2">
							<input class="button" value=" Generate Report " type="submit"/>
						</td>
					</tr>
				</form></table>
				
				<c:if test="${not empty numEntry}">
					<h2>Top ${numEntry} Item Sales: ${reportDate}</h2>
					<table>
						<tr><th>Item</th><th>Company</th><th>Current Stock</th><th>Sold</th></tr>
						<c:forEach var="item" items="${items}">
							<tr>
								<td>${item.title}</td>
								<td>${item.company}</td>
								<td>${item.stock}</td>
								<td>${item.quantity}</td>
							</tr>
						</c:forEach>
					</table>
				</c:if>
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div> <!-- wrap -->

</body>
</html>
