<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<body>
	<div id="wrap">
		<c:set var="directory" value="reports"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<div id="main">
				
				<h2>Generate Store Report</h2>
				<table><form method="post" action="/allegro/">
					<tr>
						<td colSpan="2">
						<b>Store:</b>
							<select width="125">
								<option>Marine & Byrne</option>
								<option>Coquitlam</option>
								<option>Vancouver</option>
								<option>Surrey</option>
							</select><br>
						</td>
					<tr>
						<td><b>Date:</b>
							<select>
								<c:forEach var="i" begin="1" end="31">
								<option>${i}</option>
								</c:forEach>
							</select>
							<select>
								<option>January</option>
								<option>February</option>
								<option>March</option>
								<option>April</option>
								<option>May</option>
								<option>June</option>
								<option>July</option>
								<option>August</option>
								<option>September</option>
								<option>October</option>
								<option>November</option>
								<option>December</option>
							</select>
							<select width="125">
								<c:forEach var="i" begin="2000" end="2011">
								<option>${i}</option>
								</c:forEach>
							</select><br></td> 
					</tr>
					<tr><td colSpan="2"><br><br><br><center><input class="button" value=" Generate Report " type="submit"/><center></td></tr>
				</form></table>
				
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div> <!-- wrap -->

</body>
</html>
