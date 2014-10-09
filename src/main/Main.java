package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import dataobjects.Category;
import dataobjects.Question;

public class Main 
{
	private static ArrayList<Category> categories = new ArrayList<Category>();

	public static void main(String[] args) 
	{		
		System.out.println();
		System.out.println("** Welcome **");
		System.out.println();
		
		System.out.println("Loading categories...");
		System.out.println();
		
		Document seSites = null;
		
		try
		{
			seSites = getXMLDocument("https://stackexchange.com/feeds/sites");
		}
		catch (Exception e)
		{
			System.out.println("Error getting categories - "+e);
			System.out.println("Can not continue. Exiting...");
			System.exit(1);
		}
		
		NodeList sites = seSites.getElementsByTagName("entry");
		
		for (int i = 0; i < sites.getLength(); i++) 
		{
			NodeList siteDetails = sites.item(i).getChildNodes();
			
			String catName = null;
			String catURL = null;
			
			for (int j = 0; j < siteDetails.getLength(); j++) 
			{
				Node siteDetail = siteDetails.item(j);
				
				if (siteDetail.getNodeName().equals("id")) catURL = siteDetail.getTextContent().trim();
				if (siteDetail.getNodeName().equals("title")) catName = siteDetail.getTextContent().trim();
			}
			
			if (catName != null && catURL != null)
			{
				categories.add(new Category(catName, catURL));
			}
		}
		
		// Setup input
		Scanner keyboard = new Scanner(System.in);
		
		// Start of main loop
		while(true)
		{
			System.out.println("*** --------------------- ***");
			System.out.println();
			
			Collections.shuffle(categories);
			
			System.out.println("Categories for this turn: ");
			System.out.println();
			
			int numCategoriesToShow = 4;
			
			for (int i = 0; i < numCategoriesToShow; i++) 
			{
				System.out.println((i+1)+" - "+categories.get(i).getName());
			}
			
			System.out.println();
			
			int categoryChoice = 0;
			
			while (categoryChoice<1 || categoryChoice>numCategoriesToShow)
			{				
				System.out.print("Choose a category (1-"+numCategoriesToShow+"): ");
				
				try
				{
					categoryChoice = keyboard.nextInt();
				}
				catch (Exception e)
				{
					keyboard.next();
					System.out.println("Error with category choice - "+e);
				}
			}
			
			System.out.println();
			
			Category categoryInPlay = categories.get(categoryChoice-1);
			
			ArrayList<Question> questions = categoryInPlay.getQuestions();
			
			if (questions.size()==0)
			{
				System.out.println("Sorry, no questions are available for this category at the moment.");
				System.out.println();
				continue;
			}
			
			Question question = questions.get(0);
			
			if (!question.populateData())
			{
				System.out.println("We couldn't get all the information we needed for this question. Try another category.");
				System.out.println();
				continue;
			}
						
			System.out.println(question.getQuestion());
			System.out.println();
			
			String playerAnswer = "";
			
			keyboard.nextLine();
			
			while (true)
			{
				System.out.print("* Your answer (min 50 chars, max 300 chars): ");
								
				playerAnswer = keyboard.nextLine();
				System.out.println();
				
				if (playerAnswer.length()>=50 && playerAnswer.length()<=300)
				{
					break;
				}
				else
				{
					System.out.println("Your answer was not within the characters limits.");
					System.out.println();
				}
			}
			
			
			String fullAnswer = question.getAnswer();
			String trimmedAnswer = fullAnswer;
			
			trimmedAnswer = trimmedAnswer.replace("\r", " ").replace("\n", " ");
			
			if (trimmedAnswer.length()>playerAnswer.length())
			{
				trimmedAnswer = trimmedAnswer.substring(0, playerAnswer.length());
			}
			
			System.out.println("* Actual answer (trimmed to "+trimmedAnswer.length()+" chars): "+trimmedAnswer+"[...]");
			System.out.println();
			
			int score = playerAnswer.length() - StringUtils.getLevenshteinDistance(playerAnswer, trimmedAnswer);
			
			System.out.println("Your score: "+score);
			System.out.println();
		}
	}
	
	public static Document getXMLDocument(String urlString) throws ParserConfigurationException, SAXException, IOException
	{
		Document doc = null;
		      
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		doc = db.parse(urlString);
		doc.normalize();
		
		return doc;
	}

}
