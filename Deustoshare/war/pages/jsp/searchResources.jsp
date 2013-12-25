<!-- Search Resources -->
<%@page import="com.appspot.deustosharing.domainClasses.Type"%>
<section id="contact" class="four">
	<div class="container">

		<header>
			<h2>Resources</h2>
		</header>

		<form method="post" action="#" id="search-form">
			<div class="row half">
				<div class="6u">
					<input type="text" class="text" name="name" placeholder="Name" />
				</div>
				<div class="6u">
					<select class="select" name="type">
						<option value=""></option>
						<%
							for (int i = 0, ii = Type.values().length; i < ii; i++) {
						%>
						<option value="<%=Type.values()[i].toString()%>"><%=Type.values()[i].toString()%></option>
						<%
							}
						%>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="12u">
					<a
						href="javascript:displayResourcesSearch('start/search_resources', 'search-form','display-results')"
						class="button submit">Search</a>
				</div>
			</div>
		</form>
		<div id="display-results">
		</div>
	</div>
</section>