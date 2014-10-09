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
		Document seQuestions = Main.getXMLDocument(getFeedURL());
		
		NodeList questionUrls = seQuestions.getElementsByTagName("id");
		
		ArrayList<Question> questions = new ArrayList<Question>();
		
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
