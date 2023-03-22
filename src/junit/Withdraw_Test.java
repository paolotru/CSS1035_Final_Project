package junit;

import org.junit.Assert;
import org.junit.Test;

import banking_application.*;

public class Withdraw_Test {

	@Test
	public void testWithdraw() {
		Savings_S2022_Group_1 savingsAccount = new Savings_S2022_Group_1(100.00);
		
		savingsAccount.withdraw(-10);
		Assert.assertEquals(savingsAccount.getBalance(),100,0);
		
		savingsAccount.withdraw(10);
		Assert.assertEquals(savingsAccount.getBalance(),90,0);
		
		savingsAccount.withdraw(100);
		Assert.assertEquals(savingsAccount.getBalance(),90,0);
		
	}
	
	@Test
	public void testWithdrawFees() {
		Checking_S2022_Group_1 checkingAccount = new Checking_S2022_Group_1(100.00);
		
		checkingAccount.withdraw(-10);
		Assert.assertEquals(checkingAccount.getBalance(),100,0);
		
		checkingAccount.withdraw(10);
		Assert.assertEquals(checkingAccount.getBalance(),90,0);
		
		checkingAccount.withdraw(100);
		Assert.assertEquals(checkingAccount.getBalance(),90,0);
		
		checkingAccount.withdraw(10);
		Assert.assertEquals(checkingAccount.getBalance(),80,0);
		
		checkingAccount.withdraw(10);
		Assert.assertEquals(checkingAccount.getBalance(),60,0);
		
	}

}
