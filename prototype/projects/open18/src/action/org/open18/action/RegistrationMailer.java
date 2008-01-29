package org.open18.action;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.Renderer;

@Name("registrationMailer")
@AutoCreate
public class RegistrationMailer {
	@In
	private Renderer renderer;

	public void sendWelcomeEmail() {
		renderer.render("/email/welcome.xhtml");	
	}
}
