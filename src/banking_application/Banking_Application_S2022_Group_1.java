package banking_application;

import java.util.Scanner;
import java.util.Base64;
import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.text.Normalizer;
import java.io.IOException;

public class Banking_Application_S2022_Group_1

{

	static Handler fileHandler = null;

	private static final Logger LOGGER = Logger.getLogger(Banking_Application_S2022_Group_1.class.getClass().getName());

// Method that starts logging of the banking application into a file called Banking_Application_S2022_Group_1.log

	public static void startLogging()

	{

		try

		{

			fileHandler = new FileHandler("./Banking_Application_S2022_Group_1.log");

			SimpleFormatter simple = new SimpleFormatter();

			fileHandler.setFormatter(simple);

			LOGGER.addHandler(fileHandler);

			LOGGER.setUseParentHandlers(false);

		}

		catch (IOException e)

		{

		}

	}

	public static void main(String[] args) throws Exception

	{

		startLogging();

		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");

		keyGenerator.init(256);

		SecretKey key = keyGenerator.generateKey();

		byte[] IV = new byte[12];

		SecureRandom random = new SecureRandom();

		random.nextBytes(IV);

		DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");

		Date dateobj = new Date();

		Checking_S2022_Group_1 checkingAccount = new Checking_S2022_Group_1();

		Savings_S2022_Group_1 savingsAccount = new Savings_S2022_Group_1();

		System.out.println("Welcome to Group 1 Bank");

		/*
		 * While loop is used to prevent application from crashing/terminating on
		 * errors. Verifies that user enters a valid name. Contains sensitive data
		 * relating to the user's identity.
		 */

		while (true)

		{

			System.out.println("Enter your first and last name:");

			Scanner name = new Scanner(System.in);

			String nameValue = name.nextLine();

// Normalization of entered name
			/*
			 * Normalizing input before validating it prevents potential attackers from
			 * bypassing filters and executing arbitrary code. CMU Rule: IDS01-J
			 */

			nameValue = Normalizer.normalize(nameValue, Normalizer.Form.NFKC);

// Validation of entered name

			Pattern regex = Pattern.compile("[$&+,:;=?@#|'<>.^*()%!-]");

			Matcher matcher = regex.matcher(nameValue);

			/*
			 * The following if-statement prompts the user for their password. User's
			 * password is considered sensitive data since it allows access to account.
			 */

			if (matcher.find())

			{

				System.out.println("Value entered is not a valid name");

				LOGGER.info("User has entered an invalid name");

			}

			else

			{

// Output encoding of entered password

				System.out.println("Enter your password:");

				Scanner password = new Scanner(System.in);

				String passwordValue = password.nextLine();

				byte[] encryptedName = encryptName(nameValue.getBytes(), key, IV);

				String decryptedName = decryptName(encryptedName, key, IV);

				System.out.println("You have logged in as " + Base64.getEncoder().encodeToString(encryptedName)
						+ " with password " + encodePassword(passwordValue) + " on " + df.format(dateobj));

				LOGGER.info("User has entered a valid name and password");

				break;

			}

		}

		System.out.println("Would you like to access your: \n 1. Checkings \n 2. Savings");

		Scanner inputAccountChoice = new Scanner(System.in);

		int accountChoice = 0;

		try

		{

			accountChoice = inputAccountChoice.nextInt();

		}

		catch (InputMismatchException e)

		{

		}

		switch (accountChoice)

		{

		case 1:

		{

			while (true)

			{

				System.out.println(
						"Would you like to: \n 1. Deposit \n 2. Withdraw \n 3. View Account Balance \n 4. Exit");

				Scanner inputCheckingAccountAction = new Scanner(System.in);

				int checkingAccountAction = 0;

				try

				{

					checkingAccountAction = inputCheckingAccountAction.nextInt();

				}

				catch (InputMismatchException e)

				{

				}

				if (checkingAccountAction != 1 && checkingAccountAction != 2 && checkingAccountAction != 3)

				{

					break;

				}

				switch (checkingAccountAction)

				{

				case 1:

				{

					Scanner checkingDeposit = new Scanner(System.in);

					/*
					 * While loop is used to prevent application from crashing/terminating on errors
					 * Verifies that user has enter a valid positive double amount
					 */
					while (true)

					{

						System.out.println("How much would you like to deposit into your checking account?");

						if (checkingDeposit.hasNextDouble())

						{

							double checkingDepositValue = checkingDeposit.nextDouble();

							try

							{

								checkingAccount.deposit(checkingDepositValue);

								LOGGER.info("User has deposited funds into their checking account");

							}

							catch (Negative_Exception Neg_Exc)

							{

								System.out.println("You cannot deposit a negative number into your checking account");

								LOGGER.info("User has attempted to deposit negative funds into their checking account");

							}

							break;

						}

						else

						{

							System.out.println("Value inserted is not a valid number");

							LOGGER.info(
									"User has selected a value that is not a valid number when depositing funds into their checking account");

							checkingDeposit.nextLine();

						}

						break;

					}

					break;

				}

				case 2:

				{

					Scanner checkingWithdraw = new Scanner(System.in);

					/*
					 * While loop is once again used to prevent application from
					 * crashing/terminating. Verifies that the user enters a valid positive number
					 * that is less than the total balance.
					 */
					while (true)

					{

						System.out.println("How much would you like to withdraw from your checking account?");

						if (checkingWithdraw.hasNextDouble())

						{

							double checkingWithdrawValue = checkingWithdraw.nextDouble();

							try

							{

								checkingAccount.withdraw(checkingWithdrawValue);

								LOGGER.info("User has withdrawn funds from their checking account");

							}

							catch (Negative_Exception Neg_Exc)

							{

								System.out.println("You cannot withdraw a negative number from your checking account");

								LOGGER.info(
										"User has attempted to withdraw negative funds from their checking account");

							}

							catch (Overdraft_Exception Overd_Exc)

							{

								System.out.println("Balance is not enough to withdraw chosen amount");

								LOGGER.info(
										"User has attempted to withdraw funds that is less than the balance of their checking account");

							}

							break;

						}

						else

						{

							System.out.println("Value inserted is not a valid number");

							LOGGER.info(
									"User has selected a value that is not a valid number when withdawing funds from their checking account");

							checkingWithdraw.nextLine();

						}

						break;

					}

					break;

				}

				case 3:

				{

					System.out.println(
							"The balance of your checking account is " + checkingAccount.getBalance() + " dollars.");

					break;

				}

				}

			}

			break;

		}

		case 2:

		{

			while (true)

			{

				System.out.println(
						"Would you like to: \n 1. Deposit \n 2. Withdraw \n 3. View Account Balance \n 4. Exit");

				Scanner inputSavingsAccountAction = new Scanner(System.in);

				int savingsAccountAction = 0;

				try

				{

					savingsAccountAction = inputSavingsAccountAction.nextInt();

				}

				catch (InputMismatchException e)

				{

				}

				if (savingsAccountAction != 1 && savingsAccountAction != 2 && savingsAccountAction != 3)

				{

					break;

				}

				switch (savingsAccountAction)

				{

				case 1:

				{

					Scanner savingsDeposit = new Scanner(System.in);

					/*
					 * While loop is used to prevent application from crashing/terminating on errors
					 * Verifies that user has enter a valid positive double amount
					 */

					while (true)

					{

						System.out.println("How much would you like to deposit into your savings account?");

						if (savingsDeposit.hasNextDouble())

						{

							double savingsDepositValue = savingsDeposit.nextDouble();

							try

							{

								savingsAccount.deposit(savingsDepositValue);

								LOGGER.info("User has deposited funds into their savings account");

							}

							catch (Negative_Exception Neg_Exc)

							{

								System.out.println("You cannot deposit a negative number into your savings account");

								LOGGER.info("User has attempted to deposit negative funds into their savings account");

							}

							break;

						}

						else

						{

							System.out.println("Value inserted is not a valid number");

							LOGGER.info(
									"User has selected a value that is not a valid number when depositing funds into their savings account");

							savingsDeposit.nextLine();

						}

						break;

					}

					break;

				}

				case 2:

				{

					Scanner savingsWithdraw = new Scanner(System.in);

					/*
					 * While loop is once again used to prevent application from
					 * crashing/terminating. Verifies that the user enters a valid positive number
					 * that is less than the total balance.
					 */
					while (true)

					{

						System.out.println("How much would you like to withdraw from your savings account?");

						if (savingsWithdraw.hasNextDouble())

						{

							double savingsWithdrawValue = savingsWithdraw.nextDouble();

							try

							{

								savingsAccount.withdraw(savingsWithdrawValue);

								LOGGER.info("User has withdrawn funds from their savings account");

							}

							catch (Negative_Exception Neg_Exc)

							{

								System.out.println("You cannot withdraw a negative number from your savings account");

								LOGGER.info("User has attempted to withdraw negative funds from their savings account");

							}

							catch (Overdraft_Exception Overd_Exc)

							{

								System.out.println("Balance is not enough to withdraw chosen amount");

								LOGGER.info(
										"User has attempted to withdraw funds that is less than the balance of their savings account");

							}

							break;

						}

						else

						{

							System.out.println("Value inserted is not a valid number");

							LOGGER.info(
									"User has selected a value that is not a valid number when withdawing funds from their savings account");

							savingsWithdraw.nextLine();

						}

						break;

					}

					break;

				}

				case 3:

				{

					System.out.println(
							"The balance of your savings account is " + savingsAccount.getBalance() + " dollars");

					break;

				}

				}

			}

			break;

		}

		}

	}

// Method that returns the Base64 encoded SHA-256 hash of the entered password
	/*
	 * Properly encodes the user's password so that it only contains valid
	 * characters upon output CMU Rule: IDS51-J
	 */

	public static String encodePassword(String q) throws Exception

	{

		MessageDigest md = MessageDigest.getInstance("SHA-256");

		byte[] digest = md.digest(q.getBytes());

		String encodedPassword = Base64.getEncoder().encodeToString(digest);

		return encodedPassword;

	}

// Method that returns the AES-256-GCM encrypted value of the entered name
	/*
	 * Properly encrypts the user's name with a secure and strong cryptographic
	 * algorithm CMU Rule: MSC61-J
	 */

	public static byte[] encryptName(byte[] plaintext, SecretKey key, byte[] IV) throws Exception

	{

		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

		SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, IV);

		cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

		byte[] encryptedName = cipher.doFinal(plaintext);

		return encryptedName;

	}

// Method that returns the AES-256-GCM decrypted value of the entered name
	/*
	 * Properly decrypts the user's name with a secure and strong cryptographic
	 * algorithm CMU Rule: MSC61-J
	 */

	public static String decryptName(byte[] cipherText, SecretKey key, byte[] IV) throws Exception

	{

		Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

		SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");

		GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(16 * 8, IV);

		cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

		byte[] decryptedName = cipher.doFinal(cipherText);

		return new String(decryptedName);

	}

}