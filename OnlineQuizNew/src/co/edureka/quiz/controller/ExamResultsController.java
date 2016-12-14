package co.edureka.quiz.controller;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.edureka.quiz.DatabaseConnectionFactory;
import co.edureka.quiz.Exam;
import co.edureka.quiz.QuizQuestion;

/**
 * Servlet implementation class ExamController
 */
@WebServlet("/examResults")
public class ExamResultsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Map<Integer, QuizQuestion> questionList = new LinkedHashMap<Integer,QuizQuestion>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		boolean finish=false;
		
		HttpSession session=request.getSession();		

		// 
		  //zapisanie spustenia testu do DB
		Connection conRun=DatabaseConnectionFactory.createConnection();
		String selectedExam=(String)request.getSession().getAttribute("exam");
		String username=request.getSession().getAttribute("user").toString();
		String system_user=request.getRemoteAddr();
		String session_id=request.getSession().getId();
		
		
		
		 session=request.getSession(); 	
		 try
			{
	   	 Exam showExam=new Exam(selectedExam);	
	   	 
		 session.setAttribute("currentExam",showExam);
		}catch(Exception e){e.printStackTrace();}
			// 
		 Exam exam=(Exam)request.getSession().getAttribute("currentExam");
		 int size=exam.getTestSize();
		 for(int i=0;i<size;i++)
		 {
		 QuizQuestion q=new QuizQuestion();
		 q=exam.getQuestion(i);
		 questionList.put(i,q);
		 }
		 session.setAttribute("questionResultList", questionList);
		 
		 //nacitanie udajov z DB
		 Connection con=DatabaseConnectionFactory.createConnection();
		 ResultSet set=null;
		 
		 try
			{
			 Statement st=con.createStatement();
			
			 String sql = "select * from (select max(date) as max_date,question from results_detailed where name='"+username+"' group by question) t1 inner join results_detailed t2 on t1.max_date=t2.date and t1.question=t2.question order by t1.question ";
			//select * from (select max(date) as max_date from results_detailed 
			// where name='stanislav' and session= 
			//		 (select session from results_detailed where date=(select max(date) from results_detailed where name='stanislav'))
			//		 group by question) t1 
			//		 inner join results_detailed t2 on t1.max_date=t2.date order by question 
			 
			 System.out.println(sql);
			 set=st.executeQuery(sql);
					
			 request.getSession().setAttribute("lastTestAnswers", set);
			 /*
			// System.out.println(set+","+set.getString(1));
			 while(set.next())
			 {
				 String name = set.getString("name");
				 int score = set.getInt("score");
				 System.out.println(name+ "\t"+score);
			 }
			 System.out.println(sql1);
			 set=st1.executeQuery(sql1);
			 request.getSession().setAttribute("personalresults", set);
			 */
			 
			}catch(SQLException sqe){System.out.println("Error : While Fetching records from database");}
			try
			{
			 con.close();	
			}catch(SQLException se){System.out.println("Error : While Closing Connection");}
     
			
				
		 request.getRequestDispatcher("/WEB-INF/jsps/quizResults.jsp").forward(request,response);
				
			
						
		
		
	}

}
