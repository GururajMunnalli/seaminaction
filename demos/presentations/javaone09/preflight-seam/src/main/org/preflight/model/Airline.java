package org.preflight.model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

@Entity
@Table(name = "airline", uniqueConstraints = 
	@UniqueConstraint(columnNames = "code")
)
public class Airline implements Serializable {

	private static final long serialVersionUID = 3536152523102054766L;
	
	private long id;
	private String code;
	
	private String name;

	public Airline() {
		super();
	}

	@Id @GeneratedValue
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
