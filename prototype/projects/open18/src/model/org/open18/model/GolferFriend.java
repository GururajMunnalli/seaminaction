package org.open18.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="GOLFER_FRIEND", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"golfer_id", "friend_id"})
})
public class GolferFriend {
	private Long id;
	private Golfer golfer;
	private Golfer friend;
	private String details;
	private boolean confirmed;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="golfer_id")
	public Golfer getGolfer() {
		return golfer;
	}
	public void setGolfer(Golfer golfer) {
		this.golfer = golfer;
	}

	@ManyToOne
	@JoinColumn(name="friend_id")
	public Golfer getFriend() {
		return friend;
	}
	public void setFriend(Golfer friend) {
		this.friend = friend;
	}

	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}

	public boolean isConfirmed() {
		return confirmed;
	}
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
}
