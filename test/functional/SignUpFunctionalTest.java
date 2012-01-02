package functional;


import java.util.HashMap;
import java.util.Map;

import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.data.validation.Validation;
import play.mvc.Http;
import play.mvc.Http.Response;
import play.mvc.Scope.Flash;
import play.test.Fixtures;
import play.test.FunctionalTest;
import play.utils.Utils;


public class SignUpFunctionalTest extends FunctionalTest {

	@Before
	public void setup() {
		Fixtures.deleteDatabase();
		Fixtures.loadModels("functional/test-data.yml");
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
	
	
	@Test
	public void testThatValidNewUserIsSuccessFullyRegistered() {
		
		Map<String, String> parameters = getDummyInputParameters();
		
		Response response = POST("/signup/signupuser", parameters);
        assertIsOk(response);
		
		User rahul = User.find("byEmail", parameters.get("user.email")).first();
		assertNotNull(rahul);
		assertEquals(parameters.get("user.fullName"), rahul.fullName);
		
		assertNotNull(rahul.account);
		assertEquals("Account of " + rahul.fullName, rahul.account.name);
		
		assertEquals(1, rahul.account.expensePools.size());
		assertEquals("Default Pool", rahul.account.expensePools.get(0).name);
		
	}


	
	@Test
	public void testThatAnExistingUserCanNotRegister() {
		
		Map<String, String> parameters = getDummyInputParameters();
		parameters.put("user.email", "rahulj51@gmail.com"); //existing user

		Response response = POST("/signup/signupuser", parameters); 
        assertStatus(Http.StatusCode.FOUND, response);
        String flash = response.cookies.get("PLAY_FLASH").value;
        String message = Utils.urlDecodePath(replacePlusWithSpace(flash));

        assertTrue(message.trim().indexOf("error:User rahulj51@gmail.com already exists") >= 0);
		
	}	
	
	
	@Test
	public void testValidationFailureRedirectsToIndexWithValidationErrors() {
		
		Map<String, String> parameters = getDummyInputParameters();
		parameters.remove("user.fullName");

		Response response = POST("/signup/signupuser", parameters); 
        
        String error = response.cookies.get("PLAY_ERRORS").value;
        String message = Utils.urlDecodePath(replacePlusWithSpace(error));
        assertTrue(message.length() >= 0);
        assertStatus(Http.StatusCode.FOUND, response);
	}
	
	
	private String replacePlusWithSpace(String s) {
		
		return s.replaceAll("\\+", " ");
		
	}
	
	
	Map<String, String> getDummyInputParameters() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("user.fullName", "Rahul Jain");
		parameters.put("user.email", "joeblog@gmail.com");
		parameters.put("user.password", "secret");
		parameters.put("user.isAdmin", "true");
		
		return parameters;
	}	

}
