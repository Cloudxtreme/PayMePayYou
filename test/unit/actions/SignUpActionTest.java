package unit.actions;


import models.User;

import org.junit.Test;

import play.test.UnitTest;
import actions.SignUpAction;


public class SignUpActionTest extends UnitTest {

	
	public void testThatValidUserIsSuccessfullyLoggedIn() {
		
		SignUpAction signUpAction = new SignUpAction();
		
		User user = new User("sunny@etsy.com", "hersecret", "sunny leone", true);
		
		try{
			signUpAction.signupUser(user);
			
			User sunny = User.find("byEmail", "sunny@etsy.com").first();
			assertNotNull(sunny);
			assertEquals("sunny leone", sunny.fullName);			
			
		}
		catch(Exception e) {
			fail("Exception is thrown" + e.getMessage());
		}
		
	}
	
	
}
