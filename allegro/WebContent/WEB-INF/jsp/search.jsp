<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<body>
	<div id="wrap">
		<c:set var="directory" value="search"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<div id="content-wrap">
			<div id="main">
				
				<h2>Search</h2>
				<c:if test="${not empty param.error}">
						<center><font color="red"><b>${param.error}</b></font></center>
				</c:if>
				
				<table><form method="post" action="/allegro/customer/performSearch">			
					<tr>
						<td><label>Category:</label></td>
						<td colSpan="2"><select name="j_category">
							<option>All</option>
							<option>Rock</option>
							<option>Country</option>
							<option>New Age</option>
							<option>Pop</option>
							<option>Classical</option>
							<option>Instrumental</option>
							<option>Rap</option>
						</select></td>
					</tr>
					<tr><td><label>Title (Optional):</label></td><td><input type="text" size="30" name="j_title" /></td>
					</tr>
					<tr>
					<td><label>Lead Singer(Optional)</label></td><td><input type="text" size="30" name="j_leadSinger" /></td>
					</tr>
					<tr>
					<td><label>Song Name(Optional)</label></td><td><input type="text" size ="30" name="j_songName"/></td>
					</tr>
					<tr><td></td><td><input class="button" value=" Search " type="submit"/></td></tr>
				</form></table>
				
				<c:if test="${result eq 'true'}">
					<c:choose>
					<c:when test="${not empty itemList}">
					<h2>Search Results</h2>
						<table width="100%"><center>
							<th>UPC</th><th>Title</th><th>Category</th><th>Quantity</th>		
							<c:forEach var="item" items="${itemList}">
							<form method="post" action="/allegro/customer/updateCart?upc=${item.upc}">
								<tr><td>${item.upc}</td><td><a href="<c:url value="/customer/item?upc=${item.upc}"/>">${item.title}</a></td><td>${item.category}</td>
									<td><input type="text" name="j_quantity"></input></td>
									<input type="hidden" name="j_category" value="${cat}"></input>
									<input type="hidden" name="j_title" value="${title}"></input>
									<input type="hidden" name="j_leadSinger" value="${ls}"></input>
									<input type="hidden" name="j_songName" value="${name}"></input>
									<td><input class="button" type="submit" value='Add to cart'/>  </td>
								</tr>
							</form>
							</c:forEach>
						</center></table>
					</c:when>
					<c:otherwise>
						Sorry, not results were found!
					</c:otherwise>
					</c:choose>
				</c:if>
				<!-- Search results here -->
				
			</div> <!-- main -->
		
		</div> <!-- content-wrap -->	
					
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>

</div> <!-- wrap -->

</body>
</html>
