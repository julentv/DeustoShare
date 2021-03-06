<!DOCTYPE HTML>
<!--
	Prologue 1.1 by HTML5 UP
	html5up.net | @n33co
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<%@page import="com.appspot.deustosharing.domainClasses.AppUser"%>
<%@page import="com.appspot.deustosharing.dao.AppUserDAO"%>
<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%>
<html>
<head>
<title>Deusto Sharing</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="description" content="" />
<meta name="keywords" content="" />
<link
	href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:200,300,400,600"
	rel="stylesheet" type="text/css" />
<!--[if lte IE 8]><script src="js/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="/js/jquery.form.js"></script>	
<script src="/js/skel.min.js"></script>
<script src="/js/skel-panels.min.js"></script>
<script src="/js/init.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script type="text/javascript" src="/_ah/channel/jsapi"></script>
<script src="/js/channel.js"></script>
	

<noscript>
	<link rel="stylesheet" href="/css/skel-noscript.css" />
	<link rel="stylesheet" href="/css/style.css" />
	<link rel="stylesheet" href="/css/style-wide.css" />
</noscript>
<!--[if lte IE 9]><link rel="stylesheet" href="css/ie9.css" /><![endif]-->
<!--[if lte IE 8]><link rel="stylesheet" href="css/ie8.css" /><![endif]-->
</head>
<body>
<!-- Commet code -->
  <script>
    channel = new goog.appengine.Channel('<%= request.getAttribute("token") %>');
    socket = channel.open();
    socket.onopen = onOpened;
    socket.onmessage = onMessage;
    socket.onerror = onError;
    socket.onclose = onClose;
  </script>



<% AppUser currentUser = (AppUser)request.getAttribute("currentUser"); %>
	<!-- Header -->
	<div id="header" class="skel-panels-fixed">

		<div class="top">

			<!-- Logo -->
			<div id="logo">
				<span class="image avatar48"><img src="/images/avatar.jpg"
					alt="" /></span>
				<h1 id="title"><%= currentUser.getName() %></h1>
				<span class="byline">Deusto Sharing</span>
			</div>

			<!-- Nav -->
			<nav id="nav">
				<ul>
					<li><a href="javascript:setContent('start/home')" id="home"
						class="active"><span class="fa fa-home">Home</span></a></li>
					<li><a href="javascript:setContent('start/my_resources')"
						id="my_resources" class=""><span class="fa fa-th">My
								resources</span></a></li>
					<li><a href="javascript:setContent('start/my_requests')"
						id="my_requests" class=""><span class="fa fa-comment">My
								requests</span></a></li>
					<li><a href="javascript:setContent('start/received_requests')"
						id="received_requests" class=""><span id="receivedRequestSpan" class="fa fa-envelope">Received
								requests</span></a></li>
					<li><a href="javascript:setContent('start/search_resources_page')"
						id="search_resources_page" class=""><span class="fa fa-search">Search
								resources</span></a></li>
				</ul>
			</nav>

		</div>

		<div class="bottom">

			<!-- Social Icons -->
			<ul class="icons">
				<li><a href="<%= UserServiceFactory.getUserService().createLogoutURL("/") %>" class="fa fa-sign-out solo"><span>Sign out</span></a></li>
			</ul>

		</div>

	</div>

	<!-- Main -->
	<div id="main">
		<script>
			setContent('start/home');
			//setContent('start/request');
		</script>
	</div>

</body>
</html>