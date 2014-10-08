package main;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main 
{

	public static void main(String[] args) 
	{
		System.out.println();
		System.out.println("**  **");
		System.out.println();
		
		Document seSites = getXMLDocument("https://stackexchange.com/feeds/sites");
		
		NodeList sites = seSites.getElementsByTagName("entry");
		
		for (int i = 0; i < sites.getLength(); i++) 
		{
			NodeList siteDetails = sites.item(i).getChildNodes();
			
			for (int j = 0; j < siteDetails.getLength(); j++) 
			{
				Node siteDetail = siteDetails.item(j);
				
				if (siteDetail.getNodeName().equals("id") || siteDetail.getNodeName().equals("title"))
				{
					System.out.println(siteDetail.getNodeName()+":"+siteDetail.getTextContent().trim());
				}
			}
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
