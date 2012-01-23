package controllers;

import models.ExpensePool;
import models.Test22;
import models.User;
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.Http;
import actions.LoginAction;

public class Login extends Controller {
	
    public static final String DEFAULT_POOL_ID_COOKIE = "defaultPoolId";


	public static void index(String email) {
    	render(email);
    }	
    
    public static void loginUser(@Required(message="Email address is required") 
    							String email,
    							
    							@Required(message="Password is required")
    							String password) {    

    	System.out.println("called with " + email + "  " + password);
    	
    	if (validation.hasErrors()) {
        	System.out.println("validation errors" + validation.errorsMap());    		
            params.flash(); 
            validation.keep(); 
            index(email);    		
    	}
    	
    	LoginAction loginAction = new LoginAction();
    	try {
    		User user = loginAction.login(email, password);

            ExpensePool defaultPool = setDefaultPoolInCookieIfNeeded(user);
            
            flash.success("Welcome, " + user.fullName);
            
            Dashboard.index(user.id, defaultPool.id);
            
    	} catch (Exception e) {
    		e.printStackTrace();
    		flash.error(e.getMessage());
            params.flash();
            validation.keep(); 
            index(email);    		
    	}
    	
    	
    }
    

	private static void addToSession(User user) {
		session.put("user.fullName", user.fullName);
		session.put("user.email", user.email);
		session.put("user.id", user.id);
	}

	
	private static ExpensePool setDefaultPoolInCookieIfNeeded(User user) {
		
		ExpensePool defaultPool = null;
		String defaultPoolId = null;
		Http.Cookie defaultPoolIdCookie = request.cookies.get(DEFAULT_POOL_ID_COOKIE);
		
		if (defaultPoolIdCookie == null) {
			defaultPool = user.account.expensePools.get(0);
			defaultPoolId = String.valueOf(defaultPool.id);
			response.setCookie(DEFAULT_POOL_ID_COOKIE, defaultPoolId);
		} else {
			defaultPoolId = defaultPoolIdCookie.value;
			defaultPool = ExpensePool.findById(Long.valueOf(defaultPoolId));
		}
		
		return defaultPool;
			
	}
	
}
