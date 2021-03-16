<jsp:useBean id="login" class="beans.BeansUser" type="beans.BeansUser"
	scope="page"></jsp:useBean>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login in System</title>

<link rel="stylesheet" href="resources/css/estilo.css">


</head>
<body>

	<div class="login-page">
		<div class="form">
			<form action="LoginServlet" method="post" class="login-form">
				Login: <input type="text" id="login" name="login"> <br />
				Senha: <input type="password" id="password" name="password"> <br />
				<button type="submit" value="Login">Entrar</button>
			</form>
		</div>
	</div>
</body>
</html>