package org.preflight.model;

import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "passport", uniqueConstraints = @UniqueConstraint(columnNames = "number"))
public class Passport implements Serializable {

	private static final long serialVersionUID = 4972489750943615755L;

	private long id;
//	private Passenger owner;
	private String number;
	private String country;
	private Date expirationDate;

	public Passport() {
		super();
	}
	
	public void setId(long id) {
		this.id = id;
	}

	@Id
	@Column(name = "owner_id")
	public long getId() {
		return id;
	}
	
	// Causing a NullPointerException
//	@OneToOne(mappedBy = "passport")
//	public Passenger getOwner() {
//		return owner;
//	}
//
//	public void setOwner(Passenger owner) {
//		this.owner = owner;
//	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Temporal(DATE)
	@Column(name="expiration_date")
	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

}
