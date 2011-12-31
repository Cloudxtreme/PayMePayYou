package actions;

import models.Account;
import models.AccountBuilder;
import models.ExpensePool;
import models.User;
import models.UserBuilder;

public class SignUpAction {
	
	AccountBuilder accountBuilder;
	UserBuilder userBuilder;
	
	public SignUpAction() {};
	
	public SignUpAction(AccountBuilder accountBuilder) {
		this.accountBuilder = accountBuilder;
	}
	
    public void signupUser(User user) throws Exception {

    	User existingUser = User.find("byEmail", user.email).first();
    	
    	if (existingUser != null)
    		throw new Exception("User " + user.email + " already exists");
    	else {
    		
    		//create an account
    		accountBuilder.setName("account of " + user.fullName);
    		Account newAccount = accountBuilder.build();
    		newAccount.users.add(user);
    		user.account = newAccount;
    		
    		//create an expense pool
    		/*ExpensePool newExpensePool = new ExpensePool("default");
    		newExpensePool.account = newAccount;
    		newAccount.expensePools.add(newExpensePool);*/
    		
    		newAccount.save();
    		
    	}
    		
    }	
    
    

}
