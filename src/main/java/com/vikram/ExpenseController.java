package com.vikram;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.category.Category;
import com.vikram.category.CategoryTree;
import com.vikram.db.ExpenseStore;
import com.vikram.model.Expense;
import com.vikram.openidconnect.login.core.identity.Identity;
import com.vikram.util.RequestContext;
import com.vikram.util.RequestContext.RequestKey;

@RestController
@RequestMapping("expense")
public class ExpenseController {

	@Autowired
	private ExpenseStore expenseStore;
	
	@Autowired
	private CategoryTree tree;
		
	@RequestMapping(method = RequestMethod.GET)
	public Expense get(@RequestParam("id") String id) { 		
		Expense exp = new Expense();
		exp.setCategory("2");
		exp.setDescription("Test");
		
		return exp;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Expense add(@RequestBody() Expense expense){
		
		Identity identity = RequestContext.get().getValue(RequestKey.IDENTITY);
		expense.setuID(identity.getEmailAddress());
		
		setCategoryName(expense);
		
		expenseStore.add(expense);
		
		return expense;
	}

	private void setCategoryName(Expense expense) {
		Category category = tree.findByCatName(expense.getCategory());
		if(category == null){
			category = tree.getMiscellaneousCategory();
		}		
		expense.setCategory(String.valueOf(category.getCatId()));
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Expense update(@RequestParam("expense") String expense){
		return null;
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@RequestParam("id") String id){
		
	}
	
}
