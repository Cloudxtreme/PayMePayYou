package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Expense extends Model {

	public String name;
	
	@ManyToOne
	@JoinColumn(name="EXPENSE_POOL_ID")	
	public ExpensePool expensePool;
	
	public double amount;

	public Expense(String name, ExpensePool pool, double amount) {
		super();
		this.name = name;
		this.expensePool = pool;
		this.amount = amount;
	}
	
	
	
}
