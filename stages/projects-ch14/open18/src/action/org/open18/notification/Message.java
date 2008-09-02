package org.open18.notification;

import org.jboss.seam.annotations.Name;

@Name("message")
public class Message {
	protected String recipient;
	protected String content;
	protected boolean broadcast;

	public String getRecipient() { return recipient; }

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getContent() { return content; }

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isBroadcast() { return broadcast; }

	public void setBroadcast(boolean broadcast) {
		this.broadcast = broadcast;
	}
}
