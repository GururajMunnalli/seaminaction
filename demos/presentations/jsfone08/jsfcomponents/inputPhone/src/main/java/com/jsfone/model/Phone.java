package com.jsfone.model;

public class Phone {

	private String areaCode;
	private String prefix;
	private String line;

	public Phone() {
	}

	public Phone(String areaCode, String prefix, String line) {
		this.areaCode = areaCode;
		this.prefix = prefix;
		this.line = line;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getLine() {
		return line;
	}

	public String getPrefix() {
		return prefix;
	}
	
	public void reset() {
		this.areaCode = null;
		this.prefix = null;
		this.line = null;
	}
	
	@Override
	public String toString() {
		return this.areaCode + this.prefix + this.line;
	}
}
