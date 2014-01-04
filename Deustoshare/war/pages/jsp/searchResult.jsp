<!-- Page with the result of the resource search -->
<%@page import="com.appspot.deustosharing.domainClasses.Resource"%>
<%@page import="java.util.List"%>
<!-- No resources found -->
<h3 class="resoults">Results</h3>
<%
	List<Resource> myResources = (List<Resource>) request
			.getAttribute("resources");
	if (myResources == null || myResources.isEmpty()) {
%>

<p>No resources found with these parameters.</p>
<%
	} else {
%>
<div class="result-list">
	<ul>
		<%
			for (int i = 0, ii = myResources.size(); i < ii; i++) {
		%>

		<!-- Pares con un color e inpares con otro -->
		<%
			if ((i % 2) == 0) {
		%>
		<a
			href="javascript:setContent('start/resource?resourceid=<%=myResources.get(i).getKey().getId()%>&ownerEmail=<%=myResources.get(i).getKey().getParent()
								.getName()%>')"><li
			class="li-colored"><%=myResources.get(i).getTitle()%></li></a> 
		<%
			} else {
		%>
		<a
			href="javascript:setContent('start/resource?resourceid=<%=myResources.get(i).getKey().getId()%>&ownerEmail=<%=myResources.get(i).getKey().getParent()
								.getName()%>')"><li><%=myResources.get(i).getTitle()%></li></a>
		<%
			}
				}
		%>
	</ul>
	<%
		}
	%>
</div>