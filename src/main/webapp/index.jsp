<html>
<head>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<style type="text/css">
.content {
  max-width: 500px;
  margin: auto;
}
</style>
</head>
<body class="jumbotron content">
<%@ page import="model.DataModel" %>  
<form action="/RestWebApp/restHandler" method="post" class="form-inline">
<div class="input-group">
<span class="input-group-addon">FLS/EUR.</span>
<input type="text" name="paramData" id="paramName" class="form-control" placeholder="Parameter value"/>
</div>
<button type="submit" class="btn btn-default">Fetch Data</button>
</form>
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</body>
</html>
