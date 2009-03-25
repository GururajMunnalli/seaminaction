/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.beans;

public class CategoryValuePair implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String category;
	private Integer value;

	public CategoryValuePair() {

	}

	public CategoryValuePair(String category, Integer value) {
		super();
		this.category = category;
		this.value = value;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
}
