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

				<c:choose>
					<c:when test="${not empty error}">
						${error}
					</c:when>
					<c:otherwise>
						<table width=100%><form action="/allegro/clerk/finalizeRefund">
						<tr><td>Please enter the receipt number: <input type="text" name="j_receiptID"/></td>
						<td><b>Store: </b><select name="j_store">
							<c:forEach var="store" items="${stores}">
								<option>${store.sname}</option>
							</c:forEach>
							</select></td>
						<td><input class="button" value=" Return Item" type="submit"/></td>
						</tr>
						</form></table>
					</c:otherwise>
				</c:choose>
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
</div> <!-- wrap -->

</body>
</html>
