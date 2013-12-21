<!-- My resources -->
<script>
	$(function() {
		$("#date-from").datepicker();
		$("#date-to").datepicker();
	});
</script>
<section id="portfolio" class="two">
	<div class="container">

		<header>
			<h2>New Request</h2>
		</header>
		<form method="post" action="#">
			<div class="new_resource_form">
				<ul>
					<li><span class="first_field">Resource: <input
							type="text" class="text" name="resource_title" placeholder="Name"
							disabled /></span> <span class="second_field">User: <input
							type="text" class="text" name="user" placeholder="User" disabled /></span>
					</li>

					<li><span class="first_field">From: <input type="text"
							id="date-from" class="date" name="from" /></span> <span
						class="second_field">To: <input type="text" id="date-to"
							class="date" name="to" />
					</span></li>
				</ul>


			</div>
			<div class="12u row-button">
				<a href="" class="button submit">Make Request</a>
			</div>
		</form>

	</div>
</section>