package org.open18.golftips.test;

import org.testng.annotations.Test;
import org.jboss.seam.mock.SeamTest;
import javax.faces.application.FacesMessage;
import java.util.List;

public class GolfTipsTest extends SeamTest {

	@Test
	public void testAddTip() throws Exception {
		
		new NonFacesRequest("/golftips.xhtml") {
			protected void renderResponse() throws Exception {
				assert (Boolean) getValue("#{tips.rowCount == 0}");
			}
		}.run();
		
		new FacesRequest("/golftips.xhtml") {
			protected void updateModelValues() throws Exception {
				setValue("#{tip.author}", "Ben Hogan");
				setValue("#{tip.category}", "The Swing");
				setValue("#{tip.content}",
					"Good golf begins with a good grip.");
			}
			
			protected void invokeApplication() throws Exception {
				invokeMethod("#{tipAction.add(tip)}");
			}
			
			protected void renderResponse() throws Exception {
				assert (Boolean) getValue("#{tips.rowCount == 1}");
				List<FacesMessage> messages = (List<FacesMessage>) getValue("#{facesMessages.currentMessages}");
				assert messages.size() == 1;
				assert "Thanks for the tip, Ben Hogan!".equals(messages.get(0).getSummary());
			}
		}.run();
	}
}
