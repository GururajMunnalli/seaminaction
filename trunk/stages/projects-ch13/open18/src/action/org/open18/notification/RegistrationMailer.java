package org.open18.notification;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.faces.Renderer;

@Name("registrationMailer")
public class RegistrationMailer {
	
    @In private Renderer renderer;
	
	@Observer("golferRegistered")
    public void sendWelcomeEmail() {
        renderer.render("/email/welcome.xhtml");
    }
}
