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
	</ul>
</div>