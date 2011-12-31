package models;

public class AccountBuilder {
	
	private String name;
	
	public Account build() {
		return new Account(this);
	}

	public AccountBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getName() {
		return this.name;
	}

}
