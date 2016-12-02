<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>Location, Location, Location</h1>
<form name="form" action="location" method="GET">

<input type="radio" name="city" value="park city" checked> Park City<br>
  <input type="radio" name="city" value="st louis"> St. Louis<br>
  <input type="radio" name="city" value="savannah"> Savannah<br>
<input type="radio" name="city" value="portland"> Portland
<br><input type="submit" value="submit">

</form>


</body>
</html>
