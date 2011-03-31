<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<body>
	<div id="wrap">
		<c:set var="directory" value="search"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<div id="main">
				
				<h2>Search Results</h2>
				
				<table><center>
					<th>UPC</th><th>Name</th><th>Category</th><th>Lead Singer</th><th>Quantity</th>		
					<c:forEach var="item" items="${results}">
					<form method="post" action="/allegro/customer/updateCart?upc=${item.upc}">
						<tr><td>${item.upc}</td><td>${item.name}</td><td>${item.category}</td>
							<td><input type="text" name="j_quantity"></input></td>
							<td><input class="button" type="submit" value='Add to cart'/>  </td>
						</tr>
					</form>
					</c:forEach>
				</center></table>
				
				<!-- Search results here -->
				
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
					
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>

</div> <!-- wrap -->

</body>
</html>
