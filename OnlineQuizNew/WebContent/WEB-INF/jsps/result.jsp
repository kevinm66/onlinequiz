<%@ page language="java" import="co.edureka.quiz.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style type="text/css">
body {
	background: url("${pageContext.request.contextPath}/images/tb_background.png");
	background-size: cover;
}

</style>
<title>Result</title>
</head>
<body>

<div style="position:absolute;left:30%; right:30%;top:70px;color:#dfe9ec">
<h3 align="center">Quiz Result</h3>
<table border=1 class="CSSTableResultGenerator">
<col />

        <tr>
            <td class="head">
                Quiz :
            </td>
            <td>
                <span id="lblSubject">${sessionScope.exam}</span></td>
        </tr>
        <tr>
            <td class="head">
                Starting Time :
            </td>
            <td >
                <span id="lblStime">${sessionScope.started}</span></td>
        <tr>
        	<td class="head">
        		Finish Time:
        	</td>
        	<td>
        		<span id="lblFtime"> 
        		${sessionScope.quizDuration-sessionScope.min-1} m ${60-sessionScope.sec} s 
        		     </span>
        	</td>
        
        
        </tr>
        
              
                <tr>
            <td class="head">
               No. of Questions :
            </td>
            <td>
                <span id="lblNquestions">${sessionScope.totalNumberOfQuizQuestions}</span></td>
        </tr>
        
                <tr>
            <td class="head">
                No. of correct answers :
            </td>
            <td>
                <span id="lblNcans">${requestScope.result}</span></td>
        </tr>
        
    </table>


<%--
<%

int testsize=(Integer)request.getAttribute("size");

int result=(Integer)request.getAttribute("result");

   if (result > testsize*0.5 )
   {
	  
   
   <h3 align="center">Passed</h3>
   <%} 
   

 <%
 if (result <= testsize*0.5 )
   {
	   %>
   <h3 align="center">Failed</h3>
     <%} %>
  --%>

 

<h2 align="center"><a href='${pageContext.request.contextPath}'>Take Another Exam</a></h2>
</div>

</body>
</html>