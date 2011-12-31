package controllers;

import models.AccountBuilder;
import models.ExpensePoolBuilder;
import models.User;
import play.data.validation.Valid;
import play.mvc.Controller;
import actions.SignUpAction;

public class SignUp extends Controller {

    public static void index() {
    	render();
    }
    
    public static void signupUser(@Valid User user) {
    	
    	System.out.println("called with " + user);
    	SignUpAction signUpAction = new SignUpAction(new AccountBuilder(), new ExpensePoolBuilder());
    	
    	try
    	{
    		signUpAction.signupUser(user);
    		render("SignUp/success.html");
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		flash.error(e.getMessage());
    		render("SignUp/index.html", user);
    	}
    }
    
    

}
