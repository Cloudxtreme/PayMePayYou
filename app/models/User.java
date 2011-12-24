package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class User extends Model {

	public String email;
	public String password;
	private String hash;
	public String fullName;
	public boolean isAdmin;
	
	@ManyToOne
	@JoinColumn(name="ACCOUNT_ID")
	public Account account;
	
	
	public User(String email, String password,String fullName, Account account,
			boolean isAdmin) {
		super();
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.account = account;
		this.isAdmin = isAdmin;
	}
	
	public static User login(String email, String password) {
		return find("byEmailAndPassword", email, password).first();
	}
	
	
	
}
