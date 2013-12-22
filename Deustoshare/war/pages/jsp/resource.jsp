<!-- My resources -->
<%@page import="com.appspot.deustosharing.domainClasses.Type"%>
<section id="portfolio" class="two">
	<div class="container">

		<header>
			<h2>New Resource</h2>
		</header>
		<form id="new-resource-form" method="post" action="#">
			<div class="new_resource_form">
				<ul>
					<li><span class="first_field">Title: <input type="text"
							class="text" name="name" placeholder="Name" /></span> <span
						class="second_field">Type:<select class="select" name="type">
								<%
									for (int i = 0, ii = Type.values().length; i < ii; i++) {
								%>
								<option value="<%=Type.values()[i].toString()%>"><%=Type.values()[i].toString()%></option>
								<%
									}
								%>
						</select></span></li>
					<li>Description: <textarea class="text" name="description"></textarea></li>
					<li>Visible: <input type="checkbox" name="visible"
						value="true"></li>
				</ul>


			</div>
			<div class="12u row-button">
				<a href="javascript:setContentForm('start/my_resources/new/save', 'new-resource-form')"
					class="button submit">Save</a>
			</div>
		</form>

	</div>
</section>