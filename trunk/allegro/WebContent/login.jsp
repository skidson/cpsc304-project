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
				
				<h2><a href="login.html">Login</a></h2>
				
				<table><tr><center><form method="post">			
					<label>Username:</label><input type="text" size = "30" name="j_username" />
					<label>Password:</label><input type="password" size="30" name="j_password" /> <br />
					<a href="register.html">Register</a> | <a href="password.html">Forgot your password?</a>
					<br /><br />
					<label><input type="checkbox" name="_spring_security_remember_me" /> Remember me</label>
					<center><input class="button" value="          Login          " type="submit" /></center>
				</form></center></tr></table>
				
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
