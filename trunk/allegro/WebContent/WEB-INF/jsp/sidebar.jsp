<div id="sidebar">
	<!-- Manager -->
	<c:if test="${profile.manager}">
		<h3>Bulletin</h3>
		<ul class="sidemenu">				
			<li>Reminder: Do not put dated release titles on shelves early</li>
			<li>Congratulations Vancouver store #3 for this month's highests sales!</li>
		</ul>
	</c:if>
	
	<!-- Clerk -->
	<c:if test="${profile.clerk}">
		<h3>Bulletin</h3>
		<ul class="sidemenu">				
			<li>Reminder: Do not put dated release titles on shelves early</li>
			<li>Congratulations Vancouver store #3 for this month's highests sales!</li>
		</ul>	
	</c:if>
		
	<!-- Customer -->
	<c:if test="${profile.customer}">
		<h3>Special Deals:</h3>
		<ul class="sidemenu">				
			<li>50% off Justin Bieber sale lasts until Feb 14!</li>
			<li>Don't miss Customer Appreciation day, Friday February 4th!</li>
		</ul>	
	</c:if>
</div>