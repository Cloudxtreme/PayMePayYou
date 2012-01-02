package actions;

import models.User;

public class LoginAction {

	public User login(String email, String password) throws Exception {
		
		User user = User.login(email, password);
		
		if (user == null) {
			throw new Exception("Invalid user id or password");
		}
		else {
			return user;
		}
	}
	
}
