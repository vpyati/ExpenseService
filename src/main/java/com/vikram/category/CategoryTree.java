package com.vikram.category;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

public class CategoryTree {
		
	private Category ROOT;
	
	@Autowired
	private Resource categoryJson;
	
	@PostConstruct
	public void load() throws IOException{		
		byte[] encoded = Files.readAllBytes(Paths.get(categoryJson.getFile().getPath()));
		ROOT = CategoryJsonParser.parseCategoryJson(new String(encoded));		
	}
	
	
	public Category getRootCategory(){
		return ROOT;
	}
	
	public Category findByCatId(int categoryId){
		return null;
	}
	
	public Category findByCatName(String categoryName){
		return null;
	}
}
