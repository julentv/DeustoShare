<!-- My resources -->
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.appspot.deustosharing.domainClasses.Resource"%>
<%@page import="com.appspot.deustosharing.domainClasses.Request"%>
<script>
	$(function() {
		$("#date-from").datepicker();
		$("#date-to").datepicker();
	});
</script>
<%
	Request requestVar = (Request) request.getAttribute("request");
	Resource resource = (Resource) request.getAttribute("resource");
	SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
	
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
							id="date-from" class="date" name="from" disabled
							value="<%= formatter.format(requestVar.getStartDate())%>" /></span> <span class="second_field">To:
							<input type="text" id="date-to" class="date" name="to"
							required="required" disabled value="<%=formatter.format(requestVar.getEndDate())%>" />
					</span></li>
				</ul>


			</div>
			<div class="12u row-button">
				<a
					href="javascript:setContentForm('start/request/delete?requestid=<%=requestVar.getKey().getId()%>&requesterEmail=<%=requestVar.getRequester().getEmail()%>','newRequestForm')"
					class="button submit">Delete</a>
			</div>
		</form>

	</div>
</section>