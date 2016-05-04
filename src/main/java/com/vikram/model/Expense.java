package com.vikram.model;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.util.StringUtils;
import com.vikram.category.CategoryTree;
import com.vikram.openidconnect.login.core.identity.Identity;

public class Expense {
	
	private String name;
	private String description;
	private Date creationDate;
	private String category;	
	private String uID;
	private String tags;
	private String categoryPath;
	private String amount;
	
	private String amazonRangeKey;
	
	private static Logger logger = LoggerFactory.getLogger(Expense.class);

	public String getAmazonRangeKey() {
		return amazonRangeKey;
	}

		
	public String getAmount() {
		return amount;
	}
	
	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCategoryPath() {
		return categoryPath;
	}

	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getuID() {
		return uID;
	}
	public void setuID(String uID) {
		this.uID = uID;
	}
	
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	
	public boolean isValid() {
		
		boolean basicCheck =  assertBasicChecks();
		if(!basicCheck){
			return false;
		}
		
		return isAmountValid();
	}


	private boolean isAmountValid() {
		
		try {
			Double.parseDouble(amount);
		} catch (NumberFormatException e) {
			logger.error("Invalid Amount");
			return false;
		}
		
		return true;
	}


	private boolean assertBasicChecks() {
		return !StringUtils.isNullOrEmpty(name)
				&& !StringUtils.isNullOrEmpty(category) && creationDate != null;
	}
	
	/**
	 * Convert the raw Expense to something which can be inserted into DB
	 */
	public void convert(Identity identity, CategoryTree tree){
		// Set UID
		this.setuID(identity.getEmailAddress());
		// Set description
		if(StringUtils.isNullOrEmpty(this.getDescription())){
			this.setDescription(this.getName());
		}

		//Set range key
		amazonRangeKey = String.valueOf(creationDate.getTime())+"$%^"+name+"$%^"+new Date().getTime();
		
		
	}

	
}
