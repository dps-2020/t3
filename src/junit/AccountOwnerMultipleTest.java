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
		Assert.assertEquals(ownerOne.getId(), "O1001");

		AccountOwner ownerOneWrittenToDatabase = AccountOwner.get(ownerOne.getId());

		Assert.assertEquals(ownerOne.getId(), ownerOneWrittenToDatabase.getId());
		Assert.assertEquals("ownerOne", ownerOneWrittenToDatabase.getName());
		Assert.assertEquals("J$", ownerOneWrittenToDatabase.getPassword());

		AccountOwner ownerTwo = new AccountOwner("ownerTwo", "J$");
		ownerTwo.put();

		String ownerIdTwo = ownerTwo.getId();
		Assert.assertEquals(ownerIdTwo, "O1002");

		AccountOwner ownerTwoWrittenToDatabase = AccountOwner.get(ownerIdTwo);

		Assert.assertEquals(ownerIdTwo, ownerTwoWrittenToDatabase.getId());
		Assert.assertEquals("ownerTwo", ownerTwoWrittenToDatabase.getName());
		Assert.assertEquals("J$", ownerTwoWrittenToDatabase.getPassword());

	}

}
