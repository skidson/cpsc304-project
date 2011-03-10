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
				
				<h2><a href="welcome.html">Register</a></h2>
				
				<center><table>
					<form method="post">
						<tr><td><label>First Name:</label></td><td><input type="text" size = "30" name="j_firstname" /></td></tr>
						<tr><td><label>Last Name:</label></td><td><input type="text" size = "30" name="j_lastname" /></td></tr>
						<tr><td><label>Address:</label></td><td><input type="text" size = "30" name="j_address" /></td></tr>
						<tr><td><label>Email Address:</label></td><td><input type="text" size = "30" name="j_address" /></td></tr>
						<tr><td colSpan="2"><br /></td></tr>
						<tr><td><label>Username:</label></td><td><input type="text" size = "30" name="j_username" /></td></tr>
						<tr><td><label>Password:</label></td><td><input type="password" size="30" name="j_password" /></td></tr>
						<tr><td><label>Verify Password:</label></td><td><input type="password" size="30" name="j_password2" /></td></tr>
						
						<tr><td colSpan="2"><br /><br /><center><input class="button" value="          Submit          " type="submit" /></center></td><tr>
					</form>	
				</table></center>
				
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
