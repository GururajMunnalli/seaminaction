package org.designworkz.comps.action;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.international.StatusMessages;
import org.jboss.seam.security.Role;
import org.jboss.seam.security.SimplePrincipal;
import org.jboss.seam.security.permission.Permission;
import org.jboss.seam.security.permission.PermissionManager;

@Name("designPermission")
public class DesignPermission {

	private List<String> grantedRoles;
	private List<String> grantedMembers;
	private List<String> grantedActions;
	private boolean append;

	@In
	private DesignHome designHome;
	@In
	private PermissionManager permissionManager;
	@In
	private StatusMessages statusMessages;

	public String applyPermissions() {
		Object target = designHome.getDefinedInstance();

		if (!append) {
			List<Permission> currentPermissions = permissionManager.listPermissions(target);
			if (!currentPermissions.isEmpty()) {
				permissionManager.revokePermissions(currentPermissions);
			}
		}

		List<Permission> newPermissions = new ArrayList<Permission>();
		for (String role : grantedRoles) {
			Principal r = new Role(role);
			for (String action : grantedActions) {
				newPermissions.add(new Permission(target, action, r));
			}
		}

		for (String username : grantedMembers) {
			Principal p = new SimplePrincipal(username);
			for (String action : grantedActions) {
				newPermissions.add(new Permission(target, action, p));
			}
		}

		if (!newPermissions.isEmpty()) {
			permissionManager.grantPermissions(newPermissions);
		}

		statusMessages.add("The permissions have been saved.");
		return "success";
	}

	public void setGrantedRoles(List<String> grantedRoles) {
		this.grantedRoles = grantedRoles;
	}

	public List<String> getGrantedRoles() {
		return grantedRoles;
	}

	public void setGrantedMembers(List<String> grantedMembers) {
		this.grantedMembers = grantedMembers;
	}

	public List<String> getGrantedMembers() {
		return grantedMembers;
	}

	public void setGrantedActions(List<String> grantedActions) {
		this.grantedActions = grantedActions;
	}

	public List<String> getGrantedActions() {
		return grantedActions;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}

	public boolean isAppend() {
		return append;
	}
}
