<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<body>
	<div id="wrap">
		<c:set var="directory" value="search"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<%@ include file="/WEB-INF/jsp/sidebar.jsp" %>
			<div id="main">
				
				<h2>Search</h2>
				
				<table><center><form method="post" action="/allegro/customer/performSearch">			
					<tr>
						<td><label>Category:</label></td>
						<td colSpan="2"><select name="j_category">
							<option>Rock</option>
							<option>Country</option>
							<option>New Age</option>
							<option>Pop</option>
							<option>Classical</option>
							<option>Instrumental</option>
							<option>Rap</option>
							<option>All</option>
						</select></td>
					</tr>
					<tr><td><label>Title (Optional):</label></td><td><input type="text" size="30" name="j_title" /></td>
					</tr>
					<tr>
					<td><label>Lead Singer(Optional)</label></td><td><input type="text" size="30" name="j_leadSinger" /></td>
					</tr>
					<tr><td></td><td><input class="button" value=" Search " type="submit"/></td></tr>
				</form></center></table>
				
				<!-- Search results here -->
				
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
					
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>

</div> <!-- wrap -->

</body>
</html>
