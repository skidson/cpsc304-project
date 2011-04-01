<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<body>
	<div id="wrap">
		<c:set var="directory" value="suppliers"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<div id="main">
				
				<h2>Manage Suppliers</h2>
				
				<table width="100%">
					<tr><th>Supplier Name</th><th>Address</th><th>City</th><th>Status</th></tr>
					<c:forEach var="supplier" items="${suppliers}">
						<form method="post" action="/allegro/manager/removeSupplier?supname=${supplier.supname}">
							<tr>
								<td>${supplier.supname}</td>
								<td>${supplier.address}</td>
								<td>${supplier.city}</td>
								<td><c:choose>
									 <c:when test="${supplier.status eq 0}">Active</c:when>
									 <c:otherwise>Inactive</c:otherwise>
								 </c:choose></td>
								<td><input class="button" value=" Remove " type="submit"/></td>
							</tr>
						</form>
					</c:forEach>
					<form method="post" action="/allegro/manager/addSupplier">
						<tr>
							<td><input type="text" name="in_supname" /></td>
							<td><input type="text" name="in_address" /></td>
							<td><input type="text" name="in_city" /></td>
							<td><select name="in_status">
								<option value="0">Active</option>
								<option value="1">Inactive</option>
							</select></td>
							<td><input class="button" value="     Add     " type="submit"/></td>
						</tr>
					</form>
				</table>
				
			</div> <!-- main -->
			
		</div> <!-- content-wrap -->
					
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>

</div> <!-- wrap -->

</body>
</html>
