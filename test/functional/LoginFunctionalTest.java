package functional;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

public class LoginFunctionalTest extends FunctionalTest {

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

}
