package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class Account extends Model {

	public String name;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="account")
	public List<User> users = new ArrayList<User>(0);
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="account")
	public List<ExpensePool> expensePools = new ArrayList<ExpensePool>(0);

	public Account(String name) {
		super();
		this.name = name;
	}

	public void addExpensePool(ExpensePool pool) {
		this.expensePools.add(pool);
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}	
	
	
}
