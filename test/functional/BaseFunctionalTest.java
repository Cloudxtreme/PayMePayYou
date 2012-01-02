package functional;

import play.mvc.Http.Response;
import play.test.FunctionalTest;
import play.utils.Utils;

public abstract class BaseFunctionalTest extends FunctionalTest {

	public BaseFunctionalTest() {
		super();
	}

	protected String replacePlusWithSpace(String s) {
		
		return s.replaceAll("\\+", " ");
		
	}
	
	protected void assertCookieContains(String cookieName, String cookieValue, Response response) {
		String flash = response.cookies.get(cookieName).value;
        String message = Utils.urlDecodePath(replacePlusWithSpace(flash));

        assertTrue(message.trim().indexOf(cookieValue) >= 0);
	}	
	

}