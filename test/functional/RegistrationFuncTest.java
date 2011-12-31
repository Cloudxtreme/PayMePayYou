package functional;


import java.util.HashMap;
import java.util.Map;

import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;


public class RegistrationFuncTest extends FunctionalTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
		Fixtures.loadModels("test-data.yml");
	}
	
	@After
	public void tearDown() {
		Fixtures.deleteDatabase();
	}
	
	@Test
	public void testThatIndexPageWorks() {
        Response response = GET("/signup");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);	

	}
	
	public void testThatValidNewUserIsSuccessFullyRegistered() {
		
		Map<String, String> parameters = new HashMap();
		parameters.put("fullName", "Rahul Jain");
		parameters.put("email", "joeblog@gmail.com");
		parameters.put("password", "secret");
		Response response = POST("/SignUp/signupUser");
        assertIsOk(response);
		
		User user = User.find("byEmail", "joeblog@gmail.com").first();
		assertNotNull(user);
		assertEquals("Rahul Jain", user.fullName);
		
	}

}
