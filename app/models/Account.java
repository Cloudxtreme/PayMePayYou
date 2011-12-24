package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Account extends Model {

	public String name;
	
	@OneToOne(cascade=CascadeType.ALL)
	public User owner;

	public Account(String name, User owner) {
		super();
		this.name = name;
		this.owner = owner;
	}
	
	
	
	
	
}
