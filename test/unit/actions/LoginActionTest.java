package unit.actions;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import models.User;
import models.UserBuilder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import play.test.UnitTest;
import actions.LoginAction;

@RunWith(PowerMockRunner.class)
@PrepareForTest(User.class)
public class LoginActionTest extends UnitTest {

	private User mockUser;
	private LoginAction loginAction;
	
	@Before
	public void setup() {
		loginAction = new LoginAction();
		mockUser = mock(User.class);
		PowerMockito.mockStatic(User.class);
		when (User.login(anyString(), anyString())).thenReturn(mockUser);		
	}
	
	@Test
	public void validUserCanLogin() {
		
		String email = "sunny@etsy.com";
		String password = "hersecret";
		
		try {
			User user = loginAction.login(email, password);
		
			assertEquals(mockUser, user);
		
			PowerMockito.verifyStatic(times(1));
			User.login(email, password);
		}catch(Exception e) {
			fail("Exception throwsn - " + e);
		}
		
		
	}

}
