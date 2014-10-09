package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
		
		Document seSites = getXMLDocument("https://stackexchange.com/feeds/sites");
		
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
			Collections.shuffle(categories);
			
			System.out.println("Categories for this turn: ");
			System.out.println();
			
			for (int i = 0; i < 3; i++) 
			{
				System.out.println((i+1)+" - "+categories.get(i).getName());
			}
			
			System.out.println();
			
			int categoryChoice = 0;
			
			while (categoryChoice<1 || categoryChoice>3)
			{
				System.out.print("Choose a category (1-3): ");
				
				try
				{
					categoryChoice = keyboard.nextInt();
				}
				catch (Exception e)
				{
					System.out.println("Error with category choice - "+e);
					keyboard.next();
				}
			}
			
			System.out.println();
			
			Category categoryInPlay = categories.get(categoryChoice-1);
			
			ArrayList<Question> questions = categoryInPlay.getQuestions();
			
			for (Question question : questions) 
			{
				System.out.println(question.getFeedURL());
			}
			
			System.out.println();
		}
	}
	
	public static Document getXMLDocument(String urlString)
	{
		Document doc = null;
		        
        try
		{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			doc = db.parse(urlString);
			doc.normalize();
		}
		catch(Exception e)
		{
			System.out.println("Failed to parse XML! "+e);
			System.exit(1);
		}
        
		return doc;
	}

}
