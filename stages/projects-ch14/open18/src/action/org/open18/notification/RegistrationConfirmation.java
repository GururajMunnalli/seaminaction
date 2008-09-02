package org.open18.notification;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.bpm.BeginTask;
import org.jboss.seam.annotations.bpm.CreateProcess;
import org.jboss.seam.annotations.bpm.EndTask;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.faces.FacesMessages;

@Name("registrationConfirmation")
public class RegistrationConfirmation {
	@In protected FacesMessages facesMessages;

	@In(required = false, scope = ScopeType.BUSINESS_PROCESS)
	@Out(required = false, scope = ScopeType.BUSINESS_PROCESS)
	protected String confirmationCode;
	
	@RequestParameter("code") private String confirmationCodeVerify;
	
	@CreateProcess(definition = "Confirm Registration")
	@Observer("registrationRequest")
	public void initiateConfirmation() {
		confirmationCode = "hole-in-one";
	}
	
	@BeginTask @EndTask(transition = "confirmed")
	public String confirm() {
		if (confirmationCodeVerify != null &&
			confirmationCodeVerify.equals(confirmationCode)) {
			facesMessages.add("Registration confirmed!");
			return "confirmed";
		}
		else {
			facesMessages.add("Invalid confirmation code.");
			return null;
		}
	}
}
