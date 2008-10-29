package org.open18.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Length;

@Entity
@PrimaryKeyJoinColumn(name = "MEMBER_ID")
@Table(name = "GOLFER")
public class Golfer extends Member {

	private String lastName;
	private String firstName;
	private Gender gender;
	private Date dateJoined;
	private Date dateOfBirth;
	private String location;
	private String specialty;
	private String proStatus;
	private byte[] image;
	private String imageContentType;;
	private Set<Round> rounds = new HashSet<Round>(0);

	@Column(name = "LAST_NAME", nullable = false)
	@Length(min = 1, max = 50)
	@NotNull
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "FIRST_NAME", nullable = false)
	@Length(min = 1, max = 50)
	@NotNull
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Transient
	public String getName() {
		return firstName + ' ' + lastName;
	}

	@Transient
	public String getNameLastFirst() {
		return lastName + ", " + firstName;
	}

	@Enumerated(STRING)
	@Column(name = "GENDER", nullable = false)
	@NotNull
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "joined", nullable = false, updatable = false)
	@NotNull
	public Date getDateJoined() {
		return dateJoined;
	}
	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	@Temporal(DATE)
	@Column(name = "DOB")
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Transient
	public int getAge() {
		Calendar birthday = new GregorianCalendar();
		birthday.setTime(dateOfBirth);
		int by = birthday.get(Calendar.YEAR);
		int bm = birthday.get(Calendar.MONTH);
		int bd = birthday.get(Calendar.DATE);

		Calendar now = new GregorianCalendar();
		now.setTimeInMillis(System.currentTimeMillis());
		int y = now.get(Calendar.YEAR);
		int m = now.get(Calendar.MONTH);
		int d = now.get(Calendar.DATE);

		return y - by + (m > bm || (m == bm && d >= bd) ? 0 : -1);
	}

	@Transient
	// QUESTION: perhaps better to let Seam interpolate to make i18n compliant?
	public String getAgeFormatted() {
		int age = getAge();
		return String.format("%d year%s old", age, age > 1 ? "s" : "");
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
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "golfer")
	public Set<Round> getRounds() {
		return rounds;
	}

	public void setRounds(Set<Round> rounds) {
		this.rounds = rounds;
	}

}
