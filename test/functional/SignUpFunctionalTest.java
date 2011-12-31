package functional;


import java.util.HashMap;
import java.util.Map;

import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
		
		Map<String, String> parameters = new HashMap();
		parameters.put("user.fullName", "Rahul Jain");
		parameters.put("user.email", "joeblog@gmail.com");
		parameters.put("user.password", "secret");
		parameters.put("user.isAdmin", "true");
		Response response = POST("/signup/signupuser", parameters);
        assertIsOk(response);
		
		User rahul = User.find("byEmail", "joeblog@gmail.com").first();
		assertNotNull(rahul);
		assertEquals("Rahul Jain", rahul.fullName);
		
		assertNotNull(rahul.account);
		assertEquals("Account of " + rahul.fullName, rahul.account.name);
		
		assertEquals(1, rahul.account.expensePools.size());
		assertEquals("Default Pool", rahul.account.expensePools.get(0).name);
		
	}
	
	@Test
	public void testThatAnExistingUserCanNotRegister() {
		
		Map<String, String> parameters = new HashMap();
		parameters.put("user.fullName", "Rahul Jain");
		parameters.put("user.email", "rahulj51@gmail.com");
		parameters.put("user.password", "secret2");
		parameters.put("user.isAdmin", "true");
		Response response = POST("/signup/signupuser", parameters); 
        assertIsOk(response);
        String flash = response.cookies.get("PLAY_FLASH").value;
        String message = Utils.urlDecodePath(replacePlusWithSpace(flash));

        assertEquals("error:User rahulj51@gmail.com already exists", message.trim());
		
	}	
	
	private String replacePlusWithSpace(String s) {
		
		return s.replaceAll("\\+", " ");
		
	}
	

}
