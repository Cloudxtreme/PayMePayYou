package controllers;

import models.User;
import play.data.validation.Valid;
import play.mvc.Controller;

public class SignUp extends Controller {

    public static void index() {
    	render();
    }
    
    public static void signupUser(@Valid User user) {
    	render("SignUp/success.html");
    }
    
    

}
