package com.vikram.db.awsdynamodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.vikram.db.ExpenseStore;
import com.vikram.model.Expense;

public class AwsExpenseStore extends AbstractAwsDb implements ExpenseStore {

	private static Logger logger = LoggerFactory.getLogger(AwsExpenseStore.class);
	
	
	@Override
	public void add(Expense expense) {	
		
		try {
			PutItemResult result = amazonDynamoDb.putItem(new PutItemRequest().withTableName("Expense").addItemEntry("Name", new AttributeValue(expense.getName()))
					.addItemEntry("Description", new AttributeValue(expense.getDescription()))
					.addItemEntry("CreationDate", new AttributeValue(expense.getAmazonRangeKey()))
					.addItemEntry("UID", new AttributeValue(expense.getuID()))
					.addItemEntry("Tags", new AttributeValue(expense.getTags()))
					.addItemEntry("Category", new AttributeValue(expense.getCategory()))
					.addItemEntry("Currency", new AttributeValue("INR"))
					.addItemEntry("Amount", new AttributeValue(expense.getAmount()))
					);
			
			logger.info("Expense inserted without any error");
		} catch (Exception e) {
			logger.error("Error while inserting new expense", e);
		}
		 
		
	}
	
}
