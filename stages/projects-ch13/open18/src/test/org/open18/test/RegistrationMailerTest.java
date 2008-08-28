package org.open18.test;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.jboss.seam.mock.SeamTest;
import org.open18.model.Gender;
import org.open18.model.Golfer;
import org.testng.annotations.Test;
import org.jboss.seam.Component;

public class RegistrationMailerTest extends SeamTest {
	@Test
	public void sendWelcomeEmail() throws Exception {
		new FacesRequest("/register.xhtml") {

			@Override
			protected void updateModelValues() throws Exception {
				Golfer golfer = (Golfer) Component.getInstance("newGolfer");
				golfer.setFirstName("Tommy");
				golfer.setLastName("Twoputt");
				golfer.setGender(Gender.MALE);
				golfer.setUsername("twoputt");
				golfer.setEmailAddress("twoputt@open18.org");
				setValue("#{newGolfer}", golfer);
			}

			@Override
			protected void invokeApplication() throws Exception {
				MimeMessage msg = getRenderedMailMessage("/email/welcome.xhtml");
				
				assert msg.getAllRecipients().length == 1;
				InternetAddress recipient = (InternetAddress) msg.getAllRecipients()[0];
				assert recipient.getPersonal().equals("Tommy Twoputt");
				assert recipient.getAddress().equals("twoputt@open18.org");
				
				assert msg.getFrom().length == 1;
				InternetAddress from = (InternetAddress) msg.getFrom()[0];
				assert from.getPersonal().equals("Open 18");
				assert from.getAddress().equals(getValue("#{messages['memberservices.from.address']}"));
				
				assert msg.getReplyTo().length == 1;
				InternetAddress replyTo = (InternetAddress) msg.getReplyTo()[0];
				assert replyTo.getAddress().equals(getValue("#{messages['noreply.address']}"));
				
				assert msg.getSubject() != null && msg.getSubject().equals("Open 18 - Registration Information");
				// TODO: verify contents of message (this is complicated)
			}
			
		}.run();
	}
}
