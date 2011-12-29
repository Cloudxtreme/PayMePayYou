package controllers;

import models.User;
import play.mvc.Controller;

public class SignUp extends Controller {

    public static void index() {
        render();
    }
    
    public static void signupUser(User user) {
    	render("SignUp/success.html");
    	
    	
    }

}
