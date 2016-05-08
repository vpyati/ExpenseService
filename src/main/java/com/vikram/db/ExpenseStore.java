package com.vikram.db;

import java.util.List;

public interface ExpenseStore {
	
	public void add(ExpenseDo expense);
	
	public List<ExpenseDo> findAllInDateRange(String uid, long start, long end);

}
