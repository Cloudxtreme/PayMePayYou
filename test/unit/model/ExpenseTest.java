package unit.model;

import models.Account;
import models.Expense;
import models.ExpensePool;
import models.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;


public class ExpenseTest extends UnitTest {

	Account account;
	ExpensePool expensePool;
	Expense expense;
	
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
    	expensePool.addExpense(expense);
    	
    	account.addExpensePool(expensePool);
    	
		account.save();		
    }		
	
    @After
    public void tearDown() {
        Fixtures.deleteDatabase();
    }	    

    @Test
    public void ExpenseCanBeCreated() {
    	//created in setup. check for existence
		Account savedAccount = Account.findById(account.id);
		ExpensePool pool = savedAccount.expensePools.get(0);
		assertEquals(1, pool.expenses.size());
		assertEquals(expense.name, pool.expenses.get(0).name);
    }
    
    @Test
	public void ExpenseCanBeDeleted() {
		account = Account.findById(account.id);
		expensePool = account.expensePools.get(0);
		expense = expensePool.expenses.get(0);
		long expenseId = expense.id;

		expensePool.removeExpense(expense);
		account.save();
		
		Expense ex = Expense.findById(expenseId);
		assertNull(ex);
	    assertEquals(0, account.expensePools.get(0).expenses.size());
	}  
	
    @Test
	public void ExistingExpenseCanBeUpdated() {

		expensePool = account.expensePools.get(0);
		expense = expensePool.expenses.get(0);
		long expenseId = expense.id;

		expense.name = "Android Store";
		expense.amount = 20.0d;
		account.save();
		
		Expense ex = Expense.findById(expenseId);
		assertEquals(expense.name, ex.name);
		assertEquals(expense.amount, ex.amount, 0.000001);
	}		
}
