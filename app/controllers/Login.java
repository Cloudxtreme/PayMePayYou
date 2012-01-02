package controllers;

import models.User;
import play.mvc.Controller;
import actions.LoginAction;

public class Login extends Controller {
	
    public static void index(String email, String password) {
    	render(email, password);
    }	
    
    public static void loginUser(String email, String password) {    
    	
    	LoginAction loginAction = new LoginAction();
    	try {
    		User user = loginAction.login(email, password);
    		
            session.put("user.fullName", user.fullName);
            session.put("user.email", user.email);
            session.put("user.id", user.id);
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

}
