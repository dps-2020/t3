// Binu Jacob's code modification: pull, code change, commit and push  completed
// We updated ownerOne/ownerTwo accounts - David/Joe/Ed 2017-10-03
// Updated Expected vs Actual - David 2017-10-07


package junit;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import banksystem.AccountOwner;
import database.Database;

public class AccountOwnerMultipleTest {
	Database dataBase = Database.getInstance();

	@Before
	public void setUp() throws Exception {
		Database.setFileName("test.dat");
		dataBase.eraseFile();
		dataBase.load();
	}

	
	@Test
	public void createTwoAccounts() {
		AccountOwner ownerOne = new AccountOwner("ownerOne", "J$");
		ownerOne.put();
		Assert.assertEquals("O1001",ownerOne.getId());

		AccountOwner ownerOneWrittenToDatabase = AccountOwner.get(ownerOne.getId());

		Assert.assertEquals(ownerOne.getId(), ownerOneWrittenToDatabase.getId());
		Assert.assertEquals("ownerOne", ownerOneWrittenToDatabase.getName());
		Assert.assertEquals("J$", ownerOneWrittenToDatabase.getPassword());

		AccountOwner ownerTwo = new AccountOwner("ownerTwo", "J$");
		ownerTwo.put();
		Assert.assertEquals("O1002",ownerTwo.getId());

		AccountOwner ownerTwoWrittenToDatabase = AccountOwner.get(ownerTwo.getId());

		Assert.assertEquals(ownerTwo.getId(), ownerTwoWrittenToDatabase.getId());
		Assert.assertEquals("ownerTwo", ownerTwoWrittenToDatabase.getName());
		Assert.assertEquals("J$", ownerTwoWrittenToDatabase.getPassword());

	}

}
