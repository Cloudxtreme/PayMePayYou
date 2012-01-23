package functional;

import play.mvc.Http;
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
		Http.Cookie cookie = response.cookies.get(cookieName);
		if (cookie == null)
			throw new AssertionError("Cookie not found");
		String value = response.cookies.get(cookieName).value;
		if (value == null)
			throw new AssertionError("Cookie value not availble");
        String message = Utils.urlDecodePath(replacePlusWithSpace(value));

        assertTrue(message.trim().indexOf(cookieValue) >= 0);
	}	
	
	protected void assertCookieContainsPositiveNumber(String cookieName, Response response) {
		Http.Cookie cookie = response.cookies.get(cookieName);
		if (cookie == null)
			throw new AssertionError("Cookie not found");
		String value = response.cookies.get(cookieName).value;
		if (value == null)
			throw new AssertionError("Cookie value not availble");
        String message = Utils.urlDecodePath(replacePlusWithSpace(value));

        assertTrue(Long.parseLong(message) >= 0L);
	}	
	

}