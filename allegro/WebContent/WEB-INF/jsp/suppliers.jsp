<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<body>
	<div id="wrap">
		<c:set var="directory" value="suppliers"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<div id="main">
				
				<h2>Manage Suppliers</h2>
				
				<table width="100%">
					<form method="post">
						<tr><th>Name</th><th>Address</th><th>Phone</th><th>Status</th></tr>
						<tr><td>Amazon.com</td><td>1516 2nd Ave.<br />Seattle, WA 98101</td><td>(800) 201-7575</td><td>Active</td><td><input class="button" value=" Delete " size="3"/></td><td><input class="button" value=" Update " size="3"/>
						</tr>
						
						<tr><td>BadMusic Inc.</td><td>123 Fakestreet<br />Amsterdam, 9999</td><td>(111) 555-0678</td><td>Active</td><td><input class="button" value=" Delete " size="3"/></td><td><input class="button" value=" Update " size="3"/></tr>
						
						<tr>
							<td><input type="text" size="10" name="j_name" /></td>
							<td><input type="text" name="j_address" /></td>
							<td><input type="text" size="5" name="j_phone" /></td>
							<td><select>
								<option>Active</option>
								<option>Inactive</option>
							</select></td>
							<td><input class="button" value=" Add " size="3"/></td>
						</tr>
					</form>	
				</table>
				
			</div> <!-- main -->
			
		</div> <!-- content-wrap -->
					
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>

</div> <!-- wrap -->

</body>
</html>
