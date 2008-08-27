package org.open18.notification;

import org.hibernate.validator.Email;
import org.jboss.seam.annotations.Name;

@Name("recipient")
public class Recipient {
	private String name;
	private String emailAddress;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Email
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public void reset() {
		this.name = null;
		this.emailAddress = null;
	}
	
}
