package org.open18.test;

import java.util.List;

import javax.faces.application.FacesMessage;

import org.jboss.seam.Component;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.mock.SeamTest;
import org.open18.model.Golfer;
import org.testng.annotations.Test;

public class RegisterGolferIntegrationTest extends SeamTest {

	@Test(groups = {"level.integration", "speed.slow"})
	public void registerValidGolfer() throws Exception {
		
		new FacesRequest("/register.xhtml") {

			@Override
			protected void updateModelValues() {
				Golfer golfer = (Golfer) Component.getInstance("newGolfer");
				golfer.setFirstName("Tommy");
				golfer.setLastName("Twoputt");
				golfer.setUsername("twoputt");
				golfer.setEmailAddress("twoputt@open18.org");
				setValue("#{passwordBean.password}", "ilovegolf");
				setValue("#{passwordBean.confirm}", "ilovegolf");
			}

			@Override
			protected void invokeApplication() {
				Object outcome =
					invokeMethod("#{registerAction.register}");
				assert outcome != null && outcome.equals("success");
			}

			@Override
			protected void renderResponse() throws Exception {
				List<FacesMessage> messages =
					FacesMessages.instance().getCurrentGlobalMessages();
				assert messages.size() == 1;
				assert messages.get(0).getSeverity().equals(FacesMessage.SEVERITY_INFO);
				assert messages.get(0).getSummary().contains("Tommy Twoputt");
			}
		}.run();
	}
}
