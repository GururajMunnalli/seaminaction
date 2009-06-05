package org.preflight.model;

public enum FareClass {
	FIRST("first"),
	BUSINESS("business"),
	ECONOMY("economy");
	
	private String key;
	
	private FareClass(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
}
