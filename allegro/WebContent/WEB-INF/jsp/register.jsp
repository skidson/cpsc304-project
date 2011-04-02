<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<body>
	<div id="wrap">
		<c:set var="directory" value="home"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<div id="main">
				<h2>Register</h2>
				
				<c:if test="${not empty error}">
						<center><font color="red"><b>${error}</b></font></center>
				</c:if>
				
				<center><table>
					<form method="post" action="/allegro/index/completeRegistration">
						<tr><td><label>Name:</label></td><td><input type="text" size = "30" name="j_name" /></td></tr>
						<tr><td><label>Username:</label></td><td><input type="text" size="30" name="j_username" /></td></tr>
						<tr><td><label>Address:</label></td><td><input type="text" size = "30" name="j_address" /></td></tr>
						<tr><td><label>Phone:</label></td><td><input type="text" size="30" name="j_phone" /></td></tr>
						<tr><td><label>Password:</label></td><td><input type="password" size="30" name="j_password" /></td></tr>
						
						<tr><td colSpan="2"><br /><br /><center><input class="button" value="          Submit          " type="submit" /></center></td><tr>
					</form>	
				</table></center>
				
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
					
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>

</div> <!-- wrap -->

</body>
</html>
