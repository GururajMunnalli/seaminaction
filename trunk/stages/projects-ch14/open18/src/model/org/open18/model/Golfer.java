package org.open18.model;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;

import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Length;
import org.jboss.seam.ui.graphicImage.Image;

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
	private String imageContentType;
	private Set<Round> rounds = new HashSet<Round>(0);
	private Set<CourseComment> courseComments = new HashSet<CourseComment>(0);
	private Set<Favorite> favorites = new HashSet<Favorite>(0);
	private Set<FriendLink> friendLinks = new HashSet<FriendLink>();

	@Column(name = "last_name", nullable = false)
	@NotNull
	@Length(max = 40)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "first_name", nullable = false)
	@NotNull
	@Length(max = 40)
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

	@Enumerated(EnumType.STRING)
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

	@Temporal(TemporalType.DATE)
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "golfer")
	public Set<Round> getRounds() {
		return rounds;
	}

	public void setRounds(Set<Round> rounds) {
		this.rounds = rounds;
	}

	@Column(name = "image_data")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Column(name = "image_content_type")
	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "golfer")
	public Set<CourseComment> getCourseComments() {
		return courseComments;
	}

	public void setCourseComments(Set<CourseComment> courseComments) {
		this.courseComments = courseComments;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "golfer")
	public Set<Favorite> getFavorites() {
		return favorites;
	}

	public void setFavorites(Set<Favorite> favorites) {
		this.favorites = favorites;
	}

	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "golfer")
	public Set<FriendLink> getFriendLinks() { return this.friendLinks; }

	public void setFriendLinks(Set<FriendLink> friendLinks) {
		this.friendLinks = friendLinks;
	}

	public void addFriend(Golfer friend) {
		FriendLink link = new FriendLink();
		link.setGolfer(this);
		link.setFriend(friend);
		friendLinks.add(link);
	}
	
	// NOTE: the only downside here is that we have a hard dependency to jboss-seam-ui.jar
	@Transient
	public String getImageExtension() {
		Image.Type type = Image.Type.getTypeByMimeType(imageContentType);
		return type != null ? type.getExtension() : null;
	}
	
	public double getAverageScore(long par) {
		double total = 0;
		int num = 0;

		for (Round round : getRounds()) {
			for (Score score : round.getScores()) {
				if (score.getHole().getPar(round.getGolfer().getGender()) == par) {
					total += score.getStrokes();
					num++;
				}
			}
		}

		return num > 0 ? total / num : 0;
	}
	
	public boolean is(Golfer other) {
		if (getUsername() == null || other == null) {
			return false;
		}
		return getUsername().equals(other.getUsername());
	}
	
	public boolean isNot(Golfer other) {
		return !is(other);
	}
	
	public boolean isFriendOf(Golfer other) {
		if (other == null) {
			return false;
		}
		
		for (FriendLink link : friendLinks) {
			// NOTE: we are cheating a bit by comparing ids to avoid unnecessary lazy loading
			if (link.getFriend().getId().equals(other.getId())) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isNotFriendOf(Golfer other) {
		return !isFriendOf(other);
	}
}
