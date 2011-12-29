
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	public void testRegisteringValidNewUser() {
		
	}

}
