package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import play.db.jpa.Model;

@Entity
public class ExpensePool extends Model {

	public String name;
	
	@ManyToOne
	@JoinColumn(name="ACCOUNT_ID")
	public Account account;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="expensePool",  orphanRemoval=true)
	@OrderBy("date DESC")
	public List<Expense> expenses = new ArrayList<Expense>(0);
	
	public ExpensePool() {
		
	}

	public ExpensePool(String name) {
		super();
		this.name = name;
	}
	
	public ExpensePool(ExpensePoolBuilder builder) {
		super();
		this.name = builder.getName();
	}	
	
	public void addExpense(Expense expense) {
		this.expenses.add(expense);
	}
	
	public boolean removeExpense(Expense expense) {
		return expenses.remove(expense);
	}
	
	public Map<Date, List<Expense>> getExpensesGroupedByDates() {
		
		Map<Date, List<Expense>> mapOfDatesVsExpenses = new HashMap<Date, List<Expense>>();
		
		for (Expense expense : expenses) {
			Date dateOfExpense = expense.date;
			
			if (mapOfDatesVsExpenses.containsKey(dateOfExpense)) {
				mapOfDatesVsExpenses.get(dateOfExpense).add(expense);
			} else {
				List<Expense> expensesForThisDate = new ArrayList<Expense>();
				expensesForThisDate.add(expense);
				mapOfDatesVsExpenses.put(dateOfExpense, expensesForThisDate);
			}
		}
		
		return mapOfDatesVsExpenses;
		
	}
	
	
	public Iterator<Date> getTransactionDatesSortedAsc() {
		
		Set transactionDates =  getExpensesGroupedByDates().keySet();
		
		NavigableSet s = new TreeSet();
		s.addAll(transactionDates);
		return s.iterator();
	}
	
	
	public Iterator<Date> getTransactionDatesSortedDesc() {
		
		Set transactionDates =  getExpensesGroupedByDates().keySet();
		
		NavigableSet s = new TreeSet();
		s.addAll(transactionDates);
		return s.descendingIterator();
	}	
	
}
