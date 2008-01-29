package org.open18.action;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.faces.FacesMessages;
import org.jbpm.JbpmContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

import org.open18.model.Golfer;

/**
 * This class is used for adding and removing friends. Adding a friend
 * must be approved by the prospective friend. The "Add Friend" business
 * process manages the friend request confirmation.
 *
 * TODO: I need to clean up the existing friend/duplicate request logic.
 */
@Name("friendAction")
@Restrict("#{s:hasRole('golfer')}")
public class FriendAction {
	
	private static final String ADD_FRIEND_PROCESS_NAME = "Add Friend";

	private static final String DECISION_TASK_NAME = "confirm friend";
	
	private static final String INITIATOR_VARIABLE_NAME = "initiator";
	
	@In(create = true)
	protected FacesMessages facesMessages;
	
	@In
	protected EntityManager entityManager;
	
	@In
	protected JbpmContext jbpmContext;
	
	@Out(scope = ScopeType.BUSINESS_PROCESS)
	protected String initiator;
	
	@Out(scope = ScopeType.BUSINESS_PROCESS, required = false)
	protected String personalMessage;
	
	@Out
	protected String prospect;
	
	@In("currentGolferManaged")
	protected Golfer currentGolfer;
	
	@In
	protected Golfer friend;
	
	@Out
	protected boolean duplicateCheckPerformed = false;
	
	@BypassInterceptors
	public String getPersonalMessage() {
		return personalMessage;
	}

	@BypassInterceptors
	public void setPersonalMessage(String message) {
		this.personalMessage = message;
	}
	
	@Transactional
	public boolean checkForDuplicateRequest() {
		initActors();
		return isExistingFriendOrOutstandingRequest();
	}
	
	@CreateProcess(definition = ADD_FRIEND_PROCESS_NAME)
	@Transactional
	public Boolean addFriend() {
		initActors();
		if (isExistingFriendOrOutstandingRequest()) {
			// INFO: a null return value will prevent business process from starting
			return null;
		}
		else {
			facesMessages.add("A friend request has been sent to #{prospect} for approval.");
			return true;
		}
	}
	
	@Transactional
	public Boolean removeFriend() {
		initActors();
		//if (!entityManager.contains(currentGolfer)) {
		//    currentGolfer = entityManager.merge(currentGolfer);
		//}

		if (!currentGolfer.hasFriend(friend)) {
			facesMessages.add(FacesMessage.SEVERITY_WARN, "#{prospect} is not your friend.");
			return null;
		}
		
		// explicit remove to delete orphans
		entityManager.remove(currentGolfer.removeFriend(friend));
		entityManager.remove(friend.removeFriend(currentGolfer));
		facesMessages.add("You and #{prospect} are no longer friends.");
		return true;
	}
	
	protected void initActors() {
		initiator = currentGolfer.getUsername();
		prospect = friend.getUsername();
	}

	protected boolean isExistingFriendOrOutstandingRequest() {
		duplicateCheckPerformed = true;
		if (currentGolfer.hasFriend(friend)) {
			facesMessages.add("You are already friends with #{prospect}.");
			return true;
		}
		if (isOutstandingRequest()) {
			facesMessages.add("You already have an outstanding friend request with #{prospect}.");
			return true;
		}

		return false;
	}
	
	protected boolean isOutstandingRequest() {
		@SuppressWarnings("unchecked")
		List<TaskInstance> taskInstances = jbpmContext.getTaskList(prospect);
		for (TaskInstance taskInstance : taskInstances) {
			if (taskInstance.getName().equals(DECISION_TASK_NAME) &&
				taskInstance.getProcessInstance().getProcessDefinition().getName().equals(ADD_FRIEND_PROCESS_NAME) &&
				taskInstance.hasVariable(INITIATOR_VARIABLE_NAME) &&
				taskInstance.getVariable(INITIATOR_VARIABLE_NAME).equals(initiator)) {
				return true;
			}
		}
		
		return false;
	}
}
