<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="cookie.Manage, java.net.*"%>
<!DOCTYPE html>
<html lang="en">

<head>
<!-- Required meta tags-->
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="Colrolib Templates">
<meta name="author" content="colorlib.com">
<meta name="keywords" content="Colrolib Templates">

<!-- Title Page-->
<title>HomeX</title>

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
<link href='https://fonts.googleapis.com/css?family=Gloria+Hallelujah'
	rel='stylesheet' type='text/css'>

<link
	href="https://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css"
	rel="stylesheet">

<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script>
	$(function() {
		$("#header").load("navbar.html");
	});
</script>
</head>

<div id="header"></div>

<body>
	<div class="page-wrapper bg-color-1 p-t-395 p-b-120">
		<div class="wrapper wrapper--w1070">
			<div class="card card-7">
				<div class="card-body">
					<form class="form" method="POST" action="SearchServlet">
						<%
							Cookie cRedirect = Manage.getCookie(request, "Redirect");
							if (null == cRedirect) {
								Manage.setCookie(request, response, "Redirect", "houseList");
								Manage.setCookie(request, response, "Role", "visitor");
							}
						%>
						<div class="input-group input--large">
							<label class="label">Location</label> <input class="input--style-1"
								type="text" placeholder="Address" name="address">
						</div>
						<div class="input-group input--medium">
							<label class="label">Check-In</label> <input
								class="input--style-1" type="text" name="checkin"
								placeholder="mm/dd/yyyy" id="input-start">
						</div>
						<div class="input-group input--medium">
							<label class="label">Check-Out</label> <input
								class="input--style-1" type="text" name="checkout"
								placeholder="mm/dd/yyyy" id="input-end">
						</div>
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
						<button class="btn-submit" type="submit">Search</button>

						<!-- More filters button-->
                                                <div class=" toggle-display" >
                                                    <div class="module-wrapper">
                                                        <label class="btn-submit">More Filters</label>
                                                    </div>
                                                </div>

						<div class="container">
							<h3>
								Constraint <i class="fa fa-check"></i>
							</h3>
							<div class="control-group">
								<div>
									<input class="input-check" type="checkbox" id="kidConstraint"
										name="Constraint" value="kid" /> <label for="kidConstraint">
										<div>
											<i class="fa fa-check"></i>
										</div> kid
									</label>
								</div>

								<div>
									<input class="input-check" type="checkbox" id="noiseConstraint"
										name="Constraint" value="noise" /> <label
										for="noiseConstraint">
										<div>
											<i class="fa fa-check"></i>
										</div> noise
									</label>
								</div>

								<div>
									<input class="input-check" type="checkbox" id="petConstraint"
										name="Constraint" value="pet" /> <label for="petConstraint">
										<div>
											<i class="fa fa-check"></i>
										</div> pet
									</label>
								</div>

								<div>
									<input class="input-check" type="checkbox" id="smokeConstraint"
										name="Constraint" value="smoke" /> <label
										for="smokeConstraint">
										<div>
											<i class="fa fa-check"></i>
										</div> smoke
									</label>
								</div>

								<div>
									<input class="input-check" type="checkbox" id="otherConstraint"
										name="Constraint" value="other" /> <label
										for="otherConstraint">
										<div>
											<i class="fa fa-check"></i>
										</div> other
									</label>
								</div>
							</div>
						</div>

						<div class="container">
							<h3>
								Service <i class="fa fa-check"></i>
							</h3>
							<div class="control-group">
								<div>
									<input class="input-check" type="checkbox" id="cleanService"
										name="Service" value="clean" /> <label for="cleanService">
										<div>
											<i class="fa fa-check"></i>
										</div> clean
									</label>
								</div>

								<div>
									<input class="input-check" type="checkbox" id="petService"
										name="Service" value="pet" /> <label for="petService">
										<div>
											<i class="fa fa-check"></i>
										</div> pet
									</label>
								</div>

								<div>
									<input class="input-check" type="checkbox" id="plantService"
										name="Service" value="plant" /> <label for="plantService">
										<div>
											<i class="fa fa-check"></i>
										</div> plant
									</label>
								</div>

								<div>
									<input class="input-check" type="checkbox" id="otherService"
										name="Service" value="other" /> <label for="otherService">
										<div>
											<i class="fa fa-check"></i>
										</div> other
									</label>
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
	<script src="vendor/bootstrap-wizard/bootstrap.min.js"></script>
	<script src="vendor/bootstrap-wizard/jquery.bootstrap.wizard.min.js"></script>
	<script src="vendor/datepicker/moment.min.js"></script>
	<script src="vendor/datepicker/daterangepicker.js"></script>

	<!-- Main JS-->
	<script src="js/global.js"></script>
	<script src="js/search.js"></script>

</body>
<!-- This templates was made by Colorlib (https://colorlib.com) -->

</html>
<!-- end document-->