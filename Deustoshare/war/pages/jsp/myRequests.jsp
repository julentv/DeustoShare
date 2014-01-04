<%@page import="com.appspot.deustosharing.dao.ResourcesDAO"%>
<%@page import="com.appspot.deustosharing.domainClasses.RequestState"%>
<%@page import="java.util.List"%>
<%@page import="com.appspot.deustosharing.domainClasses.Request"%>
<!-- My resources -->
<%
	List<Request> myRequests = (List<Request>) request
			.getAttribute("requests");

	ResourcesDAO rDAO = new ResourcesDAO();
	String filter = (String)request.getAttribute("filter");
%>
<section id="portfolio" class="two">
	<div class="container">
		<div class="my_requests">

			<header>
				<h2>My Requests</h2>
			</header>
			<div class="upload-button">
				<a href="javascript:setContent('start/search_resources_page')"
					class="button">Create new</a>
			</div>
			
			<%
				if (myRequests == null || myRequests.isEmpty()) {
			%>
			<p>
				You don't already have any <%=filter%> request.
			</p>
			<div id="filter">
				<form method="post" action="#">
					<div class="row half">

						<span id="filter_text">Filter:</span>
						<div class="6u">
							<select class="select" name="filter"
								onchange="changeSelectedMenuFilter('start/my_requests',this)">
								<option value="">All</option>
								<% for(RequestState requestState:RequestState.values()){ %>
								<% if(requestState.toString().equals(filter)){ %>
								<option selected value="<%=requestState%>"><%=requestState%></option>
								<% }else{ %>
								<option value="<%=requestState%>"><%=requestState%></option>
								<% }} %>
							</select>
						</div>
					</div>
				</form>
			</div>
			<%
				} else {
			%>
			<div id="filter">
				<form method="post" action="#">
					<div class="row half">

						<span id="filter_text">Filter:</span>
						<div class="6u">
							<select class="select" name="filter"
								onchange="changeSelectedMenuFilter('start/my_requests',this)">
								<option value="">All</option>
								<% for(RequestState requestState:RequestState.values()){ %>
								<% if(requestState.toString().equals(filter)){ %>
								<option selected value="<%=requestState%>"><%=requestState%></option>
								<% }else{ %>
								<option value="<%=requestState%>"><%=requestState%></option>
								<% }} %>
							</select>
						</div>
					</div>
				</form>
			</div>

			<div class="result-list">
				<ul>
					<%
						for (int i = 0, ii = myRequests.size(); i < ii; i++) {
								Request currentRequest = myRequests.get(i);
					%>
					<!-- Pares con un color e inpares con otro -->
					<%
						if ((i % 2) == 0) {
					%>
					<a
						href="javascript:setContent('start/request/show?requestid=<%=currentRequest.getKey().getId()%>&requesterEmail=<%=currentRequest.getKey().getParent().getName()%>')"><li
						class="li-colored">
							<%
								} else {
							%> <a href="#"><li>
									<%
										}
									%> <%
 	if (currentRequest.getState().equals(RequestState.Accepted)) {
 %>
									<%=rDAO.getByPrimaryKey(
								currentRequest.getResource()).getTitle()%>
									<span class="resource-list available">(Accepted)</span> <%
 	} else if (currentRequest.getState().equals(
 					RequestState.Rejected)) {
 %>
									<%=rDAO.getByPrimaryKey(
								currentRequest.getResource()).getTitle()%><span
									class="resource-list no-available">(Rejected)</span> <%
 	} else if (currentRequest.getState().equals(
 					RequestState.Pending)) {
 %>
									<%=rDAO.getByPrimaryKey(
								currentRequest.getResource()).getTitle()%><span
									class="resource-list">(Pending)</span> <%
 	}
 %>
							</li></a> <%
 	}
 %>
					
				</ul>
			</div>
			<%
				}
			%>
		</div>

	</div>
</section>