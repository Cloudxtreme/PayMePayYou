package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class ExpensePool extends Model {

	public String name;
	
	@ManyToOne
	@JoinColumn(name="ACCOUNT_ID")
	public Account account;

	public ExpensePool(String name) {
		super();
		this.name = name;
	}
	
	
	
}
