<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<body>
	<div id="wrap">
		<c:set var="directory" value="home"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<div id="main">
				<h2>${item.title}</h2>
				<table><th>Artist</th><th>Price</th><th>Category</th>
				<tr>
				<fmt:formatNumber var="price" value="${item.sellPrice}" pattern="0.00"/>
				<td>${leadSinger.name}</td><td>$${price}</td><td>${item.category}</td>
				</tr>
				</table>
				
				<table><th>Track List</th>
				<c:forEach var="song" items="${songs}">
				<tr>
				<td>${song.title}</td>
				</tr>
				</c:forEach>
				</table>
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
					
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>

</div> <!-- wrap -->

</body>
</html>
