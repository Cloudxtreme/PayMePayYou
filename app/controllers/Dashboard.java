package controllers;

import models.Account;
import models.ExpensePool;
import models.User;
import play.mvc.Controller;

public class Dashboard extends Controller {

	
    public static void index(long userId, long defaultPoolId) {
    	
    	User user = User.findById(userId);
    	ExpensePool defaultPool = ExpensePool.findById(defaultPoolId);
        System.out.println("we are herePPPPPPPPPPPPPPPPPPPPPPPPPP");
    	render(user, defaultPool);
    }
    
    
}
