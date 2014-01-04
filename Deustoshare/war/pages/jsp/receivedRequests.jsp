<%@page import="com.appspot.deustosharing.dao.ResourcesDAO"%>
<%@page import="com.appspot.deustosharing.domainClasses.RequestState"%>
<%@page import="com.appspot.deustosharing.domainClasses.Request"%>
<%@page import="java.util.List"%>
<%
	List<Request> myRequests = (List<Request>) request
			.getAttribute("requests");
	ResourcesDAO rDAO = new ResourcesDAO();
%>


<!-- My resources -->
<section id="portfolio" class="two">
	<div class="container">
		<div class="received_requests">

			<header>
				<h2>Received Requests</h2>
			</header>

			<%
				if (myRequests == null || myRequests.isEmpty()) {
			%>
			<p>You don't already have any request</p>
			<%
				} else {
			%>
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
					
						href="javascript:setContent('start/received_requests/show?requestid=<%=currentRequest.getKey().getId()%>&requesterEmail=<%=currentRequest.getKey().getParent().getName()%>')"><li
						class="li-colored">
							<%
								} else {
							%> <a href="javascript:setContent('start/received_requests/show?requestid=<%=currentRequest.getKey().getId()%>&requesterEmail=<%=currentRequest.getKey().getParent().getName()%>')"><li>
									<%
										}
									%> <%
 	if (currentRequest.getState().equals(RequestState.Accepted)) {
 %> <%=rDAO.getByPrimaryKey(
								currentRequest.getResource()).getTitle()%> <span
									class="resource-list available">(Accepted)</span> <%
 	} else if (currentRequest.getState().equals(
 					RequestState.Rejected)) {
 %> <%=rDAO.getByPrimaryKey(
								currentRequest.getResource()).getTitle()%><span
									class="resource-list no-available">(Rejected)</span> <%
 	} else if (currentRequest.getState().equals(
 					RequestState.Pending)) {
 %> <%=rDAO.getByPrimaryKey(
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