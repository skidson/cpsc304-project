<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<%@ include file="/WEB-INF/jsp/include.jsp" %>

<body>
<!-- wrap starts here -->
	<div id="wrap">
		<c:set var="directory" value="home"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		<!-- content-wrap starts here -->
		<div id="content-wrap">
		
			<%@ include file="/WEB-INF/jsp/sidebar.jsp" %>
				
			<div id="main">
				
				<h2><a href="search.html">Shopping Cart</a></h2>
				
				<table><center><form method="post">			
				
					<tr>
						<th>Image</th><th>Item Name</th><th>Price</th><th>Quantity</th><th />
					</tr>
					
					<tr>
						<td><img src="http://albums.ml-cdn.com/1265304571justin%20bieber%20my%20world%202.0.jpg" width="64" height="64" /></td>
						<td>Justin Bieber - My World 2.0 (2010)</td>
						<td>$2.99</td>
						<td><input type="text" size="3" name="j_quantity" value="7" /></td>
						<td><input class="button" value=" Remove " type="submit" /></td>
					</tr>
					
					<tr>
						<td><img src="http://images.uulyrics.com/cover/r/rick-astley/album-rick-astley-greatest-hits.jpg" width="64" height="64" /></td>
						<td>Rick Astley - Greatest Hits (2002)</td>
						<td>$13.37</td>
						<td><input type="text" size="3" name="j_quantity" value="13" /></td>
						<td><input class="button" value=" Remove " type="submit" /></td>
					</tr>
					
					<tr>
						<td><img src="http://www.astateoftrance.com/wp-content/plugins/simple-post-thumbnails/timthumb.php?src=/wp-content/thumbnails/2665.jpg&w=150&h=150&zc=1&ft=jpg" width="64" height="64" /></td>
						<td>Armin Van Buuren - A State of Trance #76 (2003)</td>
						<td>$15.99</td>
						<td><input type="text" size="2" name="j_quantity" value="7" /></td>
						<td><input class="button" value=" Remove " type="submit" /></td>
					</tr>
					
					<tr>
						<td /><td /><td /><td><input class="button" value=" Update Qty. " type="submit" /></td>
						<td><input class="button" value=" Checkout " type="submit" /></td>
					</tr>
					
				</form></center></table>
				
				
				
			</div>
		
		<!-- content-wrap ends here -->	
		</div>
					
		<!--footer starts here-->
		<div id="footer">
			
			<p>
			&copy; 2011 <strong>Allegro Music Ltd.</strong>
			
   		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
		</div>	

<!-- wrap ends here -->
</div>

</body>
</html>
