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
				assert (Boolean) getValue("#{tips.rowCount eq 0}");

				//Object value = getValue("#{tips}");
				//assert value != null && value instanceof DataModel;
				//DataModel tips = (DataModel) value;
				//assert tips.getRowCount() == 0;
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
				assert (Boolean) getValue("#{tips.rowCount eq 1}");
				List<FacesMessage> messages = (List<FacesMessage>) getValue("#{facesMessages.currentMessages}");
				assert messages.size() == 1;
				assert "Thanks for the tip, Ben Hogan!".equals(messages.get(0).getSummary());

				//Object value = getValue("#{tips}");
				//assert value != null && value instanceof DataModel;
				//DataModel tips = (DataModel) value;
				//assert tips.getRowCount() == 1;
				//List<FacesMessage> messages =
				//	FacesMessages.instance().getCurrentMessages();
				//assert messages.size() == 1;
				//assert messages.get(0).getSummary()
				//	.equals( "Thanks for the tip, Ben Hogan!" );
			}
		}.run();
	}
}
