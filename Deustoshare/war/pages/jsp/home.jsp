<%@ page import="com.appspot.deustosharing.domainClasses.AppUser" %>
<!-- Home -->
<section id="top">
	<div class="container">

		<header>
			<h2 class="alt">
				Hello <strong><%= ((AppUser)request.getAttribute("currentUser")).getName() %></strong>.<br/> Welcome to Deusto Sharing, where you can find what you need.
			</h2>
		</header>

	</div>
</section>