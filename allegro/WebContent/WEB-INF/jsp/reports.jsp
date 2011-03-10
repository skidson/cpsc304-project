<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<body>
	<div id="wrap">
		<c:set var="directory" value="reports"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<%@ include file="/WEB-INF/jsp/sidebar.jsp" %>
			<div id="main">
				
				<h2>Generate Store Report</h2>
				<table><form method="post" action="">
					<tr>
						<td colSpan="2">
						<b>Store:</b>
							<select>
								<option>#1 - Marine & Byrne</option>
								<option>#2 - Coquitlam</option>
								<option>#3 - Vancouver</option>
								<option>#4 - Surrey</option>
							</select><br>
						</td>
					<tr>
						<td colSpan="2">
							<b>Report Type:</b><br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="group1" value="Daily" checked>Daily<br>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="group1" value="Weekly">Weekly<br><br>
						</td>
					</tr>
					<tr>
						<td><b>Start Date:</b><br><iframe src="https://www.google.com/calendar/embed?title=Start%20Date&amp;showTitle=0&amp;showPrint=0&amp;showTabs=0&amp;showCalendars=0&amp;showTz=0&amp;height=200&amp;wkst=1&amp;bgcolor=%23FFFFFF&amp;src=en.canadian%23holiday%40group.v.calendar.google.com&amp;color=%23B1440E&amp;ctz=America%2FVancouver" style=" border-width:0 " width="200" height="200" frameborder="0" scrolling="no"></iframe></td> 
						<td><b>End Date:</b><br><iframe src="https://www.google.com/calendar/embed?title=Start%20Date&amp;showTitle=0&amp;showPrint=0&amp;showTabs=0&amp;showCalendars=0&amp;showTz=0&amp;height=200&amp;wkst=1&amp;bgcolor=%23FFFFFF&amp;src=en.canadian%23holiday%40group.v.calendar.google.com&amp;color=%23B1440E&amp;ctz=America%2FVancouver" style=" border-width:0 " width="200" height="200" frameborder="0" scrolling="no"></iframe></td>
					</tr>
					<tr><td colSpan="2"><br><br><br><center><input class="button" value=" Generate Report " type="submit"/><center></td></tr>
				</form></table>
				
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div> <!-- wrap -->

</body>
</html>
