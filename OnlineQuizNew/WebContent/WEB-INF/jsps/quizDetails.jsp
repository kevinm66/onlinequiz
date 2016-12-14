<%@ page language="java" import="java.sql.ResultSet" import="co.edureka.quiz.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<title>Insert title here</title>
<style type="text/css">
body {
	background: url("${pageContext.request.contextPath}/images/tb_background.png");
	background-size: cover;
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

<br><br><br>
<h2 align="left" style="color:#fff">Instruction for ${sessionScope.exam} Quiz : </h2>

<div style="position:absolute;left:100px;top:190px;color:#fff">
<ul style="list-style-type:disc;font-size: 19px;width:50%;">
 <li>Quiz contains ${sessionScope.totalNumberOfQuizQuestions} Multiple Choice Questions</li>
 <li>If the question contains multiple correct answers, you get the point for each correct answer. </li>
 <li>Each wrong answer will reduce the points for the given question </li>
 <li>Total time for the Quiz is ${sessionScope.quizDuration} Minutes</li>
 <li>OR You can finish the exam at any time</li>
 <li>Read the question carefully before answering</li>
 <li>You can change your answers before submitting</li>
 <li><b>Good luck for the test.</b></li>
</ul>  
<br><br><br>
</div>

<div  style="position:absolute;left:50%;top:150px;color:#000000">
<b style="color:#dfe9ec"> TOP 10 Global Results</b>
<table id="t01" class="CSSTableGenerator">
<tr> <td style="width: 30%"> <b>Points</b> </td> <td style="width: 70%"><b> Name </b></td> </tr>

<%
	String exam = (String) request.getSession().getAttribute("exam");
	ResultSet set1= (ResultSet) request.getSession().getAttribute(exam);
	//out.println(set);
	String user = request.getSession().getAttribute("user").toString();
	//out.println(user);
//	out.println(set1.getString(1));
set1.beforeFirst();  
while(set1.next())
{
	 String name = set1.getString("name");
	 if (!name.equals(user)) name="UserXY";
	 int score = set1.getInt("score");
	// out.println("<tr> <td>"+score+ "</td><td>"+name+"</td></td>");
	 out.println("<tr> <td>"+score+ "</td><td>"+name+"</td></td>");
	
}
%>

</table>


</div>


<div  style="position:absolute;left:70%;top:150px;color:#000000">
<b style="color:#dfe9ec"> TOP 10 Personal Results</b>
<table id="t02" class="CSSTableGenerator1">
<tr> <td style="width: 30%"> <b>Points</b> </td> <td style="width: 70%"><b> Name </b></td> </tr>

<%
	//String results = (String) request.getSession().getAttribute("personalresults");
	ResultSet set2= (ResultSet) request.getSession().getAttribute("personalresults");
	
	
	//out.println(set);
//	String user1 = request.getSession().getAttribute("user").toString();
	//out.println(user);
//	out.println(set1.getString(1));
set2.beforeFirst();  
while(set2.next())
{
	
	String name1 = set2.getString("name");
	 int score = set2.getInt("score");
	// out.println("<tr> <td>"+score+ "</td><td>"+name+"</td></td>");
	 out.println("<tr> <td>"+score+ "</td><td>"+name1+"</td></td>");
	
}
%>

</table>


</div>





<div  style="position:absolute;left:35%;top:350px">
<button onclick="location.href='${pageContext.request.contextPath}/exam'">Start Exam</button>
</div>

 <%
 
    Integer hasFinished = (Integer) request.getSession().getAttribute("hasFinishedExam");
 Integer runningExam = (Integer) request.getSession().getAttribute("runningExam");
   if(hasFinished > 0 && runningExam==0)
   {
 %>

<div  style="position:absolute;left:35%;top:550px">
<button onclick="location.href='${pageContext.request.contextPath}/examResults'">Show Results for my last Exam</button>
</div>
<%} %>

</body>
</html>