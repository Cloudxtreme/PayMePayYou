package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.data.validation.Email;
import play.data.validation.Match;
import play.data.validation.MaxSize;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class User extends Model {

	@Required
	@Email
	public String email;
	
	@Required
	@MinSize(5)
	@MaxSize(20)
	@Match("[A-Za-z0-9]")  //modify later
	public String password;
	
	private String hash;
	
	@Required
	@MinSize(3)
	@MaxSize(60)
	public String fullName;
	
	public boolean isAdmin;
	
	@ManyToOne
	@JoinColumn(name="ACCOUNT_ID")
	public Account account;
	

	public User(String email, String password,String fullName,boolean isAdmin) {
		super();
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.isAdmin = isAdmin;
	}
	
	
	public User(String email, String password,String fullName, Account account,
			boolean isAdmin) {
		super();
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.account = account;
		this.isAdmin = isAdmin;
	}
	
	public User(UserBuilder builder) {
		super();
		this.email = builder.email;
		this.password = builder.password;
		this.fullName = builder.fullName;
		this.isAdmin = builder.isAdmin;		
	}
	
	
	public static User login(String email, String password) {
		return find("byEmailAndPassword", email, password).first();
	}
	
	
	
}
