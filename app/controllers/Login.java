package controllers;

import models.User;
import play.mvc.Controller;

public class Login extends Controller {
	
    public static void index(String email, String password) {
    	render(email, password);
    }	
    
    public static void loginUser(String email, String password) {    
    	
    }

}
