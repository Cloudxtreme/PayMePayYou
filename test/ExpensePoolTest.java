import models.Account;
import models.ExpensePool;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class ExpensePoolTest extends UnitTest {
	
	Account account;
	
    @Before
    public void setup() {
        Fixtures.deleteDatabase();
        
		//create user to associate with account
		account = new Account("myAccount");		
		User user = new User("rahulj51@gmail.com", "secret", "Rahul Jain", account, true);
		account.addUser(user); 
		account.save();
		

		
    }	
    
    
    @Test
    public void ExpensePoolCanBeCreated() {
    	
    	ExpensePool expensePool = new ExpensePool("dailyExpenses");
    	expensePool.account = account;
    	account.addExpensePool(expensePool);
		account.save();
		
		Account savedAccount = Account.findById(account.id);
		assertEquals(1, savedAccount.expensePools.size());
		assertEquals("dailyExpenses", savedAccount.expensePools.get(0).name);
    }
    
	

}
