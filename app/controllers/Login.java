package controllers;

import models.User;
import play.data.validation.Email;
import play.data.validation.Match;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.mvc.Controller;
import actions.LoginAction;

public class Login extends Controller {
	
    public static void index(String email, String password) {
    	render(email, password);
    }	
    
    public static void loginUser(@Required(message="Password is required") 
    							String email,
    							
    							@Required(message="Password is required")
    							String password) {    

    	System.out.println("called with " + email + "  " + password);
    	
    	if (validation.hasErrors()) {
        	System.out.println("validation errors" + validation.errorsMap());    		
            params.flash(); 
            validation.keep(); 
            index(email, password);    		
    	}
    	
    	LoginAction loginAction = new LoginAction();
    	try {
    		User user = loginAction.login(email, password);
    		
            addToSession(user);
            flash.success("Welcome, " + user.fullName);    	
            
            render("Login/success.html");
            
    	} catch (Exception e) {
    		e.printStackTrace();
    		flash.error(e.getMessage());
            params.flash();
            validation.keep(); 
            index(email, password);    		
    	}
    	
    	
    }
    

	private static void addToSession(User user) {
		session.put("user.fullName", user.fullName);
		session.put("user.email", user.email);
		session.put("user.id", user.id);
	}

}
