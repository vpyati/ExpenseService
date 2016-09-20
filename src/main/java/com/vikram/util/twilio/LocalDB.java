package com.vikram.util.twilio;

import java.util.HashMap;
import java.util.Map;

import com.vikram.util.UserDetailBean;

public class LocalDB {
	
	private static LocalDB instance = new LocalDB();
	
	private LocalDB(){
		
	}
	

	private Map<String, UserDetailBean> cache = new HashMap<String, UserDetailBean>();
	
	
	public static LocalDB getInstance(){
		return instance;
	}
	
	
	public synchronized boolean addUser(UserDetailBean user){		
		cache.put(user.getPhoneNo(), user);	
		return true;
	}
	
	public synchronized UserDetailBean getUser(String phone){
		return cache.get(phone);
	}
	
}
