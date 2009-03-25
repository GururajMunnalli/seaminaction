package org.designworkz.comps.action;

import org.designworkz.comps.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("memberAccountHome")
public class MemberAccountHome extends EntityHome<MemberAccount> {

	public void setMemberAccountId(Long id) {
		setId(id);
	}

	public Long getMemberAccountId() {
		return (Long) getId();
	}

	@Override
	protected MemberAccount createInstance() {
		MemberAccount memberAccount = new MemberAccount();
		return memberAccount;
	}

	public void load() {
		getDefinedInstance();
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public MemberAccount getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
