package junit;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import banksystem.Account;
import banksystem.AccountOwner;
import banksystem.PasswordManager;
import banksystem.Utilities;
import banksystem.Withdrawal;
import banksystem.WithdrawalData;
import database.Database;

public class WithdrawalTest {

	Database dataBase = Database.getInstance();

	@Before
	public void setUp() throws Exception {

		Database.setFileName("test.dat");
		dataBase.eraseFile();
		dataBase.load();

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test 
	public void validWithdrawal() {
		AccountOwner newAccountOwner = new AccountOwner("Team3", "P$1111");
		newAccountOwner.put();
		Assert.assertEquals("O1001", newAccountOwner.getId());
		
		Account newAccount = new Account("O1001", "Checking", "50.00");
		newAccount.put();
		Assert.assertEquals("A1001", newAccount.getId());

		Withdrawal withdrawal = new Withdrawal("O1001", "A1001", "49.00");
		Assert.assertEquals("50.00", newAccount.getBalance());
		
		String result = withdrawal.updateBalance("P$1111");
		Assert.assertEquals("valid", result);
		Assert.assertEquals("1.00", newAccount.getBalance());
		
	}
	
	
	@Test 
	public void invalidAccountOwner() {
		AccountOwner newAccountOwner = new AccountOwner("Team3", "P$1111");
		newAccountOwner.put();
		Assert.assertEquals("O1001", newAccountOwner.getId());
		
		Account newAccount = new Account("O1001", "Checking", "50.00");
		newAccount.put();
		Assert.assertEquals("A1001", newAccount.getId());

		Withdrawal withdrawal = new Withdrawal("O1002", "A1001", "49.00");
		Assert.assertEquals("50.00", newAccount.getBalance());
		
		String result = withdrawal.updateBalance("P$1111");
		Assert.assertEquals("Invalid Account Owner ID", result);
		
	}
	
	
	@Test 
	public void invalidPassword() {
		AccountOwner newAccountOwner = new AccountOwner("Team3", "P$1111");
		newAccountOwner.put();
		Assert.assertEquals("O1001", newAccountOwner.getId());
		
		Account newAccount = new Account("O1001", "Checking", "50.00");
		newAccount.put();
		Assert.assertEquals("A1001", newAccount.getId());

		Withdrawal withdrawal = new Withdrawal("O1001", "A1001", "49.00");
		Assert.assertEquals("50.00", newAccount.getBalance());
		
		String result = withdrawal.updateBalance("P$1121");
		Assert.assertEquals("Invalid Password", result);
		
	}
	
	@Test
	public void withdrawalNotNegative() {

		
		AccountOwner newAccountOwner = new AccountOwner("Team3", "P$1111");
		newAccountOwner.put();
		Assert.assertEquals("O1001", newAccountOwner.getId());
		
		Account newAccount = new Account("O1001", "Checking", "50.00");
		newAccount.put();
		Assert.assertEquals("A1001", newAccount.getId());

		Withdrawal withdrawal = new Withdrawal("01001", "A1001", "-100");
		String result = withdrawal.validateWithdrawalAmount(withdrawal.getWithdrawalAmount());
		Assert.assertEquals("Withdrawal amount cannot be negative", result);
	}

	@Test
	public void amountNotGreaterThanBalance() {

		
		AccountOwner newAccountOwner = new AccountOwner("Team3", "P$1111");
		newAccountOwner.put();
		Account newAccount = new Account("O1001", "Checking", "50.00");
		newAccount.put();

		Withdrawal newWithdrawal = new Withdrawal("O1001", "A1001", "350.00");
		Assert.assertEquals("Withdrawal amount cannot be greater than balance",
				newWithdrawal.validateWithdrawalAmount(newWithdrawal.getWithdrawalAmount()));
	}

	@Test
	public void notEmpty() {

		
		AccountOwner newAccountOwner = new AccountOwner("Team3", "P$1111");
		newAccountOwner.put();
		Account newAccount = new Account("O1001", "Checking", "50.00");
		newAccount.put();
		
		Withdrawal newWithdrawal = new Withdrawal("O1001", "A1001", "");
		Assert.assertEquals("Withdrawal amount cannot be empty",
				newWithdrawal.validateWithdrawalAmount(newWithdrawal.getWithdrawalAmount()));
	}

	@Test
	public void withdrawalMustBeNumeric() {

		
		AccountOwner newAccountOwner = new AccountOwner("Team3", "P$1111");
		newAccountOwner.put();
		Account newAccount = new Account("O1001", "Checking", "50.00");
		newAccount.put();
		
		Withdrawal withdrawal1 = new Withdrawal("O1001", "A1001", "23w");
		Assert.assertEquals("Withdrawal amount must be numeric",
				withdrawal1.validateWithdrawalAmount(withdrawal1.getWithdrawalAmount()));
		withdrawal1 = new Withdrawal("O1001", "A1001", "23.00");
		Assert.assertEquals("valid", withdrawal1.validateWithdrawalAmount(withdrawal1.getWithdrawalAmount()));
	}

	@Test
	public void withdrawalAmountMustBeDollarsAndCents() {

		
		AccountOwner newAccountOwner = new AccountOwner("Team3", "P$1111");
		newAccountOwner.put();
		Account newAccount = new Account("O1001", "Checking", "50.00");
		newAccount.put();
		
		Withdrawal withdrawal1 = new Withdrawal("O1001", "A1001", "1.234");
		Assert.assertEquals("Amount must be dollars and cents",
				withdrawal1.validateWithdrawalAmount(withdrawal1.getWithdrawalAmount()));
		withdrawal1 = new Withdrawal("O1001", "A1001", "1.23");
		Assert.assertEquals("valid", withdrawal1.validateWithdrawalAmount(withdrawal1.getWithdrawalAmount()));
	}

	@Test
	public void withdrawalAmountCannotBeBlank() {

		
		AccountOwner newAccountOwner = new AccountOwner("Team3", "P$1111");
		newAccountOwner.put();
		Account newAccount = new Account("O1001", "Checking", "50.00");
		newAccount.put();
		
		Withdrawal withdrawal1 = new Withdrawal("O1001", "A1001", " ");
		Assert.assertEquals("Withdrawal amount cannot be blank", withdrawal1.validateWithdrawalAmount(" "));
		withdrawal1 = new Withdrawal("O1001", "A1001", "      ");
		Assert.assertEquals("Withdrawal amount cannot be blank", withdrawal1.validateWithdrawalAmount(" "));
		withdrawal1 = new Withdrawal("O1001", "A1001", "50.00");
		Assert.assertEquals("valid", withdrawal1.validateWithdrawalAmount(withdrawal1.getWithdrawalAmount()));
	}

	@Test
	public void withdrawalAmountCannotBeZero() {

		
		AccountOwner newAccountOwner = new AccountOwner("Team3", "P$1111");
		newAccountOwner.put();
		Account newAccount = new Account("O1001", "Checking", "50.00");
		newAccount.put();
		Withdrawal withdrawal1 = new Withdrawal("O1001", "A1001", "0.00");
		Assert.assertEquals("Withdrawal amount cannot be zero",
				withdrawal1.validateWithdrawalAmount(withdrawal1.getWithdrawalAmount()));
		withdrawal1 = new Withdrawal("O1001", "A1001", "0.0");
		Assert.assertEquals("Withdrawal amount cannot be zero",
				withdrawal1.validateWithdrawalAmount(withdrawal1.getWithdrawalAmount()));
		withdrawal1 = new Withdrawal("O1001", "A1001", "0");
		Assert.assertEquals("Withdrawal amount cannot be zero",
				withdrawal1.validateWithdrawalAmount(withdrawal1.getWithdrawalAmount()));
		withdrawal1 = new Withdrawal("O1001", "A1001", ".00");
		Assert.assertEquals("Withdrawal amount cannot be zero",
				withdrawal1.validateWithdrawalAmount(withdrawal1.getWithdrawalAmount()));
		withdrawal1 = new Withdrawal("O1001", "A1001", "1.00");
		Assert.assertEquals("valid", withdrawal1.validateWithdrawalAmount(withdrawal1.getWithdrawalAmount()));
	}

}