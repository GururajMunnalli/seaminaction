package org.open18.action;

/**
 * A simple wrapper object holding an initiator and a prospective friend
 * for use in the Add Friend business process.
 */
public class FriendRequest {
	private String initiator;
	private String prospectiveFriend;

	public FriendRequest() {}
	
	public FriendRequest(String initiator, String prospectiveFriend) {
		this.initiator = initiator;
		this.prospectiveFriend = prospectiveFriend;
	}
	
	public String getInitiator() {
		return initiator;
	}

	public void setInitiator(String initiator) {
		this.initiator = initiator;
	}

	public String getProspectiveFriend() {
		return prospectiveFriend;
	}

	public void setProspectiveFriend(String prospectiveFriend) {
		this.prospectiveFriend = prospectiveFriend;
	}
}
