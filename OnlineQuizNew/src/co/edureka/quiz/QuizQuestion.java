package co.edureka.quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizQuestion {
	
	int questionNumber;
	String question;
	String questionOptions[];
	String explanation;
	int correctAnswersIndex[];
	int correctOptionIndex;
	int[] answeredItems={-1,-1,-1,-1};
	
	public void setExplanation(String expl)
	{
		explanation=expl;
	}
	public String getExplanation()
	{
		return explanation;
	}
	public void setAnsweredItem(int i)
	{
		answeredItems[i-1]=i;
	}
	public int getAnsweredItem(int i)
	{
		return answeredItems[i];
	}
	public int getAnsweredItemsSize()
	{
		return answeredItems.length;
	}
	public String getQuestion()
	{ 
		return question;
	}
	
	public int getQuestionNumber()
	{
		return questionNumber;
	}
	
	public void setQuestionNumber(int i)
	{
		questionNumber=i;
	}
	
	public int getCorrectOptionIndex()
	{
		return correctOptionIndex;
	}
	
	public String[] getQuestionOptions()
	{
		return questionOptions;
	}
	public int[] getCorrectAnswersIndex()
	{
		return correctAnswersIndex;
	}
	public void setCorrectAnswersIndex(int[] i)
	{
		correctAnswersIndex=i;
	}
	public void setQuestion(String s)
	{
		question=s;
	}
	public void setCorrectOptionIndex(int i)
	{
		correctOptionIndex=i;
	}
	public void setQuestionOptions(String[]s)
	{
		questionOptions=s;
	}

}
