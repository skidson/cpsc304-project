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
				<c:if test="${not empty param.error}">
						<center><font color="red"><b>${param.error}</b></font></center>
				</c:if>
				<c:if test="${not empty error}">
						<center><font color="red"><b>${error}</b></font></center>
				</c:if>
				
				<c:if test="${not empty message}">
					<br/>
					<center><font color="green"><b>${message} 
					<fmt:formatNumber var="refundPrice" value="${refundPrice}" pattern="0.00"/>
					<c:if test="${type eq 'credit'}"> Your credit card has been credited $${refundPrice}.</c:if>
					<c:if test="${type eq 'cash'}"> Refund $${refundPrice}.</c:if></b></font></center>
					<br/>
				</c:if>
				<table><form action="/allegro/clerk/getPurchase">
					<tr>
						<td>Please enter a receipt ID</td>
						<td><input type="text" name="in_receiptID" value ="${receiptID}"/></td>
						<td><select name="in_store">
						<c:forEach var="store" items="${stores}">
							<option>${store.sname}</option>
						</c:forEach>
						</select>
				</td>
						<td><input class="button" type="submit" value=" Get Purchase "/></td>
					</tr>
				</form></table>
				<c:if test="${not empty items}">
					<h2>Items : </h2>
					<table width="100%">
					<tr>
					</tr>
					<tr><th>UPC</th><th>Title</th><th>Price</th><th>Quantity</th><th>Refund Quantity</th></tr>
					<c:forEach var="item" items="${items}">
						<c:if test="${item.quantity ne 0}">
							<form method="post" action="/allegro/clerk/refundItem?upc=${item.upc}&j_receiptID=${receiptID}&in_store=${store}&in_price=${item.sellPrice}">
								<tr>
									<td>${item.upc}</td>
									<td>${item.title}</td>
									<td>${item.sellPrice}</td>
									<td>${item.quantity}</td>
									<c:set var="isempty" value="false"/>
									<td><input type="text" name="in_quantity"/></td>
									<td><input class="button" value=" Refund this item " type="submit"/></td>
								</tr>
							</form>
						</c:if>
					</c:forEach>
					</table>
				</c:if>
				<c:if test="${isempty ne 'false' and not empty items}">
					<br/>
					<center><font color="red"><b>All the items on this order have been returned.</b></font></center>
					<br/>
				</c:if>
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div> <!-- wrap -->

</body>
</html>
