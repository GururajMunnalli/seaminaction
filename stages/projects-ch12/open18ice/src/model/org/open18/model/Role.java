package org.open18.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.jboss.seam.annotations.Name;

/**
 * Represents a security role.
 * 
 * @author Dan Allen
 */
@Entity
@Table(name = "ROLE", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Role implements Serializable {
	
	private Integer id;
	private String name;

	@Id
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
