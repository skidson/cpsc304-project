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
				
				<h2>Login</h2>
					<br>
					<center>
					<table><tr>
					<c:if test="${not empty param.error}">
						<font color="red">Login error: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</font>
					</c:if>
					<form method="post" action="<c:url value="/j_spring_security_check" />">			
						<label>Username:</label><input type="text" size = "30" name="j_username" />
						<label>Password:</label><input type="password" size="30" name="j_password" /> <br />
						<a href="register.html?error=false">Register</a> | <a href="<c:url value="/basic/forgotPassword"/>">Forgot your password?</a>
						<br />
						<label><input type="checkbox" name="_spring_security_remember_me" /> Remember me</label>
						<center><input class="button" value="          Login          " type="submit" /></center>
					</form>		
				</tr></table>
				</center>
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->
		 
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>

</div> <!-- wrap -->

</body>
</html>
