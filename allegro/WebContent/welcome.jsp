<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<body>
<!-- wrap starts here -->
	<div id="wrap">
		<c:set var="directory" value="home"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		
		<!-- content-wrap starts here -->
		<div id="content-wrap">
		
			<%@ include file="/WEB-INF/jsp/sidebar.jsp" %>
				
			<div id="main">
				
				<h2>Welcome</h2>
				
				<table>
					<tr>
						<td><a href="/login?role=customer"><img src="images/customer_logo.png" width="128" height="128" /></a></td>
						<td><a href="/login?role=clerk"><img src="images/clerk_logo.png" width="128" height="128"/></a></td>
						<td><a href="/login?role=manager"><img src="images/manager_logo.png" width="128" height="128"/></a></td>
					</tr>
					<tr>
						<td><center><a href="/customer/home"><h1>Customer</h1></a></center></td>
						<td><center><a href="/clerk/home"><h1>Clerk</h1></a></center></td>
						<td><center><a href="/manager/home"><h1>Manager</h1></center></a></td>
					</tr>
				</table>
				
			</div>
		
		<!-- content-wrap ends here -->	
		</div>
					
		<!--footer starts here-->
		<div id="footer">
			
			<p>
			&copy; 2011 <strong>Allegro Music Ltd.</strong>
			
   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
		</div>	

<!-- wrap ends here -->
</div>

</body>
</html>
