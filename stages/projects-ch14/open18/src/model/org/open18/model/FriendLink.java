package org.open18.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="friend_link", uniqueConstraints =
	@UniqueConstraint(columnNames = {"golfer_id", "friend_id"}))
public class FriendLink implements Serializable {
	private Long id;
	private Golfer golfer;
	private Golfer friend;

	@Id
	@GeneratedValue
	public Long getId() { return this.id; }
	public void setId(Long id) { this.id = id; }

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="golfer_id")
	public Golfer getGolfer() { return this.golfer; }
	public void setGolfer(Golfer golfer) { this.golfer = golfer; }

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="friend_id")
	public Golfer getFriend() { return this.friend; }
	public void setFriend(Golfer friend) { this.friend = friend; }
}
