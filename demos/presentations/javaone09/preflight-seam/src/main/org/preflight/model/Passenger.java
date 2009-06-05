package org.preflight.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.TemporalType.DATE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity
@Table(name = "passenger")
public class Passenger implements Serializable {

	private static final long serialVersionUID = -2202892886455081868L;

	private long id;
	private String lastName;
	private String firstName;
	private Address address;
	private Date birthDate;
	private Passport passport;

	public Passenger() {
		super();
	}

	@Id
	@GeneratedValue
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "last_name")
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "first_name")
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Transient
	public String getName() {
		return getFirstName() + " " + getLastName();
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Column(name = "birth_date")
	@Temporal(DATE)
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date date) {
		this.birthDate = date;
	}
	
	@OneToOne(cascade = ALL)
	@PrimaryKeyJoinColumn
	public Passport getPassport() {
		return passport;
	}

	public void setPassport(Passport passport) {
		this.passport = passport;
	}

}
