package com.vikram.db.awsdynamodb;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

public abstract class AbstractAwsDb {
	
	@Autowired
	protected AmazonDynamoDB amazonDynamoDb;
	
}
