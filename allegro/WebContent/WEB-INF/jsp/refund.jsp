<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<body>
	<div id="wrap">
		<c:set var="directory" value="refund"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<div id="main">
				
				<h2>Refund</h2>
				<c:if test="${not empty error}">
						<tr><td colSpan="2"><font color="red"><b>${error}</b></font></td></tr>
				</c:if>
				<c:if test="${not empty message}">
						<tr><td colSpan="2"><font color="green"><b>${message}</b></font></td></tr>
				</c:if>
				<table><form action="/allegro/clerk/getPurchase">
					<tr>
						<td>Please enter a receipt ID</td>
						<td><input type="text" name="in_receiptID"/></td>
						<td><select name="in_store">
						<c:forEach var="store" items="${stores}">
							<option>${store.sname}</option>
						</c:forEach>
						</select>
				</td>
						<td><input class="button" type="submit" value=" Get Purchase "/></td>
					</tr>
				</form></table>
				<c:if test="${not empty receiptID}">
				<h2>Items : </h2>
				<table width="100%">
				<tr>
				
				</tr>
					<tr><th>UPC</th><th>Title</th><th>Price</th><th>Quantity</th><th>Refund Quantity</th></tr>
					<c:forEach var="item" items="${items}">
						<c:if test="${item.quantity ne 0}">
							<form method="post" action="/allegro/clerk/refundItem?upc=${item.upc}&j_receiptID=${receiptID}&in_store=${store}">
								<tr>
									<td>${item.upc}</td>
									<td>${item.title}</td>
									<td>${item.sellPrice}</td>
									<td>${item.quantity}</td>
									<td><input type="text" name="in_quantity"/></td>
									<td><input class="button" value=" Refund this item " type="submit"/></td>
								</tr>
							</form>
						</c:if>
					</c:forEach>
				</table>
				</c:if>
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div> <!-- wrap -->

</body>
</html>
