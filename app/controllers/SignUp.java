package controllers;

import models.AccountBuilder;
import models.ExpensePoolBuilder;
import models.User;
import play.data.validation.Valid;
import play.mvc.Controller;
import actions.SignUpAction;

public class SignUp extends Controller {

    public static void index(User user) {
    	render(user);
    }
    
    public static void signupUser(@Valid User user) {
    	
    	System.out.println("called with " + user);
    	
        if(validation.hasErrors()) {
        	System.out.println("validation errors" + validation.errorsMap());
            params.flash(); 
            validation.keep(); 
            user.password = null;
            index(user);
        }    	
    	
    	SignUpAction signUpAction = new SignUpAction(new AccountBuilder(), new ExpensePoolBuilder());
    	
    	try
    	{
    		signUpAction.signupUser(user);
    		render("SignUp/success.html");
    	}
    	catch(Exception e) {
    		flash.error(e.getMessage());
            params.flash();
            validation.keep(); 
            user.password = null;
            index(user);
    	}
    }
    
    

}
