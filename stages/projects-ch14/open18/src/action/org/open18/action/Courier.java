package org.open18.action;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.bpm.BeginTask;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.security.Identity;
import org.open18.notification.Message;

@Name("courier")
public class Courier {
    @In protected Identity identity;
	@In protected FacesMessages facesMessages;
	@In(required = false) protected Message message;
	
    @Out(scope = ScopeType.BUSINESS_PROCESS, required = false)
    protected String sender;

    @CreateProcess(definition = "Courier")
    public void send() {
		if (!validate(message)) {
			return;
		}
        sender = identity.getUsername();
		if (message.isBroadcast()) {
			facesMessages.add("Your message has been broadcast to the #{message.recipient} group.");
		}
		else {
			facesMessages.add("Your message has been sent to #{message.recipient}.");
		}
	}
	
	@BeginTask @EndTask(transition = "acknowledge")
	public void acknowledge() {}

	private boolean validate(Message message) {
		return message != null &&
			message.getRecipient() != null &&
			message.getRecipient().trim().length() > 0 &&
			message.getContent() != null &&
			message.getContent().trim().length() > 0;
	}
}
