package banking_application;
public abstract class Account_S2022_Group_1 {

	private double balance;
	public int account_number;
	
	public Account_S2022_Group_1() {
		balance=0.00;
	}

	public Account_S2022_Group_1(double newBalance) {

		balance = newBalance;

	}
	
	/*
	 * The following getters and setters should be considered sensitive data.
	 * These methods provide information relating to the user's account.
	 */
	public double getBalance() {

		return balance;

	}

	public void setBalance(double balance) {

		this.balance = balance;

	}
	
	/*
	 * Method to withdraw funds from an account.
	 * Exceptions are thrown when the user attempts to withdraw a negative amount or overdraft.
	 */
	public void withdraw(double funds) throws Negative_Exception, Overdraft_Exception {

		if (funds > 0) {
			if (funds <= this.balance) {

				setBalance(getBalance() - funds);

			}else 
				throw new Overdraft_Exception(funds);
		}else
			throw new Negative_Exception(funds);

	}

	/*
	 * Method to deposit funds into an account.
	 * Exception is thrown when the user attempts to deposit a negative amount.
	 */
	public void deposit(double funds) throws Negative_Exception {

		if (funds > 0) 
		{
			this.balance = funds + balance;
			
			System.out.println(funds + " dollars has been deposited into your checking account.");
		}
			
		else
			throw new Negative_Exception(funds);
	
	}

}