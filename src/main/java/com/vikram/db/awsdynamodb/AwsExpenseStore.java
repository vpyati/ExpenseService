package com.vikram.db.awsdynamodb;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.vikram.db.ExpenseStore;
import com.vikram.model.Expense;

public class AwsExpenseStore extends AbstractAwsDb implements ExpenseStore {

	@Override
	public void add(Expense expense) {	
		
		amazonDynamoDb.putItem(new PutItemRequest().withTableName("Expense").addItemEntry("Name", new AttributeValue(expense.getName()))
				.addItemEntry("Description", new AttributeValue(expense.getDescription()))
				.addItemEntry("CreationDate", new AttributeValue(String.valueOf(expense.getCreationDate().getTime())))
				.addItemEntry("UID", new AttributeValue(expense.getuID()))
				.addItemEntry("Tags", new AttributeValue(expense.getTags()))
				.addItemEntry("Category", new AttributeValue(String.valueOf(expense.getCategory())))
				
				);
		 
		
	}
	
}
