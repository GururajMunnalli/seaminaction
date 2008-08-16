package org.open18.test;

import org.testng.annotations.Test;
import org.jboss.seam.mock.SeamTest;

public class ProfileActionTest extends SeamTest {

	@Test
	public void test() throws Exception {
		new FacesRequest() {
			@Override
			protected void invokeApplication() {
				//call action methods here
				invokeMethod("#{profileAction.view}");
			}
		}.run();
	}
}
