<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="cookie.Manage, java.net.*"%>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
    <meta charset="UTF-8">
    <title>Login</title>

     <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href='https://fonts.googleapis.com/css?family=Gloria+Hallelujah' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>


    <link href="https://netdna.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    
    
    <link rel="stylesheet" href="css/jump.css">
    <script src="js/jump.js"></script> <!-- Modernizr -->

    <script src="//code.jquery.com/jquery-1.10.2.js"></script>
    <script>
        $(function(){
            $("#header").load("navbar.jsp");
        });
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
      <h1>Login</h1>
    </div>
    <form class="form" method="POST" action="#">
    <label for="fname">User Email</label>
    <input style="background: #fab1a0" type="text" id="fname" name="useremail" placeholder="Your email">

    <label for="lname">Password</label>
    <input style="background: #fab1a0" type="password" id="password" name="password" placeholder="Your password">
    
    <input type="submit" value="Submit">
    </form>
  </div>
</div>

<script src="js/jquery-2.1.1.js"></script>
<script src="js/jump.js"></script>


</body>
</html>
