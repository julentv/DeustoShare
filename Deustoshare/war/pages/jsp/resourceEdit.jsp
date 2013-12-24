<!-- My resources -->
<%@page import="com.appspot.deustosharing.domainClasses.Resource"%>
<%@page import="com.appspot.deustosharing.domainClasses.Type"%>
<section id="portfolio" class="two">
	<div class="container">

		<header>
			<h2>Edit Resource</h2>
		</header>
		<form id="new-resource-form" method="post" action="#">
			<div class="new_resource_form">
				<ul>
					<li><span class="first_field">Title: <input type="text"
							class="text"
							value="<%=((Resource) request.getAttribute("resource")).getTitle()%>"
							name="name" placeholder="Name" /></span> <span class="second_field">Type:<select
							class="select" name="type">
								<%
									for (int i = 0, ii = Type.values().length; i < ii; i++) {
										if (Type.values()[i].toString().equals(
												((Resource) request.getAttribute("resource")).getType()
														.toString())) {
								%>
								<option value="<%=Type.values()[i].toString()%>" selected><%=Type.values()[i].toString()%></option>
								<%
									} else {
								%>
								<option value="<%=Type.values()[i].toString()%>"><%=Type.values()[i].toString()%></option>
								<%
									}
									}
								%>
						</select></span></li>
					<li>Description: <textarea class="text" name="description"><%=((Resource) request.getAttribute("resource"))
					.getDescription()%></textarea></li>
					<li>Visible: <%
						if (((Resource) request.getAttribute("resource")).isVisible()) {
					%>
						<input type="checkbox" name="visible" value="true" checked></li>
					<%
						} else {
					%>
					<input type="checkbox" name="visible" value="true"></li>
					<%
						}
					%>
				</ul>
				<input type="hidden" name="resourceid"
					value="<%=((Resource) request.getAttribute("resource")).getKey()
					.getId()%>">


			</div>
			<div class="12u row-button">
				<a
					href="javascript:setContentForm('start/my_resources/edit/save', 'new-resource-form')"
					class="button submit">Save</a>
			</div>
		</form>

	</div>
</section>