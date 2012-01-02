package actions;

import models.Account;
import models.AccountBuilder;
import models.ExpensePool;
import models.ExpensePoolBuilder;
import models.User;
import models.UserBuilder;

public class SignUpAction {
	
	AccountBuilder accountBuilder;
	UserBuilder userBuilder;
	ExpensePoolBuilder expensePoolBuilder;
	
	public SignUpAction() {};
	
	public SignUpAction(AccountBuilder accountBuilder, ExpensePoolBuilder expensePoolBuilder) {
		this.accountBuilder = accountBuilder;
		this.expensePoolBuilder = expensePoolBuilder;
	}
	
    public Account signupUser(User user) throws Exception {

    	User existingUser = User.find("byEmail", user.email).first();
    	
    	if (existingUser != null) 
    		throw new Exception("User " + user.email + " already exists");
    	else {
    		
    		//create an account
    		accountBuilder.setName("Account of " + user.fullName);
    		Account newAccount = accountBuilder.build();
    		newAccount.users.add(user);
    		user.account = newAccount;
    		
    		//create an expense pool
    		expensePoolBuilder.setName("Default Pool");
    		ExpensePool newExpensePool = expensePoolBuilder.build();
    		newExpensePool.account = newAccount;
    		newAccount.expensePools.add(newExpensePool);
    		return newAccount.save();
    	}
    }	
}
