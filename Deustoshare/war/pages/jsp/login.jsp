<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<head>
  <link rel="stylesheet" href="css/loginStyle.css" />
</head>
<body>
  <div class="ribbon ior"></div>
  <div class="login io">
    <div class="press"><strong>Deusto</strong> <span>Share</span></div>
    <img src="images/userIcon.png" alt="ggIO">
	<p></p>
	<p>You need a google account to use this application</p>
	<%! UserService userService = UserServiceFactory.getUserService(); %>
	
	<button onclick="location.href='<%= userService.createLoginURL("/start") %>'">Login</button>
  </div>
  <br><br>
  <a href="/test">TEST</a>
</body>