package main;

public class SimilarText 
{

    private String  string = "";
    private String string2 = ""; 
    private int percent = 0; 
    private int position1 = 0; 
    private int position2 = 0;

    public SimilarText(String str1,  String str2)
    {
        this.string = str1.toLowerCase();   
        this.string2 = str2.toLowerCase(); 
    }

    public int similar()
    {
        string= string.trim(); 
        string2= string2.trim();
        int len_str1 = string.length();
        int len_str2 = string2.length(); 

        int max= 0; 
        if (string.length()>1 && string2.length()>1 )
        {
            for (int p=0  ; p<=len_str1; p++)
            {
                for (int q=0  ; q<=len_str2; q++){
                    for(int l=0 ; (p + l < len_str1) && (q + l < len_str2) && (string.charAt(l) == string2.charAt(l)); l++)
                    {
                        if (l>max)
                        {
                            max=l ; 
                            position1 = p ; 
                            position2 = q; 
                        }
                    }
                }
            }

	        this.percent = max * 200 / ((string.length()) + (string2.length())  - (max) + (position2 - position1)   ) - (max*string.length() ) ;
	        if (percent>100) percent = 100; 
	        if (percent<0) percent = 0; 
        }
        
        return this.percent ; 
    }
}
