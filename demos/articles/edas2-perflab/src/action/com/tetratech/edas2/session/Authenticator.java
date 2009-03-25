/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.session;

import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Redirect;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;

@Name("authenticator")
public class Authenticator {

	@Logger private Log log;
	@In protected Identity identity;
	@In protected FacesMessages facesMessages;
	@In protected Redirect redirect;

	public boolean authenticate() {
		String username = identity.getUsername();
		String password = identity.getPassword();
		log.debug("authenticating #0", username);

		if (username.equals("admin") && password.equals("admin")) {
			log.debug("logged in #0", username);
			identity.addRole("admin");
			return true;
		} else {
			log.info("login failed!");
			facesMessages.add(FacesMessage.SEVERITY_WARN, "Login failed, please try again!");
			return false;
		}
	}

	public boolean logout() {
		String username = identity.getUsername();
		log.debug("logged out #0", username);
		identity.logout();
		return true;
	}
	
	public void captureCurrentView() {
		redirect.captureCurrentView();
		redirect.getParameters().putAll(
			FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap());
	}
}
