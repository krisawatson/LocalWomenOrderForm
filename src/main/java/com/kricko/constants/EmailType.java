package com.kricko.constants;

public enum EmailType {
	BUSINESS("business"),
	USER("user"),
	PUBLICATION("publication");
	
	private final String value;
	
	EmailType(String value) { 
		this.value = value; 
	}
    
	public String getValue() { 
    	return value; 
    }
}
