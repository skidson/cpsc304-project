<div id="sidebar">
	<c:choose>
		<!-- Manager -->
		<c:when test="${user.role eq 0}">
			<h3>Bulletin</h3>
			<ul class="sidemenu">				
				<li>Reminder: Do not put dated release titles on shelves early</li>
				<li>Congratulations Vancouver store #3 for this month's highests sales!</li>
			</ul>
		</c:when>
		
		<!-- Clerk -->
		<c:when test="${user.role eq 1}">
			<h3>Bulletin</h3>
			<ul class="sidemenu">				
				<li>Reminder: Do not put dated release titles on shelves early</li>
				<li>Congratulations Vancouver store #3 for this month's highests sales!</li>
			</ul>	
		</c:when>
			
		<!-- Customer -->
		<c:otherwise>
			<h3>Special Deals:</h3>
			<ul class="sidemenu">				
				<li>50% off Justin Bieber sale lasts until Feb 14!</li>
				<li>Don't miss Customer Appreciation day, Friday February 4th!</li>
			</ul>	
		</c:otherwise>
	</c:choose>
</div>