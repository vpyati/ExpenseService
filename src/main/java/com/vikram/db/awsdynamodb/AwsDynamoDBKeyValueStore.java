package com.vikram.db.awsdynamodb;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.GetItemRequest;
import com.amazonaws.services.dynamodbv2.model.GetItemResult;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.vikram.db.KeyValueStore;

public class AwsDynamoDBKeyValueStore implements KeyValueStore {

	private static final String VALUE = "Value";
	private static final String KEY = "Key";
	private static final String TABLE_NAME = "GenericKeyValueStore";
	@Autowired
	private AmazonDynamoDB amazonDynamoDb;
	
	@Override
	public void deleteKey(String key) {
		amazonDynamoDb.deleteItem(TABLE_NAME, getMap(key));
		
	}
	
	private Map<String, AttributeValue> getMap(String key) {
		Map<String, AttributeValue> map = new HashMap<String, AttributeValue>();
		map.put(KEY, new AttributeValue(key));
		return map;
	}


	@Override
	public String getValue(String key) {		
		GetItemResult result =  amazonDynamoDb.getItem(new GetItemRequest().withTableName(TABLE_NAME).withKey(getMap(key)));
		if(result == null || result.getItem()==null){
			return null;
		}
		
		return result.getItem().get(VALUE).getS();
	}

	
	public void setAmazonDynamoDB(AmazonDynamoDB amazonDynamoDb){
		this.amazonDynamoDb = amazonDynamoDb;
	}

	@Override
	public void setValue(String key, String value) {
		amazonDynamoDb.putItem(new PutItemRequest().withTableName(TABLE_NAME).addItemEntry(KEY, new AttributeValue(key)).addItemEntry(VALUE, new AttributeValue(value)));
	}

}
