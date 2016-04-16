package com.vikram.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestContext {

	private static ThreadLocal<Map<RequestKey,Object>> cache = new ThreadLocal<Map<RequestKey,Object>>();
	
	private static RequestContext instance = new RequestContext();
	
	private static Logger logger = LoggerFactory.getLogger(RequestContext.class);
	
	public static RequestContext get(){
		return instance;
	}
	
	
	@SuppressWarnings("unchecked")
	public <T> T getValue(RequestKey key){
		
		Map<RequestKey, Object> map = cache.get();
		if(map == null){
			return null;
		}
		T value = null;
		try {
			value = (T) map.get(key);
		} catch (Exception e) {
			logger.error("Unable to fetch key from TLC", e);
		}
		return value;			
	}

	
	public <T> void setValue(RequestKey key, T t ){
		Map<RequestKey, Object> map = cache.get();
		if(map == null) map = new HashMap<RequestContext.RequestKey, Object>();
		map.put(key, t);
		cache.set(map);
	}
	
	
	public enum RequestKey{
		IDENTITY,
		ENVIRONMENT
	}
}
