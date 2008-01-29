package org.open18.action;

import org.jboss.seam.annotations.Name;

@Name("message")
public class Message {
	protected String recipient;

	protected String content;

	public String getRecipient() {
		return recipient;
	}
	
	public String getContent() {
		return content;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public void setContent(String message) {
		this.content = message;
	}
}
