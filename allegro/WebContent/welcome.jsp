<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<body>
	<div id="wrap">
		<c:set var="directory" value="home"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		
		<div id="content-wrap">
		
			<%@ include file="/WEB-INF/jsp/sidebar.jsp" %>
				
			<div id="main">
				
				<h2>Welcome</h2>
				
				<table>
					<tr>
						<td><a href="<c:url value="/index/home?role=customer"/>"><img src="images/customer_logo.png" width="128" height="128" /></a></td>
						<td><a href="<c:url value="/index/home?role=clerk"/>"><img src="images/clerk_logo.png" width="128" height="128"/></a></td>
						<td><a href="<c:url value="/index/home?role=manager"/>"><img src="images/manager_logo.png" width="128" height="128"/></a></td>
					</tr>
					<tr>
						<td align="center"><a href="<c:url value="/index/home?role=customer"/>"><h1>Customer</h1></a></td>
						<td align="center"><a href="<c:url value="/index/home?role=clerk"/>"><h1>Clerk</h1></a></td>
						<td align="center"><a href="<c:url value="/index/home?role=manager"/>"><h1>Manager</h1></a></td>
					</tr>
				</table>
				
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
					
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>

</div> <!-- wrap -->

</body>
</html>
