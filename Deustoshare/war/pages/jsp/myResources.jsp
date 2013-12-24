<!-- My resources -->
<%@page import="com.appspot.deustosharing.domainClasses.Resource"%>
<%@page import="java.util.ArrayList"%>
<section id="portfolio" class="two">
	<div class="container">

		<header>
			<h2>My Resources</h2>
		</header>
		<div class="upload-button">
			<a href="javascript:setContent('start/my_resources/new')"
				class="button">Create new</a>
		</div>

		<%
			ArrayList<Resource> myResources = (ArrayList<Resource>) request
					.getAttribute("myResources");
			if (myResources == null || myResources.isEmpty()) {
		%>
		<p>You don't already have any resource</p>
		<%
			} else {
		%>
		<div class="result-list">
			<ul>
				<% for (int i = 0, ii = myResources.size(); i < ii; i++) { %>
				
					<!-- Pares con un color e inpares con otro -->
					<% if ((i % 2) == 0) { %>
					<a href="javascript:setContent('start/my_resources/edit?resourceid=<%= myResources.get(i).getKey().getId() %>')"><li class="li-colored"><%= myResources.get(i).getTitle() %>
						<% if (myResources.get(i).getCurrentUser()!=null) { %>
							<span class="resource-list no-available">(not available)</span></li></a>
						<% } else { %>
							<span class="resource-list available">(available)</span></li></a>
						<% } %>	
					<% } else { %>
					<a href="javascript:setContent('start/my_resources/edit?resourceid=<%= myResources.get(i).getKey().getId() %>')"><li><%= myResources.get(i).getTitle() %> 
						<% if (myResources.get(i).getCurrentUser()!=null) { %>
							<span class="resource-list no-available">(not available)</span></li></a>
						<% } else { %>
							<span class="resource-list available">(available)</span></li></a>
						<% } %>

				<% } } %>
			</ul>
		</div>
		<%
			}
		%>


	</div>
</section>