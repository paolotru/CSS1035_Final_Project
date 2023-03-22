package banking_application;

public class Negative_Exception extends Exception 

{
	
	private static final long serialVersionUID = 1L;

	public Negative_Exception()
	
	{
		
		super();
	
	}
	
	public Negative_Exception(double funds)
	
	{
		
		super("Negative Value: " + funds);
	
	}

}
