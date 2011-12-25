package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class ExpensePool extends Model {

	public String name;
	
	@ManyToOne
	@JoinColumn(name="ACCOUNT_ID")
	public Account account;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="expensePool",  orphanRemoval=true)
	public List<Expense> expenses = new ArrayList<Expense>(0);
	

	public ExpensePool(String name) {
		super();
		this.name = name;
	}
	
	public void addExpense(Expense expense) {
		this.expenses.add(expense);
	}
	
	public boolean removeExpense(Expense expense) {
		return expenses.remove(expense);
	}	
	
}
