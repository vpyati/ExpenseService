package com.vikram;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.category.CategoryTree;
import com.vikram.db.ExpenseDo;
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
	
	
	private static Logger logger = LoggerFactory.getLogger(ExpenseController.class);
		
	@RequestMapping(method = RequestMethod.GET)
	public List<Expense> get(@RequestParam("s") long start, @RequestParam("e") long end) { 		
		
		Identity identity = getIdentity();		
		logger.info("Invoking method to get expenses");
		List<ExpenseDo> dataResults =expenseStore.findAllInDateRange(identity.getEmailAddress(),start, end);
		logger.info("Found " +(dataResults==null?0:dataResults.size()+" entries"));
		
		List<Expense> results = new ArrayList<Expense>();
		for(ExpenseDo dataObj:dataResults){
			results.add(new Expense(dataObj));
		}
		
		return results;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Expense add(@RequestBody() Expense expense, HttpServletResponse response){
		
		if(!expense.isValid()){
			response.setStatus(400);
			return null;
		}
		
		Identity identity = getIdentity();
		
		expense.transformForInsert(identity, tree);
		
		logger.info("Trying to store identity using Expense store");
		expenseStore.add(expense.getDataObject());
		
		return expense;
	}

	private Identity getIdentity() {
		logger.info("Fetching identity from context");
		Identity identity = RequestContext.get().getValue(RequestKey.IDENTITY);
		logger.info("Fetching identity from context ---- completed -> "+identity==null?"Unable to fetch identity":identity.getEmailAddress());
		return identity;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Expense update(@RequestParam("expense") String expense){
		return null;
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@RequestParam("id") String id){
		
	}
	
}
