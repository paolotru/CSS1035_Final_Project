package junit;

import org.junit.Assert;
import org.junit.Test;

import banking_application.Checking_S2022_Group_1;
import banking_application.Savings_S2022_Group_1;

public class Deposit_Test  {

	@Test
	
	public void testCheckingDeposit() 
	
	{
		
		Checking_S2022_Group_1 checkingAccount = new Checking_S2022_Group_1();
		
		checkingAccount.deposit(-1000000);
		Assert.assertEquals(0, checkingAccount.getBalance(),0);

		checkingAccount.deposit(1000000);
		
		Assert.assertEquals(1000000, checkingAccount.getBalance(), Math.abs(1000000 - checkingAccount.getBalance()));
	
	}
	
	@Test
	
	public void testSavingsDeposit() 
	
	{
		
		Savings_S2022_Group_1 savingsAccount = new Savings_S2022_Group_1();

		savingsAccount.deposit(-1000000);
		Assert.assertEquals(0, savingsAccount.getBalance(), 0);
		
		savingsAccount.deposit(1000000);
		
		Assert.assertEquals(1000000, savingsAccount.getBalance(), Math.abs(1000000 - savingsAccount.getBalance()));
	
	}

}