package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class Expense extends Model {

	public String name;
	
	@ManyToOne
	@JoinColumn(name="EXPENSE_POOL_ID")	
	public ExpensePool expensePool;
	
	public double amount;
	
	public Date date;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="expense", orphanRemoval=true)
	public List<ExpenseDetail> expenseDetails = new ArrayList<ExpenseDetail>(0);

	public Expense() {
		
	}
	
	public Expense(String name, ExpensePool pool, Date date, double amount) {
		super();
		this.name = name;
		this.expensePool = pool;
		this.amount = amount;
		this.date = date;
	}
	
	public void addExpenseDetail(ExpenseDetail expenseDetail) {
		this.expenseDetails.add(expenseDetail);
	}
	
	public boolean removeExpenseDetail(ExpenseDetail expenseDetail) {
		return expenseDetails.remove(expenseDetail);
	}	
	
	
	public String getExpenseDetailsSummary() {
		
		return "Rahul > 2 people";
		
	}
	
}
