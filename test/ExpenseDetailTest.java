
import models.Account;
import models.Expense;
import models.ExpenseDetail;
import models.ExpensePool;
import models.User;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class ExpenseDetailTest extends UnitTest {

	Account account;
	ExpensePool expensePool;
	Expense expense;
	ExpenseDetail expenseDetail;
	
    @Before
    public void setup() {
        Fixtures.deleteDatabase();
        
		//create user to associate with account
		account = new Account("myAccount");		
		User user = new User("rahulj51@gmail.com", "secret", "Rahul Jain", account, true);
		account.addUser(user); 

		expensePool = new ExpensePool("dailyExpenses");
    	expensePool.account = account;
    	
    	expense = new Expense("Apple Store", expensePool, 10.0d);
    
    	expenseDetail = new ExpenseDetail(expense, user, 10.0d, 0.0d); //single user expense 
    	expense.addExpenseDetail(expenseDetail);
    	
    	expensePool.addExpense(expense);
    	account.addExpensePool(expensePool);
    	
		account.save();		
    }	
	
    @Test
    public void ExpenseDetailCanBeCreated() {
    	
		Account savedAccount = Account.findById(account.id);
		ExpensePool pool = savedAccount.expensePools.get(0);
		Expense expense  = pool.expenses.get(0);
		ExpenseDetail expenseDetail = expense.expenseDetails.get(0);
		
		assertEquals(10.0d, expenseDetail.contribution, 0.000001);
		assertEquals(0.0d, expenseDetail.due, 0.000001);
    }	
	
    
	@Test
	public void ExpenseDetailCanBeDeleted() {

		expensePool = account.expensePools.get(0);
		expense = expensePool.expenses.get(0);
		expenseDetail = expense.expenseDetails.get(0);
		long expenseDetailId = expenseDetail.id;
		expense.removeExpenseDetail(expenseDetail);  
		account.save();
		
		ExpenseDetail ex = ExpenseDetail.findById(expenseDetailId);
		assertNull(ex);
	    assertEquals(0, account.expensePools.get(0).expenses.get(0).expenseDetails.size());
	}  	    
}
