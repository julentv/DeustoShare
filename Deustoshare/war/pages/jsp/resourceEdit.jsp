<!-- My resources -->
<%@page import="com.appspot.deustosharing.domainClasses.Resource"%>
<%@page import="com.appspot.deustosharing.domainClasses.Type"%>

<script type="text/javascript">
//image upload function
$(document).ready(function() 
{ 
	$(document).on('change','#photoimg', function(){ 
		$("#preview").html('');
		$("#preview").html('<img src="images/loader.gif" alt="Uploading...."/>');
		$("#imageform").ajaxForm({target: '#preview'}).submit();
	});
}); 
</script>
<section id="portfolio" class="two">
	<div class="container">

		<header>
			<h2>Edit Resource</h2>
		</header>
		<div id="image-form">
			<form id="imageform" method="post" enctype="multipart/form-data"
				action='start/my_resources/resourceImageEdit?resourceid=<%=((Resource) request.getAttribute("resource")).getKey()
					.getId()%>'>
				<ul>
					<li>Image: <input type="file" name="photoimg" id="photoimg" /></li>
				</ul>
			</form>
		</div>
		<div id="resource-image-div">
			<ul>
				<li id="preview"><img id="resource-image"
					src="images/userIcon.png" alt="resour"></li>
			</ul>
		</div>
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
					%> <input type="checkbox" name="visible" value="true" checked></li>
					<%
						} else {
					%>
					<input type="checkbox" name="visible" value="true">
					</li>
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