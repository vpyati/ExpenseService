package com.vikram.db;

public interface KeyValueStore {
	
	public String getValue(String key);
	
	public void setValue(String key, String value);
	
	public void deleteKey(String key);
	
}
