<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="cookie.Manage, java.net.*"%>
<!DOCTYPE html>
<html lang="en">

<head>
<!-- Required meta tags-->
<meta charset="UTF-8">


<!-- Title Page-->
<title>Main</title>

<!-- Icons font CSS-->
<link href="vendor/mdi-font/css/material-design-iconic-font.min.css"
	rel="stylesheet" media="all">
<link href="vendor/font-awesome-4.7/css/font-awesome.min.css"
	rel="stylesheet" media="all">
<!-- Font special for pages-->
<link
	href="https://fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i"
	rel="stylesheet">

<!-- Vendor CSS-->
<link href="vendor/select2/select2.min.css" rel="stylesheet" media="all">
<link href="vendor/datepicker/daterangepicker.css" rel="stylesheet"
	media="all">

<!-- Main CSS-->
<link href="css/main.css" rel="stylesheet" media="all">

<style>
img {
	width: 200px;
	max-width: 200px;
	text-align: center;
	display: inline;
	margin-right: 2%;
	margin-left: 2%;
	margin-top: 2%;
}
</style>

<link href='https://fonts.googleapis.com/css?family=Gloria+Hallelujah'
	rel='stylesheet' type='text/css'>

<link
	href="https://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">

<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>

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

<body style="background: #ffece0">

	<div class="gallery" style="text-align: center"></div>



	<div class="cover">
		<input type="file" multiple id="gallery-photo-add">
	</div>



	<div class="page-wrapper bg-color-1 p-t-395 p-b-120">
		<div class="wrapper wrapper--w1070">
			<div class="card card-7">
				<div class="card-body">
					<form class="form" method="POST" action="AddHomeServlet">

						<!-- location input-->
						<div class="input-group input--large">
							<label class="label">Location</label> <input
								class="input--style-1" type="text" placeholder="Address"
								name="address">
						</div>

						<!-- Check In,Out inputs-->
						<div class="input-group input--medium">
							<label class="label">Start-date</label> <input
								class="input--style-1" type="text" name="checkin"
								placeholder="mm/dd/yyyy" id="input-start">
						</div>
						<div class="input-group input--medium">
							<label class="label">End-date</label> <input
								class="input--style-1" type="text" name="checkout"
								placeholder="mm/dd/yyyy" id="input-end">
						</div>

						<!-- Guests increment input-->
						<div class="input-group input--medium">
							<label class="label">guests</label>
							<div class="input-group-icon js-number-input">
								<div class="icon-con">
									<span class="plus">+</span> <span class="minus">-</span>
								</div>
								<input class="input--style-1 quantity" type="text" name="guests"
									value="2 Guests">
							</div>
						</div>

						<!-- Search button-->
						<button class="btn-submit" type="submit">save</button>

						<!-- More filters button-->
						<div class=" toggle-display">
							<div class="module-wrapper">
								<label class="btn-submit">More Filters</label>
							</div>
						</div>

						<!-- from this div tag, everything inside it related to checkboxes-->
						<div class="box" style="display: none;">
							<div class="container">
								<h3>
									Services <i class="fa fa-check"></i>
								</h3>
								<div class="control-group">
									<div>
										<input class="input-check" type="checkbox" name="pets" id="pets" value="pets"/> <label
											for="pets">
											<div>
												<i class="fa fa-check"></i>
											</div> Keep Pets
										</label>
									</div>

									<div>
										<input class="input-check" type="checkbox" name="plants" id="plants" value="plants"/> <label
											for="plants">
											<div>
												<i class="fa fa-check"></i>
											</div> Watering Plants
										</label>
									</div>

									<div>
										<input class="input-check" type="checkbox" name="clean" id="clean" value="clean"/> <label
											for="clean">
											<div>
												<i class="fa fa-check"></i>
											</div> Clean the Place
										</label>
									</div>

								</div>
							</div>

							<div class="container">
								<h3>
									Constraints <i class="fa fa-check"></i>
								</h3>
								<div class="control-group">
									<div>
										<input class="input-check" type="checkbox" name="noSmoking" id="noSmoking" value="noSmoking"/> <label
											for="noSmoking">
											<div>
												<i class="fa fa-check"></i>
											</div> No Smoking
										</label>
									</div>

									<div>
										<input class="input-check" type="checkbox" name="noNoise" id="noNoise" value="noNoise"/> <label
											for="noNoise">
											<div>
												<i class="fa fa-check"></i>
											</div> No Noise ( After 23h )
										</label>
									</div>

									<div>
										<input class="input-check" type="checkbox" name="maxChildren" id="maxChildren" value="maxChildren"/> <label
											for="maxChildren">
											<div>
												<i class="fa fa-check"></i>
											</div> Max 2 Children
										</label>
									</div>

									<div>
										<input class="input-check" type="checkbox" name="noPets" id="noPets" value="noPets"/> <label
											for="noPets">
											<div>
												<i class="fa fa-check"></i>
											</div> No Pets
										</label>
									</div>

								</div>
							</div>

							<div class="container">
								<h3>
									Preferences <i class="fa fa-check"></i>
								</h3>
								<div class="control-group">
									<div>
										<input class="input-check" type="checkbox" id="parking" /> <label
											for="parking">
											<div>
												<i class="fa fa-check"></i>
											</div> Parking
										</label>
									</div>

									<div>
										<input class="input-check" type="checkbox" id="bbq" /> <label
											for="bbq">
											<div>
												<i class="fa fa-check"></i>
											</div> BBQ place
										</label>
									</div>

									<div>
										<input class="input-check" type="checkbox" id="swimPool" /> <label
											for="swimPool">
											<div>
												<i class="fa fa-check"></i>
											</div> Swim Pool
										</label>
									</div>

									<div>
										<input class="input-check" type="checkbox" id="gym" /> <label
											for="gym">
											<div>
												<i class="fa fa-check"></i>
											</div> GYM
										</label>
									</div>
								</div>
							</div>
						</div>

					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- Jquery JS-->
	<script src="vendor/jquery/jquery.min.js"></script>
	<!-- Vendor JS-->
	<script src="vendor/select2/select2.min.js"></script>
	<script src="vendor/jquery-validate/jquery.validate.min.js"></script>
	<script src="vendor/datepicker/moment.min.js"></script>
	<script src="vendor/datepicker/daterangepicker.js"></script>

	<!-- Main JS-->
	<script src="js/global.js"></script>
	<script src="js/search.js"></script>


</body>
<!-- This templates was made by Colorlib (https://colorlib.com) -->

</html>
<!-- end document-->
