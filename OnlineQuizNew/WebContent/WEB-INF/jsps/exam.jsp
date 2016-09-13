<%@ page language="java" import="co.edureka.quiz.*" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<title>Quiz</title>
 <style type="text/css">
body {
	background: url("${pageContext.request.contextPath}/images/tb_background.png");
	background-size: cover;
}
</style>




</head><br/>
<body onload="examTimer()">
<div style="position:absolute;left:50px;top:20px;color:#dfe9ec">
<%
  int currentQuestion=((Exam)request.getSession().getAttribute("currentExam")).getCurrentQuestion();
int testsize=((Exam)request.getSession().getAttribute("currentExam")).getTestSize();
  System.out.println("testsize "+testsize+", question "+currentQuestion);
 %>
Current Question ${sessionScope.quest.questionNumber+1} / ${sessionScope.totalNumberOfQuizQuestions}
</div>

<div id="showtime" style="position:absolute;left:800px;top:20px"></div>

 <div style="position:absolute;width:1000px;padding:15px; color:#dfe9ec;
  height: 380px;border: 1px solid green ;left:100px;top:60px;font-size: 19px;">
 <span style="font-size: 19px;">${sessionScope.quest.question}</span>

<form id="questionForm" action="${pageContext.request.contextPath}/exam" method="post" >
<table class="CSSExamTable">

 <c:forEach var="choice" items="${sessionScope.quest.questionOptions}" varStatus="counter">
 <!-- ${sessionScope.currentExam.getAnsweredItem(sessionScope.quest.questionNumber,counter.index)}, ${counter.index} , ${counter.count}, ${sessionScope.quest.questionNumber}   --> 
<c:choose>
 <c:when test="${sessionScope.currentExam.getAnsweredItem(sessionScope.quest.questionNumber,counter.index)==counter.count}" >
<tr> <td><input type="checkbox" name="answer" value="${counter.count}" checked></td><td>${choice}</td> </tr> 
 </c:when>
 <c:otherwise>
 <tr><td><input type="checkbox" name="answer" value="${counter.count}" ></td><td>${choice} </td> </tr>
 </c:otherwise>
 </c:choose>
 </c:forEach> 
 
 </table>
 <br/> 
 <table style="width:50%;position:absolute;top:340px;">
 <tr>
 <%
   if(currentQuestion > 0)
   {
 %>
 <td width="40%">
 <input type="submit" name="action" value="Previous" onclick="customSubmit()"/>
 </td>
 
 <%} %>
 
 <%
   if(currentQuestion < (testsize-1))
   {
	   System.out.println("testvelkost: "+testsize);
 
   if(currentQuestion == 0)
   {
 %>
 <td width="40%">
 
 </td>
 <%} %>
 <td width="30%">
 <input type="submit" name="action" value="Next" onclick="customSubmit()" />
 </td>
 <%} %>
 
  <%
   if(currentQuestion == (testsize-1))
   {
 %>
 <td width="30%">
 
 </td>
 <%} %>
 <td width="30%">
 <input id="finishexam" type="submit" name="action" value="Finish Exam" onclick="customSubmit()" />
 
 </td>
	<input type="hidden" name="minute"/> 
	<input type="hidden" name="second"/>
</tr>
</table>
 </form>
</div>
<script language ="javascript" >
        var tim;       
        var min = '${sessionScope.min}';
        var sec = '${sessionScope.sec}';
       
    //    document.getElementById("showtime").innerHTML="AHOJ";
        
        function customSubmit(someValue){  
        	 document.getElementById('questionForm').minute.value = min;   
        	 document.getElementById('questionForm').second.value = sec; 
        	// document.getElementById('questionForm').submit();  
        	 }  
			
        function examTimer() {
            if (parseInt(sec) >0) {
			
			    document.getElementById("showtime").innerHTML = "Time Remaining :"+min+" Minute ," + sec+" Seconds";
                sec = parseInt(sec) - 1;                
                tim = setTimeout("examTimer()", 1000);
            }
            else {
			
			    if (parseInt(min)==0 && parseInt(sec)==0){
			    	document.getElementById("showtime").innerHTML = "Time Remaining :"+min+" Minute ," + sec+" Seconds";
				     alert("Time Up");
				     
				     
				     document.getElementById('questionForm').minute.value=0;
				     document.getElementById('questionForm').second.value=0;
				     
				     
				    
				     
				     document.getElementById('finishexam').value="Finish Exam";
				     document.getElementById('finishexam').click(); 
				     
				    //document.getElementById('questionForm').submit();
					 
			     }
				 
                if (parseInt(sec) == 0) {				
				    document.getElementById("showtime").innerHTML = "Time Remaining :"+min+" Minute ," + sec+" Seconds";					
                    min = parseInt(min) - 1;
					sec=59;
                    tim = setTimeout("examTimer()", 1000);
                }
               
            }
        }
    </script>

</body>
</html>