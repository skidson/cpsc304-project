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
				<a href="<c:url value="/index/test"/>">Test</a>
				<table>
					<tr>
						<td><a href="<c:url value="/customer/home"/>"><img src="<c:url value="/images/customer_logo.png"/>" width="128" height="128" /></a></td>
						<td><a href="<c:url value="/clerk/home"/>"><img src="<c:url value="/images/clerk_logo.png"/>" width="128" height="128"/></a></td>
						<td><a href="<c:url value="/manager/home"/>"><img src="<c:url value="/images/manager_logo.png"/>" width="128" height="128"/></a></td>
					</tr>
					<tr>
						<td align="center"><a href="<c:url value="/customer/home"/>"><h1>Customer</h1></a></td>
						<td align="center"><a href="<c:url value="/clerk/home"/>"><h1>Clerk</h1></a></td>
						<td align="center"><a href="<c:url value="/manager/home"/>"><h1>Manager</h1></a></td>
					</tr>
				</table>
				
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
					
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>

</div> <!-- wrap -->

</body>
</html>
