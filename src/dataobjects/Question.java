package dataobjects;

import main.Main;

import org.apache.commons.lang3.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Question 
{
	private int id;
	private Category category;
	private String question;
	private String answer;
	
	public Question(int id, Category category) 
	{
		this.id = id;
		this.category = category;
	}
	
	public int getId()
	{
		return id;
	}
		
	public String getFeedURL()
	{
		return category.getFeedURL()+"/question/"+id;
	}
	
	public String getQuestion()
	{
		return question;
	}
	
	public String getAnswer()
	{
		return answer;
	}
	
	public boolean populateData()
	{
		try
		{
			Document seQuestions = Main.getXMLDocument(getFeedURL());
			
			NodeList titles = seQuestions.getElementsByTagName("title");		
			NodeList summaries = seQuestions.getElementsByTagName("summary");
			
			question = "* Subject: \t"+titles.item(0).getTextContent().trim() + "\n\n" + summaries.item(0).getTextContent().trim();
			answer = summaries.item(1).getTextContent().trim();
			
			question = StringEscapeUtils.unescapeHtml4(question);
			answer = StringEscapeUtils.unescapeHtml4(answer);
			
			question = question.replaceAll("\\<.*?\\>", "");
			answer = answer.replaceAll("\\<.*?\\>", "");
			
			question = question.replace("Stack Exchange", "");
			question = question.replace(this.category.getName(), "");
			question = question.replace(" - ", "");
			
			if (question==null || answer==null)
			{
				return false;
			}
			
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
}
