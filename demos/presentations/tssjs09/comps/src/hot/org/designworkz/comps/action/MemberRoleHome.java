package org.designworkz.comps.action;

import org.designworkz.comps.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("memberRoleHome")
public class MemberRoleHome extends EntityHome<MemberRole> {

	public void setMemberRoleId(Long id) {
		setId(id);
	}

	public Long getMemberRoleId() {
		return (Long) getId();
	}

	@Override
	protected MemberRole createInstance() {
		MemberRole memberRole = new MemberRole();
		return memberRole;
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

	public MemberRole getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
