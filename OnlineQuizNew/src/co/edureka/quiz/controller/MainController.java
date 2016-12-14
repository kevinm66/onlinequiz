package co.edureka.quiz.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import co.edureka.quiz.CreateDOM;
import co.edureka.quiz.DatabaseConnectionFactory;

@WebServlet(urlPatterns = { "/login", "/register", "/takeExam", "/logout" })
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String applicationContextPath = request.getContextPath();

		if (request.getRequestURI().equals(applicationContextPath + "/")) {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/WEB-INF/jsps/home.jsp");
			dispatcher.forward(request, response);
		} else if (request.getRequestURI().equals(
				applicationContextPath + "/login")) {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/WEB-INF/jsps/login.jsp");
			dispatcher.forward(request, response);
		} else if (request.getRequestURI().equals(
				applicationContextPath + "/register")) {
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/WEB-INF/jsps/register.jsp");
			dispatcher.forward(request, response);
		} else if (request.getRequestURI().equals(
				applicationContextPath + "/takeExam")) {
			request.getSession().setAttribute("currentExam", null);
			request.getSession().setAttribute("totalNumberOfQuizQuestions",null);
			request.getSession().setAttribute("quizDuration",null);
			request.getSession().setAttribute("min",null);
			request.getSession().setAttribute("sec",null);
			
			String exam = request.getParameter("test");
			request.getSession().setAttribute("exam", exam);
			
			
			if (request.getSession().getAttribute("user") == null) {
				request.getRequestDispatcher("/login").forward(request,
						response);
				
			} else {
				
				//nacitanie vysledkov testov z DB
				System.out.println(exam);
				Connection con=DatabaseConnectionFactory.createConnection();
				ResultSet set=null;
				ResultSet set3=null;
				ResultSet set2=null;
				String username=request.getSession().getAttribute("user").toString();
				try
				{
				 Statement st=con.createStatement();
				 Statement st1=con.createStatement();
				 Statement st2=con.createStatement();
				 Statement st3=con.createStatement();
				 String sql = "Select name,score,date from  results where test='"+exam+"' order by score desc limit 10 ";
				 String sql1 = "Select name,score,date from  results where test='"+exam+"' and name='"+username+"' order by score desc limit 10 ";
				 String sql2= "Select count(*) from results where name='"+username+"'";
				 String sql3= "Select max(status) from tests where test_name='"+exam+"'";
				 System.out.println(sql);
				 set=st.executeQuery(sql);
				 set2=st2.executeQuery(sql2);
				 set3=st3.executeQuery(sql3);
				 set2.next();
				 set3.next();
				 int runningExam = set3.getInt(1);
				 int hasFinishedExam= set2.getInt(1);
				 
				 request.getSession().setAttribute("hasFinishedExam",hasFinishedExam );
				 request.getSession().setAttribute("runningExam",runningExam );
				 
				 request.getSession().setAttribute(exam, set);
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
				 
				}catch(SQLException sqe){System.out.println("Error : While Fetching records from database");}
				try
				{
				 con.close();	
				}catch(SQLException se){System.out.println("Error : While Closing Connection");}
				
				
				System.out.println(request.getSession().getAttribute("user"));
				//koniec nacitavania vysledkov testov z DB
				
				
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/WEB-INF/jsps/quizDetails.jsp");
				
				Document document=null;
				try{
				document=CreateDOM.getDOM(exam);
				
				request.getSession().setAttribute("totalNumberOfQuizQuestions",document.getElementsByTagName("size").item(0).getTextContent());
				request.getSession().setAttribute("quizDuration",document.getElementsByTagName("quizDuration").item(0).getTextContent());
				request.getSession().setAttribute("min",document.getElementsByTagName("quizDuration").item(0).getTextContent());
				request.getSession().setAttribute("sec","0");
				
				System.out.println("Minutes "+request.getSession().getAttribute("min")+"---------------- sec   "+request.getSession().getAttribute("sec"));
				}				
				catch(Exception e){e.printStackTrace();}
				
				dispatcher.forward(request, response);
			}
		} else if (request.getRequestURI().equals(
				applicationContextPath + "/logout")) {
			request.getSession().invalidate();
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("/WEB-INF/jsps/home.jsp");
			dispatcher.forward(request, response);
		}

	}

}
