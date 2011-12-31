package models;

public class ExpensePoolBuilder {
	
	private String name;
	
	public ExpensePool build() {
		return new ExpensePool(this);
	}

	public ExpensePoolBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getName() {
		return this.name;
	}

}
