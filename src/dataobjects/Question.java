package dataobjects;

public class Question 
{
	private int id;
	private Category category;
	
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
}
