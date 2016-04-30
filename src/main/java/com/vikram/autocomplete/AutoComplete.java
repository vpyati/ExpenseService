package com.vikram.autocomplete;

import java.util.ArrayList;
import java.util.List;

public class AutoComplete {
	
	private String dictionaryName;
	private TrieNode root;
	
	public AutoComplete(String dictionaryName){
		this.dictionaryName = dictionaryName;
		this.root = new TrieNode(false,true);
	}
	
	
	public void addWord(String word){
		
		TrieNode currentNode = root;
		
		char[] ch = word.toCharArray();
		for(char c:ch){
			
			TrieNode node = currentNode.getNode(c);
			if(node == null){
				node = new TrieNode(false);
				currentNode.setNode(c, node);
			}
			currentNode = node;
		}
		
		currentNode.setWord(true);
		
	}
	
	
	public List<String> search(String prefix){
		
		char[] ch = prefix.toCharArray();
		
		TrieNode prefixNode = root;
		
		for(char c:ch){
			if(prefixNode == null)break;			
			prefixNode = prefixNode.getNode(c);
		}
		
		if(prefixNode == null){
			return new ArrayList<String>();
		}
		
		return prefixNode.search(prefix);		
	}

	public String toString(){
		return "Dictionary for "+dictionaryName;
	}
	
	public static void main(String[] args){
		
		AutoComplete ac = new AutoComplete("Test");
		ac.addWord("to");
		ac.addWord("today");
		ac.addWord("tonight");
		ac.addWord("tile");
		ac.addWord("Vikram");
		ac.addWord("Vintage");
		ac.addWord("Actor");
		ac.addWord("Alien");
		
		System.out.println(ac.search("A"));
		
	}
	
}
