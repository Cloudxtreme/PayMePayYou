package unit.model;

import models.Account;
import models.AccountBuilder;

import org.junit.Test;

import play.test.UnitTest;

public class AcountBuilderTest extends UnitTest {

	@Test
	public void testBuildWithAccountName() {

		AccountBuilder builder = new AccountBuilder();
		
		Account account = builder.build("my account");
		assertNotNull(account);
		assertEquals("my account", account.name);
		
	}

}
