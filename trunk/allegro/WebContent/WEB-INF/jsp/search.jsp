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
				
				<table><center><form method="post">			
					<tr>
						<td><label>Category:</label></td>
						<td colSpan="2"><select>
							<option>Albums</option>
							<option>Books</option>
							<option>DVD</option>
							<option>Sheet Music</option>
						</select></td>
					</tr>
					<tr><td><label>Keyword:</label></td><td><input type="text" size="30" name="j_password" /></td>
						<td colSpan="2"><center><input class="button" value="          Search          " type="submit" /></center></td>
					</tr>
				</form></center></table>
				
				<!-- Search results here -->
				
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
					
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>

</div> <!-- wrap -->

</body>
</html>
