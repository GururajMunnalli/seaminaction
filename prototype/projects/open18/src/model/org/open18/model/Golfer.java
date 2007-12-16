package org.open18.model;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.validator.NotNull;
import org.hibernate.validator.Length;

@Entity
@PrimaryKeyJoinColumn(name="MEMBER_ID")
@Table(name = "GOLFER")
public class Golfer extends Member {
	private String name;
	private Gender gender;
	private Date dateJoined;
	private Date dateOfBirth;
	private String location;
	private String specialty;
	private String proStatus;

	@Column(name = "name", nullable = false)
	@Length(max = 40)
	@NotNull
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Enumerated(STRING)
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Temporal(TIMESTAMP)
	@Column(name = "joined", nullable = false)
	@NotNull
	public Date getDateJoined() {
		return dateJoined;
	}
	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	@Temporal(DATE)
	@Column(name = "dob")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	@Column(name = "pro_status")
	public String getProStatus() {
		return proStatus;
	}
	public void setProStatus(String proStatus) {
		this.proStatus = proStatus;
	}
}
