package org.open18.action;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.RaiseEvent;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.bpm.BeginTask;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.faces.FacesMessages;

import org.open18.model.Golfer;

@Name("friendDecision")
@Scope(ScopeType.CONVERSATION)
@Restrict("#{s:hasRole('golfer')}")
public class FriendDecision implements Serializable {
	
	@In(create = true)
	protected FacesMessages facesMessages;
	
	@In
	protected EntityManager entityManager;
	
	@In("currentGolferManaged")
	protected Golfer currentGolfer;
	
	@In(scope = ScopeType.BUSINESS_PROCESS)
	protected String initiator;
	
	@Out(value = "message", required = false)
	protected Message verdict;
	
	@BeginTask
	public String review() {
		return "/reviewFriendRequest.xhtml";
	}
	
	@Transactional
	@EndTask(transition = "approve")
	@RaiseEvent("courier.messageWaiting")
	public void approve() {
		Golfer primary = (Golfer) entityManager.createQuery("select g from Golfer g where username = #{initiator}")
			.getSingleResult();
		//if (!entityManager.contains(currentGolfer)) {
		//    currentGolfer = entityManager.merge(currentGolfer);
		//}
		primary.addFriend(currentGolfer);
		currentGolfer.addFriend(primary);
		generateMessages(initiator, currentGolfer.getUsername(), true);
	}
	
	@EndTask(transition = "reject")
	@RaiseEvent("courier.messageWaiting")
	public void reject() {
		generateMessages(initiator, currentGolfer.getUsername(), false);
	}
	
	/**
	 * There are two ways to have #{courier.send} executed. It can either be defined
	 * in the business process, or it can be triggered by raising an event that
	 * it listens for. The problem with letting the business process kick off the other
	 * process is that the outjections on Courier#send() get attached to the AddFriend
	 * business process rather than the Courier process because of how Seam handles
	 * the variable assignments.
	 */
	private void generateMessages(String petitioner, String issuedBy, boolean approved) {
		verdict = new Message();
		verdict.setRecipient(petitioner);
		String resultStr = approved ? "accepted" : "turned down";
		verdict.setContent(issuedBy + " has " + resultStr + " your friend request.");
		if (approved) {
			//facesMessages.add("You are now friends with " + petitioner + ".");
			facesMessages.add("You and {0} are now friends.", petitioner);
		}
		else {
			facesMessages.add("You turned down the friend request from {0}.", petitioner);
		}
	}
}
