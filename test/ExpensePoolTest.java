import models.Account;
import models.ExpensePool;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class ExpensePoolTest extends UnitTest {
	
	Account account;
	ExpensePool expensePool;
	
    @Before
    public void setup() {
        Fixtures.deleteDatabase();
        
		//create user to associate with account
		account = new Account("myAccount");		
		User user = new User("rahulj51@gmail.com", "secret", "Rahul Jain", account, true);
		account.addUser(user); 

    	expensePool = new ExpensePool("dailyExpenses");
    	expensePool.account = account;
    	account.addExpensePool(expensePool);
		account.save();		
    }	
    
    
    @Test
    public void ExpensePoolCanBeCreated() {
    	
		Account savedAccount = Account.findById(account.id);
		assertEquals(1, savedAccount.expensePools.size());
		assertEquals("dailyExpenses", savedAccount.expensePools.get(0).name);
    }
    
	@Test
	public void ExpensePoolCanBeDeleted() {

		expensePool = account.expensePools.get(0);
		long poolId = expensePool.id;
		account.removeExpensePool(expensePool);
		account.save();
		
		ExpensePool ep = ExpensePool.findById(poolId);
		assertNull(ep);
		assertEquals(0, account.expensePools.size());
	}
	
	@Test
	public void ExistingAccountCanBeUpdated() {

		expensePool = account.expensePools.get(0);
		long poolId = expensePool.id;

		expensePool.name = "tripToRome";
		account.save();
		
		ExpensePool savedPool = ExpensePool.findById(poolId);
		assertEquals("tripToRome", savedPool.name);
	}		

}
