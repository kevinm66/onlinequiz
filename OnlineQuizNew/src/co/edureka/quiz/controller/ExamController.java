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
import java.util.List;

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
@WebServlet("/exam")
public class ExamController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		boolean finish=false;
		
		HttpSession session=request.getSession();		
		try
		{
			if(session.getAttribute("currentExam")==null)
		  {  session=request.getSession(); 	
		     String selectedExam=(String)request.getSession().getAttribute("exam"); 
		     System.out.println("Setting Exam "+selectedExam);
			 Exam newExam=new Exam(selectedExam);		  
			 session.setAttribute("currentExam",newExam);
			 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
				Date date = new Date();
				String started=dateFormat.format(date);
			  session.setAttribute("started",started);
		  }
		
		}catch(Exception e){e.printStackTrace();}
		// 
		  //zapisanie spustenia testu do DB
		Connection conRun=DatabaseConnectionFactory.createConnection();
		String selectedExam=(String)request.getSession().getAttribute("exam");
		String username=request.getSession().getAttribute("user").toString();
		String system_user=request.getRemoteAddr();
		String session_id=request.getSession().getId();
	//	int uniqueID=(Integer)request.getAttribute("uniqueID");
		if(session.getAttribute("uniqueID")==null)
		{
			session.setAttribute("uniqueID", "111");
			try
			{
			 Statement stRun=conRun.createStatement();
			 String sql = "INSERT INTO started_exams values ('"+selectedExam+"','"+username+"',NOW(),'"+system_user+"','"+session_id+"',null)";
			 		System.out.println(sql);
			 stRun.executeUpdate(sql);
			}catch(SQLException sqe){System.out.println("Error : While Inserting record in database");}
			try
			{
			 conRun.close();	
			}catch(SQLException se){System.out.println("Error : While Closing Connection");}
			
		}
		//koniec zapisu do DB
		
		
		
        Exam exam=(Exam)request.getSession().getAttribute("currentExam");		
        List<Integer> answer = new ArrayList<Integer>();
        answer.clear();
        if(exam.currentQuestion==0){	
			exam.setQuestion(exam.currentQuestion,answer,0);
		    QuizQuestion q=exam.questionList.get(exam.currentQuestion);	
			session.setAttribute("quest",q);
        }
			
			String action=request.getParameter("action");
			
			System.out.println("Value of Action "+action+"   " +request.getParameter("minute"));
			int minute=-1;
			int second=-1;
			
			//ak este cas nevyprsal tak nastavit sucasne hodnnoty aby ich bolo vidiet aj na Next stranke
			
			if(request.getParameter("minute")!=null)
			{			
				//minute=Integer.parseInt(request.getParameter("minute"));
				//second=Integer.parseInt(request.getParameter("second"));
				request.getSession().setAttribute("min",request.getParameter("minute") );
				request.getSession().setAttribute("sec",request.getParameter("second") );
				System.out.println("nie je nula");
			}
			
			
			String[] radio=request.getParameterValues("answer");
		//	System.out.println("zaskrtnute:  "+radio +", "+request.getParameterValues());
			if(request.getParameterValues("answer")!=null)
			System.out.println("radio = "+ radio);
			
			int selectedRadio=-1;
			
		//	exam.selections.put(exam.currentQuestion, selectedRadio);
			if(radio != null)
			{
				for(int i=0;i<radio.length;i++)
				{
					if("1".equals(radio[i]))
					{
						selectedRadio=1;
					//	exam.selections.put(exam.currentQuestion, selectedRadio);
						answer.add(selectedRadio);
					
						System.out.println("You selected "+selectedRadio);
					}
					else if("2".equals(radio[i]))
					{
						selectedRadio=2;
						answer.add(selectedRadio);
					
					//	exam.selections.put(exam.currentQuestion, selectedRadio);
						System.out.println("You selected "+selectedRadio);
					}
					else if("3".equals(radio[i]))
					{
						selectedRadio=3;
						answer.add(selectedRadio);
					
						//exam.selections.put(exam.currentQuestion, selectedRadio);
						System.out.println("You selected "+selectedRadio);
					}
					else if("4".equals(radio[i]))
					{
						selectedRadio=4;
						answer.add(selectedRadio);
					
						//exam.selections.put(exam.currentQuestion, selectedRadio);
						System.out.println("You selected "+selectedRadio);
					}
					
				}
				//ak nie je vyplnena ziadna hodnota, tak tam dam umele hodnoty
				//if(answer.size()==0) { answer.add(9); System.out.println("Nastavujem umele hodnoty");}
				if(answer != null && answer.size()>0 ) exam.selections.put(exam.currentQuestion, answer);	
			//	answer.clear();
				
				  //zapisanie odpovede pre danu otazku do DB
				Connection conQ=DatabaseConnectionFactory.createConnection();
				
				try
				{
				 Statement st=conQ.createStatement();
				 String sql = "INSERT INTO results_detailed values ('"+selectedExam+"','"+username+"',NOW(),"+(exam.currentQuestion+1)+",'"+answer+"','"+session_id+"')";
				 		System.out.println(sql);
				 st.executeUpdate(sql);
				}catch(SQLException sqe){System.out.println("Error : While Inserting record in database");}
				try
				{
				 conQ.close();	
				}catch(SQLException se){System.out.println("Error : While Closing Connection");}
				
				
				//koniec zapisu do DB
				
				
				System.out.println("Logujem  " +selectedExam+","+username+","+(exam.currentQuestion+1)+","+ answer);
			
			}
			else
			{
				
				if(answer != null )
				{
					exam.selections.put(exam.currentQuestion, answer);	
					answer.add(9); System.out.println("Nastavujem umele hodnoty");
				}
			}
			
			if("Next".equals(action)){
				exam.currentQuestion++;
				exam.setQuestion(exam.currentQuestion,answer,0);
			    QuizQuestion q=exam.questionList.get(exam.currentQuestion);	
			    QuizQuestion q1=exam.questionList.get(exam.currentQuestion-1);	
			  	session.setAttribute("quest",q);
			  	session.setAttribute("quest1",q1);
			}
			else if("Previous".equals(action))
			{   System.out.println("You clicked Previous Button");
				exam.currentQuestion--;
				exam.setQuestion(exam.currentQuestion,answer,1);
			    QuizQuestion q=exam.questionList.get(exam.currentQuestion);	
			//    exam.synchronizeSelections();
			//    QuizQuestion q1=exam.questionList.get(exam.currentQuestion-1);	
				session.setAttribute("quest",q);
			}
			else if("Finish Exam".equals(action))
			{   finish=true;
				int result=exam.calculateResult(exam);				
				request.setAttribute("result",result);
				request.getSession().setAttribute("currentExam",null);
				  int size=exam.getTestSize();
			        request.setAttribute("size",size);
			        
			        session.removeAttribute("uniqueID");
			    	selectedExam=(String)request.getSession().getAttribute("exam"); 
					DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date date = new Date();
					
					//ziskanie mena pouzivatela
				//	String name = request.getUserPrincipal().getName();
					system_user=request.getRemoteAddr();
					System.out.println("Meno: " + system_user);
					
					//ziskanie poctu sekund, ktore trval test
					System.out.println("Sekund: "+request.getSession().getAttribute("sec"));
					int sec=Integer.parseInt(request.getSession().getAttribute("sec").toString());
					int min=Integer.parseInt(request.getSession().getAttribute("min").toString());
					int durationMin=Integer.parseInt(request.getSession().getAttribute("quizDuration").toString());
					
					int sectotal=(((durationMin-min)-1)*60)+(60-sec);
				//	session.setAttribute("minutesRemaining",(exam.quizDuration-1)-min);
					username=request.getSession().getAttribute("user").toString();
					session_id=request.getSession().getId();
					System.out.println(selectedExam+","+username+","+result+","+dateFormat.format(date));   
			        
			     //zapisanie vysledkov do DB
				Connection con=DatabaseConnectionFactory.createConnection();
				
				try
				{
				ResultSet setid=null;
					Statement st=con.createStatement();
				 Statement stid=con.createStatement();
				 String sqlid="SELECT MAX(ID) FROM STARTED_EXAMS WHERE NAME='"+username+"'";
				 setid=stid.executeQuery(sqlid);
				 setid.next();
				 int maxid=setid.getInt(1);
				 System.out.println("Zapisuje vysledky pre id: " + maxid);
				 String sql = "INSERT INTO results values ('"+selectedExam+"','"+username+"','"+result+"',NOW(),"+sectotal+",'"+system_user+"','"+session_id+"','"+maxid+"')";
				 		System.out.println(sql);
				 st.executeUpdate(sql);
				}catch(SQLException sqe){System.out.println("Error : While Inserting record in database"+sqe.getMessage()+","+sqe.getErrorCode());}
				try
				{
				 con.close();	
				}catch(SQLException se){System.out.println("Error : While Closing Connection");}
				
				
				//koniec zapisu do DB
			
				
				request.getRequestDispatcher("/WEB-INF/jsps/result.jsp").forward(request,response);
				
			}
						
		if(finish!=true){
		request.getRequestDispatcher("/WEB-INF/jsps/exam.jsp").forward(request,response);
		}
		
	}

}
