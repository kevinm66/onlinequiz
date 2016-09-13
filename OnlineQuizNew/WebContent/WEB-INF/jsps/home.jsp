<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
   <meta http-equiv="X-UA-Compatible" content="IE=edge">
   <meta name="viewport" content="width=device-width, initial-scale=1">
   <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <style type="text/css">
body {
	background: url("${pageContext.request.contextPath}/images/tb_background.png");
	background-size:cover;
	
}
.button {
	padding: 10px 15px;
	font-size: 14px;
	line-height: 100%;
	text-shadow: 0 1px rgba(0, 0, 0, 0.4);
	color: #fff;
	
	vertical-align: middle;
	text-align: center;
	cursor: pointer;
	font-weight: bold;
	transition: background 0.1s ease-in-out;
	-webkit-transition: background 0.1s ease-in-out;
	-moz-transition: background 0.1s ease-in-out;
	-ms-transition: background 0.1s ease-in-out;
	-o-transition: background 0.1s ease-in-out;
	text-shadow: 0 1px rgba(0, 0, 0, 0.3);
	color: #fff;
	-webkit-border-radius: 40px;
	-moz-border-radius: 40px;
	border-radius: 40px;
	font-family: 'Helvetica Neue', Helvetica, sans-serif;
}

.button, .button:hover, .button:active {
	outline: 0 none;
	text-decoration: none;
        color: #fff;
}

.username {
	background-color: #2ecc71;
	box-shadow: 0px 3px 0px 0px #239a55;
}

</style>
  
   <title>TechQ Online Quiz</title>
</head>
<body>

<div id='cssmenu'>
<ul>
   <li class=''><a href='${pageContext.request.contextPath}'><span>Home</span></a></li>
   <li><a href='${pageContext.request.contextPath}/login'><span>Login</span></a></li>
   <li><a href='${pageContext.request.contextPath}/register'><span>Register</span></a></li>
   
 <%--   <li class='#'><a href='#'><span>Submit a Question</span></a></li>
   <li class='#'><a href='#'><span>Feedback</span></a></li>
   <li><a href='#'><span>Contribute</span></a></li>
   <li><a href='#'><span>Contact us</span></a></li>  
   --%>
</ul>
</div>
<div style="position:absolute;top:15px;left:85%;"> <img src="${pageContext.request.contextPath}/images/ea_logo_black.png" alt="EA Logo" style="width:85%;height:60%;"></div>





<c:if test='${not empty sessionScope.user}'>

<div style="position:absolute;top:20px;left:60%;color:#fff">
Logged as <a href="#" class="button username">${sessionScope.user}</a>
</div>

<div style="position:absolute;top:20px;left:75%;color:#fff">
<a href='${pageContext.request.contextPath}/logout'>Logout</a>
</div>

</c:if>

<div style="position:absolute;left:120px;top:60px">
<table cellpadding="0" cellspacing="50">

<tr>
<td><a href="takeExam?test=soa"><img height="200" width="200" src="${pageContext.request.contextPath}/images/soa.jpg" title="Klikni pre spustenie "/></a></td>
<td><img height="200" width="200" src="${pageContext.request.contextPath}/images/archimate.png" title="Test zatial nie je implementovany"/></td>
<td><img height="200" width="200" src="${pageContext.request.contextPath}/images/Bizzdesign.png" title="Test zatial nie je implementovany"/></td>
<%-- <td><a href="takeExam?test=sql"><img height="200" width="200" src="${pageContext.request.contextPath}/images/sql-logo.png"/></a></td>--%>

</tr>
<%--
<tr>
<td><a href="takeExam?test=css"><img height="200" width="200" src="${pageContext.request.contextPath}/images/css.jpg"/></a></td>
<td><a href="takeExam?test=php"><img height="200" width="200" src="${pageContext.request.contextPath}/images/php-logo.jpg"/></a></td>
<td><a href="takeExam?test=linux"><img height="200" width="200" src="${pageContext.request.contextPath}/images/logo-linux.png"/></a></td>
<td><a href="takeExam?test=mongodb"><img height="200" width="200" src="${pageContext.request.contextPath}/images/mongodb_logo.png"/></a></td>
</tr>
 --%>
</table>
</div>


</body>
</html>
