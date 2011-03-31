<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<body>
	<div id="wrap">
		<c:set var="directory" value="refund"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<div id="main">
				
				<h2>Refund</h2>
				<table width=100%>
				<tr><td>Please enter the receipt number: <input type="text" name="j_receiptID"/></td>
				<td><input class="button" value=" Return Item" type="submit"/></td>
				</table>
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div> <!-- wrap -->

</body>
</html>
