package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class User extends Model {

	public String email;
	public String password;
	private String hash;
	public String fullName;
	public boolean isAdmin;
	
	
	public User(String email, String password,String fullName,
			boolean isAdmin) {
		super();
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.isAdmin = isAdmin;
	}
	
	public static User login(String email, String password) {
		return find("byEmailAndPassword", email, password).first();
	}
	
	
	
}
