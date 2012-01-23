package functional;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.Login;

import play.mvc.Http;
import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;
import play.utils.Utils;

public class LoginFunctionalTest extends BaseFunctionalTest {

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
        Response response = GET("/login");
        assertIsOk(response);
        assertContentType("text/html", response);
        assertCharset(play.Play.defaultWebEncoding, response);	
	}
	
	@Test
	public void testThatValidUserIsSuccessFullyLoggedIn() {
		
		Map<String, String> parameters = getDummyInputParameters();
		
		Response response = POST("/login/loginuser", parameters);
        assertStatus(Http.StatusCode.FOUND, response);
        
        //success flashed
        assertCookieContains("PLAY_FLASH", "Welcome,", response);
        
        assertCookieContainsPositiveNumber(Login.DEFAULT_POOL_ID_COOKIE, response);
	}
	
	@Test
	public void testThatUserWithNoExistingAccountCanNotLogin() {
		
		Map<String, String> parameters = getDummyInputParameters();
		parameters.put("email", "joeblogs@gmail.com"); //non existent
		
		Response response = POST("/login/loginuser", parameters);
        assertStatus(Http.StatusCode.FOUND, response);
        
        //error flashed
        assertCookieContains("PLAY_FLASH", "Invalid user id or password", response);        
	}
	
	
	@Test
	public void testThatEmailIdIsRequiredForLogin() {
		
		Map<String, String> parameters = getDummyInputParameters();
		parameters.remove("email");
		
		Response response = POST("/login/loginuser", parameters);
        assertStatus(Http.StatusCode.FOUND, response);
        
        //error flashed
        assertCookieContains("PLAY_ERRORS", "email", response);        
	}	
	
	@Test
	public void testThatPasswordIsRequiredForLogin() {
		
		Map<String, String> parameters = getDummyInputParameters();
		parameters.remove("password");
		
		Response response = POST("/login/loginuser", parameters);
        assertStatus(Http.StatusCode.FOUND, response);
        
        //error flashed
        assertCookieContains("PLAY_ERRORS", "password", response);        
	}	

	Map<String, String> getDummyInputParameters() {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("email", "rahulj51@gmail.com");
		parameters.put("password", "secret");
		
		return parameters;
	}		

}
