
<!-- My resources -->
<section id="portfolio" class="two">
	<div class="container">
		<div class="my_requests">

			<header>
				<h2>My Requests</h2>
			</header>
			<div class="upload-button">
				<a href="javascript:setContent('start/search_resources')" class="button">Create
					new</a>
			</div>

			<p>You don't already have any request</p>
			<!-- OR -->
			<div id="filter">
				<form method="post" action="#">
					<div class="row half">

						<span id="filter_text">Filter:</span>
						<div class="6u">
							<select class="select">
								<option value="audi">All</option>
								<option value="volvo">Acepted</option>
								<option value="saab">Rejected</option>
								<option value="mercedes">Pending</option>

							</select>
						</div>
					</div>
				</form>
			</div>

			<div class="result-list">
				<ul>
					<a href="#"><li class="li-colored">Request a objeto uno <span
							class="resource-list available">(Aceptada)</span></li></a>
					<a href="#"><li>Request a objeto dos <span
							class="resource-list no-available">(Rechazada)</span></li></a>
					<a href="#"><li class="li-colored">Request a objeto tres <span
							class="resource-list">(Pending)</span></li></a>
					<a href="#"><li>Request a objeto cuatro <span
							class="resource-list available">(Aceptada)</span></li></a>
				</ul>
			</div>
		</div>

	</div>
</section>