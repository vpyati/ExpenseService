package com.vikram.autocomplete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrieNode {
	
	private boolean isWord;
	private Map<Character, TrieNode> references = new HashMap<Character, TrieNode>();
	private boolean isRoot;

	public static TrieNode ROOT = new TrieNode(false);
	
	public TrieNode(boolean isWord){
		this(isWord,false);
	}
	
	public TrieNode(boolean isWord, boolean isRoot){
		this.isWord = isWord;
		this.isRoot = isRoot;
	}
		
	public TrieNode getNode(char c){
		return references.get(c);
	}
	
	public void setNode(char c, TrieNode node){
		references.put(c, node);
	}
	
	public boolean isWord(){
		return isWord;
	}
	
	public void setWord(boolean isWord){
		this.isWord = isWord;
	}
	
	public boolean isRoot(){
		return isRoot;
	}
	
	public List<String> search(String prefix){
		
		List<String> result = new ArrayList<String>();
		
		if(this.isWord()){
			result.add(prefix);
		}
		
		for(Map.Entry<Character, TrieNode> entry:references.entrySet()){
			
			Character c = entry.getKey();
			TrieNode childNode = entry.getValue();
			if(childNode!=null){
				result.addAll(childNode.search(prefix+c));						
			}
		}
		
		return result;
	}
}
