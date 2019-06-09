<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="css/main.css" rel="stylesheet" media="all">


</head>
<body>
<nav class="navbar">
        <span class="navbar-toggle" id="js-navbar-toggle">
            <i class="fas fa-bars"></i>
        </span>
    <a href="index.jsp" class="logo">logo</a>
    <ul class="main-nav" id="js-menu">
        <li>
            <a href="index.jsp" class="nav-links">Find Home</a>
        </li>
        <li>
            <a href="contact.us.jsp" class="nav-links">Contact Us</a>
        </li>
        <li>
            <a href="profile.jsp" class="nav-links">Profile</a>
        </li>
        <li>
            <a method="GET" href="AuthServlet" class="nav-links">Logout</a>
        </li>
    </ul>
</nav>
</body>
</html>