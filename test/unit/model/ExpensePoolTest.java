package unit.model;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import models.Account;
import models.Expense;
import models.ExpensePool;
import models.User;

import org.junit.After;
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
    	
    	Expense expense = new Expense("Apple Store", expensePool, new Date(2011-1900, 11,11), 10.0d);
    	expensePool.addExpense(expense);  
    	expense = new Expense("Comic Store", expensePool, new Date(2011-1900, 11,11), 15.0d);
    	expensePool.addExpense(expense);     	
    	expense = new Expense("Etsy", expensePool, new Date(2011-1900, 11,11), 45.0d);
    	expensePool.addExpense(expense);     	

    	expense = new Expense("rent", expensePool, new Date(2012-1900, 0,26), 2000d);
    	expensePool.addExpense(expense);     	
    	expense = new Expense("ticket to mumbai", expensePool, new Date(2012-1900, 0,26), 1000d);
    	expensePool.addExpense(expense);     	

    	
    	expensePool.account = account;
    	account.addExpensePool(expensePool);
		account.save();		
    }	
    
    @After
    public void tearDown() {
        Fixtures.deleteDatabase();
    }    
    
    
    @Test
    public void ExpensePoolCanBeCreated() {
    	
		Account savedAccount = Account.findById(account.id);
		assertEquals(1, savedAccount.expensePools.size());
		assertEquals(expensePool.name, savedAccount.expensePools.get(0).name);
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
		assertEquals(expensePool.name, savedPool.name);
	}	
	
	@Test
	public void getExpensesGroupedByDatesShouldReturnGroupedListOfExpenses() {
		
		Map<Date, List<Expense>> expensesByDate = expensePool.getExpensesGroupedByDates();
		
		assertEquals(2, expensesByDate.size());
		assertEquals(3, expensesByDate.get(new Date(2011-1900, 11,11)).size());
		assertEquals(2, expensesByDate.get(new Date(2012-1900, 0,26)).size());
		
	}
	
	@Test
	public void getTransactionDatesSortedAscReturnsDatesInAscendingOrder() {
		
		Iterator<Date> transactionDatesIter = expensePool.getTransactionDatesSortedAsc();
		
		Date prevTransactionDate = transactionDatesIter.next();
		while (transactionDatesIter.hasNext()) {
			Date transactionDate = transactionDatesIter.next();
			
			assertTrue(transactionDate.after(prevTransactionDate));
			prevTransactionDate = transactionDate;
		}
	}

	
	@Test
	public void getTransactionDatesSortedDescReturnsDatesInDescendingOrder() {
		
		Iterator<Date> transactionDatesIter = expensePool.getTransactionDatesSortedDesc();
		
		Date prevTransactionDate = transactionDatesIter.next();
		while (transactionDatesIter.hasNext()) {
			Date transactionDate = transactionDatesIter.next();
			
			assertTrue(transactionDate.before(prevTransactionDate));
			prevTransactionDate = transactionDate;
		}
	}	
}
