package banking_application;
public class Checking_S2022_Group_1 extends Account_S2022_Group_1 {

	private double feeCharge = 10;
	private int numOfWithdrawals = 0;

	public Checking_S2022_Group_1() {

	}

	public Checking_S2022_Group_1(double newBalance) {
		super(newBalance);
	}

	public void withdraw(double funds) throws Negative_Exception, Overdraft_Exception {

		if (funds > 0) {

			if (funds <= getBalance()) {

				setBalance(getBalance() - funds);
				numOfWithdrawals++;
				withdrawFees();
				
				System.out.println(funds + " dollars has been withdrawn from your checking account.");

			}else
				throw new Overdraft_Exception(funds);
		}else
	
			throw new Negative_Exception(funds);
			
	}

	/* 
	 * Method to charge fees if more than x amount of withdrawals are made.
	 * Set to 2 for now just to make testing easier.
	 * Example: Once the user makes more than 2 withdrawals, a fee is added to the withdrawal amount.
	 */
	public void withdrawFees() {
		if (numOfWithdrawals > 2) {
			setBalance(getBalance() - feeCharge);
			System.out.println("Withdrawals fees are being applied.");
		}
		
	}

}
