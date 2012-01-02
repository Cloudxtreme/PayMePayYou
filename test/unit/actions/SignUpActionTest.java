package unit.actions;


import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import models.Account;
import models.AccountBuilder;
import models.ExpensePool;
import models.ExpensePoolBuilder;
import models.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

	private AccountBuilder mockAccountBuilder;
	private Account mockAccount;
	private List mockUsers;
	
	private List mockExpensePools;
	private ExpensePoolBuilder mockExpensePoolBuilder;
	private ExpensePool mockExpensePool;
	
	private JPAQuery queryMock;
	
	@Before
	public void setup() {
		setupMocks();
	}
	
	@Test
	public void testThatValidUserCanSuccessfullySignup() {
		
		when (queryMock.first()).thenReturn(null);
		
		User user = new User("sunny@etsy.com", "hersecret", "sunny leone", true);
		
		SignUpAction signUpAction = new SignUpAction(mockAccountBuilder, mockExpensePoolBuilder);
		
		try{
			Account savedAccount = signUpAction.signupUser(user);
			
	        PowerMockito.verifyStatic(times(1));
	        User.find("byEmail", user.email);
	        
	        verify(mockAccountBuilder, times(1)).setName("Account of " + user.fullName);
	        verify(mockAccountBuilder, times(1)).build();
	        verify(mockUsers, times(1)).add(user);
	        assertEquals(mockAccount, user.account);
	        
	        verify(mockExpensePoolBuilder, times(1)).setName("Default Pool");
	        verify(mockExpensePoolBuilder, times(1)).build();
	        verify(mockExpensePools, times(1)).add(mockExpensePool);
	        assertEquals(mockAccount, mockExpensePool.account);

	        verify(mockAccount, times(1)).save();

		}
		catch(Exception e) {
			fail("Exception is thrown " + e);
		}
		
	}
	
	
	@Test
	public void testThatAnExistingUserCannotSignup() {
		
		User user = new User("sunny@etsy.com", "hersecret", "sunny leone", true);
		when (queryMock.first()).thenReturn(user);
		
		SignUpAction signUpAction = new SignUpAction(mockAccountBuilder, mockExpensePoolBuilder);
		
		try {
			signUpAction.signupUser(user);
			fail("Exception was not thrown");
		} catch (Exception e) {
			
		    PowerMockito.verifyStatic(times(1));
	        User.find("byEmail", user.email);
		}
	}
	
	
	private void setupMocks() {
		
		//mock query
		queryMock = mock(JPAQuery.class);

		
		//mock static find method
		PowerMockito.mockStatic(GenericModel.class);
		when (User.find(anyString(), anyString())).thenReturn(queryMock);
		
		mockUsers = mock(List.class);
		mockAccount = mock(Account.class);
		mockAccount.users = mockUsers;
		mockAccountBuilder = mock(AccountBuilder.class);
		
		when (mockAccountBuilder.setName(anyString())).thenAnswer(new Answer<AccountBuilder>() {
			
		     public AccountBuilder answer(InvocationOnMock invocation) throws Throwable {
		    	 
		         mockAccount.name = (String) invocation.getArguments()[0];
		    	 AccountBuilder mockAccountBuilder = (AccountBuilder) invocation.getMock();
		         return mockAccountBuilder;
		     }			
		} );
		
		when (mockAccountBuilder.build()).thenReturn(mockAccount);
		when(mockAccount.save()).thenReturn(mockAccount);		
		
		
		//expense pool
		mockExpensePools = mock(List.class);
		mockAccount.expensePools = mockExpensePools;

		mockExpensePool = mock(ExpensePool.class);
		mockExpensePoolBuilder = mock(ExpensePoolBuilder.class);
		
		when (mockExpensePoolBuilder.setName(anyString())).thenAnswer(new Answer<ExpensePoolBuilder>() {
			
		     public ExpensePoolBuilder answer(InvocationOnMock invocation) throws Throwable {
		    	 
		    	 mockExpensePool.name = (String) invocation.getArguments()[0];
		    	 ExpensePoolBuilder mockExpensePoolBuilder = (ExpensePoolBuilder) invocation.getMock();
		         return mockExpensePoolBuilder;
		     }			
		} );	
		
		
		when (mockExpensePoolBuilder.build()).thenReturn(mockExpensePool);		
		
	}
	
	
}
