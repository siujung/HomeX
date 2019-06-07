<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="cookie.Manage, bean.*, java.net.*, control.Authentication"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta charset="UTF-8">
<title>Admin</title>

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
		var Role = "<%=Role%>
	"
		if (Role == "visitor")
			$("#header").load("navbar.jsp");
		else
			$("#header").load("navbar.after.jsp");
	});
</script>

</head>
<div id="header"></div>

<body>
	<!-- for each Quick view we going to give it a value which will be house name, from this value we can determine which house is offered by this user or registered -->
	<div class="containers">
		<header>
			<h1>Housing</h1>
		</header>
		<!-- every li tag represent a picture with its quick view button-->
		<ul class="cd-items ul-container">
			<%
				Authentication authentication = (Authentication) session.getAttribute("authentication");

				if (!authentication.isLoggedIn()) {
					response.sendRedirect(request.getContextPath() + "/login.jsp");
				}
				if (authentication.getUser().getHouse() != null && !authentication.getUser().getHouse().isEmpty()) {
					for (Integer houseId : authentication.getUser().getHouse()) {
						House house = House.get(houseId);

						out.println("<li class=\"cd-item\"><img src=\"img/item-1.jpg\" alt=\"" + house.getHTML()
								+ "\"><a class=\"cd-trigger\">" + house.getTitle() + "</a></li>");
					}
				}
			%>
		</ul>
	</div>

	<div class="containers">
		<header>
			<h1>reservation</h1>
		</header>

		<!-- every li tag represent a picture with its quick view button-->
		<ul class="cd-items ul-container">
			<%
				if (authentication.getUser().getOrder() != null && !authentication.getUser().getOrder().isEmpty()) {
					for (Integer orderId : authentication.getUser().getOrder()) {
						House order = House.get(Order.get(orderId).getHouse());

						out.println("<li class=\"cd-item\"><img src=\"img/item-1.jpg\" alt=\"" + order.getHTML()
								+ "\"><a class=\"cd-trigger\">" + order.getTitle() + "</a></li>");
						System.out.println(order.getHTML());
					}
				}
			%>
		</ul>
	</div>
	<div class="page-wrapper bg-color-1 p-t-395 p-b-120">
		<div class="wrapper wrapper--w1070">
			<div class="card card-7">
				<div class="card-body">
					<a class="btn-submit" type="submit" href="new.property.jsp">New
						Housing</a>
				</div>
			</div>
		</div>
	</div>


	<!-- this code will check Quick view value and generate all pictures related to that house name-->
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

		<div id="info" class="cd-item-info"></div>
		<ul class="cd-item-action">
			<li><button class="register">Cancel</button></li>
		</ul>
		<a href="#0" class="cd-close">Close</a>
	</div>
	<script src="js/jquery-2.1.1.js"></script>
	<script src="js/velocity.min.js"></script>
	<script src="js/content.js"></script>
</body>
</html>
