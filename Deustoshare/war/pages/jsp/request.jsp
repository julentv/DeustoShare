<!-- My resources -->
<%@page import="com.appspot.deustosharing.domainClasses.Resource"%>
<script>
	$(function() {
		$("#date-from").datepicker();
		$("#date-to").datepicker();
	});
</script>
<%
	Resource resource = (Resource) request.getAttribute("resource");
%>
<section id="portfolio" class="two">
	<div class="container">

		<header>
			<h2>New Request</h2>
		</header>
		<form id="newRequestForm" method="post" action="#">
			<div class="new_resource_form">
				<ul>
					<li><span class="first_field">Resource: <input
							type="text" class="text" name="resource_title" placeholder="Name"
							disabled value="<%=resource.getTitle()%>" /></span> <span
						class="second_field">User: <input type="text" class="text"
							name="user" placeholder="User" disabled
							value="<%=resource.getOwner().getName()%>" /></span></li>

					<li><span class="first_field">From: <input type="text"
							id="date-from" class="date" name="from" /></span> <span
						class="second_field">To: <input type="text" id="date-to"
							class="date" name="to" required="required" />
					</span></li>
				</ul>


			</div>
			
			<div class="12u row-button">
				<a
					href="javascript:setContentForm('start/request/create?resourceid=<%=resource.getKey().getId()%>&ownerEmail=<%=resource.getOwner().getEmail()%>','newRequestForm')"
					class="button submit">Make Request</a>
			</div>
			
		</form>

	</div>
</section>