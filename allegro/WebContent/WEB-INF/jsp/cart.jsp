<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<body>
	<div id="wrap">
		<c:set var="directory" value="cart"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<div id="main">
				<h2>Shopping Cart</h2>
				<c:set var="totalPrice" value="0"/>
				<c:if test="${not empty error}">
						<center><font color="red"><b>${error}</b></font></center>
				</c:if>
					<c:choose>
						<c:when test="${checkout eq 'true'}">
							<table width="100%">
							<th>Title</th><th>Quantity</th><th>Price</th>
							<c:forEach var="item" items="${profile.shoppingCart}">
								<tr>
									<td>${item.title}</td>
									<td>${item.quantity}</td>
									<td>$${item.sellPrice}</td>
									<c:set var="totalPrice" value="${totalPrice + (item.sellPrice * item.quantity)}"/>
								</tr>
								
							</c:forEach>
							<tr />
							<tr>
								<fmt:formatNumber var="totalPrice" value="${totalPrice}" pattern="0.00"/>
								<td/><td align="right"><b>Total Price:</b></td><td>$${totalPrice}</td>
							</tr>
							</table>
						<br/>
						<br/>
						Please input your credit card number and expiry date to complete this transaction.
						<table width="100%"><form action="/allegro/customer/finalize">
						
						
						
						<tr>
						<td>Card Number: <input type="text" name="j_cardnum"/></td>
						<td>Expiry Date: 
						<select width="125" name="j_expMonth">
							<c:forEach var="i" begin="1" end="12">
							<option>${i}</option>
							</c:forEach>
						</select>
						<select width="125" name="j_expYear">
							<c:forEach var="i" begin="2011" end="2025">
							<option>${i}</option>
							</c:forEach>
						</select><br></td> 
						</tr>
						<tr>
						<td></td><td><input class="button" type="submit" value="Purchase"/></td>
						</tr>
						</table></form>
						
						</c:when>
						<c:when test="${not empty profile.shoppingCart}">
						
						<table width="100%">		
							<tr>
								<th>Item Name</th><th>Price</th><th>Quantity</th>
							</tr>
							
												
							<c:set var="counter" value ="0"/>
							<c:forEach var="item" items="${profile.shoppingCart}">
								<tr>
									<td>${item.title}</td>
									<td>$${item.sellPrice}</td>
									<td>${item.quantity}</td>
									<td><input class="button" type="submit" value="  Remove  " onclick="parent.location='/allegro/customer/removeItem?index=${counter}'" /></td>
									<c:set var="counter" value="${counter+1}"/>					
								</tr>
							</c:forEach>
							<tr />
							<tr><td colspan="3"/><td><input class="button" type="submit" value=" Checkout " onclick="parent.location='/allegro/customer/checkout'"/></td></tr>
						</table>
						
						
					</c:when>
					<c:otherwise>
						<br />
						<center><b>Your shopping cart is empty!</b></center>
						<br />
					</c:otherwise>
				</c:choose>
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
					
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>

</div> <!-- wrap -->

</body>
</html>
