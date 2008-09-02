package org.open18.action;

import java.util.List;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.security.Identity;
import org.jbpm.JbpmContext;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.open18.model.Golfer;

@Name("friendRequester")
public class FriendRequester {

	@In protected FacesMessages facesMessages;

	@In protected JbpmContext jbpmContext;
	
	@In protected Golfer friend;

	@In protected Identity identity;

	@Out(scope = ScopeType.BUSINESS_PROCESS)
	protected String initiator;
	
	@Out(scope = ScopeType.BUSINESS_PROCESS)
	protected String prospectiveFriend;

	// NOTE: Due to a bug in Seam 2.0, it's not possible to reference a top-level
	// context variable in the business process definition, so we outject a wrapper object
	@Out protected FriendRequest friendRequest;

	@Out(value = "personalMessage", scope = ScopeType.BUSINESS_PROCESS, required = false)
	protected String message;

	@BypassInterceptors
	public String getMessage() { return this.message; }

	@BypassInterceptors
	public void setMessage(String message) { this.message = message; }

	@CreateProcess(definition = "Add Friend")
	public Boolean addFriend() {
		initiator = identity.getUsername();
		prospectiveFriend = friend.getUsername();
		friendRequest = new FriendRequest(initiator, prospectiveFriend);
		if (isDuplicateRequest()) {
			facesMessages.add(
				"You already have a friend request issued to #{prospectiveFriend}.");
			return null;
		}
		else {
			facesMessages.add(
				"A friend request has been sent to #{prospectiveFriend} for approval.");
			return true;
		}
	}
	
	protected boolean isDuplicateRequest() {
		List<TaskInstance> tasks = jbpmContext.getTaskList(friend.getUsername());

		for (TaskInstance task : tasks) {
			if (task.getName().equals("confirm friend") &&
				task.getProcessInstance().getProcessDefinition().getName().equals("Add Friend") &&
				task.hasVariable("initiator") &&
				task.getVariable("initiator").equals(initiator)) {
				return true;
			}
		}

		return false;
	}
}
