package models;

public class UserBuilder {
	
	String fullName;
	String email;
	String password;
	boolean isAdmin;
	
	
	public User build() {
		return new User(this);
	}	
	
	
	public String getFullName() {
		return fullName;
	}
	public UserBuilder setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}
	public String getEmail() {
		return email;
	}
	public UserBuilder setEmail(String email) {
		this.email = email;
		return this;
	}
	public String getPassword() {
		return password;
	}
	public UserBuilder setPassword(String password) {
		this.password = password;
		return this;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}
	public UserBuilder setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
		return this;
	}
	
	
	

}
