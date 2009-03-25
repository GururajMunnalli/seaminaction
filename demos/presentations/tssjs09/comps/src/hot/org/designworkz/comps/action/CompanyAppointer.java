package org.designworkz.comps.action;

import javax.persistence.EntityManager;

import org.designworkz.comps.model.MemberAccount;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.international.StatusMessages;

@Name("companyAppointer")
@Scope(ScopeType.CONVERSATION)
public class CompanyAppointer {

	@In	private EntityManager entityManager;
	
	@In private StatusMessages statusMessages;

	@Out private MemberAccount selectedAccount;

	@Begin
	public void edit(String username) {
		selectedAccount = (MemberAccount) entityManager.createQuery(
				"select m from MemberAccount m where m.username = :username")
				.setParameter("username", username).getSingleResult();
	}

	@End
	public void save() {
		statusMessages.add("Assignment saved.");
	}
}
