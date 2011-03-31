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
					<tr><th>Item</th><th>UPC</th><th>Quantity</th><th>Price</th></tr>
					<c:set var="totalPrice" value="0" />
					<c:set var="index" value ="0"/>
					<c:forEach var="item" items="${profile.shoppingCart}">
						<tr>
							<td>${item.title}</td>
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
					</c:forEach>
				</table>
				<br />
				<h2>Payment</h2>
				<table>
					<c:if test="${not empty error}"><font color="red">${error}</font></c:if>
					<form method="post" action="/allegro/clerk/finalize">
						<tr>
							<td colSpan="2"><b>Store: </b><select name="in_store">
							<c:forEach var="store" items="${stores}">
								<option>${store.sname}</option>
							</c:forEach>
							</select></td>
						</tr>
						<tr />
						<tr>
							<td colSpan="2"><b>Card #: </b><input type="text" size="30" name="in_cardNum" /></td>
						</tr>
						<tr>
							<td><b>Expiry: </b>
								<select width="125" name="in_expMonth">
									<c:forEach var="i" begin="1" end="12"><option>${i}</option></c:forEach>
								</select>
								<select width="125" name="in_expYear">
									<c:forEach var="i" begin="2011" end="2020"><option>${i}</option></c:forEach>
								</select>
							</td>
							<td><input name="method" class="button" value="   Credit   " type="submit"/></td>
						</tr>
						<tr />
						<tr>
							<td><b>Amount: </b><input type="text" size="30" name="in_cash" /></td>
							<td><input name="method" class="button" value="    Cash    " type="submit" /></td>
						</tr>
					</form>
				</table>
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div> <!-- wrap -->

</body>
</html>