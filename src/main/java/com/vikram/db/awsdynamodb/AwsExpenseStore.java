package com.vikram.db.awsdynamodb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Select;
import com.vikram.db.ExpenseDo;
import com.vikram.db.ExpenseStore;

public class AwsExpenseStore extends AbstractAwsDb implements ExpenseStore {

	private static Logger logger = LoggerFactory.getLogger(AwsExpenseStore.class);
	
	
	@Override
	public void add(ExpenseDo expense) {	
		
		try {
			DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDb);
			mapper.save(expense);
			
			logger.info("Expense inserted without any error");
		} catch (Exception e) {
			logger.error("Error while inserting new expense", e);
		}
		 
		
	}


	@Override
	public List<ExpenseDo> findAllInDateRange(String uid, long start, long end) {
		
		DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDb);
		
		Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(uid));
        eav.put(":val2", new AttributeValue().withN(String.valueOf(start)));
        eav.put(":val3", new AttributeValue().withN(String.valueOf(end)));
		
		 DynamoDBQueryExpression<ExpenseDo> queryExpression = new DynamoDBQueryExpression<ExpenseDo>()
		            .withKeyConditionExpression("UID = :val1 AND CreationDate BETWEEN :val2 AND :val3")
		            .withExpressionAttributeValues(eav)
		            .withSelect(Select.ALL_ATTRIBUTES)
		            .withScanIndexForward(false);
		 
		List<ExpenseDo> expenses =  mapper.query(ExpenseDo.class, queryExpression);
		
		return expenses;	
	}
	
}
