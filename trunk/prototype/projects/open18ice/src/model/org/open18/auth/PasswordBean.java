package org.open18.auth;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.intercept.BypassInterceptors;

@Name("passwordBean")
@Scope(ScopeType.PAGE)
@BypassInterceptors
public class PasswordBean {
	private String password;
	private String confirm;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public boolean verify() {
		return confirm != null && confirm.equals(password);
	}

}
