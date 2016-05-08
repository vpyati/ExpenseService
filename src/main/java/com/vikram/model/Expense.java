package com.vikram.model;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.util.StringUtils;
import com.vikram.category.CategoryTree;
import com.vikram.db.ExpenseDo;
import com.vikram.openidconnect.login.core.identity.Identity;

public class Expense {
	
	private String categoryPath;
	
	private ExpenseDo dataObj;
	
	private static Logger logger = LoggerFactory.getLogger(Expense.class);
	
	public Expense(){
		dataObj = new ExpenseDo();
	}
	
	public Expense(ExpenseDo dataObj){
		this.dataObj = dataObj;
	}

	public String getAmount() {
		return this.dataObj.getAmount();
	}
	
	public void setAmount(String amount) {
		this.dataObj.setAmount(amount);
	}

	public String getCategoryPath() {
		return categoryPath;
	}

	public void setCategoryPath(String categoryPath) {
		this.categoryPath = categoryPath;
	}

	public String getName() {
		return this.dataObj.getName();
	}
	public void setName(String name) {
		this.dataObj.setName(name);
	}
	
	public String getDescription() {
		return this.dataObj.getDescription();
	}
	public void setDescription(String description) {
		this.dataObj.setDescription(description);
	}
	
	public Date getCreationDate() {
		if(this.dataObj.getCreationDate()==0) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(this.dataObj.getCreationDate());		
		return cal.getTime();
	}
	public void setCreationDate(Date creationDate) {
		if(creationDate == null) return;
		this.dataObj.setCreationDate(creationDate.getTime()+getMillisecsElapsedInDay());
	}
	
	public String getCategory() {
		return this.dataObj.getCategory();
	}
	public void setCategory(String category) {
		this.dataObj.setCategory(category);
	}
	
	public String getuID() {
		return this.dataObj.getUID();
	}
	public void setuID(String uID) {
		this.dataObj.setUID(uID);
	}
	
	public String getTags() {
		return this.dataObj.getTags();
	}
	public void setTags(String tags) {
		this.dataObj.setTags(tags);
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
			Double.parseDouble(this.getAmount());
		} catch (NumberFormatException e) {
			logger.error("Invalid Amount");
			return false;
		}
		
		return true;
	}


	private boolean assertBasicChecks() {
		return !StringUtils.isNullOrEmpty(this.getName())
				&& !StringUtils.isNullOrEmpty(this.getCategory()) && this.getCreationDate() != null;
	}
	
	/**
	 * Convert the raw Expense to something which can be inserted into DB
	 */
	public void transformForInsert(Identity identity, CategoryTree tree){
		// Set UID
		this.setuID(identity.getEmailAddress());
		// Set description
		if(StringUtils.isNullOrEmpty(this.getDescription())){
			this.setDescription(this.getName());
		}
	}

	private long getMillisecsElapsedInDay(){
		Calendar cal = Calendar.getInstance();
		long millisecForDay = cal.get(Calendar.HOUR_OF_DAY)*60*60*1000;
		millisecForDay+=cal.get(Calendar.MINUTE)*60*1000;
		millisecForDay+=cal.get(Calendar.SECOND)*1000;
		millisecForDay+=cal.get(Calendar.MILLISECOND);
		return millisecForDay;

	}
	
	public ExpenseDo getDataObject(){
		return dataObj;
	}
}
