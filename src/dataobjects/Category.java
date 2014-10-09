package dataobjects;

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
}
