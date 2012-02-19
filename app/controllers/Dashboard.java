package controllers;

import models.Account;
import models.ExpensePool;
import models.User;
import play.mvc.Controller;

public class Dashboard extends Controller {

	
    public static void index(long userId, long selectedPoolId) { //TODO rjain9 we shouldn't be passing userid here.
    	
    	User user = User.findById(userId);
    	ExpensePool selectedPool = ExpensePool.findById(selectedPoolId);
    	render(user, selectedPool);
    }
    
    
}
