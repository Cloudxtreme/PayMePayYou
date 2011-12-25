package models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class ExpenseDetail extends Model {

	@ManyToOne
	@JoinColumn(name="EXPENSE_ID")	
	public Expense expense;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")		
	public User user;
	
	public double contribution;
	
	public double due;

	
	public ExpenseDetail(Expense expense, User user, double contribution,
			double due) {
		super();
		this.expense = expense;
		this.user = user;
		this.contribution = contribution;
		this.due = due;
	}
	
	
	
	
}
