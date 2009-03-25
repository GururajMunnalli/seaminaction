package org.designworkz.comps.action;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.designworkz.comps.model.Design;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.security.Identity;
import org.jboss.seam.security.Role;
import org.jboss.seam.security.permission.Permission;
import org.jboss.seam.security.permission.PermissionManager;

@Name("designManager")
public class DesignManager {

	@In(required = false)
	private Design design;

	@In private EntityManager entityManager;

	@In	private Identity identity;
	
	@In	private StatusMessages statusMessages;
	
	@In private PermissionManager permissionManager;

	@In(create = true)
	private CompanyHome companyHome;

	public void upload() {
		design.setDateCreated(new Date());
		entityManager.persist(design);
		permissionManager.grantPermission(new Permission(design, "view", new Role("admin")));
		statusMessages.add("Design uploaded successfully.");
	}

	@SuppressWarnings("unchecked")
	@Factory("designs")
	public List<Design> loadDesignsForCompany() {
		if (companyHome.getDefinedInstance() == null) {
			return Collections.<Design>emptyList();
		}
		List<Design> designs = entityManager
			.createQuery("select d from Design d where d.company = #{companyHome.instance}")
			.getResultList();
		identity.filterByPermission(designs, "view");
		return designs;
	}
}
