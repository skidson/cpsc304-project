<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<body>
	<div id="wrap">
		<c:set var="directory" value="home"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<div id="main">
				<h2>${item.title}</h2>
				<table><th>Artist</th><th>Price</th><th></th>
				<tr>
				<td>${leadSinger.name}</td><td>${item.sellPrice}</td>
				</tr>
				</table>
				
				<table><th>Track List</th>
				<c:forEach var="song" items="songs">
				<tr>
				${song.name}
				</tr>
				</c:forEach>
				</table>
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
					
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>

</div> <!-- wrap -->

</body>
</html>
