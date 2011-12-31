package unit.model;


import models.User;
import models.UserBuilder;

import org.junit.Test;

import play.test.UnitTest;

public class UserBuilderTest extends UnitTest {

	@Test
	public void testBuildWithUserInfo() {

		UserBuilder builder = new UserBuilder().setEmail("sunny@etsy.com").setFullName("sunny leone").setIsAdmin(true).setPassword("my secret");
		User user = builder.build();
		
		assertNotNull(user);
		assertEquals("sunny@etsy.com", user.email);
		assertEquals("sunny leone", user.fullName);
		assertTrue(user.isAdmin);
		assertEquals("my secret", user.password);
	
	}

}
