<!-- My resources -->
<%@page import="com.appspot.deustosharing.domainClasses.Resource"%>
<section id="portfolio" class="two">
	<div class="container">
		<%
			Resource resource = (Resource) request.getAttribute("resource");
		%>
		<header>
			<h2><%=resource.getTitle()%></h2>
		</header>
		<form method="post" action="#">
			<div class="new_resource_form">
				<ul>
					<li><span class="first_field">Owner: <input disabled
							type="text" class="text" name="owner"
							value="<%=resource.getOwner().getName()%>" placeholder="Owner" /></span>
						<span class="second_field">Type:<input disabled type="text"
							class="text" name="type"
							value="<%=resource.getType().toString()%>" placeholder="Type" /></span>
					</li>
					<li>Description: <textarea disabled class="text"
							name="description" placeholder="description"><%=resource.getDescription()%></textarea></li>

					<%
						if (resource.getCurrentUser() == null) {
					%>
					<li class="available">Is available</li>
					<%
						} else {
					%>
					<li class="no-available">Is NOT available</li>
					<%
						}
					%>
				</ul>
			</div>
			<div class="12u row-button">
				<a
					href="javascript:setContent('start/request?resourceid=<%=resource.getKey().getId()%>&ownerEmail=<%=resource.getOwner().getEmail()%>')"
					class="button submit">Make request</a>
			</div>
		</form>

	</div>
</section>