<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="cookie.Manage, bean.House, java.net.*, java.util.*"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="UTF-8">
<title>Search Result</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link href='https://fonts.googleapis.com/css?family=Gloria+Hallelujah'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700'
	rel='stylesheet' type='text/css'>


<link
	href="https://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">

<link rel="stylesheet" href="css/content.css">
<script src="js/modernizr.js"></script>
<!-- Modernizr -->

<script src="//code.jquery.com/jquery-1.10.2.js"></script>

<%
	Cookie cRole = Manage.getCookie(request, "Role");
	String Role = null;

	if (null == cRole) {
		Manage.setCookie(request, response, "Role", "visitor");
		Role = "visitor";
	} else
		Role = cRole.getValue();
%>

<script>
	$(function() {
		var Role = "<%=Role%>"
		if (Role == "visitor")
			$("#header").load("navbar.jsp");
		else
			$("#header").load("navbar.after.jsp");
	});
</script>

</head>
<div id="header"></div>

<body>
	<%
		Set<House> houseResult = (HashSet<House>) request.getAttribute("houseResult");
	%>

	<header>
		<h1>Search Results</h1>
	</header>

	<!-- every li tag represent a picture with its quick view button-->
	<ul class="cd-items cd-container">
		<%
			if (houseResult != null) {
				for (House house : houseResult) {
					out.println("<li class=\"cd-item\"><img src=\"img/item-1.jpg\" alt=\"" + house.getHTML()
							+ "\"><a href=\"#0\" class=\"cd-trigger\">" + house.getTitle() + "</a></li>");
				}
			}
		%>
	</ul>

	<!-- inside this div is the pop up window to view the house with its details and buttons-->
	<div class="cd-quick-view">
		<div class="cd-slider-wrapper">
			<ul class="cd-slider">
				<li class="selected"><img src="img/item-1.jpg"></li>
				<li><img src="img/item-2.jpg"></li>
				<li><img src="img/item-3.jpg"></li>
			</ul>

			<div id="wrapper">
				<ul class="cd-slider-navigation">
					<li><a class="cd-next" href="#0">Prev</a></li>
					<li><a class="cd-prev" href="#0">Next</a></li>
				</ul>
			</div>
		</div>

		<div class="cd-item-info">
<div id="info" style="overflow: auto; width: 340px; height: 320px;"></div>
		<ul class="cd-item-action">
			<li><button class="register" method="POST" href="ReserveServlet">Register</button></li>
			<li>		
				<form action="chat.jsp" method="post">
					<button value="Contact me" name="contact me" style="background-color: #F82F53; 
					border-radius: 4px; color: #FFFFFF; text-align: center; width: 100px; height: 40px; 
					margin: 5px; font-size: 16px; ">Contact me</button>
				</form>
			</li>
		</ul>
		</div>
		<a href="#0" class="cd-close">Close</a>

	</div>
	<script src="js/jquery-2.1.1.js"></script>
	<script src="js/velocity.min.js"></script>
	<script src="js/content.js"></script>
	<!-- Resource jQuery -->
</body>
</html>
