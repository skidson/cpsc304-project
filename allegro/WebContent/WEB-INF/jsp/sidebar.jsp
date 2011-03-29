<div id="sidebar">
	<ul class="sidemenu">	
		<!-- Manager -->
		<c:if test="${profile.manager}">
			<h3>Bulletin</h3>
			<li>Reminder: Do not put dated release titles on shelves early</li>
			<li>Congratulations Vancouver store #3 for this month's highests sales!</li>
		</c:if>
		
		<!-- Clerk -->
		<c:if test="${profile.clerk}">
			<h3>Bulletin</h3>
			<li>Reminder: Do not put dated release titles on shelves early</li>
			<li>Congratulations Vancouver store #3 for this month's highests sales!</li>
		</c:if>
			
		<!-- Customer -->
		<c:if test="${profile.customer}">
			<h3>Special Deals:</h3>
			<li>50% off Justin Bieber sale lasts until Feb 14!</li>
			<li>Don't miss Customer Appreciation day, Friday February 4th!</li>
		</c:if>
		
		<h3>User Accounts:</h3>
		<li><b>manager</b> - baus</li>
		<li><b>clerk</b> - password</li>
		<li><b>customer</b> - abc</li>
		<li>
			Note: All user accounts have access to all menus, return to Index to switch
			between them.
		</li>
	</ul>
</div>