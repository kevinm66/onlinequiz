package co.edureka.quiz;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import co.edureka.quiz.CreateDOM;

public class Exam {
	Document dom;
	public int currentQuestion=0;	
	public int quizDuration=0;
	public int totalNumberOfQuestions=0;
	public int[] answered_questions = {-2,-2,-2,-2};
	public Map<Integer,List<Integer>> selections=new LinkedHashMap<Integer,List<Integer>>();
	public Map<Integer, QuizQuestion> questionList = new LinkedHashMap<Integer,QuizQuestion>();
	public Map<Integer,int[]> answ = new LinkedHashMap<Integer,int[]>();
	
	public Exam(String test) throws SAXException,ParserConfigurationException,IOException, URISyntaxException{
		dom=CreateDOM.getDOM(test);
	}
	//vrati objekt otazka, ak by som chcel neskor zobrazovat informacie o otazkach (aj ked nebezi test - napr. pre zobrazovanie vysledkov). 
	public QuizQuestion getQuestion(int i) 
	{
		int number=i;
		String options[]=new String[4];
	    String question=null;
	    int correct=0;
	    int[] correct_answers = {-1, -1, -1, -1 };
	    
	    System.out.println("Dom "+dom);
		NodeList qList=dom.getElementsByTagName("question");
	    NodeList childList=qList.item(i).getChildNodes();
	   
	    int counter=0;
	    int counter_a=0;
	    for (int j = 0; j < childList.getLength(); j++) {
            Node childNode = childList.item(j);
            if ("answer".equals(childNode.getNodeName()))
            {
                options[counter]=childList.item(j).getTextContent();
                counter++;
            }
            else if("quizquestion".equals(childNode.getNodeName()))
            {
            	question=childList.item(j).getTextContent();
            }
            else if("correct".equals(childNode.getNodeName()))
            {
            	correct_answers[counter_a]=Integer.parseInt(childList.item(j).getTextContent());
            	correct=Integer.parseInt(childList.item(j).getTextContent());
            	counter_a++;
            }
            
        }
	    System.out.println("Retrieving Question Number "+number+ " for showing Results");
			
		QuizQuestion q=new QuizQuestion();
		q.setQuestionNumber(number);
		q.setQuestion(question);
		q.setCorrectOptionIndex(correct);
		q.setCorrectAnswersIndex(correct_answers);
		q.setQuestionOptions(options);
		return q;
	}
		
	public void setQuestion(int i,List<Integer> answered,int backward)
	{   int number=i;
		String options[]=new String[4];
	    String question=null;
	    int correct=0;
	    int[] correct_answers = {-1, -1, -1, -1 };
	    
	    System.out.println("Dom "+dom);
		NodeList qList=dom.getElementsByTagName("question");
	    NodeList childList=qList.item(i).getChildNodes();
	    System.out.println("Total Questions "+dom.getElementsByTagName("size").item(0).getTextContent());//.getFirstChild().getTextContent());
	    
	    System.out.println("Quiz Duration "+dom.getElementsByTagName("quizDuration").item(0).getTextContent());
	    int counter=0;
	    int counter_a=0;
	    for (int j = 0; j < childList.getLength(); j++) {
            Node childNode = childList.item(j);
            if ("answer".equals(childNode.getNodeName()))
            {
                options[counter]=childList.item(j).getTextContent();
                counter++;
            }
            else if("quizquestion".equals(childNode.getNodeName()))
            {
            	question=childList.item(j).getTextContent();
            }
            else if("correct".equals(childNode.getNodeName()))
            {
            	correct_answers[counter_a]=Integer.parseInt(childList.item(j).getTextContent());
            	correct=Integer.parseInt(childList.item(j).getTextContent());
            	counter_a++;
            }
            
        }
	    System.out.println("Retrieving Question Number "+number);
		System.out.println("Question is : "+question);
		for(String a:options)
		{
			System.out.println(a);
		}
		//System.out.println("Correct answer index : "+correct);
		for(Integer a:correct_answers)
		{
			System.out.println("Correct answer index: " +a);
		}
		
		QuizQuestion q=new QuizQuestion();
		q.setQuestionNumber(number);
		q.setQuestion(question);
		q.setCorrectOptionIndex(correct);
		q.setCorrectAnswersIndex(correct_answers);
		q.setQuestionOptions(options);
		
		//nastavujem zadane hodnoty do predchadzajucej otazky, pretoze tato funkcia sa vola, uz ked je inkrementalne pocitadlo zvysene o jedno (nastavuje sa tu dalsia nova otazka)
	/*	if(answered.size()>0&& number>0)
		{
			System.out.println("NASTAVUJEM MINULU OTAZKU : " +number);
			int novyindex=number;
			if(backward==1)
				novyindex++;
			else
				novyindex--;
			QuizQuestion q1=questionList.get(novyindex);	
	
			for(Integer a:answered)
			{
				q1.setAnsweredItem(a);
				System.out.println ("nastavujem answered: "+a);
			}
			//questionList.put(novyindex, q1);
		}*/
		questionList.put(number,q);
		
	}
	
	
	public Map<Integer,QuizQuestion> getQuestionList(){
		return this.questionList;
	}
	public int getTestSize() {
		NodeList testsize =dom.getElementsByTagName("size");
		System.out.println("Size xml "+testsize.getLength());
		Node node1 =testsize.item(0);
		//Element e= (Element)node1;
		System.out.println("Node value: "+node1.getTextContent());
		return Integer.parseInt(node1.getTextContent());
	}
	public int getCurrentQuestion(){return currentQuestion;}
	
	public Map<Integer,List<Integer>> getSelections(){
		return this.selections;
	}
	
	public int getTestDurationMinutes () {
		
		//nastavenie casoveho trvania testu
		//request.getSession().setAttribute("totalNumberOfQuizQuestions",dom.getElementsByTagName("totalQuizQuestions").item(0).getTextContent());
		int minutes=Integer.parseInt(dom.getElementsByTagName("quizDuration").item(0).getTextContent());
		return minutes;
		
		//koniec nastavenia casoveho trvania testu
		
	}
	public int getAnsweredItems(int question,int index)
	{
		if(question<selections.size()&& index<selections.get(question).size())
		return selections.get(question).get(index);
		else return -2;
	}
	public void synchronizeSelections ()
	{
		if(selections.size()>0)
		for(Integer a:selections.keySet())
		{
			List<Integer> selection=selections.get(a);
			int[] pole={-1,-1,-1,-1};
			for (Integer ii:selection)
			{
				//System.out.println("ii=: "+ii);
				//ak mam umelo nastavenu hodnotu - pre pripad, ked si uzivatel nic nevyberie
				if(ii>pole.length) pole[0]=ii;
				else
				pole[ii-1]=ii;
			}
			answ.put(a, pole);
		}
	}
	public int getAnsweredItem(int question,int item)
	{
		System.out.println("VYPISUJEM: "+question+","+item);
		System.out.println("pamat: "+selections);
		this.synchronizeSelections();
		if(question<answ.size())
		return answ.get(question)[item];
		else return -2;
	}
	public int calculateResult(Exam exam){
		int questionCorrect=0;
		int questionWrong=0;
		int questionCorrectEvent=0;
		int totalCorrect=0;
		Map<Integer,List<Integer>> userSelectionsMap=exam.selections;		
		List<Integer> userSelectionsList=new ArrayList<Integer>(10);
	/*
		for (Map.Entry<Integer, Integer> entry :userSelectionsMap.entrySet()) {
			userSelectionsList.add(entry.getValue());
		}
	*/
		Map<Integer,QuizQuestion> questionList=exam.questionList;
		//List<QuizQuestion> questionList=exam.questionList;
		List<Integer> correctAnswersList=new ArrayList<Integer>(10);
		int qi=0;
		int qa=0;
		int userSelectionsSize=userSelectionsMap.size();
		System.out.println("size questions: "+questionList.size()+", "+"size answers: "+userSelectionsSize);
		if(exam.selections.size()>0)
		{
				for(Integer qn: questionList.keySet()){
			//	for(QuizQuestion question: questionList){
					QuizQuestion question=questionList.get(qn);
					List<Integer> selection = (ArrayList<Integer>) userSelectionsMap.get(qi);
					qa=0;
					questionCorrect=0;
					questionWrong=0;
					for(Integer ii: selection){
						
						int qq=0;
						questionCorrectEvent=0;
						for(Integer jj: question.getCorrectAnswersIndex())
						{
							int odpoved=question.getCorrectAnswersIndex()[qq];
							if (ii==odpoved)
							{
								questionCorrect++;
							 questionCorrectEvent++; //tymto zistujem ci v sykle nasiel rovnaku odpoved. nemozem ist cez else, lebo tam zajde, aj ked najdu spravnu odpoved. kedze prechadza cele pole
							}
							qq++;
						}
						if(questionCorrectEvent==0) questionWrong++;
						System.out.println(qi+","+qa+" - "+ii+","+""+":: "+questionCorrect+","+questionWrong+"- "+totalCorrect);
						qa++;
						
					}
					if(questionCorrect>questionWrong) 
						totalCorrect=totalCorrect+(questionCorrect-questionWrong);
					else totalCorrect=totalCorrect+0; //sice tu tento riadok nemusel byt, ale pre nazornost a lepsiu pochopitelnost ho pridavam;
					qi++;
					//kontrola ci som neskoncil na stranke, kde som nevybral ziadnu moznost. vtedy bude questionList mnozina vacsia, ako selections. preto uz nemozem pokracovat
					System.out.println("porovnanie(qi,size): " + qi+", "+userSelectionsSize);
					if(qi>(userSelectionsSize-1)) break;
				}
		
		}
		/*
			for(int i=0;i<question.getCorrectAnswersIndex().length;i++)
					if(question.getCorrectAnswersIndex()[i] != -1)
			correctAnswersList.add(question.getCorrectOptionIndex());
		}
		
		for(int i=0;i<selections.size();i++){
			System.out.println(userSelectionsList.get(i)+" --- "+correctAnswersList.get(i));
			if((userSelectionsList.get(i)-1)==correctAnswersList.get(i)){
				totalCorrect++;
			}
		}
		*/
		System.out.println("You Got "+totalCorrect+" Correct");	
		return totalCorrect;
	}
	public int getTotalNumberOfQuestions(){
		return totalNumberOfQuestions;
	}
}
