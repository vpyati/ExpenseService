package com.vikram.db;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="Expense_Modified")
public class ExpenseDo {
	
	private String Name;
	private String Description;
	private long CreationDate;
	private String Category;	
	private String UID;
	private String Tags;
	private String Amount;
	private String Currency;
	
	public ExpenseDo(){
		this.Currency = "INR";
	}

	@DynamoDBAttribute(attributeName="Currency")
	public String getCurrency() {
		return Currency;
	}
	public void setCurrency(String currency) {
		Currency = currency;
	}
	@DynamoDBAttribute(attributeName="Name")
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
	@DynamoDBAttribute(attributeName="Description")
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	
	@DynamoDBRangeKey(attributeName="CreationDate")
	public long getCreationDate() {
		return CreationDate;
	}
	public void setCreationDate(long creationDate) {
		CreationDate = creationDate;
	}
	
	@DynamoDBAttribute(attributeName="Category")
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	
	@DynamoDBHashKey(attributeName="UID")
	public String getUID() {
		return UID;
	}
	public void setUID(String uID) {
		UID = uID;
	}
	
	@DynamoDBAttribute(attributeName="Tags")
	public String getTags() {
		return Tags;
	}
	public void setTags(String tags) {
		Tags = tags;
	}
	
	@DynamoDBAttribute(attributeName="Amount")
	public String getAmount() {
		return Amount;
	}
	public void setAmount(String amount) {
		Amount = amount;
	}
}
