<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<body>
	<div id="wrap">
		<c:set var="directory" value="search"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<div id="main">
				
				<h2>Debug</h2>
				<c:if test="${not empty error}">
						<center><font color="red"><b>${error}</b></font></center>
				</c:if>
				
				<table><form method="post" action="/allegro/index/getTable">			
					<tr>
						<td><label>Table:</label></td>
						<td colSpan="2"><select name="j_table">
							<option>Customer</option>
							<option>HasSong</option>
							<option>Item</option>
							<option>LeadSinger</option>
							<option>Purchase</option>
							<option>PurchaseItem</option>
							<option>Refund</option>
							<option>ShipItem</option>
							<option>Shipment</option>
							<option>Store</option>
							<option>Stored</option>
							<option>Supplier</option>
						</select></td>
					</tr>
					<tr><td></td><td><input class="button" value=" Get Table " type="submit"/></td></tr>
				</form></table>
				<c:if test="${val eq 'true'}">
					<table>
					<c:forEach var="item" items="${rows}">
					<tr>
						<c:forEach var="itemParam" items="${item.parameters}">
							<td>${itemParam}</td>
						</c:forEach>
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
