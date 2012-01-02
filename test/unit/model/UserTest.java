package unit.model;

import models.Account;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.data.validation.Validation;
import play.data.validation.Validation.ValidationResult;
import play.test.Fixtures;
import play.test.UnitTest;


public class UserTest extends UnitTest {
	
	Account account;
	User user;

    @Before
    public void setup() {
        Fixtures.deleteDatabase();
		Validation.clear();        
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
	public void validExistingUserCanLogin() {
		
		User rahul = User.login("rahulj51@gmail.com", "secret");
		
		assertNotNull(rahul);
		assertEquals("rahulj51@gmail.com", rahul.email);		
		
	}
	
	@Test
	public void UserWithNoAccountCanNotLogin() {
		
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
	
	
	@Test
	public void fullNameIsMandatory() {
		user = new User("rahulj51@gmail.com", "secret", null, account, true);
		ValidationResult res = Validation.current().valid(user);
		assertFalse(res.ok);
        assertNotNull(Validation.errors(".fullName"));
        play.data.validation.Error error = Validation.errors(".fullName").get(0);
        assertEquals("Full Name is required", error.message());	
	}
	
	@Test
	public void fullNameMiniumLength() {
		user = new User("rahulj51@gmail.com", "secret", "rj", account, true);
		ValidationResult res = Validation.current().valid(user);
		assertFalse(res.ok);
        assertNotNull(Validation.errors(".fullName"));
        play.data.validation.Error error = Validation.errors(".fullName").get(0);
        assertEquals("Minimum size is 3", error.message());	
	}	
	
	@Test
	public void fullNameMaximumLength() {
		String name = "somethinglargerthansixtycharacters somethinglargerthansixtycharacters somethinglargerthansixtycharacters";
		user = new User("rahulj51@gmail.com", "secret", name, account, true);
		ValidationResult res = Validation.current().valid(user);
		assertFalse(res.ok);
        assertNotNull(Validation.errors(".fullName"));
        play.data.validation.Error error = Validation.errors(".fullName").get(0);
        assertEquals("Maximum size is 60", error.message());	
	}		
	
	@Test
	public void emailIsMandatory() {
		user = new User(null, "secret", "Rahul Jain", account, true);
		ValidationResult res = Validation.current().valid(user);
		assertFalse(res.ok);
        assertNotNull(Validation.errors(".email"));
        play.data.validation.Error error = Validation.errors(".email").get(0);
        assertEquals("Email address is required", error.message());	
	}		
	
	@Test
	public void invalidEmailIdISNotAllowed() {
		user = new User("rahulj51gmail.com", "secret", "rj", account, true);
		ValidationResult res = Validation.current().valid(user);
		assertFalse(res.ok);
        assertNotNull(Validation.errors(".email"));
        play.data.validation.Error error = Validation.errors(".email").get(0);
        assertEquals("Invalid email address", error.message());	
	}		
	
	@Test
	public void passwordIsMandatory() {
		user = new User("rahulj51@gmail.com", "", "Rahul Jain", account, true);
		ValidationResult res = Validation.current().valid(user);
		assertFalse(res.ok);
        assertNotNull(Validation.errors(".password"));
        play.data.validation.Error error = Validation.errors(".password").get(0);
        assertEquals("Password is required", error.message());	
	}
	
	@Test
	public void passwordMiniumLength() {
		user = new User("rahulj51@gmail.com", "sec", "rahul jain", account, true);
		ValidationResult res = Validation.current().valid(user);
		assertFalse(res.ok);
        assertNotNull(Validation.errors(".password"));
        play.data.validation.Error error = Validation.errors(".password").get(0);
        assertEquals("Minimum size is 5", error.message());	
	}	
	
	@Test
	public void passwordMaximumLength() {
		String password = "somethinglargerthantwentycharacters";
		user = new User("rahulj51@gmail.com", password, "rahul jain", account, true);
		ValidationResult res = Validation.current().valid(user);
		assertFalse(res.ok);
        assertNotNull(Validation.errors(".password"));
        play.data.validation.Error error = Validation.errors(".password").get(0);
        assertEquals("Maximum size is 20", error.message());	
	}			

	
	@Test
	public void passwordShouldOnlyBeAlphaNumeric() {
		String password = "specialCharacter$";
		user = new User("rahulj51@gmail.com", password, "rahul jain", account, true);
		ValidationResult res = Validation.current().valid(user);
		assertFalse(res.ok);
        assertNotNull(Validation.errors(".password"));
        play.data.validation.Error error = Validation.errors(".password").get(0);
        assertEquals("Password can only contain alphabets or numbers", error.message());	
	}		
}
