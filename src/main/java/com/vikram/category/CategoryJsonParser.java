package com.vikram.category;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class CategoryJsonParser {
	
	public static Category parseCategoryJson(String categoryJson){
		
		JSONObject obj = (JSONObject) JSONValue.parse(categoryJson);
		
		return parse(obj);		
	}

	private static Category parse(JSONObject obj) {
		
		String catId =(String)obj.get("catId");
		String catName = (String)obj.get("catName");
		JSONArray children = (JSONArray)obj.get("children");
		
		Category category = new Category(Integer.parseInt(catId), catName);
		
		for(int i=0;i<children.size();i++){
			category.addChild(parse((JSONObject) children.get(i)));			
		}
		
		return category;
	}

}
