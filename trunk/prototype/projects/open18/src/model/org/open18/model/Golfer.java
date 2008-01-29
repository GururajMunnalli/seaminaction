package org.open18.model;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Entity
@PrimaryKeyJoinColumn(name="MEMBER_ID")
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
	private String imageContentType;
	private Set<GolferFriend> friends = new HashSet<GolferFriend>();
	private Set<Round> rounds = new HashSet<Round>(0);
	private Set<Review> reviews = new HashSet<Review>(0);

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

	@Temporal(TIMESTAMP)
	@Column(name = "JOINED", nullable = false)
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

	@Column(name = "PRO_STATUS")
	public String getProStatus() {
		return proStatus;
	}
	public void setProStatus(String proStatus) {
		this.proStatus = proStatus;
	}

	@Lob
	@Column(name = "IMAGE_DATA")
	@Basic(fetch = FetchType.LAZY)
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Column(name = "IMAGE_CONTENT_TYPE")
	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "golfer")
	public Set<GolferFriend> getFriends() {
		return friends;
	}

	public void setFriends(Set<GolferFriend> friends) {
		this.friends = friends;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "golfer")
	public Set<Round> getRounds() {
		return rounds;
	}

	public void setRounds(Set<Round> rounds) {
		this.rounds = rounds;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reviewer")
	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	/**
	 * We are being cautious here and comparing usernames rather than object identities.
	 * However, you should always try to perform operations with entity instances managed
	 * by the same persistence context.
	 */
	public boolean hasFriend(Golfer friend) {
		for (GolferFriend golferFriend : friends) {
			if (golferFriend.getFriend().getUsername().equals(friend.getUsername())) {
				return true;
			}
		}
		
		return false;
	}
	
	public void addFriend(Golfer friend) {
		GolferFriend relationship = new GolferFriend();
		relationship.setGolfer(this);
		relationship.setFriend(friend);
		friends.add(relationship);
	}
	
	/**
	 * We are being cautious here and comparing usernames rather than object identities.
	 * However, you should always try to perform operations with entity instances managed
	 * by the same persistence context.
	 */
	public GolferFriend removeFriend(Golfer friend) {
		GolferFriend relationship = null;
		for (GolferFriend candidate : friends) {
			if (candidate.getFriend().getUsername().equals(friend.getUsername())) {
				relationship = candidate;
				break;
			}
		}
		
		if (relationship != null) {
			friends.remove(relationship);
		}
		
		return relationship;
	}
}
