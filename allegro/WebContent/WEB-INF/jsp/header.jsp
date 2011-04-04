<!--header -->
<div id="header">
	<jsp:useBean id="profile" class="ca.ubc.cs304.allegro.model.Profile" scope="session" />
	
	<h1 id="logo-text"><a href="<c:url value="/index/welcome"/>">Allegro</a></h1>		
	<p id="slogan">Music Store</p>		
	
	<c:if test="${profile.username != 'anonymousUser' && profile.username != null}">
		<div id="header-links">
			<p>Logged in as: ${profile.username} | <a href="<c:url value="/j_spring_security_logout" />">Logout</a></p>		
		</div>
	</c:if>
	
</div>
<!-- menu -->	
<div  id="menu">
	<ul>
		<li <c:if test="${directory == 'home'}" >id="current"</c:if>><a href="<c:url value="/index/welcome"/>">Role Select</a></li>
		<!-- Manager -->
		<c:if test="${profile.manager}">
			<li <c:if test="${directory == 'suppliers'}" >id="current"</c:if>><a href="<c:url value="/manager/suppliers"/>">Suppliers</a></li>
			<li <c:if test="${directory == 'reports'}" >id="current"</c:if>> <a href="<c:url value="/manager/reports"/>">Reports</a></li>
			<li <c:if test="${directory == 'shipments'}" >id="current"</c:if> <c:if test='${!profile.clerk && !profile.customer}'>class="last"</c:if>> 
				<a href="<c:url value="/manager/shipments"/>">Shipments</a>
			</li>
		</c:if>
		
		<!-- Clerk -->
		<c:if test="${profile.clerk}">
			<li <c:if test="${directory == 'purchase'}" >id="current"</c:if>><a href="<c:url value="/clerk/purchase"/>">Purchase</a></li>
			<li <c:if test="${directory == 'refund'}" >id="current"</c:if> <c:if test='${!profile.customer}'>class="last"</c:if>> <a href="<c:url value="/clerk/refund"/>">Refund</a></li>
		</c:if>
			
		<!-- Customer -->
		<c:if test="${profile.customer}">
			<li <c:if test="${directory == 'search'}" >id="current"</c:if>><a href="<c:url value="/customer/search"/>">Search</a></li>
			<li <c:if test="${directory == 'cart'}" >id="current"</c:if> class="last"> <a href="<c:url value="/customer/cart"/>">Shopping Cart</a></li>
		</c:if>
	</ul>
</div>			