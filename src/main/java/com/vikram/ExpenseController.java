package com.vikram;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vikram.model.Expense;

@RestController
@RequestMapping("expense")
public class ExpenseController {


	@RequestMapping(method = RequestMethod.GET)
	public Expense get(@RequestParam("id") String id) { 		
		Expense exp = new Expense();
		exp.setCategory(2);
		exp.setDate(new Date());
		exp.setDescription("Test");
		
		return exp;
	}

	@RequestMapping(method = RequestMethod.POST)
	public Expense add(@RequestBody() Expense expense){
		
		
		
		
		return expense;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Expense update(@RequestParam("expense") String expense){
		return null;
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void delete(@RequestParam("id") String id){
		
	}

	
}
