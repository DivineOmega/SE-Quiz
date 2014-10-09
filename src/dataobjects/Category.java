package dataobjects;

import java.util.ArrayList;
import java.util.Collections;

import main.Main;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Category 
{
	private String name;
	private String url;
	
	public Category(String name, String url) 
	{
		this.name = name;
		this.url = url;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getURL()
	{
		return url;
	}
	
	public String getFeedURL()
	{
		return url+"/feeds";
	}
	
	public ArrayList<Question> getQuestions()
	{
		Document seQuestions = null;
		
		ArrayList<Question> questions = new ArrayList<Question>();
		
		try
		{
			seQuestions = Main.getXMLDocument(getFeedURL());
		}
		catch (Exception e)
		{
			System.out.println("Error retrieving questions for this category. "+e);
			return questions;
		}
			
		NodeList questionUrls = seQuestions.getElementsByTagName("id");
		
		for (int i = 0; i < questionUrls.getLength(); i++) 
		{
			String[] questionUrlParts = questionUrls.item(i).getTextContent().trim().split("/");
			
			int questionId = 0;
			
			try
			{
				questionId = Integer.parseInt(questionUrlParts[questionUrlParts.length-1]);
			}
			catch (NumberFormatException e)
			{
				continue;
			}
			questions.add(new Question(questionId, this));
			
		}
		
		Collections.shuffle(questions);
		
		return questions;
	}
}
