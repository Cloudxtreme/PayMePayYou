package unit.actions;


import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.List;

import models.Account;
import models.AccountBuilder;
import models.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import play.db.jpa.GenericModel;
import play.db.jpa.GenericModel.JPAQuery;
import play.test.UnitTest;
import actions.SignUpAction;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GenericModel.class)
public class SignUpActionTest extends UnitTest {

	
	@Test
	public void testThatValidUserIsSuccessfullyLoggedIn() {
		
		User user = new User("sunny@etsy.com", "hersecret", "sunny leone", true);
		
		
		//mock query result - user doesn't exist 
		JPAQuery queryMock = mock(JPAQuery.class);
		when (queryMock.first()).thenReturn(null);
		
		//mock static find method
		PowerMockito.mockStatic(GenericModel.class);
		when (User.find("byEmail", "sunny@etsy.com")).thenReturn(queryMock);
		
		List mockUsers = mock(List.class);
		final Account mockAccount = mock(Account.class);
		mockAccount.users = mockUsers;
		AccountBuilder mockAccountBuilder = mock(AccountBuilder.class);
		
		when (mockAccountBuilder.setName(anyString())).thenAnswer(new Answer<AccountBuilder>() {
			
		     public AccountBuilder answer(InvocationOnMock invocation) throws Throwable {
		    	 
		         mockAccount.name = (String) invocation.getArguments()[0];
		    	 AccountBuilder mockAccountBuilder = (AccountBuilder) invocation.getMock();
		         return mockAccountBuilder;
		     }			
		} );
		
		when (mockAccountBuilder.build()).thenReturn(mockAccount);
		when(mockAccount.save()).thenReturn(mockAccount);
		
		SignUpAction signUpAction = new SignUpAction(mockAccountBuilder);
		
		try{
			signUpAction.signupUser(user);
			
	        PowerMockito.verifyStatic(Mockito.times(1));
	        User.find("byEmail", "sunny@etsy.com");
	        
	        verify(mockAccountBuilder).setName("account of " + user.fullName);
	        verify(mockAccountBuilder).build();
	        verify(mockUsers).add(user);
	        
	        assertEquals("account of " + user.fullName, mockAccount.name);
	        assertEquals(mockAccount, user.account);
	        
	        verify(mockAccount).save();

		}
		catch(Exception e) {
			fail("Exception is thrown " + e);
		}
		
	}
	
	
}
