package org.preflight.model;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

@Entity
@Table(name = "airport", uniqueConstraints = 
	@UniqueConstraint(columnNames = "code")
)
public class Airport implements Serializable {

	private static final long serialVersionUID = 6812515393129307176L;

	private long id;
	private String name;
	private String code;
	private Address address;

	public Airport() {
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
