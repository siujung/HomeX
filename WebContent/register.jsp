<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="cookie.Manage, java.net.*"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="UTF-8">
<title>Register</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link href='https://fonts.googleapis.com/css?family=Gloria+Hallelujah'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'
	rel='stylesheet' type='text/css'>


<link
	href="https://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="css/jump.css">
<script src="js/jump.js"></script>
<!-- Modernizr -->
<script src="js/validate.js"></script>

<script src="//code.jquery.com/jquery-1.10.2.js"></script>

<%
	Cookie cRole = Manage.getCookie(request, "Role");
	String Role = null;

	if (null == cRole) {
		Manage.setCookie(request, response, "Role", "visitor");
		Role = "visitor";
	} else
		Role = cRole.getValue();
	if (Role.equals("user")) {
		response.sendRedirect("index.jsp");
		return;
	} else if (Role.equals("administrator")) {
		response.sendRedirect("admin.jsp");
		return;
	}
%>

<script>
	$(function() {
		var Role = "<%=Role%>"
		if (Role == "visitor")
			$("#header").load("navbar.jsp");
		else
			$("#header").load("navbar.after.jsp");
	});
	$("#register").validate();
</script>

</head>
<div id="header"></div>

<body>

	<div id="wrapper">
		<div id="loginWindow">
			<div class="page-header">
				<p class="left">
					<span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
				</p>
				<h1>Register</h1>
			</div>
			<form class="form" method="post" action="RegisterServlet"
				id="register">
				<label for="email">User Email</label> <input
					style="background: #fab1a0" type="email" id="email" name="email"
					placeholder="Your email" required> <label for="password">Set
					Password</label> <input style="background: #fab1a0" type="password"
					id="password" name="password" placeholder="Set your password"
					required> <label for="confirm_password">Confirm
					Password</label> <input style="background: #fab1a0" type="password"
					id="confirm_password" name="confirm_password"
					placeholder="Confirm your password" required> <input
					type="submit" value="Submit">
			</form>
		</div>
	</div>
	<script src="js/jquery-2.1.1.js"></script>
	<script src="js/jump.js"></script>


</body>
</html>
