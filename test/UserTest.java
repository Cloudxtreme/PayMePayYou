import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class UserTest extends UnitTest {
	

    @Before
    public void setup() {
        Fixtures.deleteDatabase();
    }	
	
	@Test
	public void userCanBeCreated() {
		
		new User("rahulj51@gmail.com", "secret", "Rahul Jain", true).save();
		
		User rahul = User.find("byEmail", "rahulj51@gmail.com").first();
		
		assertNotNull(rahul);
		assertEquals("rahulj51@gmail.com", rahul.email);
	}
	
	@Test
	public void validUserCanLogin() {
		
		new User("rahulj51@gmail.com", "secret", "Rahul Jain", true).save();
		
		User user = User.login("rahulj51@gmail.com", "secret");
		
		assertNotNull(user);
		assertEquals("rahulj51@gmail.com", user.email);		
		
	}
	
	@Test
	public void InvalidUserCanNotLogin() {
		
		new User("rahulj51@gmail.com", "secret", "Rahul Jain", true).save();
		
		assertNull(User.login("rahulj51@gmail.com", "secretive"));
		assertNull(User.login("rahulj566@gmail.com", "secret"));
	}	
	
	@Test
	public void ExistingUserCanBeDeleted() {

		User rahul = new User("rahulj51@gmail.com", "secret", "Rahul Jain", true);
		rahul.save();
		
		
		rahul.delete();
		
		assertNull(User.find("byEmail", "rahulj51@gmail.com").first());
		assertEquals(0, User.count());
	}
	
	@Test
	public void ExistingUserCanBeUpdated() {

		User rahul = new User("rahulj51@gmail.com", "secret", "Rahul Jain", true);
		rahul.save();	
		
		rahul.isAdmin = false;
		rahul.password = "notsoSecretAnymore";
		
		rahul.save();
		
		User user = User.find("byEmail", "rahulj51@gmail.com").first();
		
		assertFalse(user.isAdmin);
		assertEquals("notsoSecretAnymore", user.password);
	}

}
