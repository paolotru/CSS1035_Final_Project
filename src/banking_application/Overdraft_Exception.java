package banking_application;

public class Overdraft_Exception extends Exception {
	
	private static final long serialVersionUID = 1L;

	public Overdraft_Exception(double amount) {
		super("Invalid amount"+amount);
	}
}
