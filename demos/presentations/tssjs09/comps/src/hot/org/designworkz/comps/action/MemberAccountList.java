package org.designworkz.comps.action;

import org.designworkz.comps.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("memberAccountList")
public class MemberAccountList extends EntityQuery<MemberAccount> {

	private static final String EJBQL = "select memberAccount from MemberAccount memberAccount";

	private static final String[] RESTRICTIONS = {
			"lower(memberAccount.passwordHash) like lower(#{memberAccountList.memberAccount.passwordHash.concat('%')})",
			"lower(memberAccount.username) like lower(#{memberAccountList.memberAccount.username.concat('%')})",};

	private MemberAccount memberAccount = new MemberAccount();

	public MemberAccountList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public MemberAccount getMemberAccount() {
		return memberAccount;
	}
}
