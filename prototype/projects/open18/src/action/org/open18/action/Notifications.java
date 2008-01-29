package org.open18.action;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Renderer;

@Name("notifications")
public class Notifications {
	@In
	private Renderer renderer;

	@In
	private FacesMessages facesMessages;

	@In(create = true)
	private Scorecard scorecard;

	public void sendScorecard() {
		scorecard.load();
		renderer.render("/email/scorecard-notification.xhtml");
		facesMessages.add("The scorecard has been sent to #{recipient.emailAddress}.");
	}
}
