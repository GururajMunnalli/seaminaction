package org.designworkz.comps.action;

import org.designworkz.comps.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("memberRoleList")
public class MemberRoleList extends EntityQuery<MemberRole> {

	private static final String EJBQL = "select memberRole from MemberRole memberRole";

	private static final String[] RESTRICTIONS = {"lower(memberRole.name) like lower(#{memberRoleList.memberRole.name.concat('%')})",};

	private MemberRole memberRole = new MemberRole();

	public MemberRoleList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public MemberRole getMemberRole() {
		return memberRole;
	}
}
