package unit.model;

import models.Account;
import models.User;

import org.junit.After;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class AccountTest extends UnitTest {
	
	
    @After
    public void tearDown() {
        Fixtures.deleteDatabase();
    }
    
    
	@Test
	public void AccountCanBeCreated() {

		
		Account account = new Account("myAccount");
		User user = new User("rahulj51@gmail.com", "secret", "Rahul Jain", account, true);
		account.addUser(user);
		account.save();
		
		Account savedAccount = Account.find("byName", "myAccount").first();
		
		assertNotNull(savedAccount);
		assertEquals("myAccount", savedAccount.name);
		assertEquals(1, savedAccount.users.size());
		assertEquals("Rahul Jain", savedAccount.users.get(0).fullName);
	}    
	
	@Test
	public void ExistingAccountCanBeDeleted() {

		Account account = new Account("myAccount");
		User user = new User("rahulj51@gmail.com", "secret", "Rahul Jain", account, true);
		account.addUser(user);
		account.save();
		
		long userId = account.users.get(0).id;
		account.delete();
		
		assertNull(Account.find("byName", "myAccount").first());
		assertNull(User.findById(userId));
		assertEquals(0, Account.count());
		assertEquals(0, User.count());
	}
	
	@Test
	public void ExistingAccountCanBeUpdated() {

		Account account = new Account("myAccount");
		User user = new User("rahulj51@gmail.com", "secret", "Rahul Jain", account, true);
		account.addUser(user);
		account.save();
		
		long id = account.id;

		account.name = "myNewAccount";
		user = new User("bob@gmail.com", "password", "Bob Dale", account, false);
		account.addUser(user);
		account.save();
		
		Account savedAccount = Account.findById(id);
		assertEquals("myNewAccount", savedAccount.name);
		assertEquals(2, savedAccount.users.size());
	}	

}
