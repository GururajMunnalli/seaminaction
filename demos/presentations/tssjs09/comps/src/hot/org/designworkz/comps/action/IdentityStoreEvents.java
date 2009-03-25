package org.designworkz.comps.action;

import org.designworkz.comps.model.MemberAccount;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.security.management.JpaIdentityStore;

@Name("identityStoreEvents")
public class IdentityStoreEvents {
	
	@Out(scope = ScopeType.SESSION) private MemberAccount authenticatedMember;
	
	/**
	 * Observes the post-authenticate event fired by JpaIdentityStore and
	 * saves the MemberAccount object into the session. Also initialized
	 * the collection of clients, which must be loaded to support various
	 * permission checks.
	 */
	@Observer(JpaIdentityStore.EVENT_USER_AUTHENTICATED)
	public void onLoginSuccess(MemberAccount member) {
		authenticatedMember = member;
		if (authenticatedMember.getCompany() != null) {
			authenticatedMember.getCompany().getClients().iterator();
		}
	}
}
