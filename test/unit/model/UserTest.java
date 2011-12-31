package unit.model;

import models.Account;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class UserTest extends UnitTest {
	
	Account account;
	User user;

    @Before
    public void setup() {
        Fixtures.deleteDatabase();
        
		account = new Account("myAccount");
		user = new User("rahulj51@gmail.com", "secret", "Rahul Jain", account, true);
		account.addUser(user);
		account.save();
        
    }	
	
	@Test
	public void userCanBeCreated() {
		User rahul = User.find("byEmail", "rahulj51@gmail.com").first();
		
		assertNotNull(rahul);
		assertEquals("rahulj51@gmail.com", rahul.email);
	}
	
	@Test
	public void validUserCanLogin() {
		
		User rahul = User.login("rahulj51@gmail.com", "secret");
		
		assertNotNull(rahul);
		assertEquals("rahulj51@gmail.com", rahul.email);		
		
	}
	
	@Test
	public void InvalidUserCanNotLogin() {
		
		assertNull(User.login("rahulj51@gmail.com", "secretive"));
		assertNull(User.login("rahulj566@gmail.com", "secret"));
	}	
	
	@Test
	public void ExistingUserCanBeDeleted() {

		user = account.users.get(0);
		account.removeUser(user);
		account.save();
		
		
		assertNull(User.find("byEmail", "rahulj51@gmail.com").first());
		assertEquals(0, User.count());
	}
	
	@Test
	public void ExistingUserCanBeUpdated() {

		user.isAdmin = false;
		user.password = "notsoSecretAnymore";
		
		account.save();
		
		User rahul = User.find("byEmail", "rahulj51@gmail.com").first();
		
		assertFalse(rahul.isAdmin);
		assertEquals("notsoSecretAnymore", rahul.password);
	}

}
