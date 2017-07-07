<%@ page language="java" import="java.sql.ResultSet"  import="java.util.*" import="co.edureka.quiz.*" contentType="text/html; charset=ISO-8859-1"
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



<div  style="position:absolute;left:5%;right:10%;top:150px;color:#000000">
<b style="color:#dfe9ec"> List of all questions with correct answers for the last test in ${sessionScope.exam} Quiz: </b>
<table id="t03" class="CSSTableGenerator1">
<tr> 
<td style="width: 5%"> <b>ID</b> </td> 
<td style="width: 20%"> <b>Question</b> </td> <td style="width: 35%"><b> Options </b></td> 
 <td style="width: 5%"><b> Correct </b></td>
 <td style="width: 5%"><b> Answered </b></td>
 <td style="width: 30%"><b> Explanation</b></td>
</tr>

<%
ResultSet set3= (ResultSet) request.getSession().getAttribute("lastTestAnswers");    


Map<Integer,QuizQuestion> questionList = (Map<Integer,QuizQuestion>) request.getSession().getAttribute("questionResultList");
String user = request.getSession().getAttribute("user").toString();
   for(Integer qn: questionList.keySet()){
	//	for(QuizQuestion question: questionList){
		String answers="";	
		if(set3.next() == true ) 
			{
				answers=set3.getString("choice");
			}
		else answers="N/A";
		
			QuizQuestion question=questionList.get(qn);
			int[] answeredItems=question.getCorrectAnswersIndex();
			String correctAnswers="";
			int i=0; //kvoli tomu, aby mi nedaval ciarku hned pri prvom cisle;
			for (Integer a: answeredItems)
			{
				if(i==0) {
					
					if (a!=-1) correctAnswers=String.valueOf(a);
				}
				else{
				
					if (a!=-1) correctAnswers=correctAnswers +","+ String.valueOf(a);
				}
				i=i+1;
				
			}
			out.println("<tr> <td>"+(question.getQuestionNumber()+1)+ "</td> <td>"+question.getQuestion()+ "</td><td> 1) "+question.getQuestionOptions()[0]+"<br>"+
					 "2) "+question.getQuestionOptions()[1]+"<br>"+
					 "3) "+question.getQuestionOptions()[2]+"<br>"+
					 "4) "+question.getQuestionOptions()[3]+"<br>"+
					
					
					"</td><td>["+correctAnswers+"]</td>"+
					"<td>"+answers+"</td>"+
					"<td>"+question.getExplanation()+"</td>"+
					"</tr>");
	}
	
	//out.println(set);
	
	//out.println(user);
//	out.println(set1.getString(1));

%>

</table>


</div>





<div  style="position:absolute;left:35%;top:80px">
<h2 align="center"><a href='${pageContext.request.contextPath}'>Back to Home</a></h2>
</div>


</body>
</html>