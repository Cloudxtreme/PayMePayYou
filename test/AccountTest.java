import models.Account;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class AccountTest extends UnitTest {
	
	User user;
	
    @Before
    public void setup() {
        Fixtures.deleteDatabase();
        
		//create user to associate with account
		user = new User("rahulj51@gmail.com", "secret", "Rahul Jain", true);
		//user.save();        
    }
    
    
	@Test
	public void AccountCanBeCreated() {
		
		new Account("myAccount", user).save();
		Account account = Account.find("byName", "myAccount").first();
		
		assertNotNull(account);
		assertEquals("myAccount", account.name);
		assertEquals("Rahul Jain", account.owner.fullName);
	}    
	
	@Test
	public void ExistingAccountCanBeDeleted() {

		Account account = new Account("myAccount", user).save();
		long userId = account.owner.id;
		account.delete();
		
		assertNull(Account.find("byName", "myAccount").first());
		assertNull(User.findById(userId));
		assertEquals(0, Account.count());
		assertEquals(0, User.count());
	}
	
	@Test
	public void ExistingAccountCanBeUpdated() {

		Account account = new Account("myAccount", user).save();
		long id = account.id;

		account.name = "myNewAccount";
		account.save();
		
		Account savedAccount = Account.findById(id);
		assertEquals("myNewAccount", savedAccount.name);
	}	

}
