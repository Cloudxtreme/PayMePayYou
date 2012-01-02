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
		user = getDummyUser(account);
		account.addUser(user);
		account.save();
    }

	User getDummyUser(Account account) {
		return new User("rahulj51@gmail.com", "secret", "Rahul Jain", account, true);
	}	
	
	@Test
	public void userCanBeCreated() {
		//created already in setup. check if exists
		User rahul = User.find("byEmail", user.email).first();
		
		assertNotNull(rahul);
		assertEquals(user.email, rahul.email);
	}
	
	@Test
	public void validExistingUserCanLogin() {
		
		User rahul = User.login(user.email, user.password);
		
		assertNotNull(rahul);
		assertEquals(user.email, rahul.email);		
		
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
		
		
		assertNull(User.find("byEmail", user.email).first());
		assertEquals(0, User.count());
	}
	
	@Test
	public void ExistingUserCanBeUpdated() {

		user.isAdmin = false;
		user.password = "notsoSecretAnymore";
		
		account.save();
		
		User rahul = User.find("byEmail", user.email).first();
		
		assertFalse(rahul.isAdmin);
		assertEquals(user.password, rahul.password);
	}
	
	
	@Test
	public void fullNameIsMandatory() {
		user.fullName = null;

        assertUserFieldValidation(".fullName", "Full Name is required");
	}
	
	@Test
	public void fullNameMiniumLength() {
		user.fullName = "rj";

        assertUserFieldValidation(".fullName", "Minimum size is 3");
	}	
	
	@Test
	public void fullNameMaximumLength() {
		user.fullName  = "somethinglargerthansixtycharacters somethinglargerthansixtycharacters somethinglargerthansixtycharacters";
	
        assertUserFieldValidation(".fullName", "Maximum size is 60");
	}		
	
	@Test
	public void emailIsMandatory() {
		user.email = null;
		
        assertUserFieldValidation(".email", "Email address is required");
	}		
	
	@Test
	public void invalidEmailIdISNotAllowed() {
		user.email = "rahulj51gmail.com";
		
        assertUserFieldValidation(".email", "Invalid email address");	
	}		
	
	@Test
	public void passwordIsMandatory() {
		user.password = null;
		
		assertUserFieldValidation(".password", "Password is required");	
	}
	
	@Test
	public void passwordMiniumLength() {
		user.password = "sec";
		
        assertUserFieldValidation(".password", "Minimum size is 5");
	}	
	
	@Test
	public void passwordMaximumLength() {
		user.password = "somethinglargerthantwentycharacters";

        assertUserFieldValidation(".password", "Maximum size is 20");
	}			

	
	@Test
	public void passwordShouldOnlyBeAlphaNumeric() {
		user.password = "specialCharacter$";
		
		assertUserFieldValidation(".password", "Password can only contain alphabets or numbers");
	
	}		
	
	
	private  void assertUserFieldValidation(String field, String message) {
		
		ValidationResult res = Validation.current().valid(user);
		assertFalse(res.ok);
        assertNotNull(Validation.errors(field));
        play.data.validation.Error error = Validation.errors(field).get(0);
        assertEquals(message, error.message());		
	}
}
