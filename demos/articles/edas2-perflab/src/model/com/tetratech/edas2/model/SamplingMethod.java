/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.model;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import org.hibernate.validator.Length;

@Embeddable
public class SamplingMethod {
	private String id;
	private String context;
	private String name;
	private String description;
	private String type;

	@Length(max = 20)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Length(max = 120)
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Length(max = 120)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(max = 500)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Length(max = 25)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
